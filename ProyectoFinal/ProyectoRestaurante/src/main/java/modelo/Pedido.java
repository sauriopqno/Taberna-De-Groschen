package modelo;

import java.io.Serializable;

import estados.EstadoEnBarra;
import estados.EstadoPedido;

public class Pedido implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idPedido;
    private double total;
    private EstadoPedido estadoActual;

    public Pedido(int idPedido, double total) {
        this.idPedido = idPedido;
        this.total = total;
        this.estadoActual = new EstadoEnBarra(); 
    }

    public void avanzarEstado() {
        estadoActual.avanzar(this);
    }

    public void cancelarPedido() {
        estadoActual.cancelar(this);
    }

    public void setEstadoActual(EstadoPedido estadoActual) {
        this.estadoActual = estadoActual;
    }

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public EstadoPedido getEstadoActual() {
		return estadoActual;
	}

}