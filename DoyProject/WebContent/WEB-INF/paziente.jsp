<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="model.Monitoraggio" %>


<%
Paziente p = (Paziente) session.getAttribute("paziente");
Monitoraggio m = (Monitoraggio) session.getAttribute("monitoraggio"); 
//int ID = Integer.parseInt(request.getParameter("ID"));
int ID = Integer.parseInt(session.getAttribute("IDpaz").toString());
int indice = p.getIndice(ID);
int conta = m.contaMonitor();
m.viewAllMonitor();
%>
	
<form method="post" action="ControllerServlet">
<div class="grid flex">
	<div class="col_12" style="margin-top: 0px;">
		<h2 class="center">
			<i class="icon-medkit"></i> Profilo del paziente
		</h2>
		<h4 style="color: #999; margin-bottom: 10px;" class="center">
			Dati:
		</h4>
		<hr class="alt1" />
	</div>
	
	<div class="col_2">
		<label for="IDPaziente">IDPaziente:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="IDPaziente"><%= p.getIDPaziente(indice)%></label></div>
		
	<div class="col_2">
		<label for="nome">Nome:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="IDPaziente"><%= p.getNome(indice)%></label></div>
		
	<div class="col_2">
		<label for="cognome">Cognome:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="cognome"><%= p.getCognome(indice)%></label></div>
		
	<div class="col_2">
		<label for="data">Data di nascita:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="data"><%= p.getDataNascita(indice)%></label></div>
		
	<div class="col_2">
		<label for="cod">Codice fiscale:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="cod"><%= p.getCodFisc(indice)%></label></div>
		
	<div class="col_2">
		<label for="dataIn">Data di ingresso:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="dataIn"><%= p.getDataIn(indice)%></label></div>
		
	<div class="col_2">
		<label for="dataOut">Data di uscita:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="dataOut"><%= p.getDataOut(indice)%></label></div>
			
	<div class="col_2">
		<label for="dataOut">Reparto:</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<label for="dataOut"><%= p.getReparto(indice)%></label></div>
		
	<!-- <div class="col_2">
		<label for="select1">Scegli cosa monitorare</label></div>
	<div class="col_10" style="margin-bottom: 30px">
		<select id="ValoreMon" name="nomeValore">
		<option value="Temperatura">Temperatura</option>
		<option value="Pressione sanguigna">Pressione sanguigna</option>
		<option value="Battito cardiaco">Battito cardiaco</option>
		<option value="Globuli bianchi">Globuli bianchi</option>
		</select>-->
		
	<div class="col_2">
			<label for="select1">Scegli cosa monitorare</label></div>
		<div class="col_10" style="margin-bottom: 30px">
			<select id="ValoreMon" name="nomeValore" required>
			<%int dim = m.getDimMon();
			for(int i=0; i<dim; i++)
			{%>
			<option value="<%=m.getMon(i)%>"><%=m.getMon(i)%></option>
			<%} %>
			</select>	
		
		<!-- <input id="nomeValore" name="nomeValore" type="text" placeholder="nome valore" style="margin-left: 20px" required /> -->
		<input id="valMin" name="valMin" type="text" placeholder="valore minimo" style="margin-left: 20px" required />
		<input id="valMax" name="valMax" type="text" placeholder="valore massimo" style="margin-left: 20px" required />
		<button class="blue" type="submit" name="val" style="margin-left: 20px" value="addValore">
		<i class="icon-tag"></i> Aggiungi</button>
	</div>
			
	<div class="center" style="margin-bottom: 80px">
		<button class="red" style="margin-left: 20px" type="submit" name="val" value="closeProfiloPaziente" formnovalidate>
		<i class="icon-ok"></i> Chiudi profilo paziente</button>
	</div>
	
	<%if(m.controllaPresenza(p.getIDPaziente(indice)))
	{%>
	<!-- Tabella con i monitoraggi del paziente -->
	<div class="center">
		<table class="sortable">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
			<th>Valore</th>
			<th>Minimo</th>
			<th>Massimo</th>
			<th>Azioni</th>
		</tr></thead>
		
	<tbody><tr> <!-- Dove vanno inseriti i dati -->
		<%
		for(int i=0; i<conta; i++)
		{
		if(Integer.parseInt((m.getIDPaziente(i))) == ID)
		{%>
		
		<tr bgcolor="white">
			<td> <%= m.getIDPaziente(i)
			%> </td>
			<td> <%= m.getValore(i)
			%> </td>
			<td> <%= m.getMinimo(i).toString()
			%> </td>
			<td> <%= m.getMassimo(i).toString()
			%> </td>
			<!-- non riesco a modificare il colore delle icone -->
			<td><a href="ControllerServlet?val=modMonitor&ID=<%=m.getIDPaziente(i)%>&VAL=<%=m.getValore(i)%>">
			<i class="icon-pencil tooltip-top" title="Modifica"> </i></a>
			
			<a href="ControllerServlet?val=delMonitor&ID=<%=m.getIDPaziente(i)%>&VAL=<%=m.getValore(i)%>">
			<i class="icon-trash tooltip-top" title="Elimina"> </i></a>
						
			</td>
		</tr>
		<% }//fine if%>
		<% }//fine for%>
		</tbody>
		</table>
	</div>
	<%}//fine if per creare la tabella %>

	
</div><!-- End Grid -->

</form>

</body>
</html>
