package com.carlos.poc.implementaciones;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.carlos.poc.entidades.Fotografia;
import com.carlos.poc.repositorio.FotografiaRepositorio;
import com.carlos.poc.servicios.CiudadanoServicio;
import com.carlos.poc.servicios.FotografiaServicio;

import sun.misc.BASE64Decoder;

@Service("FotografiaServicio")
public class FotografiaServicioImpl implements FotografiaServicio {

	@Autowired
	public FotografiaRepositorio fotografiaRepositorio;
	
    @Autowired
	@Qualifier("CiudadanoServicio")
	private CiudadanoServicio ciudadanoServicio;


	@Value("${file.upload-foto}")
	private String pathFoto;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Fotografia getFotografia(int fotografiaID) {
		try {
			if (fotografiaID > 0) {
				return fotografiaRepositorio.findById(fotografiaID).get();
			}
			return null;
		} catch (Exception ex) {
			log.error("ERROR: getFotografia" + ex.getMessage());
			return null;
		}
	}

	@Override
	public List<Fotografia> listaById(long id) {
		try {
			return (List<Fotografia>) fotografiaRepositorio.findByCiudadano_Id(id);
		} catch (Exception ex) {
			log.error("ERROR: listaById" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<Fotografia> listarFotografias() {
		try {
			return (List<Fotografia>) fotografiaRepositorio.findAll();
		} catch (Exception ex) {
			log.error("Error: listarFotografias" + ex.getMessage());
		}
		return null;
	}

	@Override
	public boolean escribirFotografia(String fotoEncoded) throws IOException {
		String nameImage = fotoEncoded.split(",")[0];
		String base64Image = fotoEncoded.split(",")[1];
		String path = pathFoto + nameImage + ".png";

		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodedBytes = decoder.decodeBuffer(base64Image);

			BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
			if (image == null) {
				log.error("Buffered Image is null");
			}
			File f = new File(path);

			// write the image
			ImageIO.write(image, "png", f);
			log.info(f.toString());

			return true;
		} catch(Exception ex) {

			return false;
		}
	}

	@Override
	public boolean agregarFotografia(Fotografia fotografia) {
		log.info("agregar fotografi implem");

		try {
			if (fotografia != null) {
				
				fotografia.setPath(pathFoto);
				log.info("path: " + fotografia.getPath());
				fotografiaRepositorio.save(fotografia);
				ciudadanoServicio.enviarMail(fotografia.getCiudadano(), "fotografia", "agregar");

				return true;
			}
		} catch (Exception ex) {
			log.error("Error(agregar):" + ex.getMessage());
		}
		return false;
	}


	@Override
	public boolean actualizarFotografia(Fotografia fotografia) {
		try {
			if (fotografia != null) {
				fotografiaRepositorio.save(fotografia);
				ciudadanoServicio.enviarMail(fotografia.getCiudadano(), "fotografia", "actualizar");
				log.info("Se actualizo la fotografia: " + fotografia.toString());
				return true;
			}
			log.warn("Fotografia vacia");
			return false;
		} catch (Exception ex) {
			log.error("ERROR: actualizarFotografia" + ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean eliminarFotografia(Fotografia fotografia) {
		try {
			if (fotografia != null) {
				fotografiaRepositorio.delete(fotografia);
				ciudadanoServicio.enviarMail(fotografia.getCiudadano(), "fotografia", "eliminar");
				log.info("Se borro la fotografia correctamente");
				return true;
			}
			log.warn("Fotografia vacia");
			return false;
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getMessage());
			return false;
		}
	}
}
