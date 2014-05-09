package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Tale classe è utilizzata per creare nel database delle entry di valori da
 * monitorare per il paziente
 * 
 * @author Federico
 * 
 */
public class Generation extends Thread {

	// ////////////////////////
	// VARIABILI E COSTRUTTORE
	// ////////////////////////

	// Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root";
	private static String SQLPW = "root";

	// NOTA BENE: TALI VARIABLI LE HO MESSE COSì MA DOVREBBERO ESSERE LE CHIAVI
	// PER LA TABELLA CON I VALORI DA AGGIORNARE
	private String dottore;
	private String paziente; // Il paziente da aggiornare
	private String valore; // Il nome del valore da aggiornare
	private int valore_max;
	private Date data_fine;
	private Date data_curr; // Data corrente che deve essere sempre minore alla
							// data_fine

	private int id;
	private Vector<String> valori;
	private Vector<Integer> minimi;
	private Vector<Integer> massimi;
	private Timestamp datain;
	private Timestamp dataout;
	private String datainString;



	public Generation(int id, Vector<String> valori, Vector<Integer> minimi,
			Vector<Integer> massimi, String datain) {
		this.id = id;
		this.valori = new Vector<String>(valori);
		this.minimi = new Vector<Integer>(minimi);
		this.massimi = new Vector<Integer>(massimi);
		this.datainString = datain + " 00:00:00.000000";

		Timestamp datainizio = Timestamp.valueOf(datain + " 00:00:00.000000");
		this.datain = datainizio;

	}

	/*
	 * this.data_curr = new Date(); try { //this.data_curr = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .parse(data_curr.toString()); }
	 * catch (ParseException e) { e.printStackTrace(); }
	 */
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("AVVIATO");

		int cont = 0;	//cONTATORE DEI DATI INSERITI.
		
		// Se la data corrente è prima della data di fine
		boolean go = true;
		while (go) {
			
			//Sleep
			try {
				sleep(1000);
			} catch (InterruptedException e1) {
				System.out
				.println("Errore query in generation.java, funzione sleep(): "
						+ e1.getMessage());
				e1.printStackTrace();
			}
			
			
			//Genero data casuale, la faccio fuori così ho la data uguale per ogni valore che monitoro del paziente
			//in modo da poter calcolare correttamente l'indice di Pearson
			long offset = Timestamp.valueOf(this.datainString)
					.getTime();
			long datafine = Calendar.getInstance().getTimeInMillis();
			long diff = datafine - offset + 1;
			Timestamp dataRand = new Timestamp(offset
					+ (long) (Math.random() * diff));

			
			//For che inserisce un dato in ogni valore monitorato
			for (byte i = 0; i < this.valori.size(); ++i) {
				try {
					Class.forName(DRIVER).newInstance();
					Connection con = DriverManager.getConnection(URL + DBNAME,
							SQLUSERNAME, SQLPW);
					// inserisco i dati
					String query = "INSERT INTO storico (IDPaziente, data, valore, dato) values (?, ?, ?, ?)";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setInt(1, this.id);

					/*// Genero data casuale
					long offset = Timestamp.valueOf(this.datainString)
							.getTime();
					long datafine = Calendar.getInstance().getTimeInMillis();
					long diff = datafine - offset + 1;
					Timestamp dataRand = new Timestamp(offset
							+ (long) (Math.random() * diff));*/
					
					ps.setTimestamp(2, dataRand);	//La data casuale è creata prima del for

					// Setto il valore
					ps.setString(3, this.valori.get(i));

					// Setto il dato casuale
					int dato = (this.minimi.get(i) + (int) (Math.random() * (this.massimi
							.get(i) - this.minimi.get(i))));
					ps.setInt(4, dato);

					int ret = ps.executeUpdate();
					ps.close();
					con.close();
					
					//if(ret>0)
						//System.out.println("Dato inserito");

				} catch (Exception e) {
					System.out
							.println("Errore query in generation.java, funzione run(): "
									+ e.getMessage());
					e.printStackTrace();

				}
			}// fine for
			
			cont++;
			if(cont >= 10)
				go = false;
		} // fine while(stop)
		
		System.out.println("Thread Fermato");

	}

	// Fine del Thread -> Dovrebbe chiudersi da solo quando finisce il run

}
