package com.spring.mvc.modelo;

public class Sistema {

	private String sistema;
	private String tipo;
	private String URL;
	private String SystemID;
	private String user;
	private String pass;

	public Sistema(String rddLine) {
		String[] partes = rddLine.split(";");

		this.sistema = partes[0];
		this.tipo = partes[1];
		this.URL = partes[2];
		this.SystemID = partes[3];
		this.user = partes[4];
		this.pass = partes[5];
		
		System.out.println(partes.length);

	}

	public Sistema() {

	}

	public String getSystemId() {
		return this.SystemID;
	}

	public String getSistema() {
		return sistema;
	}

	public String getTipo() {
		return tipo;
	}

	public String getURL() {
		return URL;
	}

	public String getUser() {
		return user;
	}

	public String getPass() {
		return pass;
	}

}
