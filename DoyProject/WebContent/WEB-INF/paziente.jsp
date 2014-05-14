<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="model.Monitoraggio" %>
<%@ page import="model.Storico" %>


<%
Paziente p = (Paziente) session.getAttribute("paziente");
Monitoraggio m = (Monitoraggio) session.getAttribute("monitoraggio");
Storico s = (Storico) session.getAttribute("storico");
//int ID = Integer.parseInt(request.getParameter("ID"));
int ID = Integer.parseInt(session.getAttribute("IDpaz").toString());
int indice = p.getIndice(ID);
int conta = m.contaMonitor();
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
		<%for(int i=0; i<m.dimErrors(); i++){%>
			<i class="icon-warning-sign icon-2x"></i>
			<label for="nome" style="margin-left: 10px; color: red; font-size:20px"> <%= m.getErrors(i) %></label>
			<br>
		<%}
		m.clearErrors();
		%>
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
		
	<div class="col_2">
			<label for="select1">Scegli cosa monitorare</label></div>
		<div class="col_10" style="margin-bottom: 30px">
			<select id="ValoreMon" name="nomeValore" required>
			<%int dim = m.viewAllMonitor();
			for(int i=0; i<dim; i++)
			{%>
			<option value="<%=m.getMon(i)%>"><%=m.getMon(i)%></option>
			<%} %>
			</select>	
		
		<!-- <input id="nomeValore" name="nomeValore" type="text" placeholder="nome valore" style="margin-left: 20px" required /> -->
		<input id="valMin" name="valMin" type="number" placeholder="valore minimo" style="margin-left: 20px" required />
		<input id="valMax" name="valMax" type="number" placeholder="valore massimo" style="margin-left: 20px" required />
		<button class="blue" type="submit" name="val" style="margin-left: 20px" value="addValore">
		<i class="icon-tag"></i> Aggiungi</button>
	</div>

</div>
</form>
	
<!-- vedo se mettere la tabella e il campo di inserimento dei dati di simulazione -->
<%if(m.controllaPresenza(p.getIDPaziente(indice)))
{%>
<!-- Tabella con i monitoraggi del paziente -->
<form method="post" action="ControllerServlet">
<div class="grid flex">

	<div class="center">
		<table class="sortable">
		<thead><tr> <!-- Intestazione -->
			<th>ID Paziente</th>
			<th>Valore</th>
			<th>Minimo</th>
			<th>Massimo</th>
			<th>Azioni</th>
		</tr></thead>
		
	<tbody> <!-- Dove vanno inseriti i dati -->
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
			
			<td>
			<a href="ControllerServlet?val=delMonitor&ID=<%=m.getIDPaziente(i)%>&VAL=<%=m.getValore(i)%>">
			<i class="icon-trash tooltip-top" title="Elimina" onclick="return conferma();"> </i></a>
				
			<script>
			function conferma()
			{
				var r=confirm("Sicuro di voler eliminare il monitoraggio?");
				if (r==true)
				{ return true; }
				else
  				{ return false; }
			}
			</script>				
						
			</td>
		</tr>
		<% }//fine if%>
		<% }//fine for%>
		</tbody>
		</table>
	</div>
	

	<!-- <form method="post" action="ControllerServlet"> -->
	<!-- bottoni per andare a calcolare Pearson e per far partire i thread -->
	<div class="center">
	<button Style="margin-top: 20px" class="blue" type="submit" name="val" value="vaiPearson">
	<i class="icon-lemon"></i> Calcola indice di Pearson</button>
	<button Style="margin-top: 20px" class="blue" type="submit" name="val" value="avviaThread">
	<i class="icon-refresh"></i> Avvia thread</button>
	</div>
	<!-- </form> -->
	
</div>
</form>
	
<%}//fine if per creare la tabella e il tasto per l'indice di Pearson%>

	<%if(m.controllaPresenza(p.getIDPaziente(indice)))
	{%>
	<!-- piccola form per la simulazione dell'aggiunta di un dato
	il controllo è sempre
	if(m.controllaPresenza(p.getIDPaziente(indice)))
	-->
	<form method="post" action="ControllerServlet">
	<div class="grid flex">
	
	<h4 style="color: #999; margin-bottom: 10px; margin-top: 40px" class="center">
			Grafici:
		</h4>
	<hr class="alt1" />

	<div class="col_2">
			<label for="select1">Aggiungi un dato a:</label></div>
		<div class="col_10">
			<select id="ValoreMon" name="valoreStorico" required>
			<% dim = s.viewMonitorPaziente(ID);
			for(int i=0; i<dim; i++)
			{%>
			<option value="<%=s.getMon(i)%>"><%=s.getMon(i)%></option>
			<%} %>
			</select>
		<input id="dato" name="dato" type="number" placeholder="dato" style="margin-left: 20px" required />
		<button class="orange" type="submit" name="val" style="margin-left: 20px" value="addDato">
		<i class="icon-tag"></i> Aggiungi</button>
	</div>
	
	</div>
	</form>


<!-- da qui c'è la zona dei grafici, da mettere solo se il paziente è presente nella tabella storico e ci sono dei dati-->
	<% dim = s.presenzaStorico(ID);
	if(dim > 0)
	{ %>
	<form method="post" action="ControllerServlet">
	<div class="grid flex">

		<div class="col_2">
			<label for="select1">Visualizza il grafico di:</label></div>
		<div class="col_10">
			<select id="visStorico" name="visStorico" required>
			<%//dim = s.monitorEstorico(ID);
			for(int i=0; i<dim; i++)
			{%>
			<option value="<%=s.getMonESto(i)%>"><%=s.getMonESto(i)%></option>
			<%} %>
			</select>
		<!-- l'unico modo per settare in modo giusto il campo del select è usare una script -->
		<script>
        document.getElementById("visStorico").value = '<%= s.getValGrafico() %>';
        </script>
        		
		<label style="margin-left: 20px">Dal</label>
		<input id="dataInizio" name="dataInizio" type="date" style="margin-left: 20px"
		value="<%= s.getDataInizio(ID) %>" required />
		
		<label style="margin-left: 20px">al</label>
		<input id="dataFine" name="dataFine" type="date" style="margin-left: 20px"
		value="<%= s.getDataFine(ID) %>" required />
		
		<button class="orange" type="submit" name="val" style="margin-left: 20px" value="aggiornaGrafico">
		<i class="icon-bolt"></i> Aggiorna</button>
		
		</div>

		<%if(s.getDimSto() != 0)
		{%>
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script type="text/javascript">
    		google.load("visualization", "1", {packages:["corechart"]});
      	
    		google.setOnLoadCallback(drawChart);
			function drawChart() {
    		    var data = google.visualization.arrayToDataTable([
      		  	['Giorno', 'Valore misurato', 'Massimo', 'Minimo'],
      	        <% dim=s.getDimSto(); int i; for(i=0; i<dim-1; i++){ %>
            	['<%= s.getDataTS(i) %>', <%= s.getStorico(i) %>, <%= s.getMax() %>, <%= s.getMin() %>], <%}%>
            	['<%= s.getDataTS(i) %>', <%= s.getStorico(i) %>, <%= s.getMax() %>, <%= s.getMin() %>]
            
			]);

       		var options = {
       	 		title: 'Andamento <%= s.getValGrafico() %> dal <%= s.getDataInizio(ID) %> al <%= s.getDataFine(ID) %>',
            	series: {0:{pointSize: '3'},
            		 1:{color: 'red'},
            		 2:{color: 'green'}
            	}
			};
              
			var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
    		chart.draw(data, options);
     		}
    	</script>
		<div id="chart_div" style="width: 1800px; height: 600px; margin-top: 80px"></div>
	
		<%}//fine if per i grafici di google %>

	</div>
	</form>
	
	<%}//fine if(dim > 0) %>
		
	<%}//fine if per mettere i grafici %>

<form method="post" action="ControllerServlet">
	<!-- bottone per chiudere il prfilo del paziente -->
	<div class="center" style="margin-bottom: 40px; margin-top: 40px">
		<button class="red" style="margin-left: 20px" type="submit" name="val" value="closeProfiloPaziente" formnovalidate>
		<i class="icon-ok"></i> Chiudi profilo paziente</button>
	</div>
</form>

</body>
</html>
