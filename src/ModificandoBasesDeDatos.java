import java.sql.*;
import java.util.Scanner;

/**
 * Practica 4. (Ejercicios 1 - 5)
 *
 * @References:
 *      @stackoverflow: http://stackoverflow.com/questions/2839321/connect-java-to-a-mysql-database
 *      @tutorialspoint: https://www.tutorialspoint.com/jdbc/jdbc-introduction.htm
 *
 * @ImplNote:
 *      @Connection: this interfece have all methods for contacting a database.
 *      @DriverManager class manages the establishment of connections
 *      @Statement: we use objects created from this interface to submit the
 *      SQL statements to the database.
 */

public class ModificandoBasesDeDatos {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // JDBC driver name, database URL, credentials, Statement and Connection.
        final String url = "jdbc:mysql://localhost:3306/misPeliculas?useSSL=false";
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String user = "ruben";
        final String password = "Sinfpracticas2017!";
        Statement statement;
        Connection connection;

        // Register JDBC driver
        Class.forName(JDBC_DRIVER);

        // Open connection
        System.out.println("Connecting to a selected database...");
        connection = DriverManager.getConnection(url,user,password);
        System.out.println("Connected database successfully...");

        // Execute a query
        System.out.println("Creating tables into database...");
        statement = connection.createStatement();

        // Create tables
        // language=MySQL
        String tableActores =
                "CREATE TABLE IF NOT EXISTS actores(\n" +
                        "  ID_actor INTEGER NOT NULL,\n" +
                        "  IMDB INTEGER,\n" +
                        "  nombre VARCHAR(100) NOT NULL,\n" +
                        "  edad INTEGER NOT NULL CHECK(edad > 0 AND edad < 120),\n" +
                        "  PRIMARY KEY (ID_actor)\n" +
                        ");";

        // language=MySQL
        String tableDirectores =
                "CREATE TABLE IF NOT EXISTS directores(\n" +
                        "  ID_director INTEGER NOT NULL,\n" +
                        "  IMDB INTEGER,\n" +
                        "  nombre VARCHAR(100) NOT NULL,\n" +
                        "  edad INTEGER NOT NULL CHECK(edad > 0 AND edad < 120),\n" +
                        "  PRIMARY KEY (ID_director)\n" +
                        ");";

        // language=MySQL
        String tablePeliculas =
                "CREATE TABLE IF NOT EXISTS peliculas(\n" +
                        "  ID_pelicula INTEGER NOT NULL PRIMARY KEY,\n" +
                        "  IMDB INTEGER,\n" +
                        "  ID_director INTEGER NOT NULL,\n" +
                        "  titulo VARCHAR(50) NOT NULL,\n" +
                        "  FOREIGN KEY (ID_director) REFERENCES directores(ID_director)\n" +
                        ");";

        // language=MySQL
        String tableActorEnPeliculas =
                "CREATE TABLE IF NOT EXISTS actorEnPelicula(\n" +
                        "  ID_actor INTEGER NOT NULL,\n" +
                        "  ID_pelicula INTEGER NOT NULL,\n" +
                        "  FOREIGN KEY (ID_actor) REFERENCES actores(ID_actor),\n" +
                        "  FOREIGN KEY (ID_pelicula) REFERENCES peliculas(ID_pelicula)\n" +
                        ");";

        statement.executeUpdate(tableActores);
        statement.executeUpdate(tableDirectores);
        statement.executeUpdate(tablePeliculas);
        statement.executeUpdate(tableActorEnPeliculas);

        // Insert into the tables
        // language=MySQL
        String insertActores =
                "INSERT IGNORE INTO actores(ID_actor,IMDB,nombre,edad) VALUES\n" +
                        "  (0,0000206,'Keanu Reeves',52),\n" +
                        "  (1,1946193,'Jamie Dornan',34),\n" +
                        "  (2,0424848,'Dakota Johnson',27),\n" +
                        "  (3,0000151,'Morgan Freeman',79),\n" +
                        "  (4,0000138,'Leonardo DiCaprio',42),\n" +
                        "  (5,0000008,'Marlon Brando',93),\n" +
                        "  (6,0000199,'Al Pacino',76),\n" +
                        "  (7,0000288,'Christian Bale',43),\n" +
                        "  (8,0000148,'Harrison Ford',74),\n" +
                        "  (9,0000434,'Mark Hamill',65),\n" +
                        "  (10,0000402,'Carrie Fisher',61),\n" +
                        "  (11,0000115,'Nicolas Cage',53);";

        // language=MySQL
        String insertDirectores =
                "INSERT IGNORE INTO directores(ID_director,IMDB,nombre,edad) VALUES\n" +
                        "  (0,0000338,'Francis Ford Coppola',77),\n" +
                        "  (1,0853374,'Sam Taylor-Johnson',49),\n" +
                        "  (2,0001226,'James Foley',63),\n" +
                        "  (3,0634240,'Christopher Nolan',46),\n" +
                        "  (4,0905154,'Lana Wachowski',52),\n" +
                        "  (5,0001104,'Frank Darabont',58),\n" +
                        "  (6,0449984,'Irvin Kershner',94),\n" +
                        "  (7,0009190,'J.J. Abrams',50),\n" +
                        "  (8,0327944,'Alejandro G. Iñárritu',53);";

        // language=MySQL
        String insertPeliculas =
                "INSERT IGNORE INTO peliculas(ID_pelicula,IMDB,ID_director,titulo) VALUES\n" +
                        "  (0,0068646,0,'El Padrino'),\n" +
                        "  (1,2322441,1,'Cincuenta sombras de Grey'),\n" +
                        "  (2,4465564,2,'Cincuenta sombras mas oscuras'),\n" +
                        "  (3,1375666,3,'Origen'),\n" +
                        "  (4,0133093,4,'Matrix'),\n" +
                        "  (5,0111161,5,'Cadena perpetua'),\n" +
                        "  (6,0468569,3,'El caballero oscuro'),\n" +
                        "  (7,0080684,6,'Star Wars, El imperio contraataca'),\n" +
                        "  (8,2488496,7,'Star Wars, El despertar de la fuerza'),\n" +
                        "  (9,0482571,3,'El truco final'),\n" +
                        "  (10,1663202,8,'The Revenant');";

        // language=MySQL
        String insertActorEnPelicula =
                "INSERT IGNORE INTO actorEnPelicula(ID_actor,ID_pelicula) VALUES\n" +
                        "  (0,4),\n" +
                        "  (1,1),\n" +
                        "  (1,2),\n" +
                        "  (2,1),\n" +
                        "  (2,2),\n" +
                        "  (3,5),\n" +
                        "  (4,3),\n" +
                        "  (4,10),\n" +
                        "  (5,0),\n" +
                        "  (6,0),\n" +
                        "  (7,6),\n" +
                        "  (7,9),\n" +
                        "  (8,6),\n" +
                        "  (8,7),\n" +
                        "  (9,6),\n" +
                        "  (9,7),\n" +
                        "  (10,6),\n" +
                        "  (10,7);";

        statement.executeUpdate(insertActores);
        statement.executeUpdate(insertDirectores);
        statement.executeUpdate(insertPeliculas);
        statement.executeUpdate(insertActorEnPelicula);

        menu(connection);

        // Clean the environment:
        connection.close();
        statement.close();
        System.out.println("Database closed correctly!");
    }


    private static void menu(Connection connection) throws SQLException {
        int opcion = 0;
        Scanner keyboard = new Scanner(System.in);
        PreparedStatement preparedStatement;

        while (opcion < 2) {
            System.out.println("*********************************");
            System.out.println("1. Insertar una nueva tupla.");
            System.out.println("2. Salir.");
            System.out.println("\nOpcion: ");
            opcion = Integer.parseInt(keyboard.nextLine());
            switch (opcion) {
                case 1:
                    int opcionTabla;
                    System.out.println("\n\nSeleccione la relacion en la que desea insertar la tupla.");
                    System.out.println("1. Actores.");
                    System.out.println("2. Directores.");
                    System.out.println("3. Peliculas.");
                    System.out.println("4. ActorEnPelicula.");
                    System.out.println("Pulse otro numero o caracter para volver.");
                    System.out.println("\nOpcion: ");
                    opcionTabla = Integer.parseInt(keyboard.nextLine());
                    switch (opcionTabla) {
                        case 1:
                            int id_actor;
                            int imdb_actor;
                            String nombre_actor;
                            int edad_actor;

                            //Get information:
                            System.out.println("\n\nInformacion necesaria para crear una nueva entrada en actores.");
                            System.out.println("Nombre del actor: ");
                            nombre_actor = keyboard.nextLine();
                            System.out.println("Edad del actor: ");
                            edad_actor = Integer.parseInt(keyboard.nextLine());
                            System.out.println("ID del actor: ");
                            id_actor = Integer.parseInt(keyboard.nextLine());
                            System.out.println("IMDB del actor: ");
                            imdb_actor = Integer.parseInt(keyboard.nextLine());

                            //Insert into the actores's table.
                            preparedStatement = connection.prepareStatement("INSERT IGNORE INTO " +
                                    "actores(ID_actor, IMDB, nombre, edad) VALUES (?,?,?,?)");
                            preparedStatement.setInt(1,id_actor);
                            preparedStatement.setInt(2,imdb_actor);
                            preparedStatement.setString(3,nombre_actor);
                            preparedStatement.setInt(4,edad_actor);
                            preparedStatement.executeUpdate();
                            break;
                        case 2:
                            int id_director;
                            int imdb_director;
                            String nombre_director;
                            int edad_director;

                            //Get information:
                            System.out.println("\n\nInformacion necesaria para crear una nueva entrada en directores.");
                            System.out.println("Nombre del director: ");
                            nombre_director = keyboard.nextLine();
                            System.out.println("Edad del director: ");
                            edad_director = Integer.parseInt(keyboard.nextLine());
                            System.out.println("ID del director: ");
                            id_director = Integer.parseInt(keyboard.nextLine());
                            System.out.println("IMDB del director: ");
                            imdb_director = Integer.parseInt(keyboard.nextLine());

                            //Insert into the directores's table.
                            preparedStatement = connection.prepareStatement("INSERT IGNORE INTO " +
                                    "directores(ID_director, IMDB, nombre, edad) VALUES (?,?,?,?)");
                            preparedStatement.setInt(1,id_director);
                            preparedStatement.setInt(2,imdb_director);
                            preparedStatement.setString(3,nombre_director);
                            preparedStatement.setInt(4,edad_director);
                            preparedStatement.executeUpdate();
                            break;
                        case 3:
                            int id_pelicula;
                            int imdb_pelicula;
                            String titulo_pelicula;

                            //Get information:
                            System.out.println("\n\nInformacion necesaria para crear una nueva entrada en peliculas.");
                            System.out.println("Titulo de la pelicula: ");
                            titulo_pelicula = keyboard.nextLine();
                            System.out.println("IMDB pelicula: ");
                            imdb_pelicula = Integer.parseInt(keyboard.nextLine());
                            System.out.println("ID de la pelicula: ");
                            id_pelicula = Integer.parseInt(keyboard.nextLine());
                            System.out.println("ID del director: ");
                            id_director = Integer.parseInt(keyboard.nextLine());

                            //Insert into the peliculas's table.
                            preparedStatement = connection.prepareStatement("INSERT IGNORE INTO " +
                                    "peliculas(ID_pelicula, IMDB, ID_director, titulo) VALUES (?,?,?,?)");
                            preparedStatement.setInt(1,id_pelicula);
                            preparedStatement.setInt(2,imdb_pelicula);
                            preparedStatement.setInt(3,id_director);
                            preparedStatement.setString(4,titulo_pelicula);
                            preparedStatement.executeUpdate();
                            break;
                        case 4:
                            //Get information:
                            System.out.println("\n\nInformacion necesaria para crear una nueva entrada en actor en pelicula.");
                            System.out.println("ID de la pelicula: ");
                            id_pelicula = Integer.parseInt(keyboard.nextLine());
                            System.out.println("ID del actor: ");
                            id_actor = Integer.parseInt(keyboard.nextLine());

                            //Insert into the actoresEnPeliculas's table.
                            preparedStatement = connection.prepareStatement("INSERT IGNORE INTO " +
                                    "actorEnPelicula(ID_actor, ID_pelicula) VALUES (?,?)");
                            preparedStatement.setInt(1,id_actor);
                            preparedStatement.setInt(2,id_pelicula);
                            preparedStatement.executeUpdate();
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }
}
























