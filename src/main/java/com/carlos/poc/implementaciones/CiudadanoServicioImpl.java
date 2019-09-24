package com.carlos.poc.implementaciones;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.carlos.poc.entidades.Ciudadano;
import com.carlos.poc.repositorio.CiudadanoRepositorio;
import com.carlos.poc.servicios.CiudadanoServicio;

@Service("CiudadanoServicio")
public class CiudadanoServicioImpl implements CiudadanoServicio {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("CiudadanoRepositorio")
	private CiudadanoRepositorio ciudadanoRepositorio;

	@Override
	public Ciudadano buscarCiudadano(boolean filtro, String dato) { // Filtro = boolean, Dato = correo ó id
		try {
			if (dato != null) {
				if (filtro) {
					// filtro = true : Busca por correo
					Ciudadano user = ciudadanoRepositorio.busquedaPorEmail(dato);
					if (dato.equals(user.getCorreoElectronico())) {
						return user;
					} else {
						log.info("No se encontro: " + dato);
					}
				} else {
					// filtro = false : Busca por id
					long id = Long.parseLong(dato);
					Ciudadano user = ciudadanoRepositorio.busquedaPorId(id);
					if (id == user.getIdCiudadano()) {
						return user;
					} else {
						log.info("No se encontro: " + id);
					}
				}
			}
			return null;
		} catch (Exception ex) {
			log.error("ERROR IMPL(buscarCiudadano): " + ex.getMessage());
			return null;
		}

	}

	@Override
	public boolean agregarCiudadano(Ciudadano ciudadano) {
		try {
			if (ciudadano != null) {
				ciudadanoRepositorio.save(ciudadano);
				log.info("se agrego el Ciudadano: " + ciudadano.toString());
				return true;
			} else {
				log.warn("El Ciudadano esta vacio");
				return false;
			}
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean actualizarCiudadano(Ciudadano ciudadano) {
		try {
			if (ciudadano != null) {
				ciudadanoRepositorio.save(ciudadano);
				log.info(("se actualizo el Ciudadano: " + ciudadano.toString()));
				return true;
			} else {
				log.warn("El ciudadano llega vacio");
				return false;
			}
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getMessage());
			return false;
		}
	}

	@Override
	public boolean borrarCiudadano(Ciudadano ciudadano) {
		try {
			if (ciudadano != null) {
				ciudadanoRepositorio.delete(ciudadano);
				log.info("Se elimino el Ciudadano");
				return true;
			}
			return false;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<Ciudadano> listarCiudadano() {
		try {
			return (List<Ciudadano>) ciudadanoRepositorio.findAll();
		} catch (Exception ex) {
			log.error(ex.getMessage());
			return null;
		}
	}

	@Override
	public void enviarMail(Ciudadano ciudadano, String tramite, String accion) {
		String correoEmisor = "usuario.prueba.1.2.3.409@gmail.com";
		String PasswordCorreoEmisor = "Usuario1234";
		String asunto = "Aviso sobre su Tratime";
		String correoReceptor = ciudadano.getCorreoElectronico();
		String mensaje = "";
		log.info("\n\nCorreo receptor: " + correoReceptor + "\n\n");
		switch (tramite) {
		case "huella":
			switch (accion) {
			case "agregar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su huella se registro correctamente!\n</h3>";
				break;
			case "actualizar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su huella se actualizo correctamente!\n</h3>";
				break;
			case "eliminar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su huella se elimino correctamente!\n</h3>";
				break;
			}
			break;
		case "fotografia":
			switch (accion) {
			case "agregar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su fotografia se registro correctamente!\n</h3>";
				break;
			case "actualizar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su fotografia se actualizo correctamente!\n</h3>";
				break;
			case "eliminar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su fotografia se elimino correctamente!\n</h3>";
				break;
			}
			break;
		case "documento":
			switch (accion) {
			case "agregar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su documento se registro correctamente!\n</h3>";
				break;
			case "actualizar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su documento se actualizo correctamente!\n</h3>";
				break;
			case "eliminar":
				mensaje = "<h3>Hola " + ciudadano.getNombre() + " " + ciudadano.getApellidoPaterno() + " "
						+ ciudadano.getApellidoMaterno() + ",\n"
						+ "<br>Le informamos que su documento se elimino correctamente!\n</h3>";
				break;
			}
			break;
		}

		Properties props = new Properties();// Nota: algunas veces el mail no se envia por el firewall de la red
		// Activar "aplicacion menos segura" desde gmail para que se puedan enviar
		// correos desde aplicaciones de terceros
		// props.put("mail.smtp.host", "smtp.gmail.com");// indica que se utilizara el
		// servidor smtp de gmail
		props.put("mail.smtp.host", "smtp.sparkpostmail.com");// indica que se utilizara el servidor smtp de gmail
		props.put("mail.smtp.port", "587");// puerto en el que gmail permite el envio de mails desde aplicaciones de
											// terceros
		props.put("mail.smtp.starttls.enable", "true");// habilita el uso del STARTTLScomando para cambiar la conexión a
														// una conexión protegida por TLS antes de emitir comandos de
														// inicio de sesión

		Session session = Session.getInstance(props, null);

		try {
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(correoEmisor);
			msg.setRecipients(Message.RecipientType.TO, correoReceptor);
			msg.setSubject(asunto);
			msg.setSentDate(new Date());
			msg.setContent(mensaje, "text/html");
			// msg.setText(mensaje);
			Transport.send(msg, correoEmisor, PasswordCorreoEmisor);
			msg = null;
		} catch (MessagingException mex) {
			log.error(";send failed, exception: " + mex);
		} finally {
			session = null;
			// session.close();
		}
	}
}
