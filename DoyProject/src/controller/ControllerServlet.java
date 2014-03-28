package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("text/html");			//Setto il tipo di risposta da mandare al client
	    String val = request.getParameter("val");		//intercetto il parametro val
	    HttpSession session = request.getSession(true);	//Con il true, se non esiste gi� una sessione ne creo una nuova!
		User u = (User) session.getAttribute("user");	//Mi prendo l'utente se esiste gi�
		String path=""; 								//path che indica la JSP dove voglio andare a seconda delle azioni	
		
		/****Intercetto le richieste dal men� orizzontale delle pagine jsp principali (homepage, messaggi, profilo, pazientiLista, pazientiCategoria)*****/
		
		//Caso che venga scelta l'homepage
		if("homepage".equals(val)){
			path = "/WEB-INF/homepage";
		}
		
		//Caso che venga cliccata la voce messaggi
		if("messaggi".equals(val)){
			path = "/WEB-INF/messaggi";
		}
		
		//Caso che venga scelta la pagina del profilo del dottore
		if("profiloDoc".equals(val)){
			path = "/WEB-INF/profiloDoc";
		}
		
		//Caso che venga scelta la voce dei pazienti listati
		if("pazientiLista".equals(val)){
			path = "/WEB-INF/pazientiLista";
		}
		
		//Caso che venga scelta la voce dei pazienti in categorie
		if("pazientiCategoria".equals(val)){
			path = "/WEB-INF/pazientiCategoria";
		}
		
		
		//Metodo finale che mi rimanda alla pagina giusta.
		String url = path + ".jsp";
		try{
			request.getRequestDispatcher(url).forward(request, response);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String val = request.getParameter("val");		//Intercetto il parametro val
		HttpSession session = request.getSession(true);	//Lavoro a livello di sessione
		User u = (User) session.getAttribute("user");	//Se c'� gi� un utente lo prendo
		String path = "";								//path che indica la JSP dove voglio andare a seconda delle azioni
		
		
		//caso del signin
		if("signin".equals(val)){
			System.out.println("username che setto: " + request.getParameter("username"));
			
			u = new User(request.getParameter("username"));	//creo un oggetto utente con l'username passato
			
			//Se non si trova corrispondenza nel database (e lo fa la Servlet) oppure l'utente � nullo allora sto nel login
			if(!u.signinU(request.getParameter("password")) || u==null )
				path = "/WEB-INF/signinErr";
			//altrimenti vado alla homepage
			else {
				session.setAttribute("user", u);
				path="/WEB-INF/homepage";
			}
			
		}
		
		//per andare alla pagina signup
		if("signup".equals(val)){
			path="/WEB-INF/signup";
			
		}
		
		//bottone per l'aggiunta di un paziente
		if("aggPaziente".equals(val)){
			path="/WEB-INF/pazienteMod";
			
		}
		
		//Prima di uscire dal post, raccolgo quello che ho seminato, e vado dove devo andare.
		String url = path + ".jsp";
		try{
			request.getRequestDispatcher(url).forward(request, response);
			
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
