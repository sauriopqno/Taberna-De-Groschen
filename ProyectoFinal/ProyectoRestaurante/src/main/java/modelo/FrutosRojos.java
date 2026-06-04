package modelo;

public class FrutosRojos extends PlatilloDECORATOR {

    public FrutosRojos(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (Con Frutos Rojos)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 10.0;
    }
}