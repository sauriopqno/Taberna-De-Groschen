package modelo;

public class ExtraNata extends PlatilloDECORATOR {

    public ExtraNata(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (Extra Nata)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 10.0;
    }
}