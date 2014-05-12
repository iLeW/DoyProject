<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Paziente" %>
<%@ page import="model.Reparto" %>

<%
Paziente p = (Paziente) session.getAttribute("paziente");
Reparto r = new Reparto();
r.viewAllReparti();
int dim = r.getDim();

//inserito è a 2 quando sto modificando un paziente
if(p.getInserito() == 2)
{
	//int ID = Integer.parseInt(request.getParameter("ID"));
	int ID = Integer.parseInt(session.getAttribute("IDpaz").toString());
	//p.setIDold(request.getParameter("ID"));
	int indice = p.getIndice(ID);
	//System.out.println("pazienteMod, ID: " + request.getParameter("ID") + " indice: " + indice + " IDold: " + p.getIDold()); 
	%>
	
	<form method="post" action="ControllerServlet">
	<div class="grid flex">
		<div class="col_12" style="margin-top: 0px;">
			<h2 class="center">
				<i class="icon-medkit"></i> Modifica di un paziente
			</h2>
			<h4 style="color: #999; margin-bottom: 10px;" class="center">
				Modificare i dati
			</h4>
			<hr class="alt1" />
			<% for(int i=0; i<p.dimErrors(); i++){%>
			<i class="icon-warning-sign"></i>
			<label for="nome" style="margin-left: 10px; color: red; font-size:20px"> <%= p.getErrors(i) %></label>
			<br>
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
			<label for="dataOut">Data di uscita</label></div>
		<div class="col_10" style="margin-bottom: 10px">
			<label for="dataOut"> <%= p.showData(p.getDataOut(indice)) %></label></div>
			<!-- <input id="dataOut" name="dataOut" type="date" value="<%= p.getDataOut(indice)%>" /> </div> -->
		
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
			<button class="green" type="submit" name="val" value="insModPaziente">Conferma modifiche</button>
			<button class="red" style="margin-left: 20px" type="submit" name="val" value="annModPaziente" formnovalidate>Annulla modifiche</button>
			<%if(p.dentro(p.getDataOut(indice)))
			{%>
			<button class="orange" style="margin-left: 20px" type="submit" name="val" value="dimettiPaziente">Dimetti paziente</button>
			<%}
			else
			{%>
			<button class="orange" style="margin-left: 20px" type="submit" name="val" value="riammettiPaziente">Riammetti paziente</button>
			<%} %>		
		
		</div>
	</div>
<!-- End Grid -->
</form>
<% //p.setInserito(0); 
 }//fine if

// se inserito è diverso da due sono nel caso di inserimento normale
else
{ %>

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
		</div>
		 	
		<div class="col_2">
			<label for="nome">Nome</label></div>
		<div class="col_10" style="margin-bottom: 10px">
			<input id="nome" name="nome" type="text" placeholder="Nome" required /></div>
		
		<div class="col_2">
			<label for="cognome">Cognome</label></div>
		<div class="col_10" style="margin-bottom: 10px">
			<input id="cognome" name="cognome" type="text" placeholder="Cognome" required /></div>
		
		<div class="col_2">
			<label for="data">Data di nascita</label></div>
		<div class="col_10" style="margin-bottom: 10px">
			<input id="data" name="dataNascita" type="date" required /></div>
		
		<div class="col_2">
			<label for="cod">Codice fiscale</label></div>
		<div class="col_10" style="margin-bottom: 10px">
			<input id="cod" name="codFisc" type="text" placeholder="Codice fiscale" required /></div>
		
		<div class="col_2">
			<label for="dataIn">Data di ingresso</label></div>
		<div class="col_10" style="margin-bottom: 10px">
			<input id="dataIn" name="dataIn" type="date" required  /></div>
		
		<div class="col_2">
			<label for="select1">Reparto</label></div>
		<div class="col_10" style="margin-bottom: 30px">
			<select id="ValoreMon" name="reparto" required>
			<%for(int i=0; i<dim; i++)
			{%>
			<option value="<%=r.getRep(i)%>"><%=r.getRep(i)%></option>
			<%} %>
			</select>
		</div>
					
		<div class="center" style="margin-bottom: 40px; margin-top: 40px">
			<button class="green" type="submit" name="val" value="insPaziente">Conferma</button>
			<button class="red" style="margin-left: 20px" type="submit" name="val" value="annPaziente" formnovalidate>Annulla</button>
		</div>
	</div>
<!-- End Grid -->
</form>
<% }//fine else %>

</body>
</html>

