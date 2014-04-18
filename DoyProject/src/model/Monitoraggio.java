package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Vector;

public class Monitoraggio {

	//Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL= "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root"; 
	private static String SQLPW = "root";
	
	//Variabili private della classe
	private Hashtable <String, String> errors;	//Per definire la lista degli errori
	private Vector<String> IDPaziente = new Vector<String>();
	private Vector<String> valore = new Vector<String>();
	private Vector<Integer> minimo = new Vector<Integer>();
	private Vector<Integer> massimo = new Vector<Integer>();
	//vector per i valori monitorati da mettere nel menu a tendina
	private Vector<String> monitor = new Vector<String>();
	
	
	//metodo vuoto che rispecchi i Bean
	public Monitoraggio(){
		
	}
	
	public Monitoraggio(String IDPaziente,String valore){
		setIDPaziente(IDPaziente);
		setValore(valore);
	}
	
	public void setIDPaziente(String IDPaziente) {
		this.IDPaziente.add(IDPaziente);
	}
	public String getIDPaziente(int i) {
		return IDPaziente.get(i);
	}
	public void setValore(String valore) {
		this.valore.add(valore);
	}
	public String getValore(int i) {
		return valore.get(i);
	}
	public void setMinimo(Integer minimo) {
		this.minimo.add(minimo);
	}
	public Integer getMinimo(int i) {
		return minimo.get(i);
	}
	public void setMassimo(Integer massimo) {
		this.massimo.add(massimo);
	}
	public Integer getMassimo(int i) {
		return massimo.get(i);
	}
	//ritorna la grandezza della tabella
	public int contaMonitor(){
		return IDPaziente.size();
	}
	
	
	//inserire un nuovo monitoraggio nella tabella del database
	public void insMonitoraggio(String IDPaziente, String nomeVal, String valMin, String valMax){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			String query ="insert into monitoraggio (IDPaziente, valore, minimo, massimo) values (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
						
			ps.setString(1, IDPaziente);
            ps.setString(2, nomeVal);
            ps.setString(3, valMin);
            ps.setString(4, valMax);
            ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("monitoraggio aggiunto!! :)");
        }
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//per vedere tutti i monitoraggi inseriti
	public void viewAllMonitoraggi(){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from monitoraggio";
            PreparedStatement ps = con.prepareStatement(strQuery);
               
        	ResultSet rs = ps.executeQuery();
        	// salvo i campi dei prodotti dell'utente nei vettori stringa della classe prodotto
			while(rs.next()){
				String IDPaziente = rs.getString("IDPaziente");
				//int IDPaziente = rs.getInt("IDPaziente");
				setIDPaziente(IDPaziente);
				String val = rs.getString("valore");
				setValore(val);
				String min = rs.getString("minimo");
				setMinimo(Integer.parseInt(min));
				String max = rs.getString("massimo");
				setMassimo(Integer.parseInt(max));
			}
			ps.close();
			con.close();
		}
		
		catch (Exception e) {
		e.printStackTrace();
		}
	}
	
	public void removeValore(String ID, String val){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="DELETE FROM monitoraggio WHERE IDPaziente=? AND valore=?";
            PreparedStatement ps = con.prepareStatement(strQuery);
            ps.setString(1, ID);
            ps.setString(2, val);
            
            ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("monitoraggio eliminato!! :)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean controllaPresenza(String ID){
		boolean presente = false;
		
		for(int i=0; i<IDPaziente.size(); i++)
		{
			if((IDPaziente.get(i)).equals(ID))
			{
				presente=true;
				break;
			}
		}
		
		return presente;
	}
	
	//funzioni per gestire il menu a tendina nella pagina paziente.jsp 
	public String getMon(int i){
		return monitor.get(i);
	}
	//ritorna tutti i valori che si possono monitorare
	public int viewAllMonitor(){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from monitor";
            //la tabella monitor è quella con i nomi delle cose che si possono monitorare
			PreparedStatement ps = con.prepareStatement(strQuery);
               
        	ResultSet rs = ps.executeQuery();
        	// salvo i campi dei prodotti dell'utente nei vettori stringa della classe prodotto
			while(rs.next()){
				String mon = rs.getString("valore");
				monitor.add(mon);
			}
			ps.close();
			con.close();
		}
		
		catch (Exception e) {
		e.printStackTrace();
		}
		
		int dim=monitor.size();
		return dim;
	}
	
	
	
}//fine classi Monitoraggio
