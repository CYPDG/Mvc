package com.spring.mvc.modelo;

import java.util.ArrayList;

public class Consulta {
	private String sistema;
	private String consulta;
	private String sqlQuerry;
	private ArrayList<String> parameters;

	public Consulta() {

	}

	public Consulta(String rddLine) {
		String[] partes = rddLine.split(";");
		parameters = new ArrayList<String>();
		this.sistema = partes[0];
		this.consulta = partes[1];
		this.sqlQuerry = partes[2];
		for (int i=3; i<partes.length; i++)	this.parameters.add(partes[i]);
		
	}

	public void addParameters(String pPar) {
		this.parameters.add(pPar);
	}

	public String getSistema() {
		return sistema;
	}

	public String getConsulta() {
		return consulta;
	}

	public String getSqlQuerry() {
		return sqlQuerry;
	}

	public ArrayList<String> getParameters() {
		return parameters;
	}

}
