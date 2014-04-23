<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Messaggio"%>
<%@ page import="model.User"%>
<%
	Messaggio mx = (Messaggio) session.getAttribute("messaggio");
	User u = (User) session.getAttribute("user");
	
	if(session.getAttribute("readMex") != null)
		System.out.println("messaggio.jsp -> esiste readMex");
	if(session.getAttribute("rispMex") != null)
		System.out.println("messaggio.jsp -> esiste rispMex");
%>

<form method="get" action="ControllerServlet">
	<div class="grid flex">
		<div class="col_12">
			<h1 class="center">
				<!-- la <i> serve per le icone -->
				<i class="icon-eye-open"></i> Messaggio
			</h1>
		</div>

		<div class="col_12 ">
			<label for="Message"><span style="font-size: 1.5em">
					Da:</span> </label> <input class="trans" id="From" type="text" name="sender"
				placeholder="" value="${messaggio.sender}" readonly="readonly" /> <label><span
				style="font-size: 1.5em"> Data:</span></label> <input class="trans"
				id="Date" type="text" name="date" placeholder=""
				value="${messaggio.date}" readonly="readonly" />
		</div>
		<div class="col_12 ">
			<span style="font-size: 1.5em; color: #999">Testo messaggio: </span>
		</div>

		<div class="col_12">
			<textarea id="MessageReceived" placeholder="" style="width: inherit"
				readonly="readonly">${messaggio.message}</textarea>
		</div>

		<%
			if (session.getAttribute("rispMex") == null) {
		%>


		<div class="col_2">
			<button type="submit" class="medium" name="val"
				value="backToMessaggi">
				<i class="icon-chevron-left"></i> Indietro
			</button>
		</div>
		<div class="col_2">
			<button type="submit" class="medium" name="val" value="rispMex">Rispondi</button>
		</div>

		<div class="col_8">
			<button type="submit" class="medium" name="val"
				value="delMex">Elimina</button>
		</div>

		<%
			}
		%>


		<!-- Solo se ho scelto di rispondere al messaggio mostro questa parte -->
		<%
			if (session.getAttribute("rispMex") != null) {
		%>
		<hr class="alt2" />

		<div class="col_12">
			<label for="Message"><span style="font-size: 1.5em">
					Da:</span> ${user.username}</label>
		</div>
		<div class="col_12">
			<textarea id="Message" name="mex"
				placeholder="Scrivi il tuo messaggio" style="width: inherit"></textarea>
		</div>



		<div class="col_2">
			<button type="submit" class="medium" name="val"
				value="backToMessaggi">
				<i class="icon-angle-left"></i> Indietro
			</button>
		</div>
		<div class="col_2">
			<button type="submit" class="medium" name="val" value="sendRisp">Invia</button>
		</div>

		<div class="col_8">
			<button type="submit" class="medium" name="val"
				value="delMex">Elimina</button>
		</div>

		<%
			}
		%>



	</div>

</form>
</body>
</html>