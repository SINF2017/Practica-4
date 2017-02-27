import java.sql.*;

/**
 * Practica 4, Ejercicio 6.
 *
 * @References:
 *      @tutorialspoint: https://www.tutorialspoint.com/jdbc/jdbc-introduction.htm
 *
 *
 * Requiered steps:
 *
 * 1. Register the JDBC driver: requires that you initialize
 * a driver so you can open a communications channel with
 * the database.
 *
 * 2. Open connection: requires using the DriverManager.getConnection()
 * method to create a Connection object, which represents a physical
 * connection with a database server.
 *
 * 3. Execute a query: requires using an object of type Statement for
 * building and submitting an SQL statement to select (i.e. fetch) records
 * form a table.
 *
 * 4. Extract data: once SQL query is executed, you can fetch records
 * from the table.
 *
 * 5. Clean up the environment: requires explicitly closing all database
 * resources versus relying on the JVM's garbage collection.
 *
 * @Note:
 *      next(): permite comprobar si quedan más tuplas por acceder y avanzar a la siguiente.
 *      getXXX: conjunto de métodos getInt(), getFloat(),...
 *
 */

public class ConsultandoBasesDeDatos {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // JDBC driver name, database URL, credentials, Statement and Connection.
        final String url = "jdbc:mysql://localhost:3306/misPeliculas?useSSL=false";
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String user = "ruben";
        final String password = "Sinfpracticas2017!";
        Statement statement;
        Connection connection;

        // Register the JDBC driver:
        Class.forName(JDBC_DRIVER);

        // Open connection:
        System.out.println("Connecting to a selected database...");
        connection = DriverManager.getConnection(url,user,password);
        System.out.println("Connected database successfully...");

        // Create queries:
        System.out.println("Creating statement...");
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        //language=MySQL
        String listarActores =
                "SELECT * FROM actores";

        //Extract data:
        ResultSet resultSet = statement.executeQuery(listarActores);
        System.out.println("Listar todos los actores: ");
        while (resultSet.next()){
            System.out.println(" > ID: " + resultSet.getInt("ID_Actor")
                    + ", IMDB: " + resultSet.getInt("IMDB") +
                    ", nombre: " + resultSet.getString("nombre") +
                    ", edad: " + resultSet.getInt("edad"));
        }


        //Put all film's titles in uppercase.
        //language=MySQL
        String uppercase = "SELECT * FROM peliculas";
        resultSet = statement.executeQuery(uppercase);
        while (resultSet.next()){
            String titulo = resultSet.getString("titulo");
            titulo = titulo.toUpperCase();
            resultSet.updateString("titulo",titulo);
            resultSet.updateRow();
        }

        // Clean up the environment:
        resultSet.close();
        connection.close();
        statement.close();
    }
}