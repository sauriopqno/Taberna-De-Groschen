package estados;
import javax.swing.JOptionPane;

import modelo.Pedido;

public class EstadoEnBarra implements EstadoPedido {
    @Override
    public void avanzar(Pedido pedido) {
        JOptionPane.showMessageDialog(null, "El pedido " + pedido.getIdPedido() + " pasa a los hornos.");
        pedido.setEstadoActual(new EstadoEnForja()); 
    }

    @Override
    public void cancelar(Pedido pedido) {
        JOptionPane.showMessageDialog(null, "Pedido cancelado con éxito. Devolviendo las monedas.");
    }

    @Override
    public String getNombreEstado() { return "En Barra"; }
}