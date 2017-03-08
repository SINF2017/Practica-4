import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio6 {
	
	private static final String user = "sara";
	private static final String contra = "contra";
	
	public static void main(String[] args) {
		String bd = "practica2";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//Inicio conexion:
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost/"+ bd +"?" +"user=" + user + "&password=" + contra);
            System.out.println("Conexion establecida con la base de datos '"+bd+"'.");
        } catch (Exception ex) {
            System.out.println("Problema al iniciar la conexion:");
            ex.printStackTrace();
        }
        
        //Acciones en BD:
        try {
        	String query;
        	stmt = conn.createStatement();
        	
        	/*2)Encontrar los nombres de todos los actores de la película "Mulan" 
				select nombre from actores where ID_actor IN
				(select ID_actor from pelicula_actor where ID_pelicula IN 
				(select ID_pelicula from Peliculas where titulo='Mulan'));*/
        	
        	query = "select nombre from actores where ID_actor IN"
        			+ "(select ID_actor from pelicula_actor where ID_pelicula IN "
        			+ "(select ID_pelicula from Peliculas where titulo='Mulan'))";
			rs = stmt.executeQuery(query);
			System.out.println("Resultado ejercicio 2 de la practica 2:");
			while (rs.next()) {
				System.out.println("Nombre: " + rs.getString("nombre"));
			}
			rs.close();
			
			/*6)Mostrar todos los actores que no han actuado en ninguna película*/
			/*usar NOT IN en vez de EXCEPT
			select * from actores where ID_actor NOT IN(select ID_actor from pelicula_actor);
			*/
			query = "select * from actores where ID_actor NOT IN(select ID_actor from pelicula_actor)";
			System.out.println("Resultado ejercicio 6 de la practica 2:");
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("ID_actor: " + rs.getString("ID_actor")+" | Nombre: "+rs.getString("nombre"));
			}
			
			
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        
        //Cerrar variables:
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
        
        //Fin conexion:
        try {
        	conn.close();
			System.out.println("Conexion con la base de datos '"+bd+"' cerrada.");
		} catch (SQLException ex) {
			System.out.println("ERROR al cerrar conexion");
		}
    }
}