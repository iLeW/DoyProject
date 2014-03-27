<%@ include file="/WEB-INF/header.jsp" %>

<body>
<form name="loginform"  method="post" action="ControllerServlet" autocomplete="on">
<!-- il grid flex serve per adattare la pagina al resize della finestra del browser -->
<div class="grid flex">
	<div class="col_12" style="margin-top:100px;">
		<h1 class="center">
		<i class="icon-bullhorn icon-4x"></i>
		SIGN UP</h1>
		<h4 style="color:#999; margin-bottom:40px;" class="center">
		
		<!-- Uso della form per delimitare un area i cui submit funzionino con metodo POST e chiamino il ControllerServlet -->
		
			<!-- Campo input per l'username, ricorda che il "name" è il campo per uso per riferirmi a questo campo -->
			<label for="Username">Username</label>
			<input id="Username" type="text" name="username" placeholder="Username" />
		
			<br>
			<br>
		
			<!-- Campo input per la password -->
			<label for="Password">Password</label>
			<input id="Password" type="password" name ="password" placeholder="Password" />
		
			<br>
			<br>
		
			<!-- Un solo bottone per la conferma -->
			<button type="submit" name="val" value="conferma">Conferma</button>
			<!-- In questo caso quello che succede è che quando viene premuto un bottone vado a ControllerServlet con .../ControllerServlet?val=signin oppure ?val=signup.
			Nell'url vedo però solo ControllerServlet perché, tramite form, ho detto che il metodo deve essere post. Se fosse stato get avrei visto quanto detto qui sopra.
			Tenendo ogni submit con il nome di "val" nella ControllerServlet mi basterà fare degli if o switch case per differenziare le varie azioni  -->
			
				
		
		</h4>
	</div>
</div> <!-- End Grid -->
</form>

</body>
</html>
