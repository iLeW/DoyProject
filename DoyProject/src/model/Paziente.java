package model;

import java.sql.Connection;
//import java.util.Date;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;

/*
 * cose da fare:
 * 1) fare tutti i metodi get e set					[fatto]
 * 2) se possibile trasformare IDPaziente in int,
 * 3) aggiungere la pagina di inserimento effettuato	[fatto]
 * 4) nella pagina pazientiLista mettere la tabella		[fatto]
 * 5) dalla tabella si pu� selezionare un paziente e premere un tasto per modificarlo ->
 * 6) mettere gli if in pazienteMod per precompilare i campi	[fatto]
 * 7) pazientiCategoria											[fatto]
 * 8) fare i grafici											[fatto]
 * 9) fare la pagina del profilo del paziente, che � paziente.jsp	[fatto]
 * 10) popup per l'eliminazione del paziente					[fatto]
 * 11) fare la selezione del reparto con il menu a tendina		[fatto]
 * 12) fare la modifica della tabella dei monitoraggi nella pagina profilo del paziente
 * 13) fare la funzione per il controllo dei valori dei pazienti	[fatto in insDato]
 * 14) mettere il tasto per dimettere i pazienti			[fatto]
 * 15) controllo per il codice fiscale da gestire le eccezioni		[fatto]
 * 16) fare pearson												[fatto] 
 * 
 * 17) controllo dataIn e dataNascita in paziente e mod paziente
 * 18) controllo massimo e minimo in profilo paziente
 * 19) controllo delle due date per il grafico in profilo paziente
 * 20) controllo massimo e minimo nella pagina dell'indice di pearson
 */

public class Paziente {

	//Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL= "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root"; 
	private static String SQLPW = "root";
	
	//Variabili private della classe
	private Vector<String> errors = new Vector<String>();	//Per definire la lista degli errori
	private String dataDefault = "1900-01-01";
	private Vector<String> IDPaziente = new Vector<String>();
	private Vector<String> nome = new Vector<String>();
	private Vector<String> cognome = new Vector<String>();
	private Vector<String> codFisc = new Vector<String>();
	private Vector<Date> dataNascita = new Vector<Date>();
	private Vector<Date> dataIn = new Vector<Date>();
	private Vector<Date> dataOut = new Vector<Date>();
	private Vector<String> Reparto = new Vector<String>();
	int contaPazienti;
	int inserito = 0; //0=vecchio, 1=nuovo inserito, 2=modificato
	
	//metodo vuoto che rispecchi i Bean
	public Paziente(){
		
	}
	
	public Paziente(String IDPaziente){
		setIDPaziente(IDPaziente);
	}
	public void setIDPaziente(String IDPaziente) {
		this.IDPaziente.add(IDPaziente);
	}
	public String getIDPaziente(int i) {
		return IDPaziente.get(i);
	}
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
	public String showData(Date data)
	{
		Date d = Date.valueOf(dataDefault);
		if(data.equals(d))
			return "non dimesso";
		else
			return "dimesso il " + data.toString();
		
	}
	public boolean dentro(Date dataO)
	{
		Date d = Date.valueOf(dataDefault);
		if(dataO.equals(d))		//� ancora dentro
			return true;
		else
			return false;		//� stato dimesso
	}
	public void setReparto(String Reparto) {
		this.Reparto.add(Reparto);
	}
	public String getReparto(int i) {
		return Reparto.get(i);
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
	//funzioni per la gestione degli errori
	public void setErrors(String s){
		this.errors.add(s);
	}
	public String getErrors(int i) {
		return errors.get(i);
	}
	public void clearErrors(){
		this.errors.clear();
	}
	public int dimErrors(){
		return errors.size();
	}
	//metodo che ritorna il primo ID disponibile
	public String getIDDisp()
	{
		Vector<Integer> vecID = new Vector<Integer>();
		String disponibile = "";
		
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from pazienti";
            PreparedStatement ps = con.prepareStatement(strQuery);
               
        	ResultSet rs = ps.executeQuery();
        	// salvo i campi dei prodotti dell'utente nei vettori stringa della classe prodotto
			while(rs.next()){
				//String IDPaziente = rs.getString("IDPaziente");
				//vecID.add(IDPaziente);
				vecID.add(Integer.parseInt(rs.getString("IDPaziente")));
			}
			ps.close();
			con.close();
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		
		int tmp=1;
		for(int i=0; i<vecID.size(); i++)
		{
			if(vecID.get(i) == tmp)
				tmp++;
			else
				break;
		}
		
		disponibile = Integer.toString(tmp);
		//System.out.println("primo disponibile: " + disponibile);
		return disponibile;
	}
	
	public void insPaziente(String IDPaziente, String nome, String cognome, String dataNascita, String codFisc, String dataIn, String reparto){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			//String query ="insert into pazienti (IDPaziente, nome, cognome, dataNascita, codFisc, dataIn, dataOut, reparto) values (?, ?, ?, ?, ?, ?, ?, ?)";
			String query ="insert into pazienti (IDPaziente, nome, cognome, dataNascita, codFisc, dataIn, dataOut, reparto) values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
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
            //devo settare un valore di default altrimenti � un pacco la visualizzazione nella tabella dopo
            Date dataO = Date.valueOf(dataDefault);
            ps.setDate(7, dataO);
            ps.setString(8, reparto);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("paziente aggiunto!! :) id del paziente aggiunto: " + IDPaziente);
        	inserito = 1;
		}
		
		catch (Exception e) {
			e.printStackTrace();
			//System.out.println("messaggio" + e.getMessage());
			if(e.getMessage().contains("Duplicate"))
			{
				errors.add("ERRORE: il codice fiscale " + codFisc + " � gi� presente nel database, inserirne uno diverso");
				/*setIDPaziente(IDPaziente);
				setNome(nome);
				setCognome(cognome);
				setDataNascita(Date.valueOf(dataNascita));
				setCodFisc(codFisc);
				setDataIn(Date.valueOf(dataIn));
				setDataOut(Date.valueOf(dataDefault));
				setReparto(reparto);*/
				//salvaProvvisorio(IDPaziente, nome, cognome, dataNascita, codFisc, dataIn, reparto);
				System.out.println("prova di errore :| "+ errors.size());
			}
		}
	}
	
	public void modificaPaziente(String IDPaziente, String nome, String cognome, String dataNascita, String codFisc, String dataIn, String reparto){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			String query ="update pazienti set nome=?, cognome=?, dataNascita=?, codFisc=?, dataIn=?, reparto=? where IDPaziente=?;";
			PreparedStatement ps = con.prepareStatement(query);
			//int id = Integer.parseInt(IDPaziente); 
			ps.setString(1, nome);
            ps.setString(2, cognome);
            //converto la data da stringa a Date
            Date dataN = Date.valueOf(dataNascita);
            //System.out.println("dataNascita: " + dataNascita);
            //System.out.println("dataN: " + dataN);
            ps.setDate(3, dataN);
            ps.setString(4, codFisc);
            Date dataI = Date.valueOf(dataIn);
            ps.setDate(5, dataI);
            //devo settare un valore di default altrimenti � un pacco la visualizzazione nella tabella dopo
            ps.setString(6, reparto);
            ps.setString(7, IDPaziente);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	//System.out.println("IDold: " + IDold + " IDnew: " + IDPaziente);
        	System.out.println("paziente modificato!! :)" + IDPaziente);
        	inserito = 2;
		}
		
		catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().contains("Duplicate"))
			{
				errors.add("ERRORE: il codice fiscale " + codFisc + " � gi� presente nel database, inserirne uno diverso");
				/*setIDPaziente(IDPaziente);
				setNome(nome);
				setCognome(cognome);
				setDataNascita(Date.valueOf(dataNascita));
				setCodFisc(codFisc);
				setDataIn(Date.valueOf(dataIn));
				setDataOut(Date.valueOf(dataDefault));
				setReparto(reparto);*/
				//salvaProvvisorio(IDPaziente, nome, cognome, dataNascita, codFisc, dataIn, reparto);
				System.out.println("prova di errore :| "+ errors.size());
			}
		}
	}
	//funzione per dimettere il paziente
	public void dimettiP(String IDPaziente)
	{
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			String query ="update pazienti set dataOut=? where IDPaziente=?;";
			PreparedStatement ps = con.prepareStatement(query);
			//int id = Integer.parseInt(IDPaziente); 
			java.util.Date today = new java.util.Date();
			Date dataO = new Date(today.getTime());
            ps.setDate(1, dataO);
            ps.setString(2, IDPaziente);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("paziente dimesso!! :)" + IDPaziente);
        	//inserito = 2;
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	//funzione per riammettere il paziente
	public void riammettiP(String IDPaziente)
	{
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			String query ="update pazienti set dataIn=?, dataOut=? where IDPaziente=?;";
			PreparedStatement ps = con.prepareStatement(query);
			//int id = Integer.parseInt(IDPaziente); 
			java.util.Date today = new java.util.Date();
			Date dataI = new Date(today.getTime());
            ps.setDate(1, dataI);
            Date dataO = Date.valueOf(dataDefault);
            ps.setDate(2, dataO);
            ps.setString(3, IDPaziente);
        	ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("paziente riammesso!! :)" + IDPaziente);
        	//inserito = 2;
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removePaziente (String ID)
	{
		//cancella dalla tabella pazienti
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="DELETE FROM pazienti WHERE IDPaziente=?";
            PreparedStatement ps = con.prepareStatement(strQuery);
            ps.setString(1, ID);
            
            ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("paziente eliminato!! :)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//cancella dalla tabella storico
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="DELETE FROM storico WHERE IDPaziente=?";
            PreparedStatement ps = con.prepareStatement(strQuery);
            ps.setString(1, ID);
            
            ps.executeUpdate();
        	ps.close();
        	con.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//cancella dalla tabella monitoraggio
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="DELETE FROM monitoraggio WHERE IDPaziente=?";
            PreparedStatement ps = con.prepareStatement(strQuery);
            ps.setString(1, ID);
            
            ps.executeUpdate();
        	ps.close();
        	con.close();
        	
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//visualizza un paziente per la tabella
	public void viewPaziente(){
		clearAll();
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from pazienti";
            PreparedStatement ps = con.prepareStatement(strQuery);
               
        	ResultSet rs = ps.executeQuery();
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
				String Reparto = rs.getString("reparto");
				setReparto(Reparto);
			}
			ps.close();
			
			con.close();
		}
		
		catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	public void clearAll(){
		//errors.clear();
		IDPaziente.clear();
		nome.clear();
		cognome.clear();
		codFisc.clear();
		dataNascita.clear();
		dataIn.clear();
		dataOut.clear();
		Reparto.clear();
	}
	
	public void viewReparto(String rep1, String rep2, String rep3){
		
		Vector<String> rep = new Vector<String>();
		rep.add(rep1);
		if(rep2 != null)
			rep.add(rep2);
		if(rep3 != null)
			rep.add(rep3);
		
		for(int i=1; i<=rep.size(); i++)
		{
			try {
				Class.forName(DRIVER).newInstance();
				Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
				String strQuery="select * from pazienti where pazienti.reparto = ?";
			
				PreparedStatement ps = con.prepareStatement(strQuery);
				ps.setString(1, rep.get(i-1));
				
				ResultSet rs = ps.executeQuery();
				//salvo i campi dei prodotti dell'utente nei vettori stringa della classe prodotto
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
					String Reparto = rs.getString("reparto");
					setReparto(Reparto);
				}
				ps.close();
				con.close();
			}//fine try
			catch (Exception e) {
				e.printStackTrace();
			}
		}//fine for che esplora il vector rep
	}
	
	//funzione per salvare tutti i campi se c'� un errore
	public void salvaProvvisorio(String IDPaziente, String nome, String cognome, String dataNascita, String codFisc, String dataIn, String reparto){
		setIDPaziente(IDPaziente);
		setNome(nome);
		setCognome(cognome);
		setDataNascita(Date.valueOf(dataNascita));
		setCodFisc(codFisc);
		setDataIn(Date.valueOf(dataIn));
		setDataOut(Date.valueOf(dataDefault));
		setReparto(reparto);
	}
	//funzioni per fare i controlli sulla data
	public void controllaDataOdierna (String d){
		Date data = Date.valueOf(d);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp corrente = new Timestamp(now);
		if(data.after(corrente)){
			errors.add("ERRORE: la data " + d + " non deve superare quella odierna!!");
		}
	}
	public void controllaDataOrdine (String d1, String d2){
		Date data1 = Date.valueOf(d1);
		Date data2 = Date.valueOf(d2);
		if(data1.after(data2)){
			errors.add("ERRORE: la data " + data1 + " non pu� essere superiore a " + data2);
		}
	}
	
	//i colori sono da #000000 a #FFFFFF dove ogni due cifre � un canale RGB
	public String getColore(int i)
	{
		String colore;
		
		switch (getReparto(i)) {
        case "Cardiologia":  colore = "#FF7D7D";
                 break;
        case "Chirurgia":  colore = "#97FF99";
                 break;
        case "Radiologia":  colore = "#9DFFF5";
                 break;
        case "Nefrologia":  colore = "#FFFD9D";
                 break;
        case "Pediatria":  colore = "#EFB361";
        		break;
        case "Pronto soccorso":  colore = "#FFA6FD";
        		break;
        default: colore = "#FFFFFF";
                 break;
		}//fine switch
		
		return colore;
	}
	
	
} //fine classe Paziente
