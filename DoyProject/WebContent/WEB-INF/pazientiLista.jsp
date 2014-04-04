<%@ include file="/WEB-INF/headerMenu.jsp"%>
<%@ page import="model.Paziente" %>

<%
// 6)creare un nuovo oggetto, il metodo conta serve per il for sotto
Paziente p = (Paziente) session.getAttribute("paziente");
int conta = p.contaPazienti();
%>

<form method="get" action="ControllerServlet">
	<div class="grid flex">
		<h1 class="center">
			<i class="icon-hospital"></i> Elenco pazienti
		</h1>
	
		<br>
	
		<div class="center" style="margin-bottom: 20px">
			<button class="green" type="submit" name="val" value="aggPaziente">Aggiungi paziente</button>
			<!-- value="addPaziente" non va bene, forse java strippa se chiami add? -->
		</div>
		
		<div class="center">
		<table class="sortable striped">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Data di nascita</th>
			<th>Codice fiscale</th>
			<th>Data ingresso</th>
			<th>Data uscita</th>
			<th>Azioni</th>
		</tr></thead>
		<tbody><tr> <!-- Dove vanno inseriti i dati -->
		<% // 7)fare il for per la visualizzazione nella tabella
		for(int i=0; i<conta; i++)
		{%>
		<tr>
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
			<!-- da vedere bene come fare per modificare i dati -->
			<td value=""> <a href> <i class="icon-pencil"> </i></a>
			</td>
		</tr>
		<% }%>
		</tbody>
		</table>
		</div>

	</div>
	
	<!-- per mostrare o no il messaggio di Paziente inserito, fare il resize -->
	<% if(p.getInserito() == 1)
	{ %>
	<div class="notice success"><i class="icon-ok icon-large"></i> Paziente inserito con successo 
	<a href="#close" class="icon-remove"></a></div>
	<% p.setInserito(0);
	} %>
	
</form>
</body>
</html>