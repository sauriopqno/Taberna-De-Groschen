package modelo;

public class EspeciasDeBohemia extends PlatilloDECORATOR {

    public EspeciasDeBohemia(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (con Especias de Bohemia)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 25.0; 
    }
}