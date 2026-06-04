package estados;

import javax.swing.JOptionPane;

import modelo.Pedido;

public class EstadoEnForja implements EstadoPedido {
    @Override
    public void avanzar(Pedido pedido) {
        JOptionPane.showMessageDialog(null, "¡Estofado listo! El pedido " + pedido.getIdPedido() + " ha sido entregado al viajero.");
        pedido.setEstadoActual(new EstadoEntregado());
    }

    @Override
    public void cancelar(Pedido pedido) {
        JOptionPane.showMessageDialog(null, "¡Imposible cancelar! El cocinero ya está preparando los ingredientes.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String getNombreEstado() { return "En Forja"; }
}