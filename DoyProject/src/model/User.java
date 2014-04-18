package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Calendar;

/**
 * 
 * @author Federico Tale classe, utilizzata per il Model, rappresenta il Bean
 *         utente usato per effettuare operazioni riguardanti l'utente che
 *         accede al sistema. Nel caso del progetto, un utente è un dottore.
 */
public class User {

	// Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root";
	private static String SQLPW = "root";

	// Variabili private della classe
	private String username;
	private String password;
	private String name;
	private String surname;
	private Date birthdate;
	private String dep1 = "";
	private String dep2 = "";
	private String dep3 = "";
	private ArrayList<String> depArray;
	private int numDep = 0;
	private Hashtable<String, String> errors; // Per definire la lista degli
												// errori
	private ArrayList<String> messages; // Lista dei messaggi di altri dottori

	/**
	 * Metodo costruttore vuoto che rispecchia la caratteristica dei Bean
	 */
	public User() {

	}

	/**
	 * Metodo costruttore che mi crea un oggetto User
	 * 
	 * @param username
	 *            Parametro che indica l'username da attribuire all'oggetto
	 */
	public User(String username) {
		setUsername(username);

	}

	/**
	 * Metodo setter che mi serve per settare la variabile privata username
	 * della classe
	 * 
	 * @param username
	 *            Parametro che indica l'username da attribuire all'oggetto
	 */
	private void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Metodo pubblico da usare per ottenere l'attributo Username dell'oggetto
	 * 
	 * @return Torna il nome dell'utente
	 */
	public String getUsername() {
		return this.username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	/**
	 * Setto il numero dei reparti
	 * 
	 * @param n
	 *            numero dei reparti
	 */
	public void setNumDep(int n) {
		this.numDep = n;
	}

	/**
	 * Ricavo il numero dei reparti
	 * 
	 * @return il numero dei reparti
	 */
	public int getNumDep() {
		return this.numDep;
	}

	/**
	 * Metodo che setta il primo reparto, e setta anche il numero dei reparti.
	 * 
	 * @param dep1
	 *            reparto
	 */
	public void setDep1(String dep1) {
		this.dep1 = dep1;
		this.setNumDep(1);
		System.out.println("dipartimento 1: " + this.dep1);
	}

	/**
	 * Metodo che torna il primo reparto
	 * 
	 * @return reparto
	 */
	public String getDep1() {
		return this.dep1;

	}

	/**
	 * Metodo che setta il secondo reparto, e setta anche il numero dei reparti.
	 * 
	 * @param dep2
	 *            reparto
	 */
	public void setDep2(String dep2) {
		this.dep2 = dep2;
		this.setNumDep(2); // Esempio in questo caso: se arrivo a settare il
							// secondo reparto allora vuol dire che ne ho due di
							// reparti.
	}

	/**
	 * Metodo che torna il secondo reparto
	 * 
	 * @return reparto
	 */
	public String getDep2() {
		return this.dep2;
	}

	/**
	 * Metodo che setta il terzo reparto, e setta anche il numero dei reparti.
	 * 
	 * @param dep3
	 *            reparto
	 */
	public void setDep3(String dep3) {
		this.dep3 = dep3;
		this.setNumDep(3);
	}

	/**
	 * Metodo che torna il terzo reparto
	 * 
	 * @return reparto
	 */
	public String getDep3() {
		return this.dep3;
	}

	/**
	 * Metodo che uso per copiare tutti i messaggi presi dal database nell'array
	 * di messaggi
	 * 
	 * @param messages
	 *            I messaggi che prendo dal database
	 */
	private void setMessages(ArrayList<String> messages) {
		Collections.copy(this.messages, messages);
	}

	/**
	 * Metodo pubblico che uso per prendere i dati, lo userò dalla servlet
	 * 
	 * @return Ritorno la lista di messaggi
	 */
	public ArrayList<String> getMessages() {
		return this.messages;
	}

	/**
	 * Funzione che mi ritorna se sono riuscito a fare il login e mi prende e
	 * salva anche gli eventuali messaggi che il dottore ha sulla bacheca
	 * 
	 * @param password
	 *            Password dell'utente
	 * @return torna true=se ho trovato i valori nel database, altrimenti falso
	 */
	public boolean signinU(String password) {
		boolean signin = false;
		System.out.println("username: " + this.getUsername() + ", Password: "
				+ password);
		// sse entrambi i campi sono veri allora procedo
		if (!username.isEmpty() && !password.isEmpty()) {
			System.out.println("QUI1");
			// la try è la classica connessione al DB
			try {
				Class.forName(DRIVER).newInstance();
				Connection con = DriverManager.getConnection(URL + DBNAME,
						SQLUSERNAME, SQLPW);
				// il punto di domanda richiama il ps.setString da cui poi vai a
				// prendere i dati
				String query = "SELECT * FROM users WHERE username=? and password=?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, getUsername());
				ps.setString(2, password);
				ResultSet rs = ps.executeQuery(); // Esegue la query preparata
													// nel PreparedStatement

				// la rs è posizionata inizialmente prima della prima riga
				// utile, in questo modo vado alla prima riga
				// utile. Se la query non ritorna niente allora sono alla fine.
				// Se torna vero vuol dire che c'è qualcosa da leggere.
				if (rs.next()) {
					signin = true;
					System.out.println("XXX");
				}

				// Devo fare anche la query sulla tabella dei messaggi degli
				// altri dottori e settare i messaggi dell'oggetto
				// che vanno messi nell'ArrayList. Usando la funzione
				// setMessages

				// Rilascio le risorse invece di aspettare che siano rilasciate
				// automaticamente
				ps.close();
				con.close();

			} catch (Exception e) {
				e.printStackTrace();

			}

		}// chiudo l'if

		return signin; // torno il valore booleano (vero o falso)
	}

	/* MF */
	public boolean signupU(String username, String password, String nome,
			String cognome, Date birthdate, String dep1, String dep2,
			String dep3) {
		boolean signup = false;

		// la try è la classica connessione al DB
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			// il punto di domanda richiama il ps.setString da cui poi vai a
			// prendere i dati
			String query = "INSERT INTO users (username, password, name, surname, birthdate, dep1, dep2, dep3) VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, getUsername());
			ps.setString(2, password);
			ps.setString(3, nome);
			ps.setString(4, cognome);
			ps.setString(5, birthdate.toString());
			ps.setString(6, dep1);
			if (!dep2.isEmpty())
				ps.setString(7, dep2);
			else
				ps.setString(7, "NULL");

			if (!dep3.isEmpty())
				ps.setString(8, dep3);
			else
				ps.setString(8, "NULL");

			ps.executeUpdate();
			ps.close();
			con.close();
			signup = true;

		} catch (Exception e) {
			signup = false;
			e.printStackTrace();
			System.out.println("Eccezione: " + e.getMessage());

		}

		return signup;
	}

	/**
	 * Metodo che serve per andare a prendere i dati personali del dottore dal
	 * database e mostrarli all'utente
	 * 
	 * @return true or false a seconda se l'operazione è andata a buon fine
	 */
	public boolean getProfileU() {
		boolean profile_ok = false;

		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			// il punto di domanda richiama il ps.setString da cui poi vai a
			// prendere i dati
			String query = "SELECT * FROM users WHERE username=?"; // Vado a
																	// prendere
																	// i dati
																	// relativi
																	// a
																	// quell'username
																	// che è
																	// chiave
																	// unica
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, getUsername());
			ResultSet rs = ps.executeQuery(); // Esegue la query preparata nel
												// PreparedStatement

			// la rs è posizionata inizialmente prima della prima riga utile, in
			// questo modo vado alla prima riga
			// utile. Se la query non ritorna niente allora sono alla fine. Se
			// torna vero vuol dire che c'è qualcosa da leggere.
			if (rs.next()) {
				profile_ok = true;
				System.out.println("XXX");
			}

			// Adesso che sono sulla riga con i risultati (e ce n'è solo una!
			// perché l'username corrisponderà a solo una riga) mi salvo i
			// risultati
			// nelle variabili dell'oggetto.
			this.setName(rs.getString("name"));
			this.setSurname(rs.getString("surname"));
			this.setBirthdate(rs.getDate("birthdate"));

			if (rs.getString("dep1") != null) {
				this.setDep1(rs.getString("dep1"));
			}

			if (rs.getString("dep2") != null) {
				this.setDep2(rs.getString("dep2"));
			}

			if (rs.getString("dep3") != null) {
				this.setDep3(rs.getString("dep3"));
			}

			// Rilascio le risorse invece di aspettare che siano rilasciate
			// automaticamente
			ps.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return profile_ok;
	}

	/**
	 * Funzione che viene chiamata quando l'utente (dottore) decide di
	 * modificare il suo profilo
	 * 
	 * @return vero se è stato tutto modificato correttamente, altrimento falso
	 */
	public boolean modProfilo(String password, String nome, String cognome,
			Date birthdate, String dep1, String dep2, String dep3,
			String username) {
		boolean result = false;
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			String strQuery = "UPDATE users SET password=?, name=?, surname=?, birthdate=?, dep1=?, dep2=?, dep3=? WHERE username=?";
			PreparedStatement ps = con.prepareStatement(strQuery);
			ps.setString(1, password);
			ps.setString(2, nome);
			ps.setString(3, cognome);
			ps.setString(4, birthdate.toString());
			ps.setString(5, dep1);
			if (!dep2.isEmpty())
				ps.setString(6, dep2);
			else
				ps.setString(6, null);	//ATTENTO QUA CHE HO CAMBIATO
			if (!dep3.isEmpty())
				ps.setString(7, dep3);
			else
				ps.setString(7, null);
			ps.setString(8, username);

			ps.executeUpdate();
			ps.close();
			con.close();
			
			//Setto i nuovi dati nell'oggetto
			this.setName(name);
			this.setSurname(surname);
			this.setBirthdate(birthdate);
			this.setNumDep(1); //di base 1 solo reparto
			this.setDep1(dep1);
			if (!dep2.isEmpty()){
				this.setDep2(dep2);
				this.setNumDep(2);
			}
			else
				this.setDep2("");
			if (!dep3.isEmpty()){
				this.setDep3(dep3);
				this.setNumDep(3);
			}
			else
				this.setDep3("");
			
			result = true;
		
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Classe per verificare se ci sono degli errori nei dati inseriti della
	 * registrazione
	 * 
	 * @param username
	 * @param password
	 * @param cpassword
	 * @param nome
	 * @param cognome
	 * @param birthdate
	 * @return true=se ci sono dei problemi
	 */
	public boolean checkSignup(String username, String password,
			String cpassword, String nome, String cognome, Date birthdate,
			ArrayList<String> deps) {
		boolean bad_signup = false;

		this.errors = new Hashtable<String, String>();

		// Controllo dei campi di inserimento
		if (username.length() <= 4 || this.checkBasic(username)
				|| this.checkXSS(username)) {
			System.out.println("err_username");
			errors.put("username", "Inserire un username valido");
			bad_signup = true;
		}

		if (!password.equals(cpassword) || password.length() < 4) { // nota:
																	// basta
																	// solo
																	// la
																	// lenght
																	// su
																	// una
																	// perché
																	// altrimenti
																	// basta
																	// il
																	// caso
																	// che
																	// sono
																	// diverse
			errors.put("password", "Inserire una password valida");
			System.out.println("err_password");
			bad_signup = true;
		}

		if (this.checkBasic(nome) || this.checkXSS(nome)) {
			errors.put("nome", "Inserire un nome valido");
			System.out.println("err_name");
			bad_signup = true;
		}

		if (this.checkBasic(cognome) || this.checkXSS(cognome)) {
			errors.put("cognome", "Inserire un cognome valido");
			System.out.println("err_surname");
			bad_signup = true;
		}

		// Se la data inserita è successiva alla data corrente non va bene
		if (birthdate.compareTo(Calendar.getInstance().getTime()) > 0) {
			errors.put("birthdate", "Inserire una data valida");
			System.out.println("err_birthdate");
			bad_signup = true;
		}

		// Se non è stato selezionato nessun reparto
		if (deps.size() == 0) {
			errors.put("deps0", "Selezionare almeno un reparto");
			System.out.println("err_deps0");
			bad_signup = true;
		}

		// Se sono stati selezionati più di 3 reparti
		if (deps.size() > 3) {
			errors.put("deps",
					"Ai dottori non è consentito seguire più di 3 reparti");
			System.out.println("err_deps");
			bad_signup = true;
		}

		return bad_signup;
	}

	/**
	 * Funzione che utilizzo per recuperare il messaggio di errore
	 * 
	 * @param s
	 *            la chiave da cercare nella mappa
	 * @return il messaggio di errore. Nel caso non lo trovi perché la chiave
	 *         non esiste allora torno una stringa vuota.
	 */
	public String getError(String s) {
		String error = errors.get(s);
		return (error == null) ? "" : error; // torno una stringa vuota se non
												// c'è la chiave corrispondente
	}

	/**
	 * Funzione per fare il check dei campi inseriti sensibili all'XSS
	 * 
	 * @param s
	 *            stringa da controllare
	 * @return vero=ha trovato dei problemi
	 */
	private boolean checkXSS(String s) {
		boolean result = false;

		if (s.contains(">") || s.contains("<") || s.contains("'")
				|| s.startsWith(" ") || s.endsWith(" "))
			result = true;

		return result;
	}

	/**
	 * Funzione che fa il check dei campi vuoti o con solo uno spazio
	 * all'interno
	 * 
	 * @param s
	 * @return
	 */
	private boolean checkBasic(String s) {
		boolean result = false;

		if (s.equals("") || s.equals(" ") || s.trim().isEmpty())
			result = true;

		return result;
	}

} // fine classe User
