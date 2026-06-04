package estados;
import javax.swing.JOptionPane;

import modelo.Pedido;

public class EstadoEntregado implements EstadoPedido {
    @Override
    public void avanzar(Pedido pedido) {
        JOptionPane.showMessageDialog(null, "Este pedido ya fue entregado y finalizado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void cancelar(Pedido pedido) {
        JOptionPane.showMessageDialog(null, "No puedes cancelar un estofado que ya te comiste.", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public String getNombreEstado() { return "Entregado"; }
}