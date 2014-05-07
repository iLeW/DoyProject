<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="model.Monitoraggio" %>
<%@ page import="model.Storico" %>


<%
Paziente p = (Paziente) session.getAttribute("paziente");
Monitoraggio m = (Monitoraggio) session.getAttribute("monitoraggio");
Storico s = (Storico) session.getAttribute("storico");
int ID = Integer.parseInt(session.getAttribute("IDpaz").toString());
int indice = p.getIndice(ID);
int conta = m.contaMonitor();
%>
	
<form method="post" action="ControllerServlet">
<div class="grid flex">
	<div class="col_12" style="margin-top: 0px;">
		<h2 class="center">
			<i class="icon-medkit"></i> Indice di Pearson
		</h2>
		<h4 style="color: #999; margin-bottom: 10px;" class="center">
			Monitoraggi attivi:
		</h4>
		<hr class="alt1" />
	</div>
	
	<div class="left">
	<i class="icon-globe icon-2x"></i><label style="margin-left: 10px"> L'indice di Pearson esprime un'eventuale relazione di linearità tra due variabili e
	assume sempre valori compresi tra -1 e +1. </label>
	<br><br>
	<ul><!-- lista -->
	<li> Se è maggiore 0 le due variabili si dicono direttamente correlate. </li>
	<li> Se è uguale 0 le due variabili si dicono incorrelate.</li>
	<li> Se è minore 0 le due variabili si dicono inversamente correlate.</li>
	</ul>
	<label> Inoltre per la correlazione diretta ed inversa si distingue: </label>
	<ul><!-- lista -->
	<li> Indice comrpeso da 0 e 0.3, si ha correlazione debole.</li>
	<li> Indice comrpeso da 0.3 e 0.7, si ha correlazione moderata.</li>
	<li> Indice maggiore di 0.7, si ha correlazione forte.</li>
	</ul>
	</div>
	
	<div class="center">
	<label style="color: #999; margin-bottom: 10px; margin-top: 20px; font-size:20px">
	Valori monitorati del paziente su cui è possibile calcolare l'indice di Pearson </label>
	<!-- tabella con le cose monitorate -->
		<table class="sortable">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Valore</th>
			<th>Minimo</th>
			<th>Massimo</th>
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
			<td> <%= p.getNome(indice)
			%> </td>
			<td> <%= p.getCognome(indice)
			%> </td>
			<td> <%= m.getValore(i)
			%> </td>
			<td> <%= m.getMinimo(i).toString()
			%> </td>
			<td> <%= m.getMassimo(i).toString()
			%> </td>
		</tr>
		<% }//fine if%>
		<% }//fine for%>
		</tbody>
		</table>
	</div><!-- fine tabella -->
	
	<div class ="center">
	<label style="margin-top: 20px; font-size:20px">Calcola l'indice di Pearson su:</label>
	<br><br>
	<label for="select1">variabile 1:</label>
		<select style="margin-left: 20px;" id="variabile1" name="variabile1" required>
		<%int dim = m.viewMonitorPaziente(ID);
		for(int i=0; i<dim; i++)
		{%>
		<option value="<%=m.getMon(i)%>"><%=m.getMon(i)%></option>
		<%} %>
		</select>
		<!-- l'unico modo per settare in modo giusto il campo del select è usare una script -->
		<script>
        document.getElementById("variabile1").value = '<%= m.getVar1() %>';
        </script>
	<label style="margin-left: 10px;"> da </label>
	<input id="min1" name="min1" type="text" placeholder="minimo"
	style="margin-left: 10px" value="<%= m.getMin1() %>" size="6" required />
	<label style="margin-left: 10px;"> a </label>
	<input id="max1" name="max1" type="text" placeholder="massimo"
	style="margin-left: 10px" value="<%= m.getMax1() %>" size="6" required />
	
	<br><br>
	<label for="select2">variabile 2:</label>
		<select style="margin-left: 20px;" id="variabile2" name="variabile2" required>
		<%
		for(int i=0; i<dim; i++)
		{%>
		<option value="<%=m.getMon(i)%>"><%=m.getMon(i)%></option>
		<%} %>
		</select>
		<!-- l'unico modo per settare in modo giusto il campo del select è usare una script -->
		<script>
        document.getElementById("variabile2").value = '<%= m.getVar2() %>';
        </script>
	<label style="margin-left: 10px;"> da </label>
	<input id="min2" name="min2" type="text" placeholder="minimo"
	style="margin-left: 10px" value="<%= m.getMin2() %>" size="6" required />
	<label style="margin-left: 10px;"> a </label>
	<input id="max2" name="max2" type="text" placeholder="massimo"
	style="margin-left: 10px" value="<%= m.getMax2() %>" size="6" required />
	
	<br><br>
	<label>genera </label>
	<input id="num" name="num" type="text" placeholder="numero"
	style="margin-left: 10px" value="<%= m.getNum() %>" size="6" required />
	<label style="margin-left: 10px;"> valori random </label>
	
    <br><br>
    <button class="green" style="margin-left: 20px" type="submit" name="val" value="calcolaPearson">
	<i class="icon-bar-chart"></i> Calcola </button>
		
	<% if(m.getPearson() != 0)
	{%>
		<h4>
		<label> Indice di Pearson calcolato sui valori scelti: <%= m.getPearson() %></label>
		</h4>
	<%} %>
	
	</div>
	
</div> <!-- fine grid flex -->
</form>

<form method="post" action="ControllerServlet">
	<!-- bottone per chiudere Pearson -->
	<div class="grid flex; center" style="margin-top: 20px; margin-bottom: 40px">
		<button class="red" style="margin-left: 20px" type="submit" name="val" value="closePearson" formnovalidate>
		<i class="icon-fighter-jet"></i> Torna al profilo</button>
	</div>
</form>

</body>
</html>