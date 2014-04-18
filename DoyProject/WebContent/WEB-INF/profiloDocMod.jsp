<!-- NOTA: TALE PAGINA è DA METTERE A POSTO CON UNA GRAFICA MIGLIORE. -->

<%@ page import="model.Reparto"%>
<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList"%>
<%@ include file="/WEB-INF/header.jsp"%>
<% 	//User u = (User) session.getAttribute("user");
	//Reparto r = (Reparto) session.getAttribute("reparto");%>


<!-- I campi in questa pagina per adesso sono:
username
password
cpassword
nome
cognome
birthdate 
-->

<body>
	<form name="signupModificationForm" method="post" action="ControllerServlet"
		autocomplete="off">
		<!-- il grid flex serve per adattare la pagina al resize della finestra del browser -->
		<div class="grid flex">
			<div class="col_12" style="margin-top: 20px;">
				<h1 class="center"><i class="icon-edit"></i>MODIFICA PROFILO</h1>


				<!-- Prima parte con dei dati di registrazione come Username e Password -->
				<div class="div_head">
					<h4>Dati di login</h4>
				</div>

				<!-- HR -->
				<hr class="alt2" />

				<div class="div_p">
					<p> Non è possibile, modificare l'username usato per il login del sistema.</p>
				</div>


				<!-- Campo input per l'username, ricorda che il "name" Ã¨ il campo che uso per riferirmi a questo campo -->

				<div class="col_3">
					<label for="Username">Username</label>
				</div>
				<div class="col_9">
	

					<input id="Username" type="text" name="username"
						placeholder="Username" value="${user.username}" readonly="readonly"/>		
					<span class="tooltip-right"
						title="Username: Lettere e numeri sono consigliati"><i
						class="icon-info-sign"></i></span>
				</div>
				<br> <br>

				<!-- Campo input per la password -->

				<div class="col_3">
					<label for="Password">Password</label>
				</div>
				<div class="col_9">
					
					<input id="Password" type="password" name="password"
						placeholder="Password"
						value="" required />
	
					<span class="tooltip-right"
						title="Password: Deve essere almeno lunga 4 e composta da numeri e lettere"><i
						class="icon-info-sign"></i></span>
				</div>

				<br> <br>


				<!-- Campo per la conferma della password -->
				<div class="col_3">
					<label for="cPassword">Conferma Password</label>
				</div>
				<div class="col_9">
					<input id="cPassword" type="password" name="cpassword"
						placeholder="Ridigita la Password"
						style="background-color: #f2dede" required />
				</div>



				<!-- Seconda parte con i dati personali dell'utente -->
				<br> <br> <br>

				<div class="div_head">
					<h4 style="margin-left: 10">Informazioni Personali</h4>
				</div>
				<!-- HR -->
				<hr />
				<p>E' possibile modificare qualsiasi tipo di dato personale. Il personale addetto sarà informato delle modifiche per ulteriori
				possibili controlli.</p>


				<div class="col_3">
					<label for="Nome">Nome</label>
				</div>


				<div class="col_9">
					
					<input id="Nome" type="text" name="nome" placeholder="Nome"
						value="${user.name}" required />
				</div>
				<br> <br>

				<div class="col_3">
					<label for="Cognome">Cognome</label>
				</div>

				<div class="col_9">
					<input id="Cognome" type="text" name="cognome"
						placeholder="Cognome" value="${user.surname}"
						required />
				</div>
				<br> <br>

				<div class="col_3">
					<label for="Birthdate">Data di nascita</label>
				</div>

				<div class="col_9">
					<input id="Birthdate" type="date" name="birthdate" placeholder=""
						value="${user.birthdate}" />
				</div>
				<br> <br>

				<!-- Checkbox che creo dinamicamente-->
				<div class="col_3">
					<fieldset>
						<legend>Reparti</legend>
						<%
						Reparto r = (Reparto) session.getAttribute("reparto");
						if(r == null)
							r = new Reparto();
							ArrayList<String> deps = new ArrayList<String>(r.getAllReparti());
							for (byte i = 0; i < deps.size(); i++) {
								out.println("<input type=\"checkbox\" id=\"check" + i
										+ "\" class=\"checkbox\" name=\"check" + i
										+ "\" value=\"" + deps.get(i)
										+ "\"/> <label for=\"check" + i
										+ "\" class=\"inline\">" + deps.get(i)
										+ "</label> <br>");
							}

							// 							byte i = 0;
							// 							String rep = "1";
							// 							while (!rep.equals("")) {
							// 								rep = r.getReparti1by1();
							// 								if (!rep.equals("")) {
							// 									out.println("<input type=\"checkbox\" id=\"check" + i
							// 											+ "\" class=\"checkbox\" name=\"check" + i
							// 											+ "\" value=\"" + rep + "\"/> <label for=\"check"
							// 											+ i + "\" class=\"inline\">" + rep
							// 											+ "</label> <br>");
							// 									i++;
							// 								}
							// 							}

						%>
					</fieldset>
				</div>


				<div class="col_12">
					<!-- Bottoni conferma e annulla -->
					<br> <br>
					<button class="green" type="submit" name="val"
						value="acceptMod">Modifica</button>
					<!-- In questo caso formnovalidate evita di cercare i campi obbligatori anche quando faccio "annulla", non funziona in Safari -->
					<button class="blue" type="submit" name="val" value="annullaModSignup"
						formnovalidate>Annulla</button>
				</div>




			</div>
		</div>
		<!-- End Grid -->
	</form>


</body>
</html>