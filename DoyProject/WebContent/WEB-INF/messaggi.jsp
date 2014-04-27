<!-- In questa JSP vengono elencati tutti i messaggi nuovi rivolti al dottore -->
<%@ include file="/WEB-INF/headerMenu.jsp"%>
<%@ page import="model.Messaggio"%>
<%@ page import="model.User"%>

<%
	System.out.println("Entro qui in messaggi");
%>

<form method="get" action="ControllerServlet">
	<div class="grid flex">

		<%
			Messaggio mx = (Messaggio) session.getAttribute("messaggio");
			User u = (User) session.getAttribute("user");
			if (mx.getNumMex(u.getUsername()) > 0) {
		%>
		<h1 class="center">
			<i class="icon-envelope"></i> Messaggi
		</h1>




		<!-- Tabs Left -->
		<ul class="tabs left">
			<%
				if (mx.getNumMexU(u.getUsername()) > 0) {
			%>
			<li><a href="#tabr1">Da Leggere</a></li>
			<%
				}
			%>
			<%
				if (mx.getNumMexR(u.getUsername()) > 0) {
			%>
			<li><a href="#tabr2">Letti</a></li>
			<%
				}
			%>
			<!-- tabr1 dei messaggi NON LETTI-->

		</ul>
		<%
			if (mx.getNumMexU(u.getUsername()) > 0) {
		%>
		<div id="tabr1" class="tab-content">
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
								if (session.getAttribute("fromMessaggi") == null
												&& session.getAttribute("fromHome") != null)
											System.out.println("OK CORRETTO DA messaggi");
										System.out.println("NUM MESS fromMessaggi tabr1: "
												+ mx.getNumMexU(u.getUsername()));
										for (int i = 0; i < mx.getNumMexU(u.getUsername()); ++i) {
											System.out.println("i :" + i);
							%>
						
						<tr>
							<td><%=mx.getSenderU(i)%></td>
							<td><%=mx.getMexPreviewU(i)%></td>
							<td><%=mx.getDateU(i)%></td>


							<!-- Azioni icone -->
							<td><a
								href="ControllerServlet?val=readMex&sender=<%=mx.getSenderU(i)%>&date=<%=mx.getDateU(i)%>">
									<i class="icon-book tooltip-top" title="Leggi"> </i>
							</a> <a
								href="ControllerServlet?val=rispMex&sender=<%=mx.getSenderU(i)%>&date=<%=mx.getDateU(i)%>">
									<i class="icon-share-alt tooltip-top" title="Rispondi"> </i>
							</a> <a
								href="ControllerServlet?val=delMex&sender=<%=mx.getSenderU(i)%>&date=<%=mx.getDateU(i)%>#tabr1">
									<i class="icon-trash tooltip-top" title="Elimina"> </i>
							</a><a
								href="ControllerServlet?val=okReadMex&sender=<%=mx.getSenderU(i)%>&date=<%=mx.getDateU(i)%>#tabr1">
									<i class="icon-check tooltip-top" title="Letto"> </i>
							</a></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
		<!-- fine tabr1 -->
		<%
			}
		%>

		<%
			if (mx.getNumMexR(u.getUsername()) > 0) {
		%>
		<!-- tabr2 dei messaggi LETTI-->
		<div id="tabr2" class="tab-content">
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
								for (int i = 0; i < mx.getNumMexR(u.getUsername()); ++i) {
							%>
						
						<tr>
							<td><%=mx.getSenderR(i)%></td>
							<td><%=mx.getMexPreviewR(i)%></td>
							<td><%=mx.getDateR(i).toString()%></td>

							<!-- Bottoni -->
							<td><a
								href="ControllerServlet?val=readMex&sender=<%=mx.getSenderR(i)%>&date=<%=mx.getDateR(i)%>&r=true">
									<i class="icon-book tooltip-top" title="Leggi"> </i>
							</a> <a
								href="ControllerServlet?val=rispMex&sender=<%=mx.getSenderR(i)%>&date=<%=mx.getDateR(i)%>&r=true">
									<i class="icon-share-alt tooltip-top" title="Rispondi"> </i>
							</a> <a
								href="ControllerServlet?val=delMex&sender=<%=mx.getSenderR(i)%>&date=<%=mx.getDateR(i)%>#tabr2">
									<i class="icon-trash tooltip-top" title="Elimina"> </i>
							</a></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
		<!-- fine tabr2 -->
		<%
			}
		%>



	</div>
</form>
<%
	} else {
%>
<h1 class="center">
	<i class="icon-envelope"></i> Non ci sono messaggi!
</h1>
<%
	}
%>

<%@ include file="/WEB-INF/footerMenu.jsp"%>

</body>
</html>