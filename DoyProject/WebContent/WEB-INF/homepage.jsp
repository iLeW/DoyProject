<!-- Inclusione del JSP con il menù principale -->
<%@ include file="/WEB-INF/headerMenu.jsp"%>
<div class="grid flex">
	<div class="col_12" style="margin-top: 100px;">
		<h1 class="center">
			<!-- la <i> serve per le icone -->
			<i class="icon-eye-open"></i> Benvenuto Dott. ${user.username}	<!-- Questo è un utilizzio delle JSP 2.x, tale metodo è possibile perché il JavaBean User.java ha i metodi getter e setter -->
		</h1>
		<h4 style="color: #999; margin-bottom: 40px;" class="center">
			I messaggi sono:</h4>
	</div>
</div>
<!-- End Grid -->
</body>
</html>