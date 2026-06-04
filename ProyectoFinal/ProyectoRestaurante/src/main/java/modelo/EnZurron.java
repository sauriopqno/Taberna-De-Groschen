package modelo;

public class EnZurron extends PlatilloDECORATOR {

    public EnZurron(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (En Zurrón de viaje)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 5.0;
    }
}