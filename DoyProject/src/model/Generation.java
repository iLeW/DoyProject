package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	/**
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
	public Generation(String dottore, String paziente, String valore,
			int valore_max, String data_fine) {
		this.dottore = dottore;
		this.paziente = paziente;
		this.valore = valore;
		this.valore_max = valore_max;
		this.data_fine = new Date();

		// data_fine formattata in modo corretto per trasformarla in data
		try {
			this.data_fine = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(data_fine);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		boolean stop = false;
		// Se la data corrente è prima della data di fine
		while (stop) {
			// data_curr formattata in modo corretto
			this.data_curr = new Date();
			try {
				this.data_curr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(data_curr.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (data_curr.before(data_fine)) {
				
				/*////////////////////////////// FARE QUESTO INSERIMENTO DI VALORE///////////////////////////////////////////////////////////////////////////
				try {
					Class.forName(DRIVER).newInstance();
					Connection con = DriverManager.getConnection(URL + DBNAME,
							SQLUSERNAME, SQLPW);

					
					String query = "INSERT INTO messages (receiver, sender, message, date, readbool) VALUES (?, ?, ?, ?, ?)";
					PreparedStatement ps = con.prepareStatement(query);
					ps.setString(1, receiver);
					ps.setString(2, sender);
					ps.setString(3, mex);
					ps.setString(4, date);
					ps.setBoolean(5, false);
					int num = ps.executeUpdate();
					
					//SE IL DATO CREATO SUPERA IL val_max ALLORA MANDO UN MESSAGGIO AL DOTTORE (quindi altra chiamata al database dei messaggi, il campo sender lo metto come speciale "ALERT")

					if (num > 0)
						result = true;
					else
						result = false;

					ps.close();
					con.close();

				}

				catch (Exception e) {
					System.out
							.println("Errore query in Messaggio.java, funzione sendMexDB(): "
									+ e.getMessage());
					e.printStackTrace();
				}
////////////////////////////////////////////////////////////////////////////////////////////////////////////// */
			}
			else
				stop = true;
		}	//fine while(stop)

	}
	
	//Fine del Thread -> Dovrebbe chiudersi da solo quando finisce il run

}
