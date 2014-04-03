package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.sql.Date;
import java.util.Hashtable;
import java.util.Locale;



public class Paziente {

	//Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL= "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root"; 
	private static String SQLPW = "root";
	
	//Variabili private della classe
	private String IDPaziente;
	private String nome, cognome, codFisc;
	private Date dataNascita, dataIn, dataOut;
	private Hashtable <String, String> errors;	//Per definire la lista degli errori
	
	//metodo vuoto che rispecchi i Bean
	public Paziente(){
		
	}
	
	public Paziente(String IDPaziente){
		setIDPaziente(IDPaziente);
	}
	
	private void setIDPaziente(String IDPaziente) {
		this.IDPaziente = IDPaziente;
	}
	
	public String getIDPaziente(){
		return this.IDPaziente;
	}
	
	private void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	private void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getCognome(){
		return this.cognome;
	}
	
	private void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}
	
	public String getCodFisc(){
		return this.codFisc;
	}
	
	private void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public Date getDataNascita(){
		return this.dataNascita;
	}
	
	private void setDataIn(Date dataIn) {
		this.dataIn = dataIn;
	}
	
	public Date getDataIn(){
		return this.dataIn;
	}
	
	private void setDataOut(Date dataOut) {
		this.dataOut= dataOut;
	}
	
	public Date getDataOut(){
		return this.dataOut;
	}
	
	/*
	 * cose da fare:
	 * 1) fare tutti i metodi get e set					[fatto]
	 * 2) se possibile trasformare IDPAziente in int
	 * 3) aggiungere la pagina di inserimento effettuato
	 * 4) nella pagina pazientiLista mettere la tabella
	 * 5) dalla tabella si può selezionare un paziente e premere un tasto per modificarlo ->
	 * 6) mettere gli if in pazienteMod per precompilare i campi
	 */
	
	
	public void insPaziente(String IDPaziente, String nome, String cognome, String dataNascita, String codFisc, String dataIn){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			String query ="insert into pazienti (IDPaziente, nome, cognome, dataNascita, codFisc, dataIn) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, IDPaziente);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            //conversto la data da stringa a Date
            Date dataN = Date.valueOf(dataNascita);
            //System.out.println("dataNascita: " + dataNascita);
            //System.out.println("dataN: " + dataN);
            ps.setDate(4, dataN);
            ps.setString(5, codFisc);
            Date dataI = Date.valueOf(dataIn);
            ps.setDate(6, dataI);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("paziente aggiunto!! :)");
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
} //fine classe Paziente
