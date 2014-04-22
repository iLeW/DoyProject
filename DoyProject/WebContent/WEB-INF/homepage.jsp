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

		<!-- Se ci sono dei messaggi allora dico che ce ne sono altrimenti no -->
		<%
			Messaggio mx = (Messaggio) session.getAttribute("messaggio"); //Di sicuro settato dalla servlet
			User u = (User) session.getAttribute("user");
			if (mx.getNumNewMex(u.getUsername()) == 0) {
		%>
		<h4 style="color: #999; margin-bottom: 40px;" class="center">Non
			ci sono nuovi messaggi, Buon Lavoro!</h4>
		<%
			} else {
				
				out.print("<h4 style=\"color: #999; margin-bottom: 40px;\" class=\"center\">");
				out.print("Ci sono " + mx.getNumNewMex(u.getUsername())
						+ " messaggi non letti: </h4>");
				%>
				<div class="center">
				<table class="sortable striped">
					<thead>
						<tr>
							<!-- Intestazione -->
							<th>Da</th>
							<th>Antemprima Messaggio</th>
							<th>Data</th>
							<th>Azioni</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<!-- Dove vanno inseriti i dati -->
							<%
								for (int i = 0; i < mx.getNumMexU(u.getUsername()); ++i) {
							%>
						
						<tr>
							<td><%=mx.getSenderU(i)%></td>
							<td><%=mx.getMexPreviewU(i)%></td>
							<td><%=mx.getDateU(i)%></td>
							

							<!-- Bottoni -->
							<td><a
								href="ControllerServlet?val=readMex&sender=<%=mx.getSenderU(i)%>&date=<%=mx.getDateU(i)%>">
									<i class="icon-book tooltip-top" title="Leggi"> </i>
							</a> <a
								href="ControllerServlet?val=rispMex&sender=<%=mx.getSenderU(i)%>">
									<i class="icon-share-alt tooltip-top" title="Rispondi"> </i>
							</a> <a
								href="ControllerServlet?val=delMex&sender=<%=mx.getSenderU(i)%>&date=<%=mx.getDateU(i)%>">
									<i class="icon-trash tooltip-top" title="Elimina"> </i>
							</a> <a
								href="ControllerServlet?val=okReadMex&sender=<%=mx.getSenderU(i)%>&date=<%=mx.getDateU(i)%>">
									<i class="icon-check tooltip-top" title="Letto"> </i>
							</a>
							</td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
			<%}
		%>
	</div>


</div>
<!-- End Grid -->

<% session.setAttribute("fromHome", true);	//dico che vengo dalla homepage %>
</body>
</html>