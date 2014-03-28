<!-- Questo è l'header che va aggiunto alle pagine jsp homepage.jsp, messaggi.jsp, pazientiList.jsp, pazientiCat.jsp
Tale header è infatti quello che permette di avere nella pagina la barra in alto che fa da menù -->

<%@ include file="/WEB-INF/header.jsp"%>
<!-- Questa è lìinclusione dei file per l'utilizzo dei css e js -->


<body>

	<!-- Codice Java per vedere in che pagina mi trovo -->
	<% 	String uri = request.getRequestURI();
	String pageName = uri.substring(uri.lastIndexOf("/")+1);
	System.out.println("Nome della Pagina = " + pageName); 
	%>

	<div>
		<!-- Menu Horizontal -->
		<ul class="menu">
			
			<!-- Il seguente è un codice Java che uso per decidere in quale punto del menù mettere la voce "current" che 
			mi indica quale elemento della tabella evidenziare. Prendo la pagina corrente con il codice Java nel blocco sopra -->
			<%
			
			if("homepage.jsp".equals(pageName)){
				out.println("<li class=\"current\"><a href=\"\"><i class =\"icon-home\"></i>Homepage</a></li>");
				out.println("<li><a href=\"\"><i class=\"icon-envelope\"></i>Messaggi</a></li>");
				out.println("<li><a href=\"\"><i class=\"icon-plus-sign-alt\"></i> Pazienti</a>");
				out.println("<ul>");
			}
			
			else if ("messaggi.jsp".equals(pageName)){
				out.println("<li><a href=\"\"><i class =\"icon-home\"></i>Homepage</a></li>");
				out.println("<li class=\"current\"><a href=\"\"><i class=\"icon-envelope\"></i>Messaggi</a></li>");
				out.println("<li><a href=\"\"><i class=\"icon-plus-sign-alt\"></i> Pazienti</a>");
				out.println("<ul>");
			}
			
			else if ("pazienti.jsp".equals(pageName)){
				out.println("<li><a href=\"\"><i class =\"icon-home\"></i>Homepage</a></li>");
				out.println("<li><a href=\"\"><i class=\"icon-envelope\"></i>Messaggi</a></li>");
				out.println("<li class=\"current\"><a href=\"\"><i class=\"icon-plus-sign-alt\"></i> Pazienti</a>");
				out.println("<ul>");
			}
	
		%>
			<li><a href=""><i class="icon-group"></i> Tutti</a></li>
			<li><a href=""><i class="icon-sitemap"></i> Per reparto</a></li>
		</ul>
	</div>
</body>
</html>
