import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

//https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-usagenotes-statements-callable.html

public class Ejercicio9 {
	
	private static final String user = "sara";
	private static final String contra = "contra";
	
	public static void main(String[] args) {
		String bd = "practica2";
		Connection conn = null;
		CallableStatement cs = null;
		
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+ bd +"?" +"user=" + user + "&password=" + contra);
            System.out.println("Conexion establecida con la base de datos '"+bd+"'.");
        } catch (Exception ex) {
            System.out.println("Problema al iniciar la conexion:");
            ex.printStackTrace();
        }
        
        try {
        	
        	//Poner en mayusculas:
        	cs = conn.prepareCall("{CALL ponerEnMayusculas(?)}");
        	cs.setString(1, "hola");
        	cs.execute();
        	System.out.println("Resultado de 'ponerEnMayusculas': " + cs.getString(1));
        	cs.close();

        	/*cs = conn.prepareCall("{CALL procedimiento}");
        	
        	boolean hadResults = cs.execute();
        	
        	while (hadResults){
        		rs = cs.getResultSet();
        		while(rs.next()){
	        		System.out.println(rs.getString(1));
	        		hadResults = cs.getMoreResults();
        		}
        	}*/
        }
        catch (SQLException ex){
        	ex.printStackTrace();
        }
        
        try {
        	conn.close();
			System.out.println("Conexion con la base de datos '"+bd+"' cerrada.");
		} catch (SQLException ex) {
			System.out.println("ERROR al cerrar conexion");
		}
    }
}