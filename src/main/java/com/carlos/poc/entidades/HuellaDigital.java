package com.carlos.poc.entidades;

import java.sql.Blob;

import javax.persistence.*;

@Entity
@Table(name="huellaDigital")
public class HuellaDigital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idHuella", nullable = false)
	private long idHuella;
	
	@Column(name = "nombreHuella", nullable = false)
	private String nombreHuella;
	
	@Column(name = "huella", nullable = false)
	private Blob huella;



	public long getIdHuella() {
		return idHuella;
	}

	public void setIdHuella(long idHuella) {
		this.idHuella = idHuella;
	}

	public String getNombreHuella() {
		return nombreHuella;
	}

	public void setNombreHuella(String nombreHuella) {
		this.nombreHuella = nombreHuella;
	}

	public Blob getHuella() {
		return huella;
	}

	public void setHuella(Blob huella) {
		this.huella = huella;
	}

	@ManyToOne
	@JoinColumn(name="id", nullable = false)
	private Ciudadano ciudadano;

	public Ciudadano getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	public HuellaDigital() {
		super();
	}


	public HuellaDigital(long idHuella, String nombreHuella, Blob huella) {
		this.idHuella = idHuella;
		this.nombreHuella = nombreHuella;
		this.huella = huella;
	}

	public HuellaDigital(long idHuella, String nombreHuella) {
		this.idHuella = idHuella;
		this.nombreHuella = nombreHuella;
	}

	public HuellaDigital(String nombreHuella, Blob huella, Ciudadano ciudadano) {
		this.nombreHuella = nombreHuella;
		this.huella = huella;
		this.ciudadano = ciudadano;
	}

	public HuellaDigital(long idHuella, String nombreHuella, Blob huella, Ciudadano ciudadano) {
		this.idHuella = idHuella;
		this.nombreHuella = nombreHuella;
		this.huella = huella;
		this.ciudadano = ciudadano;
	}

	@Override
	public String toString() {
		return "HuellaDigital{" +
				"idHuella=" + idHuella +
				", nombreHuella='" + nombreHuella + '\'' +
				", huella=" + huella +
				'}';
	}
}
