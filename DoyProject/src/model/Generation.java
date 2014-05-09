package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	/*	*//**
	 * Metodo costruttore con cui decido anche il paziente e il valore da
	 * aggiornare
	 * 
	 * @param dottore
	 *            Il dottore a cui inviare il messaggio se un valore supera il
	 *            valore di monitoraggio massimo
	 * @param paziente
	 *            Il paziente di cui voglio aggiornare il valore
	 * @param valore
	 *            Il valore che voglio aggiornare
	 * @param valore_max
	 *            Il valore massimo che se superato manda un messaggio al
	 *            dottore
	 * @param data_fine
	 *            La data in cui voglio stoppare l'aggiornamento
	 */
	/*
	 * public Generation(String dottore, String paziente, String valore, int
	 * valore_max, String data_fine) { this.dottore = dottore; this.paziente =
	 * paziente; this.valore = valore; this.valore_max = valore_max;
	 * this.data_fine = new Date();
	 * 
	 * // data_fine formattata in modo corretto per trasformarla in data try {
	 * this.data_fine = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
	 * .parse(data_fine); } catch (ParseException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	public Generation(int id, Vector<String> valori, Vector<Integer> minimi,
			Vector<Integer> massimi, String datain) {
		this.id = id;
		this.valori = new Vector<String>(valori);
		this.minimi = new Vector<Integer>(minimi);
		this.massimi = new Vector<Integer>(massimi);
		this.datainString = datain + "00:00:00";

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

		boolean stop = false;
		// Se la data corrente è prima della data di fine
		while (stop) {
			
			try {
				sleep(5000);
			} catch (InterruptedException e1) {
				System.out
				.println("Errore query in generation.java, funzione sleep(): "
						+ e1.getMessage());
				e1.printStackTrace();
			}

			for (byte i = 0; i < this.valori.size(); ++i) {
				try {
					Class.forName(DRIVER).newInstance();
					Connection con = DriverManager.getConnection(URL + DBNAME,
							SQLUSERNAME, SQLPW);
					// inserisco i dati
					String query = "INSERT INTO storico (IDPaziente, data, valore, dato) values (?, ?, ?, ?)";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setInt(1, this.id);

					// Genero data casuale
					long offset = Timestamp.valueOf(this.datainString)
							.getTime();
					long datafine = Calendar.getInstance().getTimeInMillis();
					long diff = datafine - offset + 1;
					Timestamp dataRand = new Timestamp(offset
							+ (long) (Math.random() * diff));
					ps.setTimestamp(2, dataRand);

					// Setto il valore
					ps.setString(3, this.valori.get(i));

					// Setto il dato casuale
					int dato = (this.minimi.get(i) + (int) (Math.random() * (this.massimi
							.get(i) - this.minimi.get(i))));
					ps.setInt(4, dato);

					ps.executeUpdate();
					ps.close();
					con.close();

				} catch (Exception e) {
					System.out
							.println("Errore query in generation.java, funzione run(): "
									+ e.getMessage());
					e.printStackTrace();

				}
			}// fine for
		} // fine while(stop)

	}

	// Fine del Thread -> Dovrebbe chiudersi da solo quando finisce il run

}
