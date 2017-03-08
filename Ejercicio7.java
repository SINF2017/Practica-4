import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html#cursors

public class Ejercicio7 {
	
	private static final String host = "localhost";
	private static final String user = "sara";
	private static final String contra = "contra";
	
	public static void main(String[] args) {
		String bd = "practica2";//"misPeliculas";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		CallableStatement cStmt = null;
		ArrayList <String> list = new ArrayList<>();
		
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://"+host+"/"+ bd +"?" +"user=" + user + "&password=" + contra);
            System.out.println("Conexion establecida con la base de datos '"+bd+"'.");
        } catch (Exception ex) {
            System.out.println("Problema al iniciar la conexion:");
            ex.printStackTrace();
        }
        
        try {
        	stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM peliculas");

			while (rs.next()) {
				String f = (rs.getString("Titulo")).trim();
				String aux [] = f.split(" ");
				String str = "";
				for(int i = 0; i< aux.length; i++){
					str = str + " " + Character.toUpperCase(aux[i].charAt(0)) + aux[i].substring(1);
				}
				str = str.trim();
				list.add(str);
			}
			
			for(int i = 0; i<list.size();i++){
				String update = "UPDATE Peliculas SET Titulo = '"+list.get(i)+"' where titulo='"+list.get(i)+"'";
				stmt.executeUpdate(update);
			}

		}
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
            if (cStmt != null) {
                try {
                	cStmt.close();
                } catch (SQLException sqlEx) { }
                cStmt = null;
            }
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
