<%@ include file="/WEB-INF/headerMenu.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="model.User" %>
<%@ page import="controller.ControllerServlet" %>

<% Paziente p = (Paziente) session.getAttribute("paziente");
int conta = p.contaPazienti();
User u = (User) session.getAttribute("user");
%>

<form method="get" action="ControllerServlet">
	<div class="grid flex">
		<h1 class="center">
			<i class="icon-hospital"></i> Elenco pazienti per reparto
		</h1>
	
		<br>
	
		<!-- intestazione TABS -->
		<ul class="tabs left">
		<li><a href="#tabr1"><%= u.getDep1() %></a></li>
		<% if(u.getDep2() != "")
		{%> <li><a href="#tabr2"><%= u.getDep2() %></a></li><%}%> 
		<% if(u.getDep3() != "")
		{%> <li><a href="#tabr3"><%= u.getDep3() %></a></li><%}%>
		</ul>

		<!-- TABS1 -->
		<div id="tabr1" class="tab-content">
		<div class="center">
		<table class="sortable">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Data di nascita</th>
			<th>Codice fiscale</th>
			<th>Data ingresso</th>
			<th>Data uscita</th>
			<th>Reparto</th>
			<th>Azioni</th>
		</tr></thead>
		<tbody><tr> <!-- Dove vanno inseriti i dati -->
		<% for(int i=0; i<conta; i++)
		{
		if((p.getReparto(i)).equals(u.getDep1()))
		{
		%>
		<tr bgcolor="<%=p.getColore(i) %>"> <!-- si può cambiare coloreeeeeeeee -->
			<td> <%= p.getIDPaziente(i)
			%> </td>
			<td> <%= p.getNome(i)
			%> </td>
			<td> <%= p.getCognome(i)
			%> </td>
			<td> <%= p.getDataNascita(i)
			%> </td>
			<td> <%= p.getCodFisc(i)
			%> </td>
			<td> <%= p.getDataIn(i)
			%> </td>
			<td> <%= p.getDataOut(i)
			%> </td>
			<td> <%= p.getReparto(i)
			%> </td>
			<!-- non riesco a modificare il colore delle icone -->
			<td><a href="ControllerServlet?val=profiloPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-medkit tooltip-top" title="Profilo"> </i></a> 
			
			<a href="ControllerServlet?val=modPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-pencil tooltip-top" title="Modifica"> </i></a>
			
			<a href="ControllerServlet?val=delPaziente&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-trash tooltip-top" title="Elimina" onclick="return confermaElimina(<%=p.getIDPaziente(i)%>);"> </i></a>
			
			<script>
			function confermaElimina(i)
			{
				var r=confirm("Sicuro di voler eliminare il paziente " + i + "?");
				if (r==true)
				{ return true; }
				else
  				{ return false; }
			}
			</script>
						
			</td>
		</tr>
		<%
		}//fine if
		}//fine for %>
		</tbody>
		</table>
		</div>
		</div> <!-- fine Tabs1 -->
			
		<% if(u.getDep2() != ""){%>
		<!-- TABS2 -->	
		<div id="tabr2" class="tab-content">
		<div class="center">
		<table class="sortable">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Data di nascita</th>
			<th>Codice fiscale</th>
			<th>Data ingresso</th>
			<th>Data uscita</th>
			<th>Reparto</th>
			<th>Azioni</th>
		</tr></thead>
		<tbody><tr> <!-- Dove vanno inseriti i dati -->
		<% for(int i=0; i<conta; i++)
		{
		if((p.getReparto(i)).equals(u.getDep2()))
		{
		%>
		<tr bgcolor="<%=p.getColore(i) %>"> <!-- si può cambiare coloreeeeeeeee -->
			<td> <%= p.getIDPaziente(i)
			%> </td>
			<td> <%= p.getNome(i)
			%> </td>
			<td> <%= p.getCognome(i)
			%> </td>
			<td> <%= p.getDataNascita(i)
			%> </td>
			<td> <%= p.getCodFisc(i)
			%> </td>
			<td> <%= p.getDataIn(i)
			%> </td>
			<td> <%= p.getDataOut(i)
			%> </td>
			<td> <%= p.getReparto(i)
			%> </td>
			<!-- non riesco a modificare il colore delle icone -->
			<td><a href="ControllerServlet?val=profiloPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-medkit tooltip-top" title="Profilo"> </i></a>
			
			<a href="ControllerServlet?val=modPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-pencil tooltip-top" title="Modifica"> </i></a>
			
			<a href="ControllerServlet?val=delPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-trash tooltip-top" title="Elimina"> </i></a>
			
			
			</td>
		</tr>
		<%
		}//fine if
		}//fine for %>
		</tbody>
		</table>
		</div>
		</div> <!-- fine Tabs2 -->
		<%} %>
		
		<% if(u.getDep3() != ""){%>
		<!-- TABS3 -->
		<div id="tabr3" class="tab-content">
		<div class="center">
		<table class="sortable">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Data di nascita</th>
			<th>Codice fiscale</th>
			<th>Data ingresso</th>
			<th>Data uscita</th>
			<th>Reparto</th>
			<th>Azioni</th>
		</tr></thead>
		<tbody><tr> <!-- Dove vanno inseriti i dati -->
		<% for(int i=0; i<conta; i++)
		{
		if((p.getReparto(i)).equals(u.getDep3()))
		{
		%>
		<tr bgcolor="<%=p.getColore(i) %>"> <!-- si può cambiare coloreeeeeeeee -->
			<td> <%= p.getIDPaziente(i)
			%> </td>
			<td> <%= p.getNome(i)
			%> </td>
			<td> <%= p.getCognome(i)
			%> </td>
			<td> <%= p.getDataNascita(i)
			%> </td>
			<td> <%= p.getCodFisc(i)
			%> </td>
			<td> <%= p.getDataIn(i)
			%> </td>
			<td> <%= p.getDataOut(i)
			%> </td>
			<td> <%= p.getReparto(i)
			%> </td>
			<!-- non riesco a modificare il colore delle icone -->
			<td><a href="ControllerServlet?val=profiloPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-medkit tooltip-top" title="Profilo"> </i></a>
			
			<a href="ControllerServlet?val=modPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-pencil tooltip-top" title="Modifica"> </i></a>
			
			<a href="ControllerServlet?val=delPazienteCat&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-trash tooltip-top" title="Elimina"> </i></a>
			
			</td>
		</tr>
		<%
		}//fine if
		}//fine for %>
		</tbody>
		</table>
		</div>
		</div> <!-- fine Tabs3 -->
		<%} %>
	
	</div> <!--  fine div class grid flex -->
	
	<!-- per mostrare o no il messaggio di Paziente inserito, FARE IL RESIZE -->
	<% if(p.getInserito() == 1)
	{ %>
	<div class="notice success center"><i class="icon-ok icon-large"></i> Paziente inserito con successo 
	<a href="#close" class="icon-remove"></a></div>
	<% p.setInserito(0);
	} %>
	<% if(p.getInserito() == 2)
	{ %>
	<div class="notice success center"><i class="icon-ok icon-large"></i> Paziente modificato con successo 
	<a href="#close" class="icon-remove"></a></div>
	<% p.setInserito(0);
	} %>
	
</form>
</body>
</html>
