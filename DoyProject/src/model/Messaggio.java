package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe utilizzata per la gestione dei messaggi. E' il model che va a gestire
 * i messaggi sul database.
 * 
 * @author Federico
 * 
 */
public class Messaggio {

	// ////////////////////////
	// VARIABILI E COSTRUTTORE
	// ////////////////////////

	// Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root";
	private static String SQLPW = "root";

	// Variabili della classe
	private ArrayList<String> sendersU;
	private ArrayList<String> messagesU; // I messaggi non letti
	private ArrayList<Date> datesU;
	private ArrayList<String> sendersR;
	private ArrayList<String> messagesR; // I messaggi letti
	private ArrayList<Date> datesR;

	/**
	 * Metodo costruttore per rispecchiare la costruzione di un Bean
	 */
	public Messaggio() {

	}

	// ///////////////////////////////////////
	// METODI PUBBLICI UTILIZZATI DALL'ESTERNO
	// ///////////////////////////////////////

	/**
	 * Funzione che ritorna il numero dei messaggi per il dottore loggato
	 * 
	 * @param receiver
	 *            (L'utente loggato in quel momento)
	 * @return Il numero dei messaggi
	 */
	public int getNumMex(String receiver) {
		if (this.messagesR == null && this.messagesU == null) {
			this.getMexDB(receiver);
		}

		return (this.messagesR.size() + this.messagesU.size());
	}

	/**
	 * Funzione pubblica per ricavare il numero dei messaggi NON LETTI (relativi
	 * al dottore loggato) - sono implicitamente i messaggi NON LETTI
	 * 
	 * @param receiver
	 *            Il dottore loggato
	 * @return torna il numero dei messaggi NON LETTI del dottore loggato
	 */
	public int getNumNewMex(String receiver) {
		return this.getNumNewMexDB(receiver);

	}

	/**
	 * Funzione che legge e che setta i messaggi nella variabile messaggi
	 * 
	 * @param receiver
	 *            Il dottore loggato
	 */
	public void readAndSetMex(String receiver) {
		if (this.messagesR == null && this.messagesU == null)
			this.getMexDB(receiver);
	}

	/* *********** PER MESSAGGI LETTI************* */

	/**
	 * MESSAGGI LETTI Per avere il numero di messaggi LETTI
	 * 
	 * @param receiver
	 *            Il dottore loggato in quel momento
	 * @return Il numero di messaggi LETTI
	 */
	public int getNumMexR(String receiver) {
		if (this.messagesR == null)
			this.getMexDB(receiver);

		return this.messagesR.size();
	}

	/**
	 * MESSAGGI LETTI Funzione che mi torna tutto l'array di messaggi di un
	 * utente
	 * 
	 * @param receiver
	 *            L'utente loggato nel sistema
	 * @return Torna l'array di tutti i messaggi dell'utente loggato
	 */
	public ArrayList<String> getMexR(String receiver) {
		if (this.messagesR == null)
			this.getMexDB(receiver);

		return this.messagesR;
	}

	/**
	 * MESSAGGI LETTI Funzione per ricavare il mittente del messaggio
	 * 
	 * @param idx
	 *            indice del messaggio da prendere
	 * @return Ritorno il mittente
	 */
	public String getSenderR(int idx) {
		return this.sendersR.get(idx);
	}

	/**
	 * MESSAGGI LETTI Per prendere il messaggio o eventualmente la sua
	 * anteprima. Da usare per la tabella dei messaggi ricevuti.
	 * 
	 * @param idx
	 *            l'indice del messaggio da tornare
	 * @return Il messaggio
	 */
	public String getMexPreviewR(int idx) {
		String preview = "";
		String mex = this.messagesR.get(idx);
		if (mex.length() > 50)
			preview = mex.substring(0, 48) + "...";

		if (preview.isEmpty())
			return mex;
		else
			return preview;
	}

	/**
	 * MESSAGGI LETTI Funzione per prendere il messaggio all'indice idx
	 * 
	 * @param idx
	 *            Indice del messaggio che voglio ottenere
	 * @return Il messaggio
	 */
	public String getMexR(int idx) {
		return this.messagesR.get(idx);
	}

	/**
	 * MESSAGGI LETTI Per ricavare la data del messaggio all'indice idx
	 * 
	 * @param idx
	 *            Indice del messaggio di cui voglio ricavare la data
	 * @return La data del messaggio all'indice idx
	 */
	public Date getDateR(int idx) {
		return this.datesR.get(idx);
	}

	/**
	 * Funzione per cancellare un messaggio, chiama una funzione che poi agisce
	 * effettivamente sul risultato
	 * 
	 * @param receiver
	 *            Il destinatario del messaggio (il dottore loggato)
	 * @param sender
	 *            Il mittente
	 * @param date
	 *            La data del messaggio
	 * @return Vero se è stato correttamente cancellata la riga corrispondente
	 */
	public boolean deleteMexR(String receiver, String sender, Date date) {
		return this.deleteMexDB(receiver, date, sender);
	}

	/* *********** PER MESSAGGI NON LETTI************* */

	/**
	 * MESSAGGI NON LETTI Per avere il numero di messaggi NON LETTI
	 * 
	 * @param receiver
	 *            Il dottore loggato in quel momento
	 * @return Il numero di messaggi NON LETTI
	 */
	public int getNumMexU(String receiver) {
		if (this.messagesU == null)
			this.getMexDB(receiver);

		return this.messagesU.size();
	}

	/**
	 * MESSAGGI NON LETTI Funzione che mi torna tutto l'array di messaggi di un
	 * utente
	 * 
	 * @param receiver
	 *            L'utente loggato nel sistema
	 * @return Torna l'array di tutti i messaggi dell'utente loggato
	 */
	public ArrayList<String> getMexU(String receiver) {
		if (this.messagesU == null)
			this.getMexDB(receiver);

		return this.messagesU;
	}

	/**
	 * MESSAGGI NON LETTI Funzione per ricavare il mittente del messaggio
	 * 
	 * @param idx
	 *            indice del messaggio da prendere
	 * @return Ritorno il mittente
	 */
	public String getSenderU(int idx) {
		return this.sendersU.get(idx);
	}

	/**
	 * MESSAGGI NON LETTI Per prendere il messaggio o eventualmente la sua
	 * anteprima. Da usare per la tabella dei messaggi ricevuti.
	 * 
	 * @param idx
	 *            l'indice del messaggio da tornare
	 * @return Il messaggio
	 */
	public String getMexPreviewU(int idx) {
		String preview = "";
		String mex = this.messagesU.get(idx);

		if (mex.length() > 30)
			preview = mex.substring(0, 30) + "...";

		if (preview.isEmpty())
			return mex;
		else
			return preview;
	}

	/**
	 * MESSAGGI NON LETTI Funzione per prendere il messaggio all'indice idx
	 * 
	 * @param idx
	 *            Indice del messaggio che voglio ottenere
	 * @return Il messaggio
	 */
	public String getMexU(int idx) {
		return this.messagesR.get(idx);
	}

	/**
	 * MESSAGGI NON LETTI Per ricavare la data del messaggio all'indice idx
	 * 
	 * @param idx
	 *            Indice del messaggio di cui voglio ricavare la data
	 * @return La data del messaggio all'indice idx
	 */
	public Date getDateU(int idx) {
		return this.datesR.get(idx);
	}

	/**
	 * 
	* Funzione pubblica che agisce sul database per modificare il messaggio e metterlo come letto
	 * @param receiver
	 *            Il destinatario del messaggio (il dottore loggato)
	 * @param sender
	 *            Il mittente del messaggio
	 * @param date
	 *            La data del messaggio
	 * @return Vero se la riga è stata correttamente modificata
	 * 
	 */
	public boolean markAsReadMex(String receiver, String sender, Date date) {
		return this.markAsReadMexDB(receiver, sender, date);
	}

	// ////////////////////////////////////////////
	// METODI E FUNZIONI CHE AGISCONO SUL DATABASE
	// ////////////////////////////////////////////

	/**
	 * Tale funzione va a prendere i messaggi (verso un determinato dottore,
	 * quello loggato) dal database
	 * 
	 * @param receiver
	 * @return se l'operazione è andata a buon fine o no, true = messaggi
	 *         trovati
	 */
	private boolean getMexDB(String receiver) {
		boolean result = false; // Di base è settato falso (nessun messaggio
								// trovato)
		// Creo le variabili solo se arrivo a questo punto
		this.sendersU = new ArrayList<String>();
		this.messagesU = new ArrayList<String>();
		this.datesU = new ArrayList<Date>();
		this.sendersR = new ArrayList<String>();
		this.messagesR = new ArrayList<String>();
		this.datesR = new ArrayList<Date>();
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			String query = "SELECT * FROM messages WHERE receiver=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, receiver);
			ResultSet rs = ps.executeQuery();

			if (!rs.next())
				result = false; // Non ci sono messaggi da leggere
			else {
				result = true;

				do {
					if (!rs.getBoolean("readbool")) {
						this.sendersU.add(rs.getString("sender"));
						this.messagesU.add(rs.getString("message"));
						this.datesU.add(rs.getDate("date"));
					} else {
						this.sendersR.add(rs.getString("sender"));
						this.messagesR.add(rs.getString("message"));
						this.datesR.add(rs.getDate("date"));
					}

				} while (rs.next());
			}

			ps.close();
			con.close();

		} catch (Exception e) {
			System.out
					.println("Errore query in Messaggio.java, funzione getMex(): "
							+ e.getMessage());
			e.printStackTrace();
			result = false; // Se c'è un errore metto falso
			// Qui dovrei uscire dal programma forse, è un errore più grave

		}

		return result;
	}

	/**
	 * Questa chiamata al database conta il numero di messaggi non letti
	 * 
	 * @param receiver
	 * @return
	 */
	private int getNumNewMexDB(String receiver) {
		int num = 0;

		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			String query = "SELECT COUNT(*) FROM messages WHERE receiver=? AND readbool = 0";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, receiver);
			ResultSet rs = ps.executeQuery();

			// NOTA: Con questa query viene creata una tabella risultato con la
			// prima, e unica, colonna contenente il numero
			// contato. Quindi non è che mi crea una tabella con ogni riga i
			// risultati cercati e quindi contando le righe
			// mi trovo il numero che volgio. Ma crea solo una risultato che
			// prendo come faccio di seguito (prova su mySQL per capire)

			// questo se praticamente non succede mai, perché un risultato ce
			// l'avrò sempre
			if (!rs.next()) {
				num = 0;
			} else {
				num = rs.getInt(1); // Prendo il valore contenuto nella tabella
									// alla prima e unica colonna
			}

			ps.close();
			con.close();

			// Se non c'è niente nella tabella messaggi da questo errore (e ok,
			// per evitare però errori di questo tipo inserisco un valore base
			// nella tabella "admin admin...")
		} catch (Exception e) {
			System.out
					.println("Errore query in Messaggio.java, funzione getNumNewMexDB(): "
							+ e.getMessage());
			e.printStackTrace();
			num = 0;

		}

		System.out.println("Numero: " + num);
		return num;
	}

	/**
	 * Funzione privata per cancellare il messaggio dal database (agisce sul
	 * database)
	 * 
	 * @param receiver
	 *            Il destinatario del messaggio (il dottore loggato)
	 * @param date
	 *            La data del messaggio
	 * @param sender
	 *            Il mittente del messaggio
	 * @return Vero se la riga è stata correttamente cancellata
	 */
	private boolean deleteMexDB(String receiver, Date date, String sender) {
		boolean result = false;

		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			String query = "DELETE FROM messages WHERE receiver = ? and sender = ? and date = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, receiver);
			ps.setString(2, sender);
			ps.setDate(3, date);
			int num = ps.executeUpdate(); // torna o il numero di righe affette
											// (cancellate nel nostro caso)
											// oppure 0 se non c'è stato nessun
											// effetto.

			if (num != 0)
				result = true;

			ps.close();
			con.close();
		} catch (Exception e) {
			System.out
					.println("Errore query in Messaggio.java, funzione deleteMexDB(): "
							+ e.getMessage());
			e.printStackTrace();
			result = false;
		}

		return result;
	}

	/**
	 * Funzione privata che agisce sul database per modificare il messaggio e metterlo come letto
	 * @param receiver
	 *            Il destinatario del messaggio (il dottore loggato)
	 * @param sender
	 *            Il mittente del messaggio
	 * @param date
	 *            La data del messaggio
	 * @return Vero se la riga è stata correttamente modificata
	 */
	private boolean markAsReadMexDB(String receiver, String sender, Date date) {
		boolean result = false;

		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			String query = "UPDATE messages SET readbool = 1  WHERE receiver = ? and sender = ? and date = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, receiver);
			ps.setString(2, sender);
			ps.setDate(3, date);
			int num = ps.executeUpdate(); // torna o il numero di righe affette
											// (cancellate nel nostro caso)
											// oppure 0 se non c'è stato nessun
											// effetto.

			if (num != 0)
				result = true;

			ps.close();
			con.close();
		} catch (Exception e) {
			System.out
					.println("Errore query in Messaggio.java, funzione markAsReadMexDB(): "
							+ e.getMessage());
			e.printStackTrace();
			result = false;
		}

		return result;
	}
}
