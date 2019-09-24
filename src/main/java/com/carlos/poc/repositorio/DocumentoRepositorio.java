package com.carlos.poc.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.poc.entidades.Documento;

@Repository("DocumentoRepositorio")
public interface DocumentoRepositorio extends CrudRepository<Documento, Serializable>{
	List<Documento> findByCiudadanoId(long id);
}
