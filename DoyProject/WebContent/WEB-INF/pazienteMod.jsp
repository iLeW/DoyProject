<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/headerMenu.jsp"%>

<form method="post" action="ControllerServlet">
	<div class="grid flex">
		<div class="col_12" style="margin-top: 100px;">
			<h1 class="center">
				<i class="icon-beaker"></i> Aggiunta di un paziente
			</h1>
			<h3 style="color: #999; margin-bottom: 40px;" class="center">
				Inserire i dati
			</h3>
			<hr class="alt1" />
		</div>
	
		<h5 style="margin-bottom: 80px">
			<div class="col_2">
				<label for="IDPaziente">IDPaziente</label></div>
			<div class="col_10">
				<input id="IDPaziente" type="text" placeholder="IDPaziente" /></div> <br> <br>
				
			<div class="col_2">
				<label for="nome">Nome</label></div>
			<div class="col_10">
				<input id="nome" type="text" placeholder="Nome" /></div> <br> <br>
		
			<div class="col_2">
				<label for="cognome">Cognome</label></div>
			<div class="col_10">
				<input id="cognome" type="text" placeholder="Cognome" /></div> <br> <br>
		
			<div class="col_2">
				<label for="data">Data di nascita</label></div>
			<div class="col_10">
				<input id="data" type="text" placeholder="gg/mm/aaaa" /></div> <br> <br>
		
			<div class="col_2">
				<label for="cod">Codice fiscale</label></div>
			<div class="col_10">
				<input id="cod" type="text" placeholder="Codice fiscale" /></div> <br> <br>
		
			<div class="col_2">
				<label for="dataIn">Data di ingresso</label></div>
			<div class="col_10">
				<input id="dataIn" type="text" placeholder="gg/mm/aaaa" /></div> <br> <br>
		
			<div class="col_2">
				<label for="dataOut">Data di uscita</label></div>
			<div class="col_10">
				<input id="dataOut" type="text" placeholder="gg/mm/aaaa" /></div> <br> <br>
		
			<!-- Prova del menu a tendina
			sarà poi da mettere nella pagina per monitorare i pazienti (paziente.jsp?), qui non ha senso -->
			<div class="col_2">
				<label for="select1">Scegli cosa monitorare</label></div>
			<div class="col_10">
				<select id="ValoreMon">
				<option value="0">-- Valore --</option>
				<option value="1">Pressione sanguigna</option>
				<option value="2">Battito cardiaco</option>
				<option value="3">Globuli bianchi</option>
				</select>
				<button type="submit" name="val" value="addValore">Aggiungi valore</button>
				</div> <br> <br>
		</h5>
		
	
	</div>
<!-- End Grid -->
</form>
</body>
</html>

<!-- Codice vecchio, formattazione pacco
		
		<div>
		<h5 style="margin-left: 60px; margin-bottom: 40px;">
			<label for="IDPaziente">IDPaziente</label>
			<input id="IDPaziente" type="text" placeholder="IDPaziente" />
			<br> <br> <br>
			
			<label for="nome">Nome</label>
			<input id="nome" type="text" placeholder="Nome" />
			<br> <br> <br>
			
			<label for="cognome">Cognome</label>
			<input id="cognome" type="text" placeholder="Cognome" />
			<br> <br> <br>
			
			<label for="data">Data di nascita</label>
			<input id="data" type="text" placeholder="gg/mm/aaaa" />
			<br> <br> <br>
			
			<label for="cod">Codice fiscale</label>
			<input id="cod" type="text" placeholder="Codice fiscale" />
			<br> <br> <br>
			
			<label for="dataIn">Data di ingresso</label>
			<input id="dataIn" type="text" placeholder="gg/mm/aaaa" />
			<br> <br> <br>
			
			<label for="dataOut">Data di uscita</label>
			<input id="dataOut" type="text" placeholder="gg/mm/aaaa" />
			<br> <br> <br>
			
			<label for="select1">Select Field</label>
			<select id="select1">
			<option value="0">-- Choose --</option>
			<option value="1">Option 1</option>
			<option value="2">Option 2</option>
			<option value="3">Option 3</option>
			</select>
			
		</h5>
		</div>
		-->