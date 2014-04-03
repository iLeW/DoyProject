<%@ include file="/WEB-INF/headerMenu.jsp"%>
<%@ page import="model.Paziente" %>


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
		</tr></thead>
		<tbody><tr> <!-- Dove vanno inseriti i dati -->
			<td>Item1</td>
			<td>Item2</td>
			<td>Item3</td>
		</tr><tr>
			<td>Item1</td>
			<td>Item2</td>
			<td>Item3</td>
		</tr><tr>
			<td>Item1</td>
			<td>Item2</td>
			<td>Item3</td>
		</tr><tr>
			<td>Item1</td>
			<td>Item2</td>
			<td>Item3</td>
		</tr></tbody>
		
		
		 
		</table>
		</div>


	</div>

</form>
</body>
</html>