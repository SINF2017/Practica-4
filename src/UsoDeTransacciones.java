import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Practica 4. (Ejercicios 8)
 *
 * @References:
 *      @tutorialspoint: https://www.tutorialspoint.com/jdbc/jdbc-introduction.htm
 */

public class UsoDeTransacciones {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // JDBC driver name, database URL, credentials, Statement and Connection.
        final String url = "jdbc:mysql://localhost:3306/misPeliculas?useSSL=false";
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String user = "ruben";
        final String password = "Sinfpracticas2017!";
        Statement statement = null;
        Connection connection = null;

        // Register JDBC driver
        Class.forName(JDBC_DRIVER);

        try {
            // Open connection
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(url,user,password);
            System.out.println("Connected database successfully...");

            // Enable manual-transaction.
            connection.setAutoCommit(false);

            // Execute a query
            System.out.println("Creating secure tables into database...");
            statement = connection.createStatement();

            // Insert into the tables
            // language=MySQL
            String insertActores =
                    "INSERT IGNORE INTO actores(ID_actor,IMDB,nombre,edad) VALUES\n" +
                            "  (13,0331516,'Ryan Gosling',36);";

            // language=MySQL
            String insertDirectores =
                    "INSERT IGNORE INTO directores(ID_director,IMDB,nombre,edad) VALUES\n" +
                            "  (10,3227090,'Damien Chazelle',32);";

            // language=MySQL
            String insertPeliculas =
                    "INSERT IGNORE INTO peliculas(ID_pelicula,IMDB,ID_director,titulo) VALUES\n" +
                            "  (12,3783958,10,'La La Land');";

            // language=MySQL
            String insertActorEnPelicula =
                    "INSERT IGNORE INTO actorEnPelicula(ID_actor,ID_pelicula) VALUES\n" +
                            "  (13,12);";

            statement.executeUpdate(insertActores);
            statement.executeUpdate(insertDirectores);
            statement.executeUpdate(insertPeliculas);
            statement.executeUpdate(insertActorEnPelicula);

            // Check the correct working of transactions.
            //System.exit(-1);

            // If there aren't errors, we are going to commit the changes.
            connection.commit();

        }catch (SQLException exception){
            // If there is any error.
            if (connection != null)
                connection.rollback();
        }

        // Clean the environment:
        if (connection != null)
            connection.close();
        if (statement != null)
            statement.close();
        System.out.println("Database closed correctly!");
    }
}
