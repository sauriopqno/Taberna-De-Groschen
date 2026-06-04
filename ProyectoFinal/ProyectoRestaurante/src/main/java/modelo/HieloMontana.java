package modelo;

public class HieloMontana extends PlatilloDECORATOR {

    public HieloMontana(Vendible platillo) {
        super(platillo);
    }

    @Override
    public String getNombre() {
        return platilloDecorado.getNombre() + " (Hielo de Montaña)";
    }

    @Override
    public double getPrecio() {
        return platilloDecorado.getPrecio() + 5.0;
    }
}