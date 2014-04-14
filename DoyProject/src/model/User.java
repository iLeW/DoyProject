package model;

import java.sql.Connection;
import java.sql.Date;
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
	private String password;
	private String name;
	private String surname;
	private Date birthdate;
	private String dep1 = null;
	private String dep2 = null;
	private String dep3 = null;
	private ArrayList<String> depArray;
	private int numDep = 0;
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

public void setName(String name){
	this.name = name;
}

public String getName(){
	return this.name;
}

public void setSurname(String surname){
	this.surname = surname;
}

public String getSurname(){
	return this.surname;
}

public void setBirthdate(Date birthdate){
	this.birthdate = birthdate;
}

public Date getBirthdate(){
	return this.birthdate;
}

/**
 * Setto il numero dei reparti
 * @param n numero dei reparti
 */
public void setNumDep(int n){
	this.numDep = n;
}

/**
 * Ricavo il numero dei reparti
 * @return il numero dei reparti
 */
public int getNumDep(){
	return this.numDep;
}

/**
 * Metodo che setta il primo reparto, e setta anche il numero dei reparti.
 * @param dep1 reparto
 */
public void setDep1(String dep1){
	this.dep1 = dep1;
	this.setNumDep(1);
}

/**
 * Metodo che torna il primo reparto
 * @return reparto
 */
public String getDep1(){
	return this.dep1;
	
}

/**
 * Metodo che setta il secondo reparto, e setta anche il numero dei reparti.
 * @param dep2 reparto
 */
public void setDep2(String dep2){
	this.dep2 = dep2;
	this.setNumDep(2);	//Esempio in questo caso: se arrivo a settare il secondo reparto allora vuol dire che ne ho due di reparti.
}

/**
 * Metodo che torna il secondo reparto
 * @return reparto
 */
public String getDep2(){
	return this.dep2;
}

/**
 * Metodo che setta il terzo reparto, e setta anche il numero dei reparti.
 * @param dep3 reparto
 */
public void setDep3(String dep3){
	this.numDep = 3;
	this.setNumDep(3);
}


/**
 * Metodo che torna il terzo reparto
 * @return reparto
 */
public String getDep3(){
	return this.dep3;
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


/**
 * Metodo che serve per andare a prendere i dati personali del dottore dal database e mostrarli all'utente
 * @return true or false a seconda se l'operazione è andata a buon fine
 */
public boolean getProfileU(){
	boolean profile_ok = false;
	
	try{
		Class.forName(DRIVER).newInstance();
		Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
		//il punto di domanda richiama il ps.setString da cui poi vai a prendere i dati
		String query = "SELECT * FROM users WHERE username=?";	//Vado a prendere i dati relativi a quell'username che è chiave unica
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, getUsername());
		ResultSet rs = ps.executeQuery();	//Esegue la query preparata nel PreparedStatement
		
		//la rs è posizionata inizialmente prima della prima riga utile, in questo modo vado alla prima riga
		//utile. Se la query non ritorna niente allora sono alla fine. Se torna vero vuol dire che c'è qualcosa da leggere.
		if(rs.next()){
			profile_ok = true;
			System.out.println("XXX");
		}
		
		//Adesso che sono sulla riga con i risultati (e ce n'è solo una! perché l'username corrisponderà a solo una riga) mi salvo i risultati
		//nelle variabili dell'oggetto.
		this.setName(rs.getString("name"));
		this.setSurname(rs.getString("surname"));
		this.setBirthdate(rs.getDate("birthdate"));
		
		if(rs.getString("dep1") != null){
			this.setDep1(rs.getString("dep1"));
		}
		
		if(rs.getString("dep2") != null){
			this.setDep2(rs.getString("dep2"));
		}
		
		if(rs.getString("dep3") != null){
			this.setDep3(rs.getString("dep3"));
		}
		
		//Rilascio le risorse invece di aspettare che siano rilasciate automaticamente
		ps.close();
		con.close();
		
	} catch (Exception e){
		e.printStackTrace();
		
	}
	
	
	return profile_ok;
}
	
} //fine classe User
