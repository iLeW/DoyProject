package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Messaggio;
import model.Monitoraggio;
import model.Paziente;
import model.Reparto;
import model.Storico;
import model.User;

/**
 * Servlet implementation class ControllerServlet
 */
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ControllerServlet() {
		super();
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
		Storico s = new Storico();
		Paziente p = new Paziente();
		Monitoraggio m = new Monitoraggio();

		Messaggio mx; // Variabile messaggio

		String path = ""; // path che indica la JSP dove voglio andare a seconda
		// delle azioni

		/****
		 * Intercetto le richieste dal menù orizzontale delle pagine jsp
		 * principali (homepage, messaggi, profilo, pazientiLista,
		 * pazientiCategoria)
		 *****/

		// Caso che venga scelta l'homepage
		if ("homepage".equals(val)) {
			if (session.getAttribute("fromMessaggi") != null)
				session.removeAttribute("fromMessaggi"); // Rimuovo l'attributo
			// messaggi se c'è
			// (Serve per capire
			// dove sono per la
			// tabella)
			session.setAttribute("fromHome", "1"); // ***//
			path = "/WEB-INF/homepage";
		}

		// Caso che venga cliccata la voce messaggi
		if ("messaggi".equals(val)) {
			if (session.getAttribute("fromHome") != null)
				session.removeAttribute("fromHome");
			mx = new Messaggio(); // La assegno se arrivo qua. In ogni caso la
			// creo sempre nuova,
			// perché nel frattempo potrebbero essermi
			// arrivati nuovi messaggi
			mx.readAndSetMex(u.getUsername()); // Leggo e setto i messaggi
			session.setAttribute("messaggio", mx); // Setto l'oggetto nella
			// sessione
			session.setAttribute("fromMessaggi", "1"); // ***//
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
			p.viewReparto(u.getDep1(), u.getDep2(), u.getDep3());
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
			// System.out.println("cosa ho selezionato: " +
			// session.getAttribute("paziente"));
			// con queste due righe ricreo l'array di tutti i pazienti
			p.viewPaziente();
			session.setAttribute("paziente", p);
			session.setAttribute("IDpaz", request.getParameter("ID"));
			// adesso devo passare l'ID del paziente da modificare alla pagina
			// pazienteMod
			p.setInserito(2);
			path = "/WEB-INF/pazienteMod";
		}

		if ("modPazienteCat".equals(val)) {
			// System.out.println("cosa ho selezionato: " +
			// session.getAttribute("paziente"));
			// con queste due righe ricreo l'array di tutti i pazienti
			p.viewPaziente();
			session.setAttribute("paziente", p);
			session.setAttribute("IDpaz", request.getParameter("ID"));
			// adesso devo passare l'ID del paziente da modificare alla pagina
			// pazienteMod
			p.setInserito(2);
			session.setAttribute("categoria", 1);
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

		if ("delPazienteCat".equals(val)) {
			System.out.println("cancellare il paziente "
					+ request.getParameter("ID") + "?");
			// JOptionPane.showMessageDialog(null, "cancellare il paziente " +
			// request.getParameter("ID") + "?");
			// JOptionPane.showMessageDialog(this, "cacca");
			p.removePaziente(request.getParameter("ID"));
			p.viewPaziente();
			session.setAttribute("paziente", p);
			session.setAttribute("categoria", 1);
			path = "/WEB-INF/pazientiLista";
		}

		// quando si chiaccia l'icona per il profilo utente
		if ("profiloPaziente".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			session.setAttribute("IDpaz", request.getParameter("ID"));
			m.viewAllMonitoraggi();
			session.setAttribute("monitoraggio", m);
			// session.setAttribute("valStorico", "");
			session.setAttribute("storico", s);
			path = "/WEB-INF/paziente";
		}
		if ("profiloPazienteCat".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			session.setAttribute("IDpaz", request.getParameter("ID"));
			session.setAttribute("categoria", 1);
			m.viewAllMonitoraggi();
			session.setAttribute("monitoraggio", m);
			// session.setAttribute("valStorico", "");
			session.setAttribute("storico", s);
			path = "/WEB-INF/paziente";
		}
		// per eliminare un valore seguito
		if ("delMonitor".equals(val)) {
			System.out.println("cancellare il valore monitorato "
					+ request.getParameter("ID") + ", "
					+ request.getParameter("VAL") + "?");
			// JOptionPane.showMessageDialog(null, "cancellare il paziente " +
			// request.getParameter("ID") + "?");
			// JOptionPane.showMessageDialog(this, "cacca");
			m.removeValore(request.getParameter("ID"),
					request.getParameter("VAL"));
			m.viewAllMonitoraggi();
			session.setAttribute("monitoraggio", m);
			path = "/WEB-INF/paziente";
		}

		if ("modProfilo".equals(val)) {
			System.out.println("Modifica del profilo");
			// Qua l'utente esiste già per forza perché è loggato dentro
			path = "/WEB-INF/profiloDocMod";
		}

		// Questo chiude la sessione e quindi invalida tutti i dati della
		// sessione, forzando subito la schermata di login
		if ("signout".equals(val)) {
			session.invalidate();
			System.out.println("signout");
			response.sendRedirect("signin.jsp");
			return; // serve il return perché tutte le varie istruzioni come il
			// redirect non fanno bloccare il codice,
			// quindi anche il codice successivo a questo è fatto
			// girare. E da errore.
		}

		// ///////////////////////////////
		// HREF DALLE TABELLE DEI MESSAGGI
		// ///////////////////////////////

		System.out.println("VAL: " + val);

		// Se premo l'azione di lettura
		if ("readMex".equals(val)) {
			mx = (Messaggio) session.getAttribute("messaggio");

			// Metto subito il messaggio come letto però solo se non è già stato
			// letto
			if (request.getParameter("r") == null) {
				String stringDate = this
						.stringdateToMYSQLFormattedStringdate(request
								.getParameter("date"));
				mx.markAsReadMex(u.getUsername(),
						request.getParameter("sender"), stringDate);
				mx.readAndSetMex(u.getUsername());
			}

			// Setto comunque i valori del messaggio
			mx.setMex(u.getUsername(), request.getParameter("sender"), this
					.stringdateToMYSQLFormattedStringdate(request
							.getParameter("date")));

			session.setAttribute("readMex", "1");

			// Poi vado a chiamare la pagina messaggio.jsp
			path = "/WEB-INF/messaggio";
		}

		// Se premo l'azione di risposta
		if ("rispMex".equals(val)) {
			mx = (Messaggio) session.getAttribute("messaggio");
			session.setAttribute("rispMex", "1"); /*
												 * Perché uso la stessa pagina
												 * di lettura e di risposta,
												 * mettendo questo attributo
												 * posso modificarla per la
												 * risposta (con tasti appositi)
												 */

			System.out.println("DATE: " + request.getParameter("date"));
			// Setto anche qui i valori del messaggio
			mx.setMex(u.getUsername(), request.getParameter("sender"), this
					.stringdateToMYSQLFormattedStringdate(request
							.getParameter("date")));

			path = "/WEB-INF/messaggio";
		}

		// Se premo l'azione di delete
		if ("delMex".equals(val)) {

			// Elimino l'attributo newMex se era presente
			if (session.getAttribute("newMex") != null)
				session.removeAttribute("newMex");

			mx = (Messaggio) session.getAttribute("messaggio");

			String stringDate = request.getParameter("date");

			// Solo se non sono nella pagina messaggio
			if (session.getAttribute("rispMex") == null
					&& session.getAttribute("readMex") == null) {
				stringDate = this.stringdateToMYSQLFormattedStringdate(request
						.getParameter("date"));
			}

			boolean result = mx.deleteMex(u.getUsername(),
					request.getParameter("sender"), stringDate);
			System.out.println("************: RESULT : " + result);
			if (result) {
				if (session.getAttribute("fromHome") != null) {
					mx.readAndSetMex(u.getUsername()); /*
														 * Invece di fare questo
														 * sarebbe stato meglio
														 * gestire gli array
														 * direttamente, dalla
														 * classe messaggio, per
														 * evitare questa
														 * chiamata in più al
														 * database. Oppure
														 * comunque questa
														 * chiamata lasciarla
														 * dentro alla classe
														 * Messaggio.
														 */

					// session.setAttribute("messaggio", mx); //NON SERVE
					// RISETTARE L'ATTRIBUTO !!!
					path = "/WEB-INF/homepage";
				} else {
					mx.readAndSetMex(u.getUsername());
					// session.setAttribute("messaggio", mx);
					path = "/WEB-INF/messaggi";
				}

			}
		}

		// Se premo l'azione di messaggio gestito
		if ("gestAlert".equals(val)) {

			// Elimino l'attributo newMex se era presente
			if (session.getAttribute("newMex") != null)
				session.removeAttribute("newMex");

			mx = (Messaggio) session.getAttribute("messaggio");

			String stringDate = request.getParameter("date");

			// Solo se non sono nella pagina messaggio
			if (session.getAttribute("rispMex") == null
					&& session.getAttribute("readMex") == null) {
				stringDate = this.stringdateToMYSQLFormattedStringdate(request
						.getParameter("date"));
			}

			boolean result = mx.deleteAlerts(request.getParameter("sender"), stringDate);
			
			if(result){
				mx.readAndSetMex(u.getUsername());	//Ricarico i messaggi
				path = "/WEB-INF/messaggi";
			} else {
				path = "/WEB-INF/messaggio";
			}
			
		}

		// se premo l'azione di segnalazione come letto
		if ("okReadMex".equals(val)) {
			mx = (Messaggio) session.getAttribute("messaggio");
			String stringDate = this
					.stringdateToMYSQLFormattedStringdate(request
							.getParameter("date"));
			boolean result = mx.markAsReadMex(u.getUsername(),
					request.getParameter("sender"), stringDate);
			if (result) {
				if (session.getAttribute("fromHome") != null) {
					mx.readAndSetMex(u.getUsername());
					// session.setAttribute("messaggio", mx);
					path = "/WEB-INF/homepage";
					/*
					 * session.removeAttribute("fromHome"); Lo rimuovo
					 * altrimenti sarà sempre settato e tutta questa cosa non
					 * funzionerebbe più
					 */
				} else {
					mx.readAndSetMex(u.getUsername());
					// session.setAttribute("messaggio", mx);
					path = "/WEB-INF/messaggi";
				}

			}
		}

		// //////////////////////////////
		// DENTRO LA PAGINA messaggio.jsp
		// //////////////////////////////

		// Se decido di tornare indietro
		if ("backToMessaggi".equals(val)) {
			if (session.getAttribute("readMex") != null)
				session.removeAttribute("readMex");
			if (session.getAttribute("rispMex") != null)
				session.removeAttribute("rispMex");
			if (session.getAttribute("newMex") != null)
				session.removeAttribute("newMex");

			if (session.getAttribute("fromHome") != null)
				path = "/WEB-INF/homepage";
			else
				path = "/WEB-INF/messaggi";
		}

		// Se premo il bottone per inviare un messaggio
		if ("sendRisp".equals(val)) {

			mx = (Messaggio) session.getAttribute("messaggio");
			String receiver = u.getUsername(); // La dichiaro qua e di base ci
												// metto l'user stesso (ma
												// giusto per non creare errori
												// nel database)

			if (session.getAttribute("newMex") == null) {
				// Prendo i dati e invio un messaggio, cioè lo aggiungo al
				// database
				// dei messaggi, del sender (che diventa il destinatario)
				receiver = request.getParameter("sender");
			}

			else {

				// Elimino l'attributo newMex se era presente
				if (session.getAttribute("newMex") != null)
					session.removeAttribute("newMex");

				receiver = request.getParameter("option");
				System.out.println("ECCO IL VALORE:::::: "
						+ request.getParameter("option"));

			}
			String sender = u.getUsername();
			String mex = request.getParameter("mex");

			java.util.Date date = new java.util.Date();
			String datenow = this
					.stringdateToMYSQLFormattedStringdate(new Timestamp(date
							.getTime()).toString());
			boolean result = mx.sendMex(receiver, sender, mex, datenow);

			if (result)
				System.out.println("Messaggio inviato correttamente");
			mx.readAndSetMex(u.getUsername()); // Questo a dire la verità non
												// servirebbe perché di solito
												// uno no si automanda i
												// messaggi, però, non si sa mai

			if (session.getAttribute("fromHome") != null)
				path = "/WEB-INF/homepage";
			else
				path = "/WEB-INF/messaggi";
		}

		// //////////////////////////////////
		// DAL MENU IN BASSO in messaggi.jsp
		// //////////////////////////////////

		if ("newMex".equals(val)) {
			session.setAttribute("newMex", "1");
			path = "/WEB-INF/messaggio";
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
		// Paziente p = new Paziente();
		Paziente p = (Paziente) session.getAttribute("paziente"); // Se c'è già
																	// un
																	// elemento
																	// paziente
																	// lo prendo
		Monitoraggio m = new Monitoraggio();
		Storico s = new Storico();
		// Storico s = (Storico) session.getAttribute("storico");
		String path = ""; // path che indica la JSP dove voglio andare a seconda
		// delle azioni
		Reparto r = (Reparto) session.getAttribute("Reparto");
		if (r == null)
			r = new Reparto();
		Messaggio mx; // Variabile messaggio

		// caso del signin
		if ("signin".equals(val)) {
			System.out.println("username che setto: "
					+ request.getParameter("username"));

			String username = request.getParameter("username");
			u = new User(username); // creo un oggetto utente con l'username
			// passato
			mx = new Messaggio(); // Quando entro qui di sicuro non ho ancora i
			// messaggi settati
			mx.readAndSetMex(username); // Setto i messaggi
			session.setAttribute("messaggio", mx); // Setto l'oggetto messaggio
			// nella sessione così da
			// risprenderlo nella
			// homepage

			// Se non si trova corrispondenza nel database (e lo fa la Servlet)
			// oppure l'utente è nullo allora sto nel login
			if (!u.signinU(request.getParameter("password")) || u == null)
				path = "/WEB-INF/signinErr";
			// altrimenti vado alla homepage
			else {
				u.getProfileU(); // In questo modo vengono settati i dati
				// nell'oggetto utente.
				session.setAttribute("user", u);
				session.setAttribute("fromHome", "1"); // ***//
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
			session.invalidate();
			response.sendRedirect("signin.jsp");
			return;
		}

		// Anche qui se annullo, cancello tutto *****************DA DOVE VIENE
		// QUESTO??? FORSE NON ESISTE ********
		if ("annullaModSignup".equals(val)) {
			System.out.println("RICERCA STO COSO TERMINATA********");
			session.invalidate();
			response.sendRedirect("signin.jsp");
			return;
		}

		// bottoni aggiungi paziente e annulla. Dalla pagina pazienteMod
		// riportano a pazientiLista
		if ("insPaziente".equals(val)) {
			p.insPaziente(p.getIDDisp(), request.getParameter("nome"),
					request.getParameter("cognome"),
					request.getParameter("dataNascita").toString(),
					request.getParameter("codFisc"),
					request.getParameter("dataIn").toString(),
					request.getParameter("reparto"));
			// devo mettere quì il controllo perchè prima aggiunge e poi
			// controlla se c'è l'errore
			if (p.dimErrors() != 0) {
				System.out.println("errore ins paziente");
				path = "/WEB-INF/pazienteModErr";
			} else {
				p.viewPaziente();
				session.setAttribute("paziente", p);
				path = "/WEB-INF/pazientiLista";
			}
		}

		if ("annPaziente".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			path = "/WEB-INF/pazientiLista";
		}

		// due funzioni per modificare il paziente
		if ("insModPaziente".equals(val)) {
			// Paziente p = new Paziente(request.getParameter("IDPaziente"));
			p.modificaPaziente(session.getAttribute("IDpaz").toString(),
					request.getParameter("nome"),
					request.getParameter("cognome"),
					request.getParameter("dataNascita").toString(),
					request.getParameter("codFisc"),
					request.getParameter("dataIn").toString(),
					request.getParameter("reparto"));
			if (p.dimErrors() != 0) {
				System.out.println("errore mod paziente");
				path = "/WEB-INF/pazienteMod";
			} else {
				p.viewPaziente();
				session.setAttribute("paziente", p);
				if (session.getAttribute("categoria") != null) {
					session.removeAttribute("categoria");
					path = "/WEB-INF/pazientiCategoria";
				} else {
					path = "/WEB-INF/pazientiLista";
				}
			}
		}

		if ("annModPaziente".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			if (session.getAttribute("categoria") != null) {
				session.removeAttribute("categoria");
				path = "/WEB-INF/pazientiCategoria";
			} else {
				path = "/WEB-INF/pazientiLista";
			}
		}
		// funzioni per dimettere e riammetere il paziente
		if ("dimettiPaziente".equals(val)) {
			p.dimettiP(session.getAttribute("IDpaz").toString());
			p.viewPaziente();
			session.setAttribute("paziente", p);
			if (session.getAttribute("categoria") != null) {
				session.removeAttribute("categoria");
				path = "/WEB-INF/pazientiCategoria";
			} else {
				path = "/WEB-INF/pazientiLista";
			}
		}
		if ("riammettiPaziente".equals(val)) {
			p.riammettiP(session.getAttribute("IDpaz").toString());
			p.viewPaziente();
			session.setAttribute("paziente", p);
			if (session.getAttribute("categoria") != null) {
				session.removeAttribute("categoria");
				path = "/WEB-INF/pazientiCategoria";
			} else {
				path = "/WEB-INF/pazientiLista";
			}
		}
		// per entrare e uscire dalla pagina di Pearson
		if ("vaiPearson".equals(val)) {
			m.setVar1("");
			m.setVar2("");
			m.viewAllMonitoraggi();
			session.setAttribute("monitoraggio", m);
			path = "/WEB-INF/pearson";
		}
		if ("closePearson".equals(val)) {
			m.resetPearson();
			m.viewAllMonitoraggi();
			session.setAttribute("monitoraggio", m);
			path = "/WEB-INF/paziente";
		}
		if ("calcolaPearson".equals(val)) {
			m.setVar1(request.getParameter("variabile1").toString());
			m.setVar2(request.getParameter("variabile2").toString());
			m.setMin1(request.getParameter("min1"));
			m.setMin2(request.getParameter("min2"));
			m.setMax1(request.getParameter("max1"));
			m.setMax2(request.getParameter("max2"));
			m.setNum(request.getParameter("num"));

			/*
			 * m.CalcolaPearson(Integer.parseInt(request.getParameter("num")),
			 * Integer.parseInt(request.getParameter("min1")),
			 * Integer.parseInt(request.getParameter("min2")),
			 * Integer.parseInt(request.getParameter("max1")),
			 * Integer.parseInt(request.getParameter("max2")));
			 */
			m.CalcolaPearson();
			// mettere i controlli sui valori minimo e massimo
			m.viewAllMonitoraggi();
			session.setAttribute("monitoraggio", m);
			path = "/WEB-INF/pearson";
		}

		/* MF */
		if ("confermaSignup".equals(val)) {
			System.out.println("confermaSignup");
			u = new User(request.getParameter("username"));
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String cpassword = request.getParameter("cpassword");
			String name = request.getParameter("nome");
			String surname = request.getParameter("cognome");
			Date birthdate = Date.valueOf(request.getParameter("birthdate"));
			ArrayList<String> deps = new ArrayList<String>();

			// Prendo i reparti che sono stati scelti nelle checkboxes
			int numRep = r.getNumRep();
			// byte count = 0; //Per il massimo numero di reparti possibili
			for (byte i = 0; i < numRep; ++i) {
				String par = "check" + i;
				String dep = request.getParameter(par);
				System.out.println("Reparto preso:" + dep);
				if (dep != null) {
					if (!dep.equals("null")) { // se una checkbox non è segnata,
						// mi torna null, allora devo
						// stare attento a non prenderla
						System.out.println("Aggiungo il reparto:" + dep);
						deps.add(dep);
						// count++; //Per renderlo più efficiente, si blocca se
						// il numero di reparti aggiunti è 3
					}
				}
			} // notare che il numero di reparti selezionato potrebe essere
				// maggiore di 3 e quindi non andrebbe bene, lo controllo dopo,
				// in checkSignup.

			// Controllo dei campi inseriti correttamente (una prima
			// obbligatorietà è controllata tramite HTML5)

			// se c'è qualche errore allora torno alla pagina di signup ma
			// quella di errore. Prima recupero i messaggi di errore e li setto
			// nella sessione.
			if (u.checkSignup(username, password, cpassword, name, surname,
					birthdate, deps)) {

				// Setto gli errori da riprendere nella signupErr.jsp
				session.setAttribute("err_username", u.getError("username"));
				session.setAttribute("err_password", u.getError("password"));
				session.setAttribute("err_name", u.getError("nome"));
				session.setAttribute("err_surname", u.getError("cognome"));
				session.setAttribute("err_birthdate", u.getError("birthdate"));
				session.setAttribute("err_deps0", u.getError("deps0"));
				session.setAttribute("err_deps", u.getError("deps"));

				// Setto gli attributi che sono già stati inseriti in modo da
				// poterli riprendere
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("name", name);
				session.setAttribute("surname", surname);
				session.setAttribute("birthdate", birthdate);
				session.setAttribute("deps", deps);

				path = "/WEB-INF/signupErr";
			}

			// se non ci sono errori posso inserire i dati nel database
			else {
				System.out.println("Inserimento nel database");

				// Se ho solo un reparto allora ne metto altri due vuoti
				if (deps.size() == 1) {
					deps.add("");
					deps.add("");
				}

				// Se è grande due ne aggiungo solo una vuota
				if (deps.size() == 2) {
					deps.add("");
				}

				// faccio il signup e ritorno al login
				u.signupU(username, cpassword, name, surname, birthdate,
						deps.get(0), deps.get(1), deps.get(2));
				session.invalidate();
				response.sendRedirect("signin.jsp");
				return;
			}

		}

		// Qui ci arrivo da profiloDoc quando clicco su Modifica per confermare
		// le modifiche ai dati personali
		if ("acceptMod".equals(val)) {
			System.out.println("acceptMod");

			// Prendo i valori passati dalla form
			String username = request.getParameter("username");
			System.out.println("USERNAME: " + username); // Ok risolto il null
			// qua con
			// readonly="readonly"
			// sul campo input
			String password = request.getParameter("password");
			System.out.println("PASSWORD: " + password);
			String cpassword = request.getParameter("cpassword");
			String name = request.getParameter("nome");
			System.out.println("NOME: " + name);
			String surname = request.getParameter("cognome");
			Date birthdate = Date.valueOf(request.getParameter("birthdate"));
			ArrayList<String> deps = new ArrayList<String>();

			// Prendo i reparti che sono stati scelti nelle checkboxes (questo
			// funziona perché è stato settatto l'oggetto reparto, per la
			// sessione, dalla pagina jsp)
			int numRep = r.getNumRep();
			// byte count = 0; //Per il massimo numero di reparti possibili
			for (byte i = 0; i < numRep; ++i) {
				String par = "check" + i;
				String dep = request.getParameter(par);
				System.out.println("Reparto preso:" + dep);
				if (dep != null) {
					if (!dep.equals("null")) { // se una checkbox non è segnata,
						// mi torna null, allora devo
						// stare attento a non prenderla
						System.out.println("Aggiungo il reparto:" + dep);
						deps.add(dep);
						// count++; //Per renderlo più efficiente, si blocca se
						// il numero di reparti aggiunti è 3
					}
				}
			}

			if (u.checkSignup(username, password, cpassword, name, surname,
					birthdate, deps)) {

				// Setto gli errori da riprendere nella signupErr.jsp
				session.setAttribute(
						"err_mod",
						"Errore inserimento dati nella modifica del profilo. Controllare i campi evidenziati in rosso"); // Errore
				// per
				// capire
				// che
				// veniamo
				// dalla
				// modifica
				// (per
				// cambiare
				// il
				// tasto)
				// session.setAttribute("err_username",u.getError("username"));
				// In questo caso non serve perché questo non può dare errore,
				// esiste già
				session.setAttribute("err_password", u.getError("password"));
				session.setAttribute("err_name", u.getError("nome"));
				session.setAttribute("err_surname", u.getError("cognome"));
				session.setAttribute("err_birthdate", u.getError("birthdate"));
				session.setAttribute("err_deps0", u.getError("deps0"));
				session.setAttribute("err_deps", u.getError("deps"));

				// Setto gli attributi che sono già stati inseriti in modo da
				// poterli riprendere
				session.setAttribute("username", username);
				session.setAttribute("password", password);
				session.setAttribute("name", name);
				session.setAttribute("surname", surname);
				session.setAttribute("birthdate", birthdate);
				session.setAttribute("deps", deps);

				path = "/WEB-INF/signupErr";
			}

			// se non ci sono errori posso inserire i dati nel database
			else {
				System.out.println("Inserimento nel database");

				// Se ho solo un reparto allora ne metto altri due vuoi
				if (deps.size() == 1) {
					deps.add("");
					deps.add("");
				}

				// Se è grande due ne aggiungo solo una vuota
				if (deps.size() == 2) {
					deps.add("");
				}

				if (!u.modProfilo(password, name, surname, birthdate,
						deps.get(0), deps.get(1), deps.get(2), username))
					session.setAttribute("err_mod1",
							"Errore nella modifica del profilo, riprovare.");
				else {
					u.getProfileU();
					session.setAttribute("user", u);// Setto quello modificato
				}
				path = "/WEB-INF/profiloDoc";
			}
		}

		// quì ci arriva quando si schiaccia il bottone per aggiungere un valore
		// da monitorare
		if ("addValore".equals(val)) {
			m.insMonitoraggio(session.getAttribute("IDpaz").toString(), request
					.getParameter("nomeValore"), request.getParameter("valMin")
					.toString(), request.getParameter("valMax").toString());
			// System.out.println("prova IDPAZ: "+session.getAttribute("IDpaz"));
			m.viewAllMonitoraggi();
			session.setAttribute("monitoraggio", m);
			path = "/WEB-INF/paziente";
		}

		// aggiunge un dato al monitoraggio selezionato
		if ("addDato".equals(val)) {
			mx = new Messaggio();
			boolean pericolo = false;
			pericolo = s.insDato(session.getAttribute("IDpaz").toString(),
					request.getParameter("valoreStorico"),
					request.getParameter("dato"));
			if (pericolo)// entra se il valore è fuori dal range
			{
				System.out.println("occhio!!");// manda un messaggio ai
												// dottori!!
				// Ricavo il reparto e mando il messaggio e tutti i pazienti di
				// quel reparto

				p.viewPaziente(); // Ricarica i pazienti
				int id = Integer.parseInt(session.getAttribute("IDpaz")
						.toString());
				int idx = p.getIndice(id);
				String reparto = p.getReparto(idx);
				String nome = p.getNome(idx);
				String cognome = p.getCognome(idx);
				String paziente = nome + " " + cognome;
				String mex = "ATTENZIONE! Il valore: \""
						+ request.getParameter("valoreStorico")
						+ "\" del paziente: \"" + paziente
						+ "\", ha superato i limiti stabiliti.";
				mx.sendAlerts(mex, paziente, reparto);

				// Faccio una funzione in user che mi manda i messaggi di
				// allerta a tutti

			}
			session.setAttribute("storico", s);
			path = "/WEB-INF/paziente";
		}

		// quando si chiude il profilo del paziente
		if ("closeProfiloPaziente".equals(val)) {
			p.viewPaziente();
			session.setAttribute("paziente", p);
			if (session.getAttribute("categoria") != null) {
				session.removeAttribute("categoria");
				path = "/WEB-INF/pazientiCategoria";
			} else {
				path = "/WEB-INF/pazientiLista";
			}
			// session.removeAttribute("valStorico");
		}

		if ("aggiornaGrafico".equals(val)) {
			// session.setAttribute("valStorico",
			// request.getParameter("visStorico"));
			s.setValGrafico(request.getParameter("visStorico").toString());
			s.setDataInizio(Date.valueOf(request.getParameter("dataInizio")));
			s.setDataFine(Date.valueOf(request.getParameter("dataFine")));
			s.selezionaStorico(session.getAttribute("IDpaz").toString(),
					request.getParameter("visStorico").toString(),
					request.getParameter("dataInizio"),
					request.getParameter("dataFine"));
			session.setAttribute("storico", s);
			path = "/WEB-INF/paziente";
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

	/**
	 * Funzione per tornare una stringa correttamente formattata per coincidere
	 * con il formato Date di MYSQL. Trasforma il formato yyyy-MM-dd
	 * HH:mm:ss.SSS in yyyy-MM-dd HH:mm:ss
	 * 
	 * @param inputStringdate
	 *            la stringa data del tipo yyyy-MM-dd HH:mm:ss.SSS
	 * @return outputStringdate La stringa corretta in forma yyyy-MM-dd HH:mm:ss
	 */
	private String stringdateToMYSQLFormattedStringdate(String inputStringdate) {
		DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // forma
		// attuale
		DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // nuova
		// forma
		// accettata
		// da
		// mysql

		String outputStringdate = "";
		Exception ee = null;

		java.util.Date date = null;
		try {
			date = inputFormat.parse(inputStringdate);
			outputStringdate = outputFormat.format(date);
		} catch (Exception e) {
			System.out
					.println("**Errore nel parsing nella Servlet, della funzione stringdateToMYSQLFormattedStringdate. L'errore è stato gestito correttamente.**");
			ee = e;
		}

		if (ee != null)
			outputStringdate = inputStringdate;

		System.out.println("OutputText: " + outputStringdate);

		return outputStringdate;
	}

}
