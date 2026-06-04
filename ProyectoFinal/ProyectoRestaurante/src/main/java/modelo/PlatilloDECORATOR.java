package modelo;

public class PlatilloDECORATOR implements Vendible{
	protected Vendible platilloDecorado;

    public PlatilloDECORATOR(Vendible platillo) {
        this.platilloDecorado = platillo;
    }

    @Override
    public int getIdProducto() {
        return platilloDecorado.getIdProducto(); 
    }

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getPrecio() {
		// TODO Auto-generated method stub
		return 0;
	}
}
