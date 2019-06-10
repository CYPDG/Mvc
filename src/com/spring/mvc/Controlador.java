package com.spring.mvc;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.spring.mvc.modelo.*;


public class Controlador {
	
	private ArrayList<Consulta> Consultas;
	private Conector Conn;
	private static int cont =0;



	public Controlador() {
		
		
		if(cont == 0 || cont == 1) {
		Consultas = new ArrayList<Consulta>();
		cont++;
		this.leer();
		Conn = new Conector();
//		Conn.Consultar(Consultas.get(0));
		}
		else if(cont != 0) 
		{
			Consultas = new ArrayList<Consulta>();
			cont++;
			this.leer();
			Conn = new Conector();
			
		}
	}
	
	public Controlador(String sistema,String consulta) {
		
		if(cont == 0 || cont == 1) {
		Consultas = new ArrayList<Consulta>();
		cont++;
		this.leer();
		Conn = new Conector();
		//Conn.Consultar(Consultas.get(0));
		}
	}


	public void actualizar(String ConsultaSeleccionada) {
		
		Conn.Consultar(Consultas.get(0));

	}

	
	public static int getCount() {
		return cont;
	}

	private  void leer() {
		File ConfigF = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			ConfigF = new File("C:\\ConsultasFile.txt");
			fr = new FileReader(ConfigF);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while ((linea = br.readLine()) != null) {
				Consultas.add(new Consulta(linea));
			}
		} catch (Exception e) {
			//System.out.println("aqyjasdksa!!!!!!!"+e);
			e.getMessage();
			e.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	
	public ArrayList<Consulta> getConsultas() {
		return this.Consultas;
	}
	
	public Conector getConector() {
		return Conn;
	}
}
