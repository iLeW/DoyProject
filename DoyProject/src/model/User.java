package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * 
 * @author Federico
 * Tale classe, utilizzata per il Model, rappresenta il Bean utente usato per effettuare operazioni
 * riguardanti l'utente che accede al sistema. Nel caso del progetto, un utente è un dottore. 
 */
public class User {
	
	//Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL= "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root"; 
	private static String SQLPW = "root";
	
	//Variabili private della classe
	private String username;
	private Hashtable <String, String> errors;	//Per definire la lista degli errori
	private ArrayList<String> messages;			//Lista dei messaggi di altri dottori

/**
 * Metodo costruttore vuoto che rispecchia la caratteristica dei Bean
 */
public User(){
	
}

/**
 * Metodo costruttore che mi crea un oggetto User
 * @param username Parametro che indica l'username da attribuire all'oggetto
 */
public User(String username){
	setUsername(username);
	
}

/**
 * Metodo setter che mi serve per settare la variabile privata username della classe
 * @param username	Parametro che indica l'username da attribuire all'oggetto
 */
private void setUsername(String username) {
	this.username = username;
}

/**
 * Metodo pubblico da usare per ottenere l'attributo Username dell'oggetto
 * @return Torna il nome dell'utente
 */
public String getUsername(){
	return this.username;
}

/**
 * Metodo che uso per copiare tutti i messaggi presi dal database nell'array di messaggi
 * @param messages I messaggi che prendo dal database
 */
private void setMessages(ArrayList<String> messages){
	Collections.copy(this.messages, messages);
}

/**
 * Metodo pubblico che uso per prendere i dati, lo userò dalla servlet
 * @return Ritorno la lista di messaggi
 */
public ArrayList<String> getMessages(){
	return this.messages;
}

/**
 * Funzione che mi ritorna se sono riuscito a fare il login e mi prende e salva anche gli eventuali
 * messaggi che il dottore ha sulla bacheca
 * @param password Password dell'utente
 * @return torna true=se ho trovato i valori nel database, altrimenti falso
 */
public boolean signinU(String password){	
	boolean signin = false;
	System.out.println("username: " + this.getUsername() + ", Password: " + password);
	//sse entrambi i campi sono veri allora procedo
	if(!username.isEmpty() && !password.isEmpty()){
		System.out.println("QUI1");
		//la try è la classica connessione al DB
		try{
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//il punto di domanda richiama il ps.setString da cui poi vai a prendere i dati
			String query = "SELECT * FROM users WHERE username=? and password=?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, getUsername());
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();	//Esegue la query preparata nel PreparedStatement
			
			//la rs è posizionata inizialmente prima della prima riga utile, in questo modo vado alla prima riga
			//utile. Se la query non ritorna niente allora sono alla fine. Se torna vero vuol dire che c'è qualcosa da leggere.
			if(rs.next()){
				signin = true;
				System.out.println("XXX");
			}
			
			//Devo fare anche la query sulla tabella dei messaggi degli altri dottori e settare i messaggi dell'oggetto
			//che vanno messi nell'ArrayList. Usando la funzione setMessages
			
			//Rilascio le risorse invece di aspettare che siano rilasciate automaticamente
			ps.close();
			con.close();
			
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
	}//chiudo l'if
	
	return signin;	//torno il valore booleano (vero o falso)
}
	
} //fine classe User
