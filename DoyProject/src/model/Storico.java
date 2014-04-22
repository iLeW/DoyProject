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
	private Vector<String> ID = new Vector<String>();
	private Vector<String> valore = new Vector<String>();
	private Vector<Integer> dato = new Vector<Integer>();
	private Vector<String> monitor = new Vector<String>();
	Vector<Date> d = new Vector<Date>();
	private String valGrafico = "";
	private Date inizio, fine;
		
	
	public Storico() {

	}
	
	//funzioni per lavorare con i dati da passare per la costruzione dei grafici
	public void setValGrafico(String v) {
		this.valGrafico = v;
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
	public Date getDataInizio() {
		return this.inizio;
	}
	/*
	public Date getDataInizio(int ID) {
	se inizio è nullo allora
	{
		returnData(ID, getValGrafico(), "primo")
	}
		return this.inizio;
	}*/
	public void setDataFine(Date d) {
		this.fine = d;
	}
	public Date getDataFine() {
		return this.fine;
	}
	
	
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
	
	//funzione che ritorna le cose già monitorare per un paziente
	public String getMon(int i){
		return monitor.get(i);
	}
	public int viewMonitorPaziente(int ID){
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
	
	
	
}//fine classe Storico
