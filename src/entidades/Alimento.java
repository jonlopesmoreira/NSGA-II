package entidades;

import java.util.ArrayList;
import java.util.List;

public class Alimento 
{
	public static int num=0;
	protected int id=-1;
	private String nome;
	private TipoAlimento tipo;
	private List<Nutriente> nutrientes = new ArrayList<Nutriente>();
	private static final double calorias=2000.;
	private static final double proteinas=75.;
	private static final double carboidrato=300.;
	private static final double fibra=25.;
	private static final double calcio=1000.;
	private static final double magnesio=260.;
	private static final double manganes=2.3;
	private static final double fosforo=700.;
	private static final double ferro=14.;
	private static final double sodio=2400;
	private static final double zinco=7;

	
	public Alimento()
	{
		Alimento.num++;
	}
	public Alimento(String nome, TipoAlimento t) {
		this.nome = nome;
		this.tipo = t;
	}
	
	@Override
	public String toString() {
		return "Alimento(" +id+"): [[Nome=" + nome + "], [tipo=" + tipo + "], Nutrientes=" + nutrientes + "]\n";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoAlimento getTipo() {
		return tipo;
	}
	public void setTipo(TipoAlimento t) {
		this.tipo = t;
	}
	public void addNutriente(Nutriente n )
	{
		nutrientes.add(n);
	}
	public void setNutrientes(Alimento a)
	{
		this.nutrientes.addAll(a.getNutrientes());
	}
	public int getNutrienteSize()
	{
		return this.nutrientes.size();
	}
	public List<Nutriente> getNutrientes()
	{
		return this.nutrientes;
	}
	public Alimento somaNutrientes(Alimento a, double quantidade)
	{
		Double valor=0.;
		Nutriente n = new Nutriente();
		Alimento b = new Alimento ();
		if(this.getNutrienteSize()==0) //Acumula o alimento nele mesmo na primeira vez
		{
			for(int i=0; i<a.getNutrienteSize(); i++)
			{
				valor = a.getNutrientes().get(i).getValor()*quantidade;
				n.setNomeNutriente(a.getNutrientes().get(i).getNome());
				n.setValor(valor);
				b.addNutriente(n);
				n = new Nutriente();
			}
			this.setNome("Dieta");
			this.setNutrientes(b);
			this.setTipo(TipoAlimento.DIETA);
		}
		else //acumula o alimento com outro alimento
		{
			for(int i=0; i<a.getNutrienteSize(); i++)
			{
				valor = (a.getNutrientes().get(i).getValor()*quantidade)+this.getNutrientes().get(i).getValor();
				n.setNomeNutriente(a.getNutrientes().get(i).getNome());
				n.setValor(valor);
				b.addNutriente(n);
				n = new Nutriente();
			}
			this.nutrientes.clear(); // limpa os alimentos antigos e deixa os acumulados
			this.setNutrientes(b);
		}
		return this;
	}
	public static double getCalorias() {
		return calorias;
	}
	public static double getProteinas() {
		return proteinas;
	}
	public static double getCarboidrato() {
		return carboidrato;
	}
	public static double getFibra() {
		return fibra;
	}
	public static double getCalcio() {
		return calcio;
	}
	public static double getMagnesio() {
		return magnesio;
	}
	public static double getManganes() {
		return manganes;
	}
	public static double getFosforo() {
		return fosforo;
	}
	public static double getFerro() {
		return ferro;
	}
	public static double getSodio() {
		return sodio;
	}
	public static double getZinco() {
		return zinco;
	}
	
}
