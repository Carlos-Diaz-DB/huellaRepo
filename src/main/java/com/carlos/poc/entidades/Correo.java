package com.carlos.poc.entidades;

public class Correo {
	private String ciudadano;
	private int veces;

	public Correo() {
	}

	public Correo(String ciudadano, int veces) {
		super();
		this.ciudadano = ciudadano;
		this.veces = veces;
	}

	public String getCiudadano() {
		return ciudadano;
	}

	public void setCiudadano(String ciudadano) {
		this.ciudadano = ciudadano;
	}

	public int getVeces() {
		return veces;
	}

	public void setVeces(int veces) {
		this.veces = veces;
	}

	@Override
	public String toString() {
		return "Correo [ciudadano=" + ciudadano + ", veces=" + veces + "]";
	}
}
