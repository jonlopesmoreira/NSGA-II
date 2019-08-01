package entidades;

public class Ponto {
	private Double x;
	private Double y;
	
	public Ponto()
	{
		
	}
	
	public Ponto(Double x, Double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Double getX() {
		return x;
	}
	public void setX(Double x) {
		this.x = x;
	}
	public Double getY() {
		return y;
	}
	public void setY(Double y) {
		this.y = y;
	}
	public static Double distanciaEuclidiana(Ponto p1, Ponto p2)
	{
		double distancia=0;
		distancia = Math.pow((p2.getX() - p1.getX()), 2.) +  Math.pow((p2.getY() - p1.getY()), 2.); 
		distancia = Math.sqrt(distancia);
		return distancia;
	}
}
