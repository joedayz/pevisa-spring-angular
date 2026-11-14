package pe.com.pevisa.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {


    static void main() throws SQLException {

        // jdbc:<proveedor>:<tipo o subprotocolo>:<detalles de conexión>
        String url = "jdbc:h2:mem:tienda";
        String usuario = "pm";
        String clave = "welcome1";

        IO.println("Drivers registrados:");
        DriverManager.drivers().forEach(d -> IO.println("  " + d.getClass().getName()));

        try(Connection con = DriverManager.getConnection(url, usuario, clave)) {
            DatabaseMetaData md = con.getMetaData();
            IO.println("Base de datos: " + md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
            IO.println("Driver:        " + md.getDriverName() + " " + md.getDriverVersion());
            IO.println("API JDBC:      " + md.getJDBCMajorVersion() + "." + md.getJDBCMinorVersion());
            IO.println("Implementación de Connection: " + con.getClass().getName());
            IO.println("¿válida? " + con.isValid(2) + ", autoCommit: " + con.getAutoCommit());
        }


        // Otra forma: usuario y clave en un objeto Properties
        var props = new Properties();
        props.setProperty("user", usuario);
        props.setProperty("password", clave);

        try (Connection con = DriverManager.getConnection(url, props)) {
            IO.println("Con Properties, usuario: " + con.getMetaData().getUserName());
        }

        // En aplicaciones reales se usa un DataSource (normalmente con pool de conexiones)
        DataSource ds = crearDataSource(url, usuario, clave);
        try (Connection con = ds.getConnection()) {
            IO.println("Con DataSource (" + ds.getClass().getSimpleName() + "): " + con.getMetaData().getURL());
        }

        // Si ningún driver acepta la URL: SQLException con SQLState 08001
        try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
                "pm", "x")) {
            IO.println("¡conectado!");
        } catch (SQLException e) {
            IO.println("Error: " + e.getMessage() + " | SQLState: " + e.getSQLState());
        }

    }


    static DataSource crearDataSource(String url, String usuario, String clave) {
        var ds = new org.h2.jdbcx.JdbcDataSource();   // clase propia del proveedor
        ds.setURL(url);
        ds.setUser(usuario);
        ds.setPassword(clave);
        return ds;
    }
}
