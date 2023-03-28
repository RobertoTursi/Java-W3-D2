package esercizio;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestionDb {

	public static void main(String[] args) {
		
		try {
			DbConnection db = new DbConnection();
			
			Studente s1 = new Studente("Fabio", "Marangi", "m", "2001/05/21", 7.53, 4.5, 9);
			Studente s2 = new Studente("Marika", "Fellini", "f", "2003/04/02", 10, 10, 10);
			Studente s3 = new Studente("Federica", "Merola", "f", "1996/11/01", 6, 5, 7);
//			db.creaStudente(s1);
//			db.creaStudente(s2);
//			db.creaStudente(s3);
//			
			//db.eliminareStudente(1);
			//System.out.println(db.getBest());
			
			List<Studente> arrayStudenti = db.getVotoRange(3, 9);
			arrayStudenti.forEach(s -> System.out.println(s));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

}
