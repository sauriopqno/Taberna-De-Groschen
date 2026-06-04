package estados;

import modelo.Pedido;

public interface EstadoPedido {
    void avanzar(Pedido pedido);
    void cancelar(Pedido pedido);
    String getNombreEstado();
}