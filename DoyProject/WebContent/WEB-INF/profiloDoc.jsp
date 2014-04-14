<!-- Inclusione del JSP con il menù principale e della classe User-->
<%@ include file="/WEB-INF/headerMenu.jsp"%>

<!-- Prendo il dottore che SICURAMENTE è nella sessione a questo punto -->
<%@ page import="model.User"%>
<%
	User u = (User) session.getAttribute("user"); //Questo non so se serve
%>

<!-- Creo la pagina HTML, per mostrare tutte le informazioni del dottore -->

<hr />
<div class="grid flex">
	<h1 class="center">
		<i class="icon-user-md icon-2x"></i> Dottore
	</h1>
	
	<!-- Username -->
	<div class="col_3">Username: </div>
	<div class="col_9">${user.username}</div>
	
	<!-- Password -->
	<div class="col_3"></div>
	<div class="col_9"></div>
	
	<!-- Nome -->
	<div class="col_3">Nome: </div>
	<div class="col_9">${user.name}</div>
	
	<!-- Cognome -->
	<div class="col_3">Cognome:</div>
	<div class="col_9">${user.surname}</div>
	
	<!-- Data di nascita -->
	<div class="col_3">Data di nascita: </div>
	<div class="col_9">${user.birthdate}</div>
	
	<!-- Reparti -->
	<% for(int i=0; i<u.getNumDep(); ++i) %>
	<div class="col_3"></div>
	<div class="col_9"></div>
	
</div>



</body>
</html>