package com.carlos.poc.repositorio;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.carlos.poc.entidades.Ciudadano;

@Repository("CiudadanoRepositorio")
public interface CiudadanoRepositorio extends CrudRepository<Ciudadano, Serializable> {
	@Query(value = "SELECT * FROM ciudadano WHERE id = :id", nativeQuery = true)
    public Ciudadano busquedaPorId(@Param("id") long id);
	
	@Query(value = "SELECT * FROM ciudadano WHERE correo_electronico = :correo_electronico", nativeQuery = true)
    public Ciudadano busquedaPorEmail(@Param("correo_electronico") String correo);
}
