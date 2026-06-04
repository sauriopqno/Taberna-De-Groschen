package consultas;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

public class ProductoSQL {

    private static final String IP_SERVIDOR = "127.0.0.1"; // Cambiar por apolocloud.lci.ulsa.mx en producción
    private static final int PUERTO = 40008;

    @SuppressWarnings("unchecked")
    public List<Producto> listarProductos() {
        PeticionTaberna peticion = new PeticionTaberna("listarProductos");
        List<Producto> lista = new ArrayList<>();

        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Enviar la petición al servidor
            out.writeObject(peticion);
            out.flush();

            // Recibir la lista de productos serializada desde el servidor
            lista = (List<Producto>) in.readObject();

        } catch (Exception e) {
            System.out.println("Error de red al listar productos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }
}