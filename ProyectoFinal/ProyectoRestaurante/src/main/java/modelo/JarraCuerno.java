package modelo;

public class JarraCuerno extends PlatilloDECORATOR {

    public JarraCuerno(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (En Jarra de Cuerno)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 15.0;
    }
}