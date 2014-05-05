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
	
	
	<!-- tabella con le cose monitorate -->
	<div class="center">
		<table class="sortable">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
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
	<label>Calcola l'indice di Pearson su:</label><br><br>
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
	<label style="margin-left: 20px;">genera </label>
	<input id="num1" name="num1" type="text" placeholder="numero" style="margin-left: 10px" value="10" required />
	<label style="margin-left: 10px;">valori, da </label>
	<input id="min1" name="min1" type="text" placeholder="numero" style="margin-left: 10px" value="1" required />
	<label style="margin-left: 10px;"> a </label>
	<input id="max1" name="max1" type="text" placeholder="numero" style="margin-left: 10px" value="10" required />
	<br><br>
	
	<label style="margin-left: 20px;" for="select2">variabile 2:</label>
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
	<label style="margin-left: 20px;">genera </label>
	<input id="num2" name="num2" type="text" placeholder="numero" style="margin-left: 10px" value="10" required />
	<label style="margin-left: 10px;">valori, da </label>
	<input id="min2" name="min2" type="text" placeholder="numero" style="margin-left: 10px" value="1" required />
	<label style="margin-left: 10px;"> a </label>
	<input id="max2" name="max2" type="text" placeholder="numero" style="margin-left: 10px" value="10" required />
	
        <br><br>
        <button class="green" style="margin-left: 20px" type="submit" name="val" value="calcolaPearson">
		<i class="icon-gift"></i> Calcola</button>
	</div>
	
	
	
</div> <!-- fine grid flex -->
</form>

<form method="post" action="ControllerServlet">
	<!-- bottone per chiudere Pearson -->
	<div class="grid flex; center" style="margin-top: 20px;">
		<button class="red" style="margin-left: 20px" type="submit" name="val" value="closePearson" formnovalidate>
		<i class="icon-fighter-jet"></i> Torna al profilo</button>
	</div>
</form>

</body>
</html>