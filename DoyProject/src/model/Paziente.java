package model;

import java.sql.Connection;
//import java.util.Date;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;



public class Paziente {

	//Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL= "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root"; 
	private static String SQLPW = "root";
	
	//Variabili private della classe
	private Hashtable <String, String> errors;	//Per definire la lista degli errori
	private String dataDefault = "1900-01-01";
	// 1) cambiare le variabili da String a Vector<String>
	private Vector<String> IDPaziente = new Vector<String>();
	//private Vector<Integer> IDPaziente = new Vector<Integer>();
	private Vector<String> nome = new Vector<String>();
	private Vector<String> cognome = new Vector<String>();
	private Vector<String> codFisc = new Vector<String>();
	private Vector<Date> dataNascita = new Vector<Date>();
	private Vector<Date> dataIn = new Vector<Date>();
	private Vector<Date> dataOut = new Vector<Date>();
	int contaPazienti;
	int inserito = 0; //0=vecchio, 1=nuovo inserito, 2=modificato
	
	//metodo vuoto che rispecchi i Bean
	public Paziente(){
		
	}
	
	/* metodi vecchi
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
	}*/
	
	//metodi nuovi
	// 2)cambiare i metodi per far ritornare il valore desiderato
	public Paziente(String IDPaziente){
		setIDPaziente(IDPaziente);
	}
	public void setIDPaziente(String IDPaziente) {
		this.IDPaziente.add(IDPaziente);
	}
	public String getIDPaziente(int i) {
		return IDPaziente.get(i);
	}
	/*public void setIDPaziente(int IDPaziente) {
		this.IDPaziente.add(IDPaziente);
	}
	public int getIDPaziente(int i) {
		return IDPaziente.get(i);
	}*/
	public void setNome(String nome) {
		this.nome.add(nome);
	}
	public String getNome(int i) {
		return nome.get(i);
	}
	public void setCognome(String cognome) {
		this.cognome.add(cognome);
	}
	public String getCognome(int i) {
		return cognome.get(i);
	}
	public void setCodFisc(String codFisc) {
		this.codFisc.add(codFisc);
	}
	public String getCodFisc(int i) {
		return codFisc.get(i);
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita.add(dataNascita);
	}
	public Date getDataNascita(int i) {
		return dataNascita.get(i);
	}
	public void setDataIn(Date dataIn) {
		this.dataIn.add(dataIn);
	}
	public Date getDataIn(int i) {
		return dataIn.get(i);
	}
	public void setDataOut(Date dataOut) {
		this.dataOut.add(dataOut);
	}
	public Date getDataOut(int i) {
		return dataOut.get(i);
	}
	//aggiungere anche questo per avere il conteggio dei pazienti
	public int contaPazienti(){
		return IDPaziente.size();
	}
	
	public void setInserito(int ins){
		this.inserito = ins;
	}
	public int getInserito() {
		return this.inserito;
	}
	//metodo che ritorna l'indice dato l'ID
	public int getIndice(int ID)
	{
		int i;
		for(i=0; i<IDPaziente.size(); i++)
		{
			if(Integer.parseInt(IDPaziente.get(i)) == ID)
				break;
		}
		
		return i;
	}
	
	//metodo che ritorna il primo ID disponibile
	public String getIDDisp(){
		
		String disponibile = "";
		Vector<Integer> vec = new Vector<Integer>();
		for(int i=0; i<IDPaziente.size(); i++)
		{
			int id = Integer.parseInt(IDPaziente.get(i));
			vec.add(id);
		}
		
		int tmp=1;
		for(int i=0; i<vec.size(); i++)
		{
			if(vec.get(i) == tmp)
				tmp++;
			
			else
				break;
		}
		
		disponibile = Integer.toString(tmp);
		//System.out.println("primo disponibile: " + disponibile);
		return disponibile;
	}
	/*
	 * cose da fare:
	 * 1) fare tutti i metodi get e set					[fatto]
	 * 2) se possibile trasformare IDPAziente in int,
	 * 3) aggiungere la pagina di inserimento effettuato	[fatto, aggiunto il messaggio]
	 * 4) nella pagina pazientiLista mettere la tabella		[fatto, guardare bene la questione della data d'uscita]
	 * 5) dalla tabella si può selezionare un paziente e premere un tasto per modificarlo ->
	 * 6) mettere gli if in pazienteMod per precompilare i campi	[fatto]
	 */
	
	
	public void insPaziente(String IDPaziente, String nome, String cognome, String dataNascita, String codFisc, String dataIn){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			String query ="insert into pazienti (IDPaziente, nome, cognome, dataNascita, codFisc, dataIn, dataOut) values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
			//int id = Integer.parseInt(IDPaziente); 
			ps.setString(1, IDPaziente);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            //converto la data da stringa a Date
            Date dataN = Date.valueOf(dataNascita);
            //System.out.println("dataNascita: " + dataNascita);
            //System.out.println("dataN: " + dataN);
            ps.setDate(4, dataN);
            ps.setString(5, codFisc);
            Date dataI = Date.valueOf(dataIn);
            ps.setDate(6, dataI);
            //devo settare un valore di default altrimenti è un pacco la visualizzazione nella tabella dopo
            Date dataO = Date.valueOf(dataDefault);
            ps.setDate(7, dataO);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("paziente aggiunto!! :)");
        	inserito = 1;
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modificaPaziente(String IDPaziente, String nome, String cognome, String dataNascita, String codFisc, String dataIn, String dataOut){
		try {
			//int IDp = Integer.parseInt("ID");
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			//String query ="insert into pazienti (IDPaziente, nome, cognome, dataNascita, codFisc, dataIn, dataOut) values (?, ?, ?, ?, ?, ?, ?)";
			String query ="update pazienti set IDPaziente=?, nome=?, cognome=?, dataNascita=?, codFisc=?, dataIn=?, dataOut=? where IDPaziente=?;";
			PreparedStatement ps = con.prepareStatement(query);
			//int id = Integer.parseInt(IDPaziente); 
			ps.setString(1, IDPaziente);
            ps.setString(2, nome);
            ps.setString(3, cognome);
            //converto la data da stringa a Date
            Date dataN = Date.valueOf(dataNascita);
            //System.out.println("dataNascita: " + dataNascita);
            //System.out.println("dataN: " + dataN);
            ps.setDate(4, dataN);
            ps.setString(5, codFisc);
            Date dataI = Date.valueOf(dataIn);
            ps.setDate(6, dataI);
            //devo settare un valore di default altrimenti è un pacco la visualizzazione nella tabella dopo
            Date dataO = Date.valueOf(dataOut);
            ps.setDate(7, dataO);
            ps.setString(8, IDPaziente);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("paziente modificato!! :)");
        	inserito = 2;
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//visualizza un paziente per la tabella
	// 3)fare questo metodo per andare a leggere riga per riga il database, passare alla ControllerServlet
	public void viewPaziente(){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from pazienti";
            PreparedStatement pst = con.prepareStatement(strQuery);
               
        	ResultSet rs = pst.executeQuery();
        	// salvo i campi dei prodotti dell'utente nei vettori stringa della classe prodotto
			while(rs.next()){
				String IDPaziente = rs.getString("IDPaziente");
				//int IDPaziente = rs.getInt("IDPaziente");
				setIDPaziente(IDPaziente);
				String nome = rs.getString("nome");
				setNome(nome);
				String cognome = rs.getString("cognome");
				setCognome(cognome);
				String dataN = rs.getString("dataNascita");
				Date dataNascita = Date.valueOf(dataN);
				setDataNascita(dataNascita);
				String codFisc = rs.getString("codFisc");
				setCodFisc(codFisc);
				String dataI = rs.getString("dataIn");
				Date dataIn = Date.valueOf(dataI);
				setDataIn(dataIn);
				String dataO = rs.getString("dataOut");
				Date dataOut = Date.valueOf(dataO);
				setDataOut(dataOut);
			}
			pst.close();
			
			con.close();
		}
		
		catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	
} //fine classe Paziente
