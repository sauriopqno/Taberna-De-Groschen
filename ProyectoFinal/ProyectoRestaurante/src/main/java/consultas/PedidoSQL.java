package consultas;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;

public class PedidoSQL {

    private static final String IP_SERVIDOR = "127.0.0.1"; // Cambiar por apolocloud
    private static final int PUERTO = 40008;

    public void registrarTicket(Usuario usuario, double totalPagado, DefaultTableModel carrito) {
        // pasamos el DefaultTableModel a una matriz serializable
        int filas = carrito.getRowCount();
        int columnas = carrito.getColumnCount();
        Object[][] datos = new Object[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datos[i][j] = carrito.getValueAt(i, j);
            }
        }

       
        PeticionTaberna peticion = new PeticionTaberna(usuario, totalPagado, datos);

        // Enviamos al servidor con sockets
        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(peticion);
            out.flush();

            
            String respuesta = in.readUTF();
            System.out.println("Servidor: " + respuesta);

        } catch (Exception e) {
            System.out.println("Error de red al registrar ticket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public DefaultTableModel listarPedidosActivos() {
        PeticionTaberna peticion = new PeticionTaberna();
        
        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(peticion);
            out.flush();

            
            DefaultTableModel modeloRemoto = (DefaultTableModel) in.readObject();

            // cambiamos el formato y lo hacemos no editable
            DefaultTableModel modeloFinal = new DefaultTableModel() {
                private static final long serialVersionUID = 1L;
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            
            for (int i = 0; i < modeloRemoto.getColumnCount(); i++) {
                modeloFinal.addColumn(modeloRemoto.getColumnName(i));
            }
            
            for (int i = 0; i < modeloRemoto.getRowCount(); i++) {
                Object[] fila = new Object[modeloRemoto.getColumnCount()];
                for (int j = 0; j < modeloRemoto.getColumnCount(); j++) {
                    fila[j] = modeloRemoto.getValueAt(i, j);
                }
                modeloFinal.addRow(fila);
            }

            return modeloFinal;

        } catch (Exception e) {
            System.out.println("Error de red al listar pedidos: " + e.getMessage());
            return new DefaultTableModel();
        }
    }

    public void actualizarEstadoBD(int idPedido, String nuevoEstado) {
        PeticionTaberna peticion = new PeticionTaberna(idPedido, nuevoEstado);

        try (Socket socket = new Socket(IP_SERVIDOR, PUERTO);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(peticion);
            out.flush();

            String respuesta = in.readUTF();
            System.out.println("Servidor confirma: " + respuesta);

        } catch (Exception e) {
            System.out.println("Error de red al actualizar estado: " + e.getMessage());
        }
    }
}