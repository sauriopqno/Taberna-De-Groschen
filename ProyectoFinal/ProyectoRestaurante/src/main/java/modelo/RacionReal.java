package modelo;

public class RacionReal extends PlatilloDECORATOR {

    public RacionReal(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (Ración del Rey)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 40.0;
    }
}