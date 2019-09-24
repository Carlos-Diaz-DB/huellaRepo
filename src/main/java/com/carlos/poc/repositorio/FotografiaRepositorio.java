package com.carlos.poc.repositorio;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.carlos.poc.entidades.Fotografia;

@Repository("FotografiaRepositorio")
public interface FotografiaRepositorio extends CrudRepository<Fotografia, Serializable> {
	List <Fotografia> findByCiudadano_Id(long id);
}
