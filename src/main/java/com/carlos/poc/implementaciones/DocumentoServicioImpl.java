package com.carlos.poc.implementaciones;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.carlos.poc.controladores.DocumentoControlador;
import com.carlos.poc.entidades.Documento;
import com.carlos.poc.repositorio.DocumentoRepositorio;
import com.carlos.poc.servicios.CiudadanoServicio;
import com.carlos.poc.servicios.DocumentoServicio;

@Service("DocumentoServicio")
public class DocumentoServicioImpl implements DocumentoServicio {

    @Autowired
    public DocumentoRepositorio documentoRepositorio;
    
    @Autowired
	@Qualifier("CiudadanoServicio")
	private CiudadanoServicio ciudadanoServicio;
    private static final Logger log = LoggerFactory.getLogger(DocumentoControlador.class);

    @Value("${file.upload-docs}")
    private String pathServer;

    @Override
    public Documento obtenerPorId(int docID) {
        try {
            if (docID > 0) {
                return documentoRepositorio.findById(docID).get();
            }
            return null;
        } catch (Exception ex) {
            log.error("Error(obtenerPorId):" + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Documento> listar() {
        try {
            return (List<Documento>) documentoRepositorio.findAll();
        } catch (Exception ex) {
            log.error("Error(listar):" + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Documento> listarDocumentosDeUser(int userId) {
        try {
            List<Documento> aux = documentoRepositorio.findByCiudadanoId(userId);
//			log.info("\nLista de documento del ciudadano con ID: "+userId+"\n"+aux+"\n");
            for (Documento docs : aux) {
                File archivo = new File(docs.getPath());
                if (!archivo.exists()) {
//					Si el archivo no existe, lo marca como desactivado
                    docs.setStatus('0');
                }
            }
            return (List<Documento>) documentoRepositorio.findByCiudadanoId(userId);
        } catch (Exception ex) {
            log.error("Error(listarDocumentosDeUser):" + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean agregar(Documento doc) {
        try {
            if (doc != null) {
                doc.setPath(pathServer + "/" + doc.getNombre());
                log.info("path: " + doc.getPath());
                documentoRepositorio.save(doc);
                ciudadanoServicio.enviarMail(doc.getCiudadano(), "documento", "agregar");
                return true;
            }
        } catch (Exception ex) {
            log.error("Error(agregar):" + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean guardarFile(String doc) {
        String[] base64 = doc.split("base64,");
        try {
        	File root = new File(pathServer);
        	if(!root.exists()) {
        		root.mkdir();
        	}
            byte[] decodedBytes = Base64.getDecoder().decode(base64[1]);
            FileUtils.writeByteArrayToFile(new File(pathServer + "/" + base64[2] + ".pdf"), decodedBytes);
            return true;
        } catch (IOException e) {
            log.error("ERROR (guardarFile): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Documento doc, boolean editar) {
        try {
            if (doc != null) {
                if (editar) {
                    //Editar solo el estado
                    documentoRepositorio.save(doc);
                    ciudadanoServicio.enviarMail(doc.getCiudadano(), "documento", "actualizar");
                } else {
                    //Hacer borrado logico
                    File original = new File(doc.getPath());
                    File renombrado = new File(pathServer + "/Borrado_" + doc.getNombre());
                    if (original.renameTo(renombrado)) {
                        doc.setStatus('0');
                        documentoRepositorio.save(doc);
                    }
                    ciudadanoServicio.enviarMail(doc.getCiudadano(), "documento", "eliminar");
                }
                    return true;
                }else{
                    log.error("ERROR: No se pudo renombrar " + doc.getPath());
                    return false;
                }
            }catch (Exception ex) {
            log.error("Error(actualizar):" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean borrar(Documento doc) {
        try {
            if (doc != null) {
                documentoRepositorio.delete(doc);
                ciudadanoServicio.enviarMail(doc.getCiudadano(), "documento", "eliminar");
                return true;
            }
            return false;
        } catch (Exception ex) {
            log.error("Error(borrar):" + ex.getMessage());
            return false;
        }
    }
}
