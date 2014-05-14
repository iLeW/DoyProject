package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Vector;


public class Monitoraggio {

	//Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL= "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root"; 
	private static String SQLPW = "root";
	
	//Variabili private della classe
	private Vector<String> errors = new Vector<String>();;	//Per definire la lista degli errori
	private Vector<String> IDPaziente = new Vector<String>();
	private Vector<String> valore = new Vector<String>();
	private Vector<String> parametri = new Vector<String>();
	private Vector<Integer> minimo = new Vector<Integer>();
	private Vector<Integer> massimo = new Vector<Integer>();
	//vector per i valori monitorati da mettere nel menu a tendina
	private Vector<String> monitor = new Vector<String>();
	private Vector<Integer> storico = new Vector<Integer>();
	private Vector<Integer> d = new Vector<Integer>();
	private Vector<Timestamp> data = new Vector<Timestamp>();
	
	private String var1="", var2="";
	private int min1=1, min2=1, max1=10, max2=10, num=10;
	private Vector<Integer> dati1 = new Vector<Integer>();
	private Vector<Integer> dati2 = new Vector<Integer>();
	double pearson=0;
	private Vector<Double> pearsonTab = new Vector<Double>();
	//private Vector<Vector<Integer>> datiTab = new Vector<Vector<Integer>>();
	private int[][] datiTab;
	
	
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
		//System.out.println("valore.size: " + valore.size());
		return valore.get(i);
	}
	public Vector<String> getVecValore(){
		return this.valore;
	}
	public void setMinimo(Integer minimo) {
		this.minimo.add(minimo);
	}
	public Integer getMinimo(int i) {
		return minimo.get(i);
	}
	public Vector<Integer> getVecMinimo(){
		return this.minimo;
	}
	public void setMassimo(Integer massimo) {
		this.massimo.add(massimo);
	}
	public Integer getMassimo(int i) {
		return massimo.get(i);
	}
	public Vector<Integer> getVecMassimo(){
		return this.massimo;
	}
	//ritorna la grandezza della tabella
	public int contaMonitor(){
		return IDPaziente.size();
	}
	//funzioni per la gestione degli errori
	public String getErrors(int i) {
		return errors.get(i);
	}
	public void clearErrors(){
		this.errors.clear();
	}
	public int dimErrors(){
		return errors.size();
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
			if(e.getMessage().contains("Duplicate")){
				errors.add("ERRORE: il monitoraggio " + nomeVal + " è già attivo per il paziente selezionato");
			}
				
		}
	}
	
	//per vedere tutti i monitoraggi inseriti
	public void viewAllMonitoraggi(){
		IDPaziente.clear();
		valore.clear();
		minimo.clear();
		massimo.clear();
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
		//elimina da monitoraggio
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
	//ritorna tutti i valori che si possono monitorare per il menu a tendina
	public int viewAllMonitor(){
		monitor.clear();
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
	
	//-------------------------------------- parte relativa a Pearson ---------------------------------------------
	public void setVar1(String v){
		this.var1=v;
	}
	public String getVar1(){
		if(var1.isEmpty())
		{
			return valore.get(0);
		}
		else
		{
			return this.var1;
		}
	}
	public void setVar2(String v){
		this.var2=v;
	}
	public String getVar2(){
		if(var2.isEmpty())
		{
			if(valore.size() > 1)
				return valore.get(1);
			else
				return valore.get(0);
		}
		else
		{
			return this.var2;
		}
	}
	public void resetVar(){
		var1="";
		var2="";
	}
	public int viewMonitoraggioPaziente(int ID){
		//monitor.clear();
		valore.clear();
		minimo.clear();
		massimo.clear();
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from monitoraggio where IDPaziente=?";
	        PreparedStatement ps = con.prepareStatement(strQuery);
	        
	        Integer id = ID;
	        ps.setString(1, id.toString());
	           
	       	ResultSet rs = ps.executeQuery();
	       	while(rs.next()){
				valore.add(rs.getString("valore"));
				minimo.add(rs.getInt("minimo"));
				massimo.add(rs.getInt("massimo"));
			}
			ps.close();
			con.close();
		}	
		catch (Exception e) {
		e.printStackTrace();
		}
			
		return valore.size();
	}
	//funzioni per calcolare l'indice di Pearson
	public void CalcolaPearson(){
		generaDati();
		//la deviazione standard è il quadrato della varianza
		double devStd1 = deviazioneStandard(dati1);
		double devStd2 = deviazioneStandard(dati2);
		double cov = covarianza(dati1, dati2);
		
		pearson = cov/(devStd1*devStd2);
	}
	public double getPearson(){
		return this.pearson;
	}
	public void resetPearson(){
		this.pearson = 0;
		this.min1=1;
		this.min2=1;
		this.max1=10;
		this.max2=10;
		this.num=10;
	}
	//genera random
	public void generaDati(){
		dati1.clear();
		dati2.clear();
		for(int i=0; i<num; i++)
		{
			dati1.add(min1+(int)(Math.random()*(max1-min1)));
			dati2.add(min2+(int)(Math.random()*(max2-min2)));
			//System.out.println("dati1: " + dati1.get(i) + " dati2: " + dati2.get(i));
		}
	}
	//media
	public double media(Vector<Integer> dati){
		double somma=0;
		for(int i=0; i< dati.size(); i++)
			somma += dati.get(i);
		return somma/dati.size();
	}
	//varianza
	public double varianza(double media, Vector<Integer> dati){
		double var=0;
		for(int i=0; i< dati.size(); i++)
			var += (dati.get(i) - media) * (dati.get(i)-media);
		return var/dati.size();
	}
	//deviazione standard
	public double deviazioneStandard(Vector<Integer> dati){
		double media = media(dati);
		double var = varianza(media, dati);
		return Math.sqrt(var);
	}
	//covarianza
	public double covarianza(Vector<Integer>dati1,Vector<Integer> dati2){
		double cov=0;
		double m1 = media(dati1);
		double m2 = media(dati2);
		for(int i=0; i< dati1.size(); i++)
			cov += (dati1.get(i)-m1) * (dati2.get(i)-m2);
		return cov/dati1.size();
	}
	//per settare i valori min, max, num
	public void setMin1(String m){
		this.min1 = Integer.parseInt(m);
	}
	public int getMin1(){
		return this.min1;
	}
	public void setMin2(String m){
		this.min2 = Integer.parseInt(m);
	}
	public int getMin2(){
		return this.min2;
	}
	public void setMax1(String m){
		this.max1 = Integer.parseInt(m);
	}
	public int getMax1(){
		return this.max1;
	}
	public void setMax2(String m){
		this.max2 = Integer.parseInt(m);
	}
	public int getMax2(){
		return this.max2;
	}
	public void setNum(String n){
		this.num = Integer.parseInt(n);
	}
	public int getNum(){
		return this.num;
	}
	
	/* funzione vecchia per usare i dati random
	public void calcolaTabellaPearson(int n){
		int size = valore.size();
		datiTab = new int[size][n];
		//genero i dati e li metto nella matrice
		generaDatiTab(n);
		//calcolo Pearson
		for(int r=0; r<size; r++){
			for(int c=0; c<size; c++){
				if(c<r){		//se non si vuole la diagonale di 1.0 basta mettere <=
					this.pearsonTab.add(2.0);
				}
				else{
					this.pearsonTab.add(calcolaPearsonDaIndici(c, r, n));
				}
			}
		}
		
		System.out.println("pearsonTab:" + pearsonTab);
		
	}*/
	/* funzione vecchia per generare i dati random
	public void generaDatiTab(int n){
		int temp=0;
		for(int j=0; j<valore.size(); j++)
			for(int i=0; i<n; i++)
			{
				temp = (minimo.get(j)+(int)(Math.random()*(massimo.get(j)-minimo.get(j))));
				//System.out.println("dati: " + temp + " i: " + i);
				datiTab[j][i] = temp;
			}
	
	}*/
	
	public void calcolaTabellaPearson(int ID){
		
		Vector<Integer> s = getStoricoPearson(ID, valore.get(0), valore.size());
		int n= s.size();
		datiTab = new int[valore.size()][n];
		
		
		for(int i=0; i<valore.size(); i++)
		{
			s = getStoricoPearson(ID, valore.get(i), valore.size());
			for(int j=0; j<s.size(); j++)
				datiTab[i][j] = s.get(j);
		}
		
		//calcolo Pearson
		for(int r=0; r<valore.size(); r++){
			for(int c=0; c<valore.size(); c++){
				if(c<r){		//se non si vuole la diagonale di 1.0 basta mettere <=
					this.pearsonTab.add(2.0);
				}
				else{
					this.pearsonTab.add(calcolaPearsonDaIndici(c, r, n));
				}
			}
		}
		
		System.out.println("pearsonTab:" + pearsonTab);
	}
	
	//calcolo pearson sapendo gli indici
	public double calcolaPearsonDaIndici(int c, int r, int n){
		//calcolo la media delle due righe
		Vector<Integer> x = new Vector<Integer>();
		Vector<Integer> y = new Vector<Integer>();
		
		for(int i=0; i<n; i++)
		{
			x.add(datiTab[r][i]);
			y.add(datiTab[c][i]);
		}
		
		double devStd1 = deviazioneStandard(x);
		double devStd2 = deviazioneStandard(y);
		double cov = covarianza(x, y);
		
		return cov/(devStd1*devStd2);
	}
	
	public String getPearsonTab(int i){
		if(pearsonTab.get(i) == 2.)
			return "---";
		else
			return pearsonTab.get(i).toString();
	}
	
	public int getDimPearsonTab(){
		return pearsonTab.size(); 
	}
	public int getDimValore(){
		return valore.size();
	}
	
	public Vector<Integer> getStoricoPearson(int ID, String parametro, int size){
		storico.clear();
		data.clear();
		parametri.clear();
		d.clear();
		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME, SQLUSERNAME, SQLPW);
			String strQuery="select * from storico where IDPaziente=?";
	        PreparedStatement ps = con.prepareStatement(strQuery);
	        
	        ps.setInt(1, ID);
	        
	       	ResultSet rs = ps.executeQuery();
	       	while(rs.next()){
	       		parametri.add(rs.getString("valore"));
	       		data.add(rs.getTimestamp("data"));
				d.add(rs.getInt("dato"));
			}
			ps.close();
			con.close();
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		
		for(int i=0; i<parametri.size(); i++){
			int conta=0;
			if((parametri.get(i)).equals(parametro)){
				for(int j=0; j<parametri.size(); j++){
					if(!(parametri.get(j)).equals(parametro) && (data.get(i)).equals(data.get(j))){
						conta++;
					}		
					if(conta == size-1){
						conta=0;
						storico.add(d.get(i));
					}
				}
			}
		}
			
		return storico;
	}
	
	//funzioni per controllare gli errori
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
			errors.add("ERRORE: la data " + data1 + " non può essere superiore a " + data2);
		}
	}
	public void controllaMinMax (int min, int max){
		if(min >= max){
			errors.add("ERRORE: il valore minimo " + min + " non può essere maggiore o uguale al massimo " + max);
		}
	}
	
}//fine classi Monitoraggio
