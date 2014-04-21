package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe utilizzata per la gestione dei messaggi. E' il model che va a gestire i messaggi sul database.
 * @author Federico
 *
 */
public class Messaggio {

		// Variabili statiche per accesso al database
		private static String DBNAME = "doy";
		private static String URL = "jdbc:mysql://localhost:3306/";
		private static String DRIVER = "com.mysql.jdbc.Driver";
		private static String SQLUSERNAME = "root";
		private static String SQLPW = "root";
		
		//Variabili della classe
		private ArrayList<String> messages;	//i messaggi che prendo (di solito relativi solo al dottore loggato nel sistema)
		
		/**
		 * Metodo costruttore per rispecchiare la costruzione di un Bean
		 */
		public Messaggio(){
			
		}
		
		/**
		 * Funzione che ritorna il numero dei messaggi per il dottore loggato
		 * @param receiver (L'utente loggato in quel momento)
		 * @return	Il numero dei messaggi
		 */
		public int getNumMex(String receiver){
			if(this.messages == null){
				this.getMexDB(receiver);
			}
			return this.messages.size();
		}
		
		/**
		 * Funzione pubblica per ricavare il numero dei messaggi NON LETTI (relativi al dottore loggato)
		 * @param receiver Il dottore loggato
		 * @return torna il numero dei messaggi NON LETTIdel dottore loggato
		 */
		public int getNumNewMex(String receiver){
			return this.getNumNewMexDB(receiver);
			
		}
		
		
		/**
		 * Funzione che mi torna tutto l'array di messaggi di un utente
		 * @param receiver L'utente loggato nel sistema
		 * @return	Torna l'array di tutti i messaggi dell'utente loggato
		 */
		public ArrayList<String> getMex(String receiver){
			if(this.messages == null)
				this.getMexDB(receiver);
			
			return this.messages;
		}
		
		
		//////////////////////////////////////////////
		// METODI E FUNZIONI CHE AGISCONO SUL DATABASE
		//////////////////////////////////////////////
		
		/**
		 * Tale funzione va a prendere i messaggi (verso un determinato dottore, quello loggato) dal database
		 * @param receiver
		 * @return se l'operazione è andata a buon fine o no, true = messaggi trovati
		 */
		private boolean getMexDB(String receiver){
			boolean result = false;	//Di base è settato falso (nessun messaggio trovato)
			try {
				Class.forName(DRIVER).newInstance();
				Connection con = DriverManager.getConnection(URL + DBNAME,
						SQLUSERNAME, SQLPW);
				String query = "SELECT * FROM messages WHERE receiver=?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, receiver);
				ResultSet rs = ps.executeQuery();
				
				
				if(!rs.next())
					result = false;	//Non ci sono messaggi da leggere
				else{
					result = true;
					this.messages = new ArrayList<String>();	//Creo sse arrivo a questo punto la variabile messaggi
					do{
						this.messages.add(rs.getString("message"));
						
					} while(rs.next());
				}
				
				ps.close();
				con.close();
				

			} catch (Exception e) {
				System.out.println("Errore query in Messaggio.java, funzione getMex(): "
						+ e.getMessage());
				e.printStackTrace();
				result = false;	//Se c'è un errore metto falso
				//Qui dovrei uscire dal programma forse, è un errore più grave

			}
			
			
			
			return result;
		}
		
		/**
		 * Questa chiamata al database conta il numero di messaggi non letti
		 * @param receiver
		 * @return
		 */
		private int getNumNewMexDB(String receiver){
			int num = 0;
			
			try {
				Class.forName(DRIVER).newInstance();
				Connection con = DriverManager.getConnection(URL + DBNAME,
						SQLUSERNAME, SQLPW);
				String query = "SELECT COUNT(*) FROM messages WHERE receiver=? AND readbool = 1";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, receiver);
				ResultSet rs = ps.executeQuery();
				
				//NOTA: Con questa query viene creata una tabella risultato con la prima, e unica, colonna contenente il numero
				//contato. Quindi non è che mi crea una tabella con ogni riga i risultati cercati e quindi contando le righe
				//mi trovo il numero che volgio. Ma crea solo una risultato che prendo come faccio di seguito (prova su mySQL per capire)
				
				//questo se praticamente non succede mai, perché un risultato ce l'avrò sempre
				if(!rs.next()){
					num = 0;
				}
				else{
					num = rs.getInt(1);	//Prendo il valore contenuto nella tabella alla prima e unica colonna
				}
				
				ps.close();
				con.close();

				//Se non c'è niente nella tabella messaggi da questo errore (e ok, per evitare però errori di questo tipo inserisco un valore base nella tabella "admin admin...")
			} catch (Exception e) {
				System.out.println("Errore query in Messaggio.java, funzione getNumNewMexDB(): "
						+ e.getMessage());
				e.printStackTrace();
				num = 0;

			}
			
			System.out.println("Numero: " + num);
			return num;
		}
}
