import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio4 {
	private static final String user = "sara";
	private static final String contra = "contra";
	
	public static void main(String[] args) {
		String bd = args[0];//misPeliculas
		Connection conn = null;
		Statement stmt = null;
		
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
        	
        	stmt.executeUpdate("drop table if exists pelicula_actor");
        	stmt.executeUpdate("drop table if exists Peliculas");
        	stmt.executeUpdate("drop table if exists Directores");
        	stmt.executeUpdate("drop table if exists Actores");
        	
        	stmt.executeUpdate("create table Actores("
        			+ "ID_actor mediumint(8) NOT NULL PRIMARY KEY, "
					+ "Nombre varchar(50) NOT NULL, "
					+ "ID_IMDb mediumint(8) UNIQUE, "
					+ "Edad int(3) NOT NULL, CHECK (Edad>0 AND Edad<120)) ENGINE=InnoDB");
        	
        	stmt.executeUpdate(
					"create table Directores("
					+ "ID_director mediumint(8) NOT NULL PRIMARY KEY, "
					+ "Nombre varchar(50) NOT NULL, "
					+ "IMDb mediumint(8) UNIQUE, "
					+ "Edad int(3) NOT NULL, CHECK (Edad>=0 AND Edad<=120)) ENGINE=InnoDB");
        	
        	stmt.executeUpdate("create table Peliculas("
        			+ "ID_pelicula mediumint(8) NOT NULL PRIMARY KEY, "
        			+ "Titulo varchar(50) NOT NULL, "
        			+ "IMDb mediumint(8) UNIQUE, "
        			+ "ID_director mediumint(8), "
        			+ "FOREIGN KEY (ID_director) REFERENCES Directores (ID_director) "
        			+ "ON DELETE CASCADE ON UPDATE CASCADE) ENGINE=InnoDB");
        	
        	stmt.executeUpdate("create table pelicula_actor("
        			+ "ID_actor mediumint(8) NOT NULL , "
        			+ "ID_pelicula mediumint(8) NOT NULL, "
        			+ "PRIMARY KEY(ID_actor,ID_pelicula), F"
        			+ "OREIGN KEY (ID_actor) REFERENCES Actores (ID_actor) "
        			+ "ON DELETE CASCADE ON UPDATE CASCADE, "
        			+ "FOREIGN KEY (ID_pelicula) REFERENCES Peliculas (ID_pelicula) "
        			+ "ON DELETE CASCADE ON UPDATE CASCADE) ENGINE=InnoDB");
           	
        	stmt.executeUpdate("INSERT INTO Actores (ID_actor,Nombre,IMDb,Edad) VALUES (0,'Jorge Sanz',1584,25)");
        	stmt.executeUpdate("INSERT INTO Actores (ID_actor,Nombre,IMDb,Edad) VALUES (1,'Margarita Perez',1111,52)");
        	stmt.executeUpdate("INSERT INTO Actores (ID_actor,Nombre,IMDb,Edad) VALUES (2,'Maria Torres',8852,29)");
        	
        	stmt.executeUpdate("INSERT INTO Directores VALUES (0,'Juan Dominguez',5,88)");
        	
        	stmt.executeUpdate("INSERT INTO Peliculas VALUES (0,'Mulan',8941,0)");
        	
        	stmt.executeUpdate("INSERT INTO pelicula_actor VALUES (2,0)");
        	
        }
        catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        finally {
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
