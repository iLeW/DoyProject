<!-- Questo � l'header che va aggiunto alle pagine jsp homepage.jsp, messaggi.jsp, pazientiList.jsp, pazientiCat.jsp
Tale header � infatti quello che permette di avere nella pagina la barra in alto che fa da men� -->

<!-- Questa � l'inclusione dei file per l'utilizzo dei css e js -->
<%@ include file="/WEB-INF/header.jsp"%>

<body>
	<form>
		<!-- Codice Java (scriptlet) per vedere in che pagina mi trovo -->
		<%
			String uri = request.getRequestURI();
			String pageName = uri.substring(uri.lastIndexOf("/") + 1);
			System.out.println("Nome della Pagina = " + pageName);
		%>

		<div>
			<!-- Menu Horizontal -->
			<ul class="menu">

				<!-- Il seguente � un codice Java (scriptlet) che uso per decidere in quale punto del men� mettere la voce "current" che 
			mi indica quale elemento della tabella evidenziare. Prendo la pagina corrente con il codice Java nel blocco sopra -->
				<%
					if ("homepage.jsp".equals(pageName)) {
						out.println("<li style=\"float:right;\" class=\"icon_signout\"><a href=\"ControllerServlet?val=signout\"> <i class=\"icon-off\"></i> SigOut</a></li>");
						out.println("<li class=\"current\"><a href=\"ControllerServlet?val=homepage\"><i class =\"icon-home\"></i>Homepage</a></li>");
						out.println("<li class=\"\"><a href=\"ControllerServlet?val=profiloDoc\"><i class =\"icon-user-md\"></i>Profilo</a></li>");
						out.println("<li><a href=\"ControllerServlet?val=messaggi\"><i class=\"icon-envelope\"></i>Messaggi</a></li>");
						out.println("<li><a href=\"ControllerServlet?val=homepage\"><i class=\"icon-plus-sign-alt\"></i> Pazienti</a>");	//Ho dovuto mettere solo in questo caso su pazienti il link alla homepage altrimenti dava 404 per
																																			//perch� non aveva ancora niente nel URL (se arrivavo dal login) e quindi non trovava niente. L'indirizzo
																																			//della URL dal login era http://localhost:8080/DoyProject/ControllerServlet e quindi se cliccavo su Pazienti
																																			//dava 404 perch� cercava di "rimanere/andare" su quella pagina che per� non esiste.
						out.println("<ul>");
					}

					else if ("profiloDoc.jsp".equals(pageName)) {
						out.println("<li style=\"float:right;\" class=\"icon_signout\"><a href=\"ControllerServlet?val=signout\"> <i class=\"icon-off\"></i> SigOut</a></li>");
						out.println("<li class=\"\"><a href=\"ControllerServlet?val=homepage\"><i class =\"icon-home\"></i>Homepage</a></li>");
						out.println("<li class=\"current\"><a href=\"ControllerServlet?val=profiloDoc\"><i class =\"icon-user-md\"></i>Profilo</a></li>");
						out.println("<li><a href=\"ControllerServlet?val=messaggi\"><i class=\"icon-envelope\"></i>Messaggi</a></li>");
						out.println("<li><a href=\"\"><i class=\"icon-plus-sign-alt\"></i> Pazienti</a>");
						out.println("<ul>");
					}

					else if ("messaggi.jsp".equals(pageName)) {
						out.println("<li style=\"float:right;\" class=\"icon_signout\"><a href=\"ControllerServlet?val=signout\"> <i class=\"icon-off\"></i> SigOut</a></li>");
						out.println("<li><a href=\"ControllerServlet?val=homepage\"><i class =\"icon-home\"></i>Homepage</a></li>");
						out.println("<li class=\"\"><a href=\"ControllerServlet?val=profiloDoc\"><i class =\"icon-user-md\"></i>Profilo</a></li>");
						out.println("<li class=\"current\"><a href=\"ControllerServlet?val=messaggi\"><i class=\"icon-envelope\"></i>Messaggi</a></li>");
						out.println("<li><a href=\"\"><i class=\"icon-plus-sign-alt\"></i> Pazienti</a>");
						out.println("<ul>");
					}

					else if ("pazientiLista.jsp".equals(pageName) || "pazientiCategoria.jsp".equals(pageName) ) {
						out.println("<li style=\"float:right;\" class=\"icon_signout\"><a href=\"ControllerServlet?val=signout\"> <i class=\"icon-off\"></i> SigOut</a></li>");
						out.println("<li><a href=\"ControllerServlet?val=homepage\"><i class =\"icon-home\"></i>Homepage</a></li>");
						out.println("<li class=\"\"><a href=\"ControllerServlet?val=profiloDoc\"><i class =\"icon-user-md\"></i>Profilo</a></li>");
						out.println("<li><a href=\"ControllerServlet?val=messaggi\"><i class=\"icon-envelope\"></i>Messaggi</a></li>");
						out.println("<li class=\"current\"><a href=\"\"><i class=\"icon-plus-sign-alt\"></i> Pazienti</a>");
						out.println("<ul>");
					}
				%>
				<li><a href="ControllerServlet?val=pazientiLista"><i
						class="icon-group"></i> Tutti</a></li>
				<li><a href="ControllerServlet?val=pazientiCategoria"><i
						class="icon-sitemap"></i> Per reparto</a></li>
			</ul>
		</div>
	</form>
</body>
</html>
