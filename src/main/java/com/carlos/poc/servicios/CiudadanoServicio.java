package com.carlos.poc.servicios;

import java.util.List;

import com.carlos.poc.entidades.Ciudadano;

public interface CiudadanoServicio {
	public Ciudadano buscarCiudadano(boolean filtro, String ciudadano);

	public boolean agregarCiudadano(Ciudadano ciudadano);

	public boolean actualizarCiudadano(Ciudadano ciudadano);

	public boolean borrarCiudadano(Ciudadano ciudadano);

	public List<Ciudadano> listarCiudadano();

	public void enviarMail(Ciudadano ciudadano, String tramite, String accion);
}
