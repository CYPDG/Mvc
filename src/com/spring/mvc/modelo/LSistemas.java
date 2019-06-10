package com.spring.mvc.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public class LSistemas {
	private  ArrayList<Sistema> ListaSistemas;
	//para modificar la ruta del fichero de configuracion de sistemas
	private  String RutaDelFicheroConfig = "C:\\SistemasConfig.txt";

	public  LSistemas() {
		ListaSistemas = new ArrayList<Sistema>();
		leer();
	}

	private void leer() {
		File ConfigF = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			ConfigF = new File(RutaDelFicheroConfig);
			fr = new FileReader(ConfigF);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			while ((linea = br.readLine()) != null) {

				ListaSistemas.add(new Sistema(linea));
			System.out.println(linea);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ha ocurrido un error en el sistema");
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



	public  ArrayList<Sistema> getListaSistemas() {
		return ListaSistemas;
	}
}
