<%@page import="com.spring.mvc.modelo.Consulta"%>
<%@page import="com.spring.mvc.modelo.LSistemas"%>
<%@page import="com.spring.mvc.modelo.Sistema"%>
<%@page import="com.spring.mvc.Controlador"%>
<%@page import="com.spring.mvc.Conector"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%!Controlador Control = new Controlador();%>
<% 

if(Control== null)
{
	Control = new Controlador();
}
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sistemas</title>

<link rel="stylesheet"
	href="<%=request.getRequestURL().substring(0, 21)%>/Mvc/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="<%=request.getRequestURL().substring(0, 21)%>/Mvc/css/datatables.min.css" />
<link rel="stylesheet"
	href="<%=request.getRequestURL().substring(0, 21)%>/Mvc/css/index.css" />
<script
	src="<%=request.getRequestURL().substring(0, 21)%>/Mvc/js/jquery-3.3.1.slim.min.js"></script>
<script
	src="<%=request.getRequestURL().substring(0, 21)%>/Mvc/js/popper.min.js"></script>
<script
	src="<%=request.getRequestURL().substring(0, 21)%>/Mvc/js/bootstrap.min.js"></script>
<script
	src="<%=request.getRequestURL().substring(0, 21)%>/Mvc/js/datatables.min.js"></script>
<script
	src="<%=request.getRequestURL().substring(0, 21)%>/Mvc/js/moment.min.js"></script>
<script
	src="<%=request.getRequestURL().substring(0, 21)%>/Mvc/js/index.js"></script>
</head>
<body>
	<div>
		<span class="input-group-prepend"> <img
			src="<%=request.getRequestURL().substring(0, 21)%>/Mvc/images/GfiLogo.png"
			height="50%" width="20%">
		</span>
	</div>
	<div class="container">
		<div class="d-flex justify-content-center h-100">
			<div class="card">
				<div class="card-header">
					<h3>Consultas</h3>
					<div class="d-flex justify-content-end social_icon"></div>
				</div>
				<div class="card-body">
					<form action="procesarSistema" method="post">
						<div class="input-group form-group">
							<div class="input-group-prepend">
								<h3 class="input-group-text">SISTEMA</h3>
							</div>
							<select class="form-control" name="sistema">
								<option value="0">Seleccione un sistema</option>
								<%
									for (int i = 0; i < Control.getConector().getLSistemas().getListaSistemas().size(); i++) {
								%>

								<option
									value="<%=Control.getConector().getLSistemas().getListaSistemas().get(i).getSistema()%>"><%=(String) Control.getConector().getLSistemas().getListaSistemas().get(i).getSistema()%></option>
								<%
									}
								%>
							</select>

						</div>
						<div class="input-group form-group">
							<div class="input-group-prepend">
								<h3 class="input-group-text">CONSULTA</h3>
							</div>
							<select class="form-control" name="consulta">
								<option value="0">Seleccione una Consulta</option>

								<%
									for (int i = 0; i < Control.getConsultas().size(); i++) {
								%>
								<option
									value="<%= (String) Control.getConsultas().get(i).getConsulta()%>"><%=(String) Control.getConsultas().get(i).getConsulta()%></option>
								<%
									}
								%>
							</select>
						</div>
						<div class="form-group">
							<input type="submit" class="btn float-right login_btn">
						</div>
					</form>
				</div>
				<div class="card-footer">
					<div class="input-group form-group">
						<a href="#">Acerca de:</a>
					</div>

				</div>
			</div>
		</div>
	</div>


	<!-- 	<form action="procesarSistema" method="post"> -->
	<!-- 		<input type="text" name="prueba"> <input type="submit"> -->
	<!-- 	</form> -->
</body>
</html>