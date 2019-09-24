package com.carlos.poc.controladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.carlos.poc.entidades.Documento;
import com.carlos.poc.servicios.DocumentoServicio;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(path = "/")
public class DocumentoControlador {

	@Autowired
	@Qualifier("DocumentoServicio")
	DocumentoServicio documentoServicio;

	@Value("${file.upload-docs}")
	private String pathServer;
	
	private static final Logger log = LoggerFactory.getLogger(DocumentoControlador.class);
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
	
//	Para ver todos los documentos en el servidor
	@RequestMapping(path = "documentos", method = RequestMethod.GET)
	public @ResponseBody List<Documento> listaDocumentos() {
		try {
			return documentoServicio.listar();
		} catch (Exception ex) {
			log.error("=====\nError Ctrl:(listaDocumentos):\n" + ex.getMessage()+"\n");
			return null;
		}
	}
//	Para ver los documentos de un ciudadano en especifico
	@RequestMapping(path = "documentosUser", method = RequestMethod.GET)
	public @ResponseBody List<Documento> listarDocumentosDeUsuario(@RequestParam(value="id") int idUser) {
		try {
			return documentoServicio.listarDocumentosDeUser(idUser);
		} catch (Exception ex) {
			log.error("=====\nError Ctrl:(listarDocumentosDeUsuario):\n" + ex.getMessage()+"\n");
			return null;
		}
	}

	@RequestMapping(path = "/files/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(@PathVariable String filename) {
		try {
			File file = new File(pathServer + File.separator + filename);
			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=filename.pdf");
			header.add("Cache-Control", "no-cache, no-store, must-revalidate");
			header.add("Pragma", "no-cache");
			header.add("Expires", "0");

			Path path = Paths.get(file.getAbsolutePath());
			ByteArrayResource resource = null;
			resource = new ByteArrayResource(Files.readAllBytes(path));
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment;filename=" + file.getName())
					.contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
					.body(resource);
		} catch (IOException e) {
			log.error("Error(download):" + e.getMessage());
		}
		return null;
	}

//	Para guardar un documento en la carpeta del server de un ciudadano en especifico
	@RequestMapping(path = "documentoPDF", method = RequestMethod.POST)
	public @ResponseBody boolean registrarDocumentoPDF(@RequestBody String docBASE64) {
		try {
//			Documento doc = mapper.readValue(docJSON, Documento.class);
//			return documentoServicio.agregar(doc);
//			log.info("D: \n\n"+docBASE64);
			return documentoServicio.guardarFile(docBASE64);
		} catch (Exception ex) {
			log.error("=====\nError Ctrl:(registrarDocumentoPDF):\n" + ex.getMessage()+"\n");
			return false;
		}
	}
	@RequestMapping(path = "documentos", method = RequestMethod.POST)
	public @ResponseBody boolean registrarDocumentoDB(@RequestBody String docJSON) {
		try {
			Documento doc = mapper.readValue(docJSON, Documento.class);
			return documentoServicio.agregar(doc);
		} catch (Exception ex) {
			log.error("=====\nError Ctrl:(registrarDocumentoDB):\n" + ex.getMessage()+"\n");
			return false;
		}
	}
//	Actualiza la info de un documento de un ciudadano en especifico
	@RequestMapping(path = "documentos", method = RequestMethod.PUT)
	public @ResponseBody boolean actualizarDocumento(@RequestBody String docJSON) {
		try {
			Documento doc = null;
			boolean editar;
			log.info("docJSON: "+docJSON);
			log.info("A: "+docJSON.contains("accion"));
			if (docJSON.contains("accion")){
				//Si tiene accion es porque solo se esta editando el estado...
				docJSON = docJSON.substring(0, docJSON.length()-19)+"}";
				doc = mapper.readValue(docJSON, Documento.class);
				editar = true;
			}else{
				doc = mapper.readValue(docJSON, Documento.class);
				editar = false;
			}
			return documentoServicio.actualizar(doc, editar);
		} catch (Exception ex) {
			log.error("=====\nError Ctrl:(actualizarDocumento):\n" + ex.getMessage()+"\n");
			return false;
		}
	}
//	Borra un documento en de un ciudadano en especifico
	@RequestMapping(path = "documentos", method = RequestMethod.DELETE)
	public @ResponseBody boolean borrarDocumento(@RequestBody String docJSON) {
		try {
			if (docJSON != null) {
				Documento doc = mapper.readValue(docJSON, Documento.class);
				return documentoServicio.borrar(doc);
			}
			return false;
		} catch (Exception ex) {
			log.error("=====\nError Ctrl:(borrarDocumento):\n" + ex.getMessage()+"\n");
			return false;
		}
	}
}
