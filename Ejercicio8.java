import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

//https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html

public class Ejercicio8 {

	private static final String user = "sara";
	private static final String contra = "contra";
	
	public static void main(String[] args) {
		String bd = "practica2";
		Connection conn = null;
		Statement stmt = null;
		//PreparedStatement ps = null;
		
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+ bd +"?" +"user=" + user + "&password=" + contra);
            System.out.println("Conexion establecida con la base de datos '"+bd+"'.");
        } catch (Exception ex) {
            System.out.println("Problema al iniciar la conexion:");
            ex.printStackTrace();
        }
        
        try {
        	conn.setAutoCommit(false);
        	stmt = conn.createStatement();
        	stmt.executeUpdate("INSERT INTO actores VALUES (6,'Juan Dominguez',846,88,'Holandesa')");
        	/*ps = conn.prepareStatement("insert into actores values(?,?,?,?,?)");
        	ps.setInt(1, 6);
        	ps.setString(2, "Raul Torres");
        	ps.setInt(3, 1495);
        	ps.setInt(4, 74);
        	ps.setString(5, "Holandesa");*/
        	
        	conn.commit();
        	//ps.close();	
        }
        catch (SQLException ex){
        	ex.printStackTrace();
        }
        try {
        	conn.setAutoCommit(true);
        	conn.close();
			System.out.println("Conexion con la base de datos '"+bd+"' cerrada.");
		} catch (SQLException ex) {
			System.out.println("ERROR al cerrar conexion");
		}
    }
}
