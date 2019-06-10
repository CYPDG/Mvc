package com.spring.mvc;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.mvc.modelo.Consulta;

@Controller
public class SistemasController {

	@RequestMapping("/muestraSistemas")
	public String muestraSistemas()// controlador que se encarga de mostrar los sistemas
	{
		return "sistemas";
	}

	@RequestMapping("/procesarSistema")
	public String procesarSistema(@RequestParam("sistema") String a, @RequestParam("consulta") String consulta,ModelMap model) throws SQLException,IOException,NullPointerException{
		
		Controlador cont = new Controlador();
		
		
		int posicion = 0;
		for(int i=0;i< cont.getConsultas().size();i++  ) 
		{
			
			Consulta consul = cont.getConsultas().get(i);	
			if(consulta.equals(consul.getConsulta())) 
			{
				posicion = i;
			}
			
		}
		
		Conector conec = new Conector();		

		ArrayList<ArrayList<String>> lista = conec.ConsultarResulset(cont.getConsultas().get(posicion));
		

		ArrayList<ArrayList<String>> columnas = new ArrayList<ArrayList<String>>();
		columnas.add(lista.get(0));
		
		ArrayList<ArrayList<String>> filas = new ArrayList<ArrayList<String>>();
		
		for(int j =1;j < lista.size();j++) 
		{
			
			filas.add(lista.get(j));
		}
		
		
		model.addAttribute("columnas",columnas);

		model.addAttribute("filas",filas);

		
		return "lista";
	}
	
	
	
	

	@ExceptionHandler({ IOException.class, java.sql.SQLException.class, java.lang.NullPointerException.class })
	public ModelAndView handleIOException(Exception ex) {
		ModelAndView model = new ModelAndView("IOError");

		model.addObject("exception", ex.getMessage());

		return model;
	}
}
