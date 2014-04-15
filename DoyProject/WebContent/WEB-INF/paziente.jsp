<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Paziente" %>

<%
// 6)creare un nuovo oggetto, il metodo conta serve per il for sotto
Paziente p = (Paziente) session.getAttribute("paziente");
//String disponibile = p.getIDDisp();

//inserito è a 2 quando sto modificando un paziente
int ID = Integer.parseInt(request.getParameter("ID"));
int indice = p.getIndice(ID);
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
	
	<!-- 	
	<div class="col_2">
		<label for="IDPaziente">IDPaziente</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="IDPaziente" name="IDPaziente" type="text" value="" placeholder="IDPaziente"/>
		<span class="tooltip-top"
					title="Primo ID disponibile:"><i
					class="icon-info-sign"></i></span>
	</div> -->
				
	<div class="col_2">
		<label for="nome">Nome</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="nome" name="nome" type="text" value="<%= p.getNome(indice)%>" placeholder="Nome" required /></div>
		
	<div class="col_2">
		<label for="cognome">Cognome</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="cognome" name="cognome" type="text" value="<%= p.getCognome(indice)%>" placeholder="Cognome" required /></div>
		
	<div class="col_2">
		<label for="data">Data di nascita</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="data" name="dataNascita" type="date" value="<%= p.getDataNascita(indice)%>" required /></div>
		
	<div class="col_2">
		<label for="cod">Codice fiscale</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="cod" name="codFisc" type="text" value="<%= p.getCodFisc(indice)%>" placeholder="Codice fiscale" required /></div>
		
	<div class="col_2">
		<label for="dataIn">Data di ingresso</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="dataIn" name="dataIn" type="date" value="<%= p.getDataIn(indice)%>" required  /></div>
		
	<div class="col_2">
		<label for="dataOut">Data di uscita</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="dataOut" name="dataOut" type="date" value="<%= p.getDataOut(indice)%>" /></div>
			
	<div class="col_2">
		<label for="dataOut">Reparto</label></div>
	<div class="col_10" style="margin-bottom: 10px">
		<input id="reparto" name="reparto" type="text" value="<%= p.getReparto(indice)%>" /></div>
		
	<!-- Prova del menu a tendina
	sarà poi da mettere nella pagina per monitorare i pazienti (paziente.jsp?), qui non ha senso -->
	<div class="col_2">
		<label for="select1">Scegli cosa monitorare</label></div>
	<div class="col_10" style="margin-bottom: 30px">
		<select id="ValoreMon">
		<option value="0">-- Valore --</option>
		<option value="1">Pressione sanguigna</option>
		<option value="2">Battito cardiaco</option>
		<option value="3">Globuli bianchi</option>
		</select>
		<button type="submit" name="val" value="addValore">Aggiungi valore</button>
	</div>
			
	<div class="center" style="margin-bottom: 80px">
		<button class="green" type="submit" name="val" value="insModPaziente">Conferma modifiche</button>
		<button class="red" style="margin-left: 20px" type="submit" name="val" value="annModPaziente" formnovalidate>Annulla modifiche</button>
	</div>
</div>
<!-- End Grid -->
</form>


</body>
</html>
