package com.carlos.poc.servicios;

import com.carlos.poc.entidades.Fotografia;

import java.io.IOException;
import java.util.List;

public interface FotografiaServicio {
	public Fotografia getFotografia(int fotografiaID);
	public List<Fotografia> listaById(long id);
	public List<Fotografia> listarFotografias();
	public boolean agregarFotografia(Fotografia fotografia);
    public boolean actualizarFotografia(Fotografia fotografia);
    public boolean eliminarFotografia(Fotografia fotografia);
    
    public boolean escribirFotografia(String foto) throws IOException;
}
