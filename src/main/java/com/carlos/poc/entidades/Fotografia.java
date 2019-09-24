package com.carlos.poc.entidades;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "fotografia")
public class Fotografia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column
	private String nombre;

	@NotNull
	@Column
	private String path;

	@NotNull
	@Column(columnDefinition = "TINYINT")
	private char status;
	
//	@NotNull
//	@Column(name = "fecha_creacion", nullable=true, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@Column(name = "fecha_creacion", updatable=false)
	@CreationTimestamp
	private LocalDateTime fechaCreacion;

//	@NotNull
	@Column(name = "fecha_actualizacion")
//	@Column(name = "fecha_actualizacion", nullable=true, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private LocalDateTime fechaActualizacion;

	@ManyToOne
	@JoinColumn(name = "ID_CIUDADANO")
	private Ciudadano ciudadano;

	public Fotografia() {
	}
	
	

	public Fotografia(@NotNull String nombre, @NotNull String path, @NotNull char status, Ciudadano ciudadano) {
		super();
		this.nombre = nombre;
		this.path = path;
		this.status = status;
		this.ciudadano = ciudadano;
	}



	public Fotografia(@NotNull String nombre, @NotNull String path, @NotNull char status,
			@NotNull LocalDateTime fechaCreacion, @NotNull LocalDateTime fechaActualizacion, Ciudadano ciudadano) {
		super();
		this.nombre = nombre;
		this.path = path;
		this.status = status;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.ciudadano = ciudadano;
	}



	public Fotografia(int id, @NotNull String nombre, @NotNull String path, @NotNull char status,
			@NotNull LocalDateTime fechaCreacion, @NotNull LocalDateTime fechaActualizacion, Ciudadano ciudadano) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.path = path;
		this.status = status;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.ciudadano = ciudadano;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public char getStatus() {
		return status;
	}

	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Override
	public String toString() {
		return "Fotografia [id=" + id + ", nombre=" + nombre + ", path=" + path + ", status=" + status
				+ ", fechaCreacion=" + fechaCreacion + ", fechaActualizacion=" + fechaActualizacion + ", ciudadano="
				+ ciudadano + "]";
	}

	
}
