import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-installing.html
//https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-connect-drivermanager.html
//https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-statements.html

public class Ejercicio1 {
	
	private static final String user = "sara";
	private static final String contra = "contra";
	
	public static void main(String[] args) {
		String bd = args[0];//practica2
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+ bd +"?" +"user=" + user + "&password=" + contra);
            System.out.println("Conexion establecida con la base de datos '"+bd+"'.");
        } catch (Exception ex) {
            System.out.println("Problema al iniciar la conexion:");
            ex.printStackTrace();
        }
        
        try {
        	stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Peliculas");

			// Recorremos el resultado, mientras haya registros para leer, y escribimos el resultado en pantalla.
			while (rs.next()) {
				System.out.println("ID: " + rs.getString("ID_pelicula") + " | titulo: "+ rs.getString("titulo"));
				//System.out.println("ID: " + rs.getInt(1));
			}
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { }
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { }
                stmt = null;
            }
        }
        
        try {
        	conn.close();
			System.out.println("Conexion con la base de datos '"+bd+"' cerrada.");
		} catch (SQLException ex) {
			System.out.println("ERROR al cerrar conexion");
		}
    }
}
