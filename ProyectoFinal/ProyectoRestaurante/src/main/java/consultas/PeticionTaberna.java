package consultas;

import java.io.Serializable;
import modelo.Usuario;

public class PeticionTaberna implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String accion; 
    
   
    private String user;
    private String pass;
    private Usuario usuario;

    private double totalPagado;
    private Object[][] datosCarrito;
    private int idPedido;
    private String nuevoEstado;

    // validar
    public PeticionTaberna(String user, String pass) {
        this.accion = "validarAcceso";
        this.user = user;
        this.pass = pass;
    }

    // registrarUsuario o actualizaUsuario o registrarTicket
    public PeticionTaberna(String accion, Usuario usuario) {
        this.accion = accion;
        this.usuario = usuario;
    }
    public PeticionTaberna() {
        this.accion = "listar";
    }
    
    //Pedidos
    public PeticionTaberna(Usuario usuario, double totalPagado, Object[][] datosCarrito) {
        this.accion = "registrar";
        this.usuario = usuario;
        this.totalPagado = totalPagado;
        this.datosCarrito = datosCarrito;
    }
    public PeticionTaberna(String accion) { this.accion = accion; }
    public PeticionTaberna(int idPedido, String nuevoEstado) {
        this.accion = "actualizar";
        this.idPedido = idPedido;
        this.nuevoEstado = nuevoEstado;
    }
    


    public String getAccion() { return accion; }
    public String getUser() { return user; }
    public String getPass() { return pass; }
    public Usuario getUsuario() { return usuario; }
    public double getTotalPagado() { return totalPagado; }
    public Object[][] getDatosCarrito() { return datosCarrito; }
    public int getIdPedido() { return idPedido; }
    public String getNuevoEstado() { return nuevoEstado; }
}