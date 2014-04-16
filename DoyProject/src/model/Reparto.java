package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Classe per gestire le chiamate alla tabella dei reparti nel database
 * 
 * @author Federico
 * 
 */
public class Reparto {

	// Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root";
	private static String SQLPW = "root";

	private ArrayList<String> reparti = new ArrayList<String>();
	private boolean notagain = false;
	private byte i = 0;

	/**
	 * Metodo costruttore vuoto che rispecchia la caratteristica dei Bean
	 */
	public Reparto() {

	}
	
	/**
	 * Funzione che ritorna il numero di reparti
	 * @return torna il numero di reparti
	 */
	public int getNumRep(){
		
		//Se per caso non c'� niente in reparti, li (ri)carico dal DB
		if(this.reparti.size() == 0){
			System.out.println("getNumRep, caricamento reparti...");
			this.repartiR();
		}
		return this.reparti.size();
	}

	/**
	 * Funzione micidiale che uso per tornare tutti i reparti
	 * 
	 * @return un reparto
	 */
	public String getReparti1by1() {

		if (!notagain) {
			this.repartiR(); // Faccio la richiesta al database solo se non l'ho
								// gi� fatta per sto giro
			this.notagain = true;
			this.i = 0;
		}

		System.out.println("*******************");

		boolean exception = false; // L'eccezione che se esco dall'array allora
									// non chiedo pi� niente
		String nth = "";

		try {
			this.reparti.get(i);
		} catch (Exception e) {
			System.out.println("Eccezione al giro i: " + this.i + " tipo "
					+ e.getMessage());
			// e.printStackTrace(); Gestita
			exception = true;
		}

		// Se non ho sforato l'array allora torno il reparto corretto
		if (!exception) {
			this.i++;
			System.out.println("Reparto " + this.i + "-> "
					+ this.reparti.get(i - 1));
			return this.reparti.get(i - 1);
		}
		// altrimenti torno la stringa vuota
		else {
			System.out.println("getReparto1by1, else");
			this.i = 0;
			this.notagain = false;
			exception = false;
			return nth;
		}
	}

	/**
	 * Metodo privato che setta i reparti
	 * 
	 * @param reparto
	 */
	private void setReparti(String reparto) {
		System.out.println("setReparti: " + reparto);
		this.reparti.add(reparto.toString());
	}

	/**
	 * Funzione che fa la query sul database per chiedere i reparti
	 * 
	 * @return
	 */
	private boolean repartiR() {
		boolean result = false;
		System.out.println("Chiedo i reparti. RepartiR()");

		try {
			Class.forName(DRIVER).newInstance();
			Connection con = DriverManager.getConnection(URL + DBNAME,
					SQLUSERNAME, SQLPW);
			String query = "SELECT * FROM reparti";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery(); // Esegue la query preparata
												// nel PreparedStatement
			if (rs.next()) {
				result = true;
				this.setReparti(rs.getString("reparto")); // prendo il risultato
															// dall'unica
															// colonna che ho
															// adesso

				while (rs.next()) {
					this.setReparti(rs.getString("reparto"));
				}

			}

			ps.close();
			con.close();

		} catch (Exception e) {
			System.out.println("Errore query repartiR in Reparto.java: "
					+ e.getMessage());
			e.printStackTrace();

		}

		return result;
	}

}// fine Reparto