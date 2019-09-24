package com.carlos.poc.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "documento")
public class Documento {

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

	@Column(name = "fecha_subido", nullable = true, updatable= false, insertable=false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private Date fechaSubido;

	@Column(name = "fecha_actualizado",  nullable = true, updatable= false, insertable=false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private Date fechaActualizado;

	@ManyToOne()
	@JoinColumn(name = "ciudadano_id")
	private Ciudadano ciudadano;

	public Documento() {
	}

	public Documento(@NotNull String nombre, @NotNull String path, Ciudadano ciudadano) {
		this.nombre = nombre;
		this.path = path;
		this.ciudadano = ciudadano;
	}

	public Documento(@NotNull String nombre, @NotNull String path, @NotNull char status) {
		super();
		this.nombre = nombre;
		this.path = path;
		this.status = status;
	}

	public Documento(@NotNull String nombre, @NotNull String path, @NotNull char status, Ciudadano ciudadano) {
		super();
		this.nombre = nombre;
		this.path = path;
		this.status = status;
		this.ciudadano = ciudadano;
	}

	public Documento(@NotNull String nombre, @NotNull String path, @NotNull char status, Date fechaSubido,
			Date fechaActualizado, Ciudadano ciudadano) {
		super();
		this.nombre = nombre;
		this.path = path;
		this.status = status;
		this.fechaSubido = fechaSubido;
		this.fechaActualizado = fechaActualizado;
		this.ciudadano = ciudadano;
	}

	@Override
	public String toString() {
		return "Documento [id=" + id + ", nombre=" + nombre + ", path=" + path + ", status=" + status + ", fechaSubido="
				+ fechaSubido + ", fechaActualizado=" + fechaActualizado + "]";
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

	public void setStatus(char status) {
		this.status = status;
	}

	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public Date getFechaSubido() {
		return fechaSubido;
	}

	public void setFechaSubido(Date fechaSubido) {
		this.fechaSubido = fechaSubido;
	}

	public Date getFechaActualizado() {
		return fechaActualizado;
	}

	public void setFechaActualizado(Date fechaActualizado) {
		this.fechaActualizado = fechaActualizado;
	}
}