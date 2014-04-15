package controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Paziente;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet doGet");
		// response.setContentType("text/html"); //Setto il tipo di risposta da
		// mandare al client
		String val = request.getParameter("val"); // intercetto il parametro val
		HttpSession session = request.getSession(true); // Con il true, se non
														// esiste già una
														// sessione ne creo una
														// nuova!
		User u = (User) session.getAttribute("user"); // Mi prendo l'utente se
														// esiste già
		// 4)creare il nuovo paziente all'inizio sia del doGet che del doPost
		Paziente p = new Paziente();
		String path = ""; // path che indica la JSP dove voglio andare a seconda
							// delle azioni

		/****
		 * Intercetto le richieste dal menù orizzontale delle pagine jsp
		 * principali (homepage, messaggi, profilo, pazientiLista,
		 * pazientiCategoria)
		 *****/

		// Caso che venga scelta l'homepage
		if ("homepage".equals(val)) {
			path = "/WEB-INF/homepage";
		}

		// Caso che venga cliccata la voce messaggi
		if ("messaggi".equals(val)) {
			path = "/WEB-INF/messaggi";
		}

		// Caso che venga scelta la pagina del profilo del dottore (dal menù in
		// alto)
		if ("profiloDoc".equals(val)) {

			// Devo mostrare il profilo del dottore
			// Non devo creare un nuovo utente perché c'è già per forza

			if (!u.getProfileU() || u == null) {
				path = "/WEB-INF/Err";
				System.out.println("Err"); // Fare questa pagina di errore!!!!!!
											// ****************************************************
			} else {
				path = "/WEB-INF/profiloDoc";
				System.out.println("profiloDoc");
			}
		}

		// Caso che venga scelta la voce dei pazienti listati
		if ("pazientiLista".equals(val)) {
			// 5)prima di fare il setAttribute chiamare il metodo per
			// visualizzare così setta il valore giusto
			// andare nella pagina dove si vuole costruire la tabella
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiLista";
		}

		// Caso che venga scelta la voce dei pazienti in categorie
		if ("pazientiCategoria".equals(val)) {
			// come faccio ad avere i vari reparti?
			System.out.println("reparti: " + u.getDep1());
			
			p.viewReparto();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiCategoria";
		}

		// Si arriva qui quando voglio aggiungere un paziente
		if ("aggPaziente".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazienteMod";
		}

		// quì ci arriva quando si schiaccia l'icona con la penna nella tabella
		// dei pazienti
		if ("modPaziente".equals(val)) {
			System.out.println("cosa ho selezionato: " + session.getAttribute("paziente"));
			// con queste due righe ricreo l'array di tutti i pazienti
			p.viewPaziente();
			session.setAttribute("paziente", p);
			session.setAttribute("IDpaz", request.getParameter("ID"));
			// adesso devo passare l'ID del paziente da modificare alla pagina
			// pazienteMod
			p.setInserito(2);
			path = "/WEB-INF/pazienteMod";
		}
		// se dalla pagina pazientiLista schiacchio il tasto per cancellare il
		// paziente
		if ("delPaziente".equals(val)) {
			System.out.println("cancellare il paziente "
					+ request.getParameter("ID") + "?");
			// JOptionPane.showMessageDialog(null, "cancellare il paziente " +
			// request.getParameter("ID") + "?");
			// JOptionPane.showMessageDialog(this, "cacca");
			p.removePaziente(request.getParameter("ID"));
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiLista";
		}

		// Questo chiude la sessione e quindi invalida tutti i dati della
		// sessione, forzando subito la schermata di login
		if ("signout".equals(val)) {
			session.invalidate();
			System.out.println("signout");
			response.sendRedirect("signin.jsp");
			return;		//serve il return perché tutte le varie istruzioni come il redirect non fanno bloccare il codice,
						//quindi anche il codice successivo a questo è fatto girare. E da errore.
		}

		// Metodo finale che mi rimanda alla pagina giusta.
		String url = path + ".jsp";
		try {
			request.getRequestDispatcher(url).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet doPost");
		String val = request.getParameter("val"); // Intercetto il parametro val
		HttpSession session = request.getSession(true); // Lavoro a livello di
														// sessione
		User u = (User) session.getAttribute("user"); // Se c'è già un utente lo
														// prendo
		// Paziente p = (Paziente) session.getAttribute("paziente"); //Se c'è
		// già un paziente lo prendo
		Paziente p = new Paziente();
		String path = ""; // path che indica la JSP dove voglio andare a seconda
							// delle azioni

		// caso del signin
		if ("signin".equals(val)) {
			System.out.println("username che setto: "
					+ request.getParameter("username"));

			u = new User(request.getParameter("username")); // creo un oggetto
															// utente con
															// l'username
															// passato

			// Se non si trova corrispondenza nel database (e lo fa la Servlet)
			// oppure l'utente è nullo allora sto nel login
			if (!u.signinU(request.getParameter("password")) || u == null)
				path = "/WEB-INF/signinErr";
			// altrimenti vado alla homepage
			else {
				session.setAttribute("user", u);
				path = "/WEB-INF/homepage";
			}

		}

		// Subito si pensava di farlo con il metodo get, poi però essendo che
		// c'è la possibilità che un utente, nel momento in cui clicca su
		// signup,
		// abbia già inserito username e password, questi sarebbero passati
		// dall'URL con GET e quindi non sarebbe stato troppo sicuro.
		if ("signup".equals(val)) {
			path = "/WEB-INF/signup";

		}

		// Se arrivo dal bottone "Annulla" dalla pagina di signup. Come prima,
		// mi tocca farlo in post perché altrimenti i dati viaggiano in GET.
		if ("annullaSignup".equals(val)) {
			path = "signin";
		}

		// bottoni aggiungi paziente e annulla. Dalla pagina pazienteMod
		// riportano a pazientiLista
		if ("insPaziente".equals(val)) {
			// Paziente p = new Paziente(request.getParameter("IDPaziente"));
			p.insPaziente(p.getIDDisp(), request.getParameter("nome"), request.getParameter("cognome"),
					request.getParameter("dataNascita").toString(), request.getParameter("codFisc"),
					request.getParameter("dataIn").toString(), request.getParameter("reparto"));
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiLista";
		}

		if ("annPaziente".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiLista";
		}

		// quì ci arriva quando si schiaccia l'icona con la penna nella tabella dei pazienti
		// in teoria non serve nel doPost ma solo nel doGet
		/*if ("modPaziente".equals(val)) {
			path = "/WEB-INF/pazienteMod";
		}*/

		// due funzioni per modificare il paziente
		if ("insModPaziente".equals(val)) {
			// Paziente p = new Paziente(request.getParameter("IDPaziente"));
			//System.out.println("IDPaziente: " + session.getAttribute("IDpaz").toString());
			p.modificaPaziente(session.getAttribute("IDpaz").toString(), request.getParameter("nome"), request.getParameter("cognome"),
					request.getParameter("dataNascita").toString(), request.getParameter("codFisc"),
					request.getParameter("dataIn").toString(), request.getParameter("dataOut").toString(),
					request.getParameter("reparto"));
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiLista";
		}

		if ("annModPaziente".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiLista";
		}
		
		/*MF*/
		if("confermaSignup".equals(val)){
			System.out.println("confermaSignup");
			u = new User(request.getParameter("username"));
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String cpassword = request.getParameter("cpassword");
			String name = request.getParameter("nome");
			String surname = request.getParameter("surname");
			Date birthdate = Date.valueOf(request.getParameter("birthdate"));
			
			//Controllo dei campi inseriti correttamente (non l'obbligatorietà perché quella è fatta tramite HTML5 però comunque ricontrollata)
			
			//se c'è qualche errore allora torno alla pagina di signup ma quella di errore. Prima recupero i messaggi di errore e li setto nella sessione.
			if(u.checkSignup(username, password, cpassword, name, surname, birthdate)){
				session.setAttribute("err_username",u.getError("username"));
				session.setAttribute("err_password", u.getError("password"));
				session.setAttribute("err_name", u.getError("nome"));
				session.setAttribute("err_cognome", u.getError("cognome"));
				session.setAttribute("err_birthdate", u.getError("birthdate"));
				path = "/WEB-INF/signupErr";
			}
			
			else{
				//*********+++DA FARE L'ELSE CON IL PASSAGGIO DALLA FUNZIONE DI REGISTRAZIONE SUL DATABASE(da fare direi) e il passaggio alla pagina di login eliminando però prima i dati di sessione
			}
			
		}

		// Prima di uscire dal post, raccolgo quello che ho seminato, e vado
		// dove devo andare.
		String url = path + ".jsp";
		try {
			request.getRequestDispatcher(url).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
