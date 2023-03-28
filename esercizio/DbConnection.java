package esercizio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbConnection {
	
	private String url = "jdbc:postgresql://localhost:5432/";
	private String dbName = "studenti_esercizio2";
	private String userName = "postgres";
	private String password = "database";
	
	Statement st;
	
	public DbConnection() throws SQLException {
		Connection conn = DriverManager.getConnection(url+dbName, userName, password);
		st = conn.createStatement();
		creaTableSchoolStudents();
		
	}
	
	//Creo il metodo per creare la tabella "school_students":
	public void creaTableSchoolStudents() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS SchoolStudents("
				+ "id_students SERIAL PRIMARY KEY,"
				+ "name VARCHAR NOT NULL,"
				+ "lastname VARCHAR NOT NULL,"
				+ "gender VARCHAR NOT NULL,"
				+ "birthdate DATE NOT NULL,"
				+ "avg DECIMAL NOT NULL,"
				+ "min_vote DECIMAL NOT NULL,"
				+ "max_vote DECIMAL NOT NULL"
				+ ")";
		this.st.executeUpdate(sql);
	}
	
	//creo il metodo per aggiungere una istanza Studente alla tabella school_students
	public void creaStudente(Studente s) throws SQLException {
		String sql = "INSERT INTO SchoolStudents"
				+ "(name, lastname, gender, birthdate, avg, min_vote, max_vote)"
					+ "VALUES ('"+s.getName()+"', '"+s.getLastname()+"', '"+s.getGender()+"', '"+s.getBirthdate()+"', "+s.getAvg()+", "+s.getMin_vote()+", "+s.getMax_vote()+")";
		this.st.executeUpdate(sql);
		System.out.println("studente inserito nel db");
	}
	
	//creoil metodo per eliminare una istanza Studente in base all'id
	public void eliminareStudente(int id) throws SQLException {
		String sql = "DELETE FROM SchoolStudents WHERE id_students = " + id;
		this.st.executeUpdate(sql);
		System.out.println("Studente con id: " + id + " rimosso dal db");
	}
	
	
	//creo il metodo che restituisce lo studente con il max_vote più alto 
	public Studente getBest() throws SQLException {
		Studente s = null;
		
		String sql = "SELECT * FROM SchoolStudents WHERE max_vote = (SELECT MAX(avg) FROM SchoolStudents) ";
		ResultSet rs = this.st.executeQuery(sql);
		
		if(rs.next()) {
			Long id_students = rs.getLong("id_students");
			String name = rs.getString("name");
			String lastname = rs.getString("lastname");
			String gender = rs.getString("gender");
			String birthdate = rs.getString("birthdate");
			double avg = rs.getDouble("avg");
			double min_vote = rs.getDouble("min_vote");
			double max_vote = rs.getDouble("max_vote");
			
			s = new Studente(id_students, name, lastname, gender, birthdate, avg, min_vote, max_vote);
		}
		
		return s;
	}
	
	//creo il metodo che ritorna la lista di tutti gli studenti che hanno un min_move <= al primo parametro che inseriamo e un max_vote >= al secondo parametro che inseriamo
	public List<Studente> getVotoRange(int min, int max) throws SQLException{
		List<Studente> arrayStudenti = new ArrayList<Studente>();
		
		String sql = "SELECT * FROM SchoolStudents WHERE min_vote >= " + min + " AND max_vote <= " + max;
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			Long id_students = rs.getLong("id_students");
			String name = rs.getString("name");
			String lastname = rs.getString("lastname");
			String gender = rs.getString("gender");
			String birthdate = rs.getString("birthdate");
			double avg = rs.getDouble("avg");
			double min_vote = rs.getDouble("min_vote");
			double max_vote = rs.getDouble("max_vote");
			
			Studente s = new Studente(id_students, name, lastname, gender, birthdate, avg, min_vote, max_vote);
			arrayStudenti.add(s);
		}
		return arrayStudenti;
		
	}
}
