package modelo;

public class PlatilloBase implements Vendible{
	private int idProducto;
    private String nombre;
    private double precio;

    public PlatilloBase(int idProducto, String nombre, double precio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public int getIdProducto() { return idProducto; }

    @Override
    public String getNombre() { return nombre; }

    @Override
    public double getPrecio() { return precio; }
}
