<%@ include file="/WEB-INF/headerMenu.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="controller.ControllerServlet" %>

<% Paziente p = (Paziente) session.getAttribute("paziente");
int conta = p.contaPazienti();
%>

<form method="get" action="ControllerServlet">
	<div class="grid flex">
		<h1 class="center">
			<i class="icon-hospital"></i> Elenco pazienti per reparto
		</h1>
	
		<br>
	
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
		<% // 7)fare il for per la visualizzazione nella tabella
		for(int i=0; i<conta; i++)
		{%>
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
			<td> <a href="ControllerServlet?val=modPaziente&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-pencil tooltip-top" title="modifica"> </i></a>
			
			<a href="ControllerServlet?val=delPaziente&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-trash tooltip-top" title="elimina"> </i></a>
			</td>
		</tr>
		<% }%>
		</tbody>
		</table>
		</div>

	</div>
	
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
