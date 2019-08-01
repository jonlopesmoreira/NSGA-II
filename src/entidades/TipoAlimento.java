package entidades;

public enum TipoAlimento 
{

	BEBIDAS(1), FRUTAS(2), CARBOIDRATO1(3),
	LACTEOS(4), CARBOIDRATO2(5), GRAOS(6),
	VEGETAIS(7), PROTEINAS(8), SUCOS(9),
	DIETA(10);
	private int valor;

	TipoAlimento( int valor)
	{
		this.valor = valor;
	}
	public int getValor()
	{
		return this.valor;
	}
}
