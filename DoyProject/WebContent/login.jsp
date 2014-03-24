<!DOCTYPE html>
<html>
<head>
	<!-- META -->
	<title>DoyProject</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta name="description" content="" />
	
	<!-- CSS -->
	<link rel="stylesheet" type="text/css" href="css/kickstart.css" media="all" />
	<link rel="stylesheet" type="text/css" href="css/style.css" media="all" /> 
	
	<!-- Javascript -->
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="js/kickstart.js"></script>
</head>
<body>
<div class="grid">
	<div class="col_12" style="margin-top:100px;">
		<h1 class="center">
		<p><i class="icon-user-md"></i></p>
		LOGIN</h1>
		<h4 style="color:#999;margin-bottom:40px;" class="center">
		
		<!-- Uso della form per delimitare un area i cui submit funzionino con metodo POST e chiamino il ControllerServlet -->
		<form name="loginform"  method="get" action="ControllerServlet" autocomplete="on">
			<!-- Campo input per l'username -->
			<label for="Username">Username</label>
			<input id="Username" type="text" placeholder="Username" />
		
			<br>
			<br>
		
			<!-- Campo input per la password -->
			<label for="Password">Password</label>
			<input id="Password" type="password" placeholder="Password" />
		
			<br>
			<br>
		
			<!-- Due bottoni che fanno entrambi un submit -->
			<button type="submit" name="val" value="signin">Sign In</button> or <button type="submit" name="val" value="signup">Sign Up</button>
			<!-- In questo caso quello che succede è che quando viene premuto un bottone vado a ControllerServlet con .../ControllerServlet?val=signin oppure ?val=signup.
			Nell'url vedo però solo ControllerServlet perché, tramite form, ho detto che il metodo deve essere post. Se fosse stato get avrei visto quanto detto qui sopra.
			Tenendo ogni submit con il nome di "val" nella ControllerServlet mi basterà fare degli if o switch case per differenziare le varie azioni  -->
		</form>
		
		
		</h4>
	</div>
</div> <!-- End Grid -->

</body>
</html>
