import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ejercicio5 {
	private static final String user = "sara";
	private static final String contra = "contra";
	
	public static void main(String[] args) {
		String bd = "misPeliculas";//args[0];
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
					+ "IMDb mediumint(8) UNIQUE, "
					+ "Edad int(3) NOT NULL, CHECK (Edad>0 AND Edad<120)) ENGINE=InnoDB");
        	
        	stmt.executeUpdate("create table Directores("
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
        	ex.printStackTrace();
        }
        
        
        try{
        	Scanner in = new Scanner(System.in);
        	
        	System.out.println("Almacenar tupla en:");
        	System.out.println("1 - Actores\n2 - Peliculas\n3 - Directores\n4 - pelicula_actor");
        	
        	int opcion = Integer.parseInt(in.nextLine());
        	String [] p;
        	
        	switch(opcion){
        	case 1:
        		System.out.println("Introduzca: ID_actor,Nombre,imbd,edad");
        		p = (in.nextLine()).split(",");
        		stmt.executeUpdate("INSERT INTO Actores VALUES ("+p[0]+",'"+p[1]+"',"+p[2]+","+p[3]+")");
        		System.out.println("Nuevo actor introducido.");
        		break;
        	case 2:
        		System.out.println("Introduzca: ID_pelicula,titulo,imbd,ID_director");
        		p = (in.nextLine()).split(",");
        		stmt.executeUpdate("INSERT INTO peliculas VALUES ("+p[0]+",'"+p[1]+"',"+p[2]+","+p[3]+")");
        		System.out.println("Nueva pelicula introducida.");
        		break;
        	case 3:
        		System.out.println("Introduzca: ID_director,Nombre,imbd,edad");
        		p = (in.nextLine()).split(",");
        		stmt.executeUpdate("INSERT INTO Directores VALUES ("+p[0]+",'"+p[1]+"',"+p[2]+","+p[3]+")");
        		System.out.println("Nuevo director introducido.");
        		break;
        	case 4:
        		System.out.println("Introduzca: ID_actor,ID_pelicula");
        		p = (in.nextLine()).split(",");
        		stmt.executeUpdate("INSERT INTO pelicula_actor VALUES ("+p[0]+","+p[1]+")");
        		System.out.println("Nueva tupla anadida en pelicula_actor.");
        		break;
        	}
        	in.close();
        	
        	/*Insert into the peliculas's table:
            preparedStatement = connection.prepareStatement("INSERT IGNORE INTO " +
            									"peliculas(ID_pelicula, IMDB, ID_director, titulo) VALUES (?,?,?,?)");
            preparedStatement.setInt(1,id_pelicula);
            preparedStatement.setInt(2,imdb_pelicula);
            preparedStatement.setInt(3,id_director);
            preparedStatement.setString(4,titulo_pelicula);
            preparedStatement.executeUpdate();*/
        }catch (SQLException ex){
        	System.out.println("Hubo un error introduciendo la tupla.");
        }finally {
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
