package consultas;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import modelo.Usuario;

public class UsuarioSQL {
    
    private static final String IP_SERVIDOR = "127.0.0.1"; // Cambiar por apolocloud
    private static final int PUERTO = 40008;

    public Usuario validarAcceso(String user, String pass) {
        PeticionTaberna peticion = new PeticionTaberna(user, pass);
        Usuario usuarioValidado = null;

        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            
            out.writeObject(peticion);
            out.flush();

            
            usuarioValidado = (Usuario) in.readObject();

        } catch (Exception e) {
            System.out.println("Error de red al validar acceso: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarioValidado;
    }
    
    public void registrarUsuario(Usuario usuario) {
        PeticionTaberna peticion = new PeticionTaberna("registrarUsuario", usuario);

        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(peticion);
            out.flush();
            
            // confirmacion (disque buena practica)
            String respuesta = in.readUTF();
            System.out.println("Servidor: " + respuesta);

        } catch (Exception e) {
            System.out.println("Error de red al registrar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void actualizaUsuario(Usuario usuario) {
        PeticionTaberna peticion = new PeticionTaberna("actualizaUsuario", usuario);

        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(peticion);
            out.flush();
            
            String respuesta = in.readUTF();
            System.out.println("Servidor: " + respuesta);

        } catch (Exception e) {
            System.out.println("Error de red al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}