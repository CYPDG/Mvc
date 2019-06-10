package com.spring.mvc;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.spring.mvc.modelo.*;

public class Conector {
	private LSistemas SysList;

	public Conector() {
		SysList = new LSistemas();
	}

	public void Consultar(Consulta C) {

		int SysIndex = this.identificarSistema(C.getSistema());

		String url = SysList.getListaSistemas().get(SysIndex).getURL();
		String SisID = SysList.getListaSistemas().get(SysIndex).getSystemId();
		String user = SysList.getListaSistemas().get(SysIndex).getUser();
		String pass = SysList.getListaSistemas().get(SysIndex).getPass();

		try {
			Class.forName("com.sybase.jdbc3.jdbc.SybDriver");
			// Class.forName("oracle.jdbc.driver.OracleDriver");
			// Class.forName("com.mysql.jdbc.Driver");
			// Connection con =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo", "root",
			// "root");
			// url = "jdbc:mysql://" + url + "/" + SisID;
			// url = "jdbc:oracle:thin:@" + url + "/" + SisID;
			url = "jdbc:sybase:Tds:" + url + "/" + SisID;

			System.out.println(url);

			Connection con = DriverManager.getConnection(url, user, pass);
			// here sonoo is database name, root is username and password
			System.out.println("la ruta de conexion es: " + url + "; " + user + "; " + pass);
			System.out.println(
					"la consulta es: " + C.getSqlQuerry() + " con parametros: " + C.getParameters().toString());
			PreparedStatement pStmt;
			// pStmt = con.prepareStatement(C.getSqlQuerry());
			pStmt = con.prepareStatement(C.getSqlQuerry());
			// System.out.println(pStmt);
			/*
			 * 
			 * Aqui falta la configuracion del PreparedStatement....
			 * 
			 */

			ResultSet rs = pStmt.executeQuery();
			while (rs.next())

				System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
			/*
			 * for(int i =0; i<data.size(); i++) { String aux =""; for(int j =0;
			 * j<data.get(i).size(); j++) { aux += data.get(i).get(j)+ ": _ :"; }
			 * System.out.println(aux); }
			 */

			con.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Se ha producido un error en la conexion");
			System.out.println(e);
		}
	}

	@SuppressWarnings("rawtypes")
	public ArrayList ConsultarResulset(Consulta C) {

		int SysIndex = this.identificarSistema(C.getSistema());

		String url = SysList.getListaSistemas().get(SysIndex).getURL();
		String SisID = SysList.getListaSistemas().get(SysIndex).getSystemId();
		String user = SysList.getListaSistemas().get(SysIndex).getUser();
		String pass = SysList.getListaSistemas().get(SysIndex).getPass();

		String[] driverClass = { "oracle.jdbc.driver.OracleDriver", "com.sybase.jdbc3.jdbc.SybDriver" };
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

		switch (SisID) {

		case "maexe":
			try {
				url = manager(C, url, SisID, user, pass, data, driverClass[0]);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Se ha producido un error al intentar acceder a maexe");
				System.out.println(e);
			}

			break;

		case "vantive":

			try {
				url = manager(C, url, SisID, user, pass, data, driverClass[1]);

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Se ha producido un error al intentar acceder a Vantive");
				System.out.println(e);
			}

			break;
		default:
			break;
		}

		return data;
	}

	private String manager(Consulta C, String url, String SisID, String user, String pass,
			ArrayList<ArrayList<String>> data, String driverClass) throws ClassNotFoundException, SQLException {

		Class.forName(driverClass);

		if (SisID.equals("maexe")) {

			url = "jdbc:oracle:thin:@" + url + "/" + SisID;

		} else {

			url = "jdbc:sybase:Tds:" + url + "/" + SisID;
		}

		// url = (SisID == "maexe") ? "jdbc:oracle:thin:@" + url + "/" + SisID :
		// "jdbc:sybase:Tds:" + url + "/" + SisID;

		Connection con = DriverManager.getConnection(url, user, pass);
		PreparedStatement pStmt;
		pStmt = con.prepareStatement(C.getSqlQuerry());

		System.out.println(C.getSqlQuerry());
		ResultSet rs = pStmt.executeQuery();

		if (rs != null) {
			
			int cuanto = ((java.sql.ResultSetMetaData) rs.getMetaData()).getColumnCount();
			ArrayList<String> columnas = new ArrayList<String>();
			ResultSetMetaData columns = rs.getMetaData();
			int i = 0;
			while (i < cuanto) {
				i++;
				columnas.add(columns.getColumnName(i));
			}
			data.add(columnas);

		}

		while (rs.next()) {

			ArrayList<String> tokens = new ArrayList<String>();
			int cuantos = ((java.sql.ResultSetMetaData) rs.getMetaData()).getColumnCount();

			for (int j = 1; j <= cuantos; j++) {

				tokens.add(rs.getString(j));
			}

			data.add(tokens);
		}
		
		System.out.println(data.size());
		
		for(int i =0; i<data.size(); i++) {
			String aux ="";
			for(int j =0; j<data.get(i).size(); j++) {
				aux += data.get(i).get(j)+ ": _ :";					
			}
			System.out.println(aux);
			
		}

		return url;
	}

	
	protected int identificarSistema(String sistema) {
		// devuelve la posicion del sistema en la lista de sistemas
		int sysPos = 0;

		for (int i = 0; i < SysList.getListaSistemas().size(); i++) {
			Sistema sis = SysList.getListaSistemas().get(i);

			if (sistema.equals(sis.getSistema())) {

				sysPos = i;

			}

		}

		return sysPos;
	}

	public LSistemas getLSistemas() {
		return SysList;
	}
}
