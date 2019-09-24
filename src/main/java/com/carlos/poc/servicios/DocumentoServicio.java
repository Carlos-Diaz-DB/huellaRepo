package com.carlos.poc.servicios;

import java.util.List;

import com.carlos.poc.entidades.Documento;

public interface DocumentoServicio {
	public Documento obtenerPorId(int doc);

	public List<Documento> listarDocumentosDeUser(int userId);
	
	public List<Documento> listar();

	public boolean agregar(Documento doc);

	public boolean guardarFile(String doc);

	public boolean actualizar(Documento doc, boolean editar);

	public boolean borrar(Documento doc);
}
