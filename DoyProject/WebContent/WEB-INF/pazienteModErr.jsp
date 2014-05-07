<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="model.Reparto" %>

<%
Paziente p = (Paziente) session.getAttribute("paziente");
Reparto r = new Reparto();
r.viewAllReparti();
int dim = r.getDim();
int indice = p.contaPazienti() - 1;
%>

<form method="post" action="ControllerServlet">
	<div class="grid flex">
		<div class="col_12" style="margin-top: 0px;">
			<h2 class="center">
				<i class="icon-beaker"></i> Aggiunta di un paziente
			</h2>
			<h4 style="color: #999; margin-bottom: 10px;" class="center">
				Inserire i dati
			</h4>
			<hr class="alt1" />
			<%for(int i=0; i<p.dimErrors(); i++){%>
			<i class="icon-warning-sign"></i><label for="nome"> <%= p.getErrors(i) %></label>
			<%}
			p.clearErrors();
			%>
		</div>
		
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
			<label for="select1">Reparto</label></div>
		<div class="col_10" style="margin-bottom: 30px">
			<select id="reparto" name="reparto" required>
			<%for(int i=0; i<dim; i++)
			{%>
			<option value="<%=r.getRep(i)%>"><%=r.getRep(i)%></option>
			<%} %>
			</select>
		<script>
        document.getElementById("reparto").value = '<%= p.getReparto(indice) %>';
        </script>
		</div>
					
		<div class="center" style="margin-bottom: 40px; margin-top: 40px">
			<button class="green" type="submit" name="val" value="insPaziente">Conferma</button>
			<button class="red" style="margin-left: 20px" type="submit" name="val" value="annPaziente" formnovalidate>Annulla</button>
		</div>
	
	</div><!-- End Grid -->
</form>

</body>
</html>

