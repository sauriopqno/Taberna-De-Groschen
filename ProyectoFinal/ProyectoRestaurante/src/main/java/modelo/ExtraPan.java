package modelo;

public class ExtraPan extends PlatilloDECORATOR {

    public ExtraPan(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (con Pan Extra)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 15.0;
    }
}