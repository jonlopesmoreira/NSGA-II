package entidades;


public class Nutriente 
{
	private NomeNutriente t;
	private Double valor;
	
	public Nutriente()
	{
		this.valor = 0.;
	}
	public Nutriente(NomeNutriente tipo, Double valor) 
	{
		this.t = tipo; 
		this.valor = valor;
	}
	
	public NomeNutriente getNome() {
		return t;
	}
	public void setNomeNutriente(NomeNutriente tipo) {
		this.t = tipo;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	@Override
	public String toString() {
		return t + "=" + valor;
	}
	
}
