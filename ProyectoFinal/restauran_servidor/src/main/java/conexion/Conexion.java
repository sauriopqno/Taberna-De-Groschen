package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static final String connect = "jdbc:sqlite:Taberna del Groschen.db";
	
	public static Connection conectar () {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connect);
			
			System.out.println("¡Conexión establecida con éxito!");
		}catch(SQLException e) {
			System.out.println("Error al entrar a la taberna: " + e.getMessage());
		}
		return conn;
	}
	
}

