<%@ include file="/WEB-INF/header.jsp"%>
<%@ page import="model.Messaggio"%>
<%@ page import="model.User"%>
<%@ page import="java.util.ArrayList"%>
<%
	Messaggio mx = (Messaggio) session.getAttribute("messaggio");
	User u = (User) session.getAttribute("user");

	//DEBUG ////////////////////////////////////////////////////
	if (session.getAttribute("readMex") != null)
		System.out.println("messaggio.jsp -> esiste readMex");
	if (session.getAttribute("rispMex") != null)
		System.out.println("messaggio.jsp -> esiste rispMex");
	////////////////////////////////////////////////////////////
%>

<form method="get" action="ControllerServlet">
	<div class="grid flex">
		<div class="col_12">
			<h1 class="center">
				<!-- la <i> serve per le icone -->
				<i class="icon-eye-open"></i> Messaggio
			</h1>
		</div>

		<!-- Campo del messaggio ricevuto -->
		<%
			if (session.getAttribute("newMex") == null) {
		%>

		<div class="col_12 ">
			<label for="Message"><span style="font-size: 1.5em">
					Da:</span> </label> <input class="trans" id="From" type="text" name="sender"
				placeholder="" value="${messaggio.sender}" readonly="readonly" /> <label><span
				style="font-size: 1.5em"> Data ricezione:</span></label> <input class="trans"
				id="Date" type="text" name="date" placeholder=""
				value="${messaggio.date}" readonly="readonly" />
		</div>
		<div class="col_12 ">
			<span style="font-size: 1.5em; color: #999">Testo messaggio: </span>
		</div>

		<div class="col_12">
			<textarea <%if(mx.getSender().contains("ALERT")) { %> style="border:1px solid #FF0000" <%} %>id="MessageReceived" placeholder="" style="width: inherit"
				readonly="readonly">${messaggio.message}</textarea>
		</div>

		<%
			}
		%>

		<!-- Mostro questi bottoni solo se devo rispondere a un messaggio -->

		<%
			if (session.getAttribute("rispMex") == null
					&& session.getAttribute("newMex") == null) {
		%>


		<div class="col_2">
			<button type="submit" class="medium" name="val"
				value="backToMessaggi">
				<i class="icon-chevron-left"></i> Indietro
			</button>
		</div>
		<% if(mx.getSender().contains("ALERT")) { 
		%>
		<div class="col_2">
			<button type="submit" class="medium" name="val" value="rispMex" disabled>Rispondi</button>
		</div>
		<%} else  {%>
		<div class="col_2">
			<button type="submit" class="medium" name="val" value="rispMex" >Rispondi</button>
		</div> 
		<%} %>

		<div class="col_8">
			<button type="submit" class="medium" name="val" value="delMex">Elimina</button>
		</div>

		<%
			}
		%>


		<!-- Campo di scrittura del messaggio e bottoni corrispondenti. Solo se ho scelto di rispondere, 
		o inviare un nuovo messaggio mostro questa parte -->
		<%
			if (session.getAttribute("rispMex") != null
					|| session.getAttribute("newMex") != null) {
		%>
		<hr class="alt2" />

		<div class="col_12">
			<label for="Message"><span style="font-size: 1.5em">
					Da:</span> ${user.username}</label>
		</div>
		<%
			if (session.getAttribute("newMex") != null) {
				ArrayList<String> docs = new ArrayList<String>(u.getDocs());
		%>
		<div class="col_1">
			<label for="Message"><span style="font-size: 1.5em">
					A:</span></label>
		</div>
		<div class="col_11">
		 <select id="select" name="option">
				<% for(byte i=0; i<docs.size(); ++i){
				out.print("<option value=\"" + docs.get(i) + "\">" + docs.get(i) + "</option>");	//I vari campi hanno nome option. Posso selezionare solo una opzione.
				} %>
			</select>
		</div>
		<%
			}
		%>
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
			<button type="submit" class="medium" name="val" value="delMex">Elimina</button>
		</div>

		<%
			}
		%>



	</div>

</form>
</body>
</html>