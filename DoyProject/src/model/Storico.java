package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;


public class Storico {
	
	// Variabili statiche per accesso al database
	private static String DBNAME = "doy";
	private static String URL = "jdbc:mysql://localhost:3306/";
	private static String DRIVER = "com.mysql.jdbc.Driver";
	private static String SQLUSERNAME = "root";
	private static String SQLPW = "root";

	//variabili locali
	private Vector<String> rep = new Vector<String>();
	
	
	public Storico() {

	}
	
	
	
	
	
	
}//fine classe Storico
