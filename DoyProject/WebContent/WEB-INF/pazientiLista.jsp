<%@ include file="/WEB-INF/headerMenu.jsp"%>


<form method="get" action="ControllerServlet">
	<div class="grid flex">
		<h1 class="center">
			<i class="icon-hospital"></i> Elenco pazienti
		</h1>
	
		<br>
	
		<div class="center">
			<button class="green" type="submit" name="val" value="aggPaziente">Aggiungi paziente</button>
			<!-- value="addPaziente" non va bene, forse java strippa se chiami add? -->
		</div>

	</div>

</form>
</body>
</html>