package servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import consultas.PedidoSQL;
import consultas.UsuarioSQL;
import consultas.ProductoSQL;
import consultas.PeticionTaberna;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import modelo.Producto;

public class ServidorTaberna {
    private static final int PUERTO = 40008;

    public static void main(String[] args) {
        PedidoSQL controladorPedidos = new PedidoSQL();
        UsuarioSQL controladorUsuarios = new UsuarioSQL();
        ProductoSQL controladorProductos = new ProductoSQL();

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor de la Taberna iniciado en el puerto " + PUERTO);

            while (true) {
                Socket cliente = serverSocket.accept();

                try (ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream())) {

                    PeticionTaberna peticion = (PeticionTaberna) in.readObject();

                    switch (peticion.getAccion()) {
                        
                        // productos
                        case "listarProductos":
                            List<Producto> listaProductos = controladorProductos.listarProductos();
                            out.writeObject(listaProductos); // se envian
                            break;

                        // usuarios
                        case "validarAcceso":
                            Usuario usuarioLogueado = controladorUsuarios.validarAcceso(peticion.getUser(), peticion.getPass());
                            out.writeObject(usuarioLogueado);
                            break;

                        case "registrarUsuario":
                            controladorUsuarios.registrarUsuario(peticion.getUsuario());
                            out.writeUTF("¡Usuario registrado exitosamente en la Taberna!");
                            break;

                        case "actualizaUsuario":
                            controladorUsuarios.actualizaUsuario(peticion.getUsuario());
                            out.writeUTF("¡Estado de primera sesión actualizado!");
                            break;

                        // pedidos
                        case "registrar":
                            controladorPedidos.registrarTicket(
                                peticion.getUsuario(), 
                                peticion.getTotalPagado(), 
                                peticion.getDatosCarrito()
                            );
                            out.writeUTF("¡Ticket guardado exitosamente en los archivos de la Taberna!");
                            break;

                        case "listar":
                            DefaultTableModel modelo = controladorPedidos.listarPedidosActivos();
                            out.writeObject(modelo);
                            break;

                        case "actualizar":
                            controladorPedidos.actualizarEstadoBD(peticion.getIdPedido(), peticion.getNuevoEstado());
                            out.writeUTF("Estado del pedido actualizado.");
                            break;
                    }
                    out.flush();

                } catch (Exception e) {
                    System.out.println("Error al atender petición: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error crítico en el servidor: " + e.getMessage());
        }
    }
}