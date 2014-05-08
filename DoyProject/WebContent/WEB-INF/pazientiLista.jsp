<%@ include file="/WEB-INF/headerMenu.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="controller.ControllerServlet" %>

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
		<tbody> <!-- Dove vanno inseriti i dati -->
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
			<td> <%= p.showData(p.getDataOut(i))
			%> </td>
			<td> <%= p.getReparto(i)
			%> </td>
			<!-- non riesco a modificare il colore delle icone -->
			<td><a href="ControllerServlet?val=profiloPaziente&ID=<%=p.getIDPaziente(i)%>">
			<i class="icon-medkit tooltip-top" title="Profilo"> </i></a>
			
			<a href="ControllerServlet?val=modPaziente&ID=<%=p.getIDPaziente(i)%>">
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
	<% } %>
	<% if(p.getInserito() == 2)
	{ %>
	<div class="notice success center"><i class="icon-ok icon-large"></i> Paziente modificato con successo 
	<a href="#close" class="icon-remove"></a></div>
	<% } %>
	
</form>
</body>
</html>