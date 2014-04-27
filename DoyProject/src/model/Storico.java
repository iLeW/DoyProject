package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Vector;


public class Storico {
	
	// Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root";
	private static String SQLPW = "root";

	//variabili locali
	private Vector<Integer> ID = new Vector<Integer>();
	private Vector<String> valore = new Vector<String>();
	//private Vector<Integer> dato = new Vector<Integer>();
	private Vector<Integer> Storico = new Vector<Integer>();
	private Vector<Date> data = new Vector<Date>();
	private Vector<String> monitor = new Vector<String>();
	private Vector<Date> d = new Vector<Date>();
	private String valGrafico = "";
	private String dataDefault = "1900-01-01";
	private Date inizio = Date.valueOf(dataDefault);
	private Date fine = Date.valueOf(dataDefault);		
	
	public Storico() {

	}
	
	//funzioni per lavorare con i dati da passare per la costruzione dei grafici
	public void setValGrafico(String v) {
		this.valGrafico = v;
		inizio = Date.valueOf(dataDefault);
		fine = Date.valueOf(dataDefault);
	}
	public String getValGrafico() {
		if(valGrafico.isEmpty())
		{ return monitor.get(0); }
		else
		{ return this.valGrafico; }
	}
	public void setDataInizio(Date d) {
		this.inizio = d;
	}
	public Date getDataInizio(int id) {
		if((inizio.toString()).equals(dataDefault))
		{ return returnData(id, getValGrafico(), "primo"); }
		else
		{ return this.inizio; }
	}
	public void setDataFine(Date d) {
		this.fine = d;
	}
	public Date getDataFine(int id) {
		if((fine.toString()).equals(dataDefault))
		{ return returnData(id, getValGrafico(), "ultimo"); }
		else
		{ return this.fine; }
	}
	
	//funzione per inserire un nuovo dato nello storico
	public void insDato(String ID, String valoreStorico, String dato){
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			//inserisco i dati
			String query ="insert into storico (IDPaziente, data, valore, dato) values (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(query);
						
			ps.setString(1, ID);
			
			long now = Calendar.getInstance().getTimeInMillis();
			Timestamp ts = new Timestamp(now);
			ps.setTimestamp(2, ts);
			
            ps.setString(3, valoreStorico);
            ps.setString(4, dato);
            ps.executeUpdate();
        	ps.close();
        	con.close();
        	
        	System.out.println("monitoraggio aggiunto!! :)");
        }
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//funzione che ritorna le cose gi� monitorare per un paziente
	public String getMon(int i){
		return monitor.get(i);
	}
	public boolean controllaMon(){
		if(monitor.size()==0)
			return false;
		else
			return true;
	}
	public int viewMonitorPaziente(int ID){
		monitor.clear();
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from monitoraggio where IDPaziente=?";
	        PreparedStatement ps = con.prepareStatement(strQuery);
	        
	        Integer id = ID;
	        ps.setString(1, id.toString());
	           
	       	ResultSet rs = ps.executeQuery();
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
	
	//funzione che ritorna la prima o l'ultima data
	public Date returnData(int ID, String valore, String quale){		
		d.clear();
		
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String query ="select * from storico where IDPaziente=? and valore=?";
			PreparedStatement ps = con.prepareStatement(query);
					
			Integer id = ID;
	        ps.setString(1, id.toString());
	        ps.setString(2, valore);
	        
	        ResultSet rs = ps.executeQuery();
	       	
	        while(rs.next()){
				Date data = rs.getDate("data");
				d.add(data);
			}
			ps.close();
			con.close();
    	}
		
		catch (Exception e) {
		e.printStackTrace();
		}
		
		if((quale).equals("primo"))
		{ return d.firstElement(); }
		else
		{ return d.lastElement(); }
	}
	
	//funzione per selezionare i dati dell storico nel tempo giusto
	public void selezionaStorico(String IDp, String valore, String dataInizio, String dataFine){
		Storico.clear();
		data.clear();
		Timestamp dInizio = Timestamp.valueOf(dataInizio + " 00:00:00.000000");
		Timestamp dFine = Timestamp.valueOf(dataFine + " 23:59:59.999999");
		int ID = Integer.parseInt(IDp);
		
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from storico where IDPaziente=? and valore=? and data>=? and data<=?";
	        PreparedStatement ps = con.prepareStatement(strQuery);
	        
	        ps.setInt(1, ID);
	        ps.setString(2, valore);
	        ps.setTimestamp(3, dInizio);
	        ps.setTimestamp(4, dFine);
	        
	       	ResultSet rs = ps.executeQuery();
	       	while(rs.next()){
	       		data.add(rs.getDate("data"));
				Storico.add(rs.getInt("dato"));
			}
			ps.close();
			con.close();
		}
				
		catch (Exception e) {
		e.printStackTrace();
		}
		
		/*for(int i=0; i<datoStorico.size();i++){
			System.out.println(i+" datoStorico: "+datoStorico.get(i));
		}*/
	}
	public int getSto(){
		return Storico.size();
	}
	public Date getData(int i) {
		return data.get(i);
	}
	public int getStorico(int i) {
		return Storico.get(i);
	}
	
	public boolean presenzaStorico(int IDp){
		ID.clear();
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from storico where IDPaziente=?";
	        PreparedStatement ps = con.prepareStatement(strQuery);
	        
	        ps.setInt(1, IDp);
	        
	       	ResultSet rs = ps.executeQuery();
	       	while(rs.next()){
	       		ID.add(rs.getInt("IDPaziente"));
			}
			ps.close();
			con.close();
		}
				
		catch (Exception e) {
		e.printStackTrace();
		}
		
		if(ID.size()==0)
			return false;
		else
			return true;
	}
	
}//fine classe Storico