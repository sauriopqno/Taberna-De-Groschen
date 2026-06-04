package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexion.Conexion;
import modelo.Usuario;

public class UsuarioSQL {
    
    public Usuario validarAcceso(String user, String pass) {
        String sql = "SELECT * FROM Usuarios WHERE username = ? AND password = ?";
        Connection connect = null;
        PreparedStatement consulta = null;
        Usuario usuario = null;
        try {
            connect = Conexion.conectar();
            consulta = connect.prepareStatement(sql);
            consulta.setString(1, user);
            consulta.setString(2, pass);
            ResultSet result = consulta.executeQuery();
            while(result.next()) {
                
                usuario = new Usuario(
                    result.getInt("id_usuario"),
                    result.getInt("es_primera"), 
                    result.getString("username"), 
                    result.getString("email"), 
                    result.getString("password"), 
                    result.getString("nombre_completo"), 
                    result.getString("telefono")
                );
            }
            result.close();
            consulta.close();
            connect.close();
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (consulta != null) consulta.close();
                if (connect != null) connect.close();
                System.out.println("Conexión cerrada de forma segura.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }
    
    public void registrarUsuario(Usuario usuario){
        String sql = "INSERT INTO Usuarios (username, email, password, nombre_completo, telefono, es_primera) VALUES (?,?,?,?,?,?)";
        Connection connect = null;
        PreparedStatement consulta = null;
        try {
            connect = Conexion.conectar();
            consulta = connect.prepareStatement(sql);

            // Revisa que coincidan con los getters exactos de tu clase Usuario original
            consulta.setString(1, usuario.getUser());
            consulta.setString(2, usuario.getCorreo());
            consulta.setString(3, usuario.getContraseña());
            consulta.setString(4, usuario.getNombre());
            consulta.setString(5, usuario.getTelefono());
            consulta.setInt(6, 1);
            consulta.executeUpdate();
            System.out.println("Usuario registrado con éxito en SQLite.");
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (consulta != null) consulta.close();
                if (connect != null) connect.close();
                System.out.println("Conexión cerrada de forma segura.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void actualizaUsuario(Usuario usuario) {
        String sql = "UPDATE Usuarios SET es_primera = 0 WHERE username = ?";
        Connection connect = null;
        PreparedStatement consulta = null;
        try {
            connect = Conexion.conectar();
            consulta = connect.prepareStatement(sql);
            consulta.setString(1, usuario.getUser());
            consulta.executeUpdate();
            System.out.println("Usuario actualizado con éxito en SQLite.");
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (consulta != null) consulta.close();
                if (connect != null) connect.close();
                System.out.println("Conexión cerrada de forma segura.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}