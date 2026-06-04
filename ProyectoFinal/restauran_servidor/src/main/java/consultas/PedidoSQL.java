package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import conexion.Conexion;
import modelo.Usuario;

public class PedidoSQL {

    public void registrarTicket(Usuario usuario, double totalPagado, Object[][] datosCarrito) {
        Connection connect = null;
        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;
        ResultSet rs = null;
        String sqlPedido = "INSERT INTO Pedidos (id_usuario, fecha_hora, total, estado_pedido) VALUES (?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO Detalles_Pedido (id_pedido, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";

        try {
            connect = Conexion.conectar();
            connect.setAutoCommit(false); 
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaHora = ahora.format(formato);
            psPedido = connect.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, usuario.getId()); 
            psPedido.setString(2, fechaHora);
            psPedido.setDouble(3, totalPagado);
            psPedido.setString(4, "En Barra");
            psPedido.executeUpdate();
            rs = psPedido.getGeneratedKeys();
            int idPedidoGenerado = 0;
            if (rs.next()) {
                idPedidoGenerado = rs.getInt(1); 
            }

            psDetalle = connect.prepareStatement(sqlDetalle);
            
            // Recorremos la matriz recibida por red
            for (int i = 0; i < datosCarrito.length; i++) {
                int idProducto = Integer.parseInt(datosCarrito[i][0].toString());
                double precioPlatillo = Double.parseDouble(datosCarrito[i][2].toString());
                psDetalle.setInt(1, idPedidoGenerado); 
                psDetalle.setInt(2, idProducto);
                psDetalle.setInt(3, 1);
                psDetalle.setDouble(4, precioPlatillo);
                psDetalle.executeUpdate(); 
            }

            connect.commit();
            System.out.println("¡Ticket guardado exitosamente en los archivos de la Taberna!");

        } catch (SQLException e) {
            System.out.println("Error al guardar el ticket. Revirtiendo cambios...");
            try {
                if (connect != null) connect.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (psPedido != null) psPedido.close();
                if (psDetalle != null) psDetalle.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public DefaultTableModel listarPedidosActivos() {
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("Ticket ID");
        modelo.addColumn("ID Viajero");
        modelo.addColumn("Fecha y Hora");
        modelo.addColumn("Total ($)");
        modelo.addColumn("Estado");

        Connection connect = null;
        PreparedStatement consulta = null;
        ResultSet rs = null;
        String sql = "SELECT id_pedido, id_usuario, fecha_hora, total, estado_pedido "
                   + "FROM Pedidos WHERE estado_pedido != 'Entregado' "
                   + "ORDER BY fecha_hora ASC";

        try {
            connect = Conexion.conectar();
            consulta = connect.prepareStatement(sql);
            rs = consulta.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("id_pedido");
                fila[1] = rs.getInt("id_usuario");
                fila[2] = rs.getString("fecha_hora");
                fila[3] = rs.getDouble("total");
                fila[4] = rs.getString("estado_pedido");
                
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los pedidos de la cocina: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (consulta != null) consulta.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return modelo;
    }

    public void actualizarEstadoBD(int idPedido, String nuevoEstado) {
        String sql = "UPDATE Pedidos SET estado_pedido = ? WHERE id_pedido = ?";
        Connection connect = null;
        PreparedStatement ps = null;
        
        try {
            connect = Conexion.conectar();
            ps = connect.prepareStatement(sql);
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el estado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
