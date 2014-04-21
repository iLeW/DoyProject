<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/headerMenu.jsp"%>
<%@ page import="model.Messaggio"%>
<%@ page import="model.User"%>
<div class="grid flex">
	<div class="col_12" style="margin-top: 100px;">
		<h1 class="center">
			<!-- la <i> serve per le icone -->
			<i class="icon-eye-open"></i> Benvenuto Dott. ${user.username}
			<!-- Questo è un utilizzio delle JSP 2.x, tale metodo è possibile perché il JavaBean User.java ha i metodi getter e setter -->
		</h1>

		<%
			Messaggio m = (Messaggio) session.getAttribute("messaggio");
			User u = (User) session.getAttribute("user");
			if (m == null)
				m = new Messaggio();
			if (m.getNumNewMex(u.getUsername()) == 0) {
		%>
		<h4 style="color: #999; margin-bottom: 40px;" class="center">Non
			ci sono nuovi messaggi, Buon Lavoro!</h4>
		<%
			} else {
				out.print("<h4 style=\"color: #999; margin-bottom: 40px;\" class=\"center\">");
				out.print("Ci sono " + m.getNumNewMex(u.getUsername())
						+ " messaggi non letti: </h4>");
			}
		%>
	</div>


</div>
<!-- End Grid -->
</body>
</html>