<!-- Inclusione del JSP con il menù principale e della classe User-->
<%@ include file="/WEB-INF/headerMenu.jsp"%>

<!-- Prendo il dottore che SICURAMENTE è nella sessione a questo punto -->
<%@ page import="model.User"%>


<!-- Creo la pagina HTML, per mostrare tutte le informazioni del dottore -->

<hr />
<!-- Per far spazio tra la barra del menù il titolo con l'icona -->



<div class="grid flex">
	<h1 class="center">
		<i class="icon-user-md icon-2x"></i> Profilo
	</h1>

	<%
		if (session.getAttribute("err_mod1") != null) {
	%>
	<!-- Error -->
	<div class="notice error">
		<i class="icon-remove-sign icon-large"></i>
		<%=session.getAttribute("err_mod1").toString()%>
		<a href="#close" class="icon-remove"></a>
	</div>
	<%
		}
	%>

<%
	User u = (User) session.getAttribute("user");
	System.out.println("NOME:" + u.getName());
%>
	<!-- Username -->
	<div class="col_3">
		<label class="l4">Username:</label>
	</div>
	<div class="col_9">
		<label class="l5">${user.username}</label>
	</div>
	<div class="col_12 center"></div>

	<!-- Nome -->
	<div class="col_3">
		<label class="l4">Nome:</label>
	</div>
	<div class="col_9">
		<label class="l5">${user.name}</label>
	</div>
	<div class="col_12 center"></div>

	<!-- Cognome -->
	<div class="col_3">
		<label class="l4">Cognome:</label>
	</div>
	<div class="col_9">
		<label class="l5">${user.surname}</label>
	</div>
	<div class="col_12 center"></div>

	<!-- Data di nascita -->
	<div class="col_3">
		<label class="l4">Data di nascita:</label>
	</div>
	<div class="col_9">
		<label class="l5">${user.birthdate}</label>
	</div>
	<div class="col_12 center"></div>
	<div class="col_12 center"></div>

	<!-- Reparti, possono essere al massimo 3 quelli seguiti-->


	<%
		switch (u.getNumDep()) {
		case 1: {
			out.println("<h3 class=\"center\">Dott. " + u.getSurname() 
					+ ", sta seguendo il reparto di: </h3><br>");
			out.println("<div class=\"col_12 center\"></div>");
			out.println("<h4 class=\"center\">" + u.getDep1() + "</h4><br>");
			break;
		}

		case 2: {
			out.println("<h3 class=\"center\">Dott. " + u.getSurname()
					+ ", sta seguendo i reparti di: </h3>");
			out.println("<div class=\"col_12 center\"></div>");
			out.println("<h4 class=\"center\">" + u.getDep1() + " e "
					+ u.getDep2() + "</h4> <br>");
			break;
		}

		case 3: {
			out.println("<h3 class=\"center\">Dott. " + u.getSurname()
					+ ", sta seguendo i reparti di: </h3>");
			out.println("<div class=\"col_12 center\"></div>");
			out.println("<h4 class=\"center\">" + u.getDep1() + ", "
					+ u.getDep2() + " e " + u.getDep3() + "</h4> <br>");
			break;
		}

		default:
			break;
		}
	%>

	<div class="col_12 center"></div>

	<form name="profiloForm" method="get" action="ControllerServlet"
		autocomplete="off">
		<div class="col_12 center">
			<button class="blue" type="submit" name="val" value="modProfilo">Modifica</button>
		</div>
	</form>
</div>




</body>
</html>