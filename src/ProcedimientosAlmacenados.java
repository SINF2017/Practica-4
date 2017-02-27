import java.sql.*;

/**
 * Practica 4. (Ejercicios 9)
 *
 * Ask Mikic.
 *
 * @References:
 *      @tutorialspoint: https://www.tutorialspoint.com/jdbc/jdbc-introduction.htm
 */
public class ProcedimientosAlmacenados {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // JDBC driver name, database URL, credentials, Statement and Connection.
        final String url = "jdbc:mysql://localhost:3306/misPeliculas?useSSL=false";
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String user = "ruben";
        final String password = "Sinfpracticas2017!";
        Connection connection;
        CallableStatement callableStatement;

        // Register JDBC driver
        Class.forName(JDBC_DRIVER);

        // Open connection
        System.out.println("Connecting to a selected database...");
        connection = DriverManager.getConnection(url,user,password);
        System.out.println("Connected database successfully...");

        // Execute a procedure
        System.out.println("Call procedure...");
        String procedure = "{CALL  contar_directores()}";
        callableStatement = connection.prepareCall(procedure);
        callableStatement.execute();

        // Clean the environment:
        callableStatement.close();
        connection.close();
        System.out.println("Database closed correctly!");
    }
}
