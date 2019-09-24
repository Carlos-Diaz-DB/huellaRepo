package com.carlos.poc.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.poc.entidades.HuellaDigital;

@Repository("HuellaRepositorio")
public interface HuellaRepositorio extends CrudRepository<HuellaDigital, Serializable>{
	List<HuellaDigital> findAllByCiudadano(int idCiudadnano);
}
