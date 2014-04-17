<!-- NOTA: TALE PAGINA è DA METTERE A POSTO CON UNA GRAFICA MIGLIORE. -->

<%@ page import="model.Reparto"%>
<%@ page import="java.util.ArrayList"%>
<%@ include file="/WEB-INF/header.jsp"%>
<!-- Inclusione dell'header per la formattazione della pagina -->

<!-- Con questa Scriptlet vado a prendere i valori di username e password che l'utente potrebbe aver inserito nel campo nome e password -->
<%
	String username = request.getParameter("username").toString();
	String password = request.getParameter("password").toString();
	System.out.println("username:" + username + ", password:"
			+ password);

	Reparto r = (Reparto) session.getAttribute("reparto");
%>

<!-- I campi in questa pagina per adesso sono:
username
password
cpassword
nome
cognome
birthdate 
-->

<body>
	<form name="signupform" method="post" action="ControllerServlet"
		autocomplete="off">
		<!-- il grid flex serve per adattare la pagina al resize della finestra del browser -->
		<div class="grid flex">
			<div class="col_12" style="margin-top: 20px;">
				<h1 class="center">SIGN UP</h1>

				<!-- Error -->

				<!-- Error -->
				<div class="notice error">
					<i class="icon-remove-sign icon-large"></i> Errore inserimento dati. Controllare i campi evidenziati in rosso.
					<a href="#close" class="icon-remove"></a>
				</div>


				<!-- Prima parte con dei dati di registrazione come Username e Password -->
				<div class="div_head">
					<h4>Registrazione</h4>
				</div>

				<!-- HR -->
				<hr class="alt2" />

				<div class="div_p">
					<p>Inserire i dati relativi all'accesso al sistema quali
						Username e Password. I campi evidenziati in arancione sono
						obbligatori.</p>
				</div>





				<!-- Campo input per l'username, ricorda che il "name" Ã¨ il campo che uso per riferirmi a questo campo -->

				<div class="col_3">
					<label for="Username">Username</label>
				</div>
				<div class="col_9">
					<%
						if (session.getAttribute("err_username").equals("")) {
					%>

					<input id="Username" type="text" name="username"
						placeholder="Username" value="" required />
					<%
						} else {
					%>
					<input id="Username" class="error" type="text" name="username"
						value="<%=session.getAttribute("username")%>" required />
					<%
						}
					%>
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
					<%
						if (session.getAttribute("err_password").equals("")) {
					%>
					<input id="Password" type="password" name="password"
						placeholder="Password"
						value="<%=session.getAttribute("password")%>" required />
					<%
						} else {
					%>
					<input id="Password" class="error" type="password" name="password"
						value="<%=session.getAttribute("password")%>" required />
					<%
						}
					%>
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
				<p>Inserire i dati personali. I campi evidenziati in arancione
					sono obbligatori. E' obbligatorio selezionare almeno uno dei
					reparti di appartenenza.</p>


				<div class="col_3">
					<label for="Nome">Nome</label>
				</div>


				<div class="col_9">
					<%
						if (session.getAttribute("err_name").equals("")) {
					%>
					<input id="Nome" type="text" name="nome" placeholder="Nome"
						value="<%=session.getAttribute("name")%>" required />
					<%
						} else {
					%>
					<input id="Nome" class="error" type="text" name="nome"
						value="<%=session.getAttribute("name")%>" required />
					<%
						}
					%>
				</div>
				<br> <br>

				<div class="col_3">
					<label for="Cognome">Cognome</label>
				</div>

				<div class="col_9">
					<%
						if (session.getAttribute("err_surname").equals("")) {
					%>
					<input id="Cognome" type="text" name="cognome"
						placeholder="Cognome" value="<%=session.getAttribute("surname")%>"
						required />
					<%
						} else {
					%>
					<input id="Cognome" class="error" type="text" name="cognome"
						value="<%=session.getAttribute("surname")%>" required />
					<%
						}
					%>
				</div>
				<br> <br>

				<div class="col_3">
					<label for="Birthdate">Data di nascita</label>
				</div>

				<div class="col_9">
					<%
						if (session.getAttribute("err_birthdate").toString().equals("")) {
					%>
					<input id="Birthdate" type="date" name="birthdate" placeholder=""
						value="<%=session.getAttribute("birthdate")%>" />
					<%
						} else {
					%>
					<input id="Birthdate" class="error" type="date" name="birthdate"
						value="<%=session.getAttribute("birthdate")%>" required />
					<%
						}
					%>
				</div>
				<br> <br>

				<!-- Checkbox che creo dinamicamente-->
				<div class="col_3">
					<%
						//Reparto r = new Reparto();
					%>
					<fieldset class="error">
						<legend class="error">Reparti</legend>
						<%
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

							session.setAttribute("reparto", r); //****NON SO SE E' CORRETTO SETTARE QUA NEL MVC. A parte che non so nemmeno se serve settare il reparto.
						%>
					</fieldset>
				</div>


				<div class="col_12">
					<!-- Bottoni conferma e annulla -->
					<br> <br>
					<button class="green" type="submit" name="val"
						value="confermaSignup">Conferma</button>
					<!-- In questo caso formnovalidate evita di cercare i campi obbligatori anche quando faccio "annulla", non funziona in Safari -->
					<button class="blue" type="submit" name="val" value="annullaSignup"
						formnovalidate>Annulla</button>
				</div>




			</div>
		</div>
		<!-- End Grid -->
	</form>


</body>
</html>

</body>
</html>