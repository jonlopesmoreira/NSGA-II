package entidades;

public enum NomeNutriente
{
	CALORIAS(1), PROTEINA(2), CARBOIDRATO(3), 
	FIBRA(4), CALCIO(5), MAGNESIO(6),
	MANGANES(7), FOSFORO(8), FERRO(9),
	SODIO(10), ZINCO(11);
	
	private int valor;
	
	NomeNutriente( int valor)
	{
		this.valor = valor;
	}
	public int getValor()
	{
		return this.valor;
	}
}