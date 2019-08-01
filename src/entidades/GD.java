/**
 * 
 */
package entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jonpr
 *
 */
public class GD 
{
	
	public static void main(String[] args) 
	{
		Double[][] paretoAtual = new Double[][] 
		{//valores do Pareto Atual Aqui
			
			{0.69931796,0.99999970},
			{0.76260636,0.99947885},
			{0.75974245,0.99982904},
			{0.71016485,0.99985454},
			{0.70844168,0.99998853},
			{0.70851117,0.99991394},
			{0.70374092,0.99999922},
			{0.70984867,0.99988828},
			{0.70610387,0.99999286},

		};
		List<List<Double>> list = new ArrayList<List<Double>>();
		for (Double[] p : paretoAtual) 
		    list.add(Arrays.asList(p));
		List<Ponto> pAtual = new ArrayList<>();
		for (int i=0; i<list.size(); i++)
		{
			Ponto p = new Ponto();
			p.setX(list.get(i).get(0));
			p.setY(list.get(i).get(1));
			pAtual.add(p);
		}
		GD gd = new GD(pAtual);
		double r = gd.generationalDistance();
		System.out.println(r);
	}
	
	private List<Ponto> paretoAtual = new ArrayList<>(); // conjunto de aptidao das funcoes objetivo
	private List<Ponto> paretoOtimo = new ArrayList<>(); // conjunto de aptidao das funcoes objetivo
	public GD()
	{
		
	}
	public GD(List<Ponto> paretoAtual)
	{
		this.paretoAtual=paretoAtual;
		Ponto p = new Ponto(0.9,1.);
		this.paretoOtimo.add(p);
	}
	public GD(List<Ponto> paretoAtual, List<Ponto> paretoOtimo)
	{
		this.paretoAtual=paretoAtual;
		this.paretoOtimo=paretoOtimo;
	}

	public List<Ponto> getParetoAtual() {
		return paretoAtual;
	}
	public List<Ponto> getParetoOtimo() {
		return paretoOtimo;
	}
	public void setParetoAtual(List<Ponto> paretoAtual) {
		this.paretoAtual = paretoAtual	;
	}
	public void setParetoOtimo(List<Ponto> paretoOtimo) {
		this.paretoOtimo = paretoOtimo;
	}
	public double generationalDistance()
	{
		double r=0;
		for(int i=0; i<getParetoAtual().size(); i++)
			r+= Math.pow(Ponto.distanciaEuclidiana(getParetoAtual().get(i), getParetoOtimo().get(0)),2.);
		r = Math.sqrt(r)/getParetoAtual().size();
		return r;
	}
	public void showParetoAtual()
	{
		for(int i=0; i<getParetoAtual().size(); i++)
		{
			System.out.print("[" +getParetoAtual().get(i).getX()+",");
			System.out.print(getParetoAtual().get(i).getY()+"]\n");
		}
	}
	public void showParetoOtimo()
	{
		for(int i=0; i<getParetoOtimo().size(); i++)
		{
			System.out.print("[" +getParetoOtimo().get(i).getX()+",");
			System.out.print(getParetoOtimo().get(i).getY()+"]\n");
		}
	}
	
	
}
