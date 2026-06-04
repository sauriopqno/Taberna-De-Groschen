package modelo;

public class MielBohemia extends PlatilloDECORATOR {

    public MielBohemia(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (Miel de Bohemia)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 15.0;
    }
}