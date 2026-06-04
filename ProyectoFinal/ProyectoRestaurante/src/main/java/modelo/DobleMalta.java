package modelo;

public class DobleMalta extends PlatilloDECORATOR {

    public DobleMalta(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (Doble Malta)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 20.0;
    }
}