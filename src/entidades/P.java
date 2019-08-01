package entidades;
import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.Calendar;
import java.util.List;


public class P implements Comparable<P>, Cloneable
{
	private static int num=0;
	protected int id=0;
	protected Double dist;
	protected int N; //tamanho da populacao
	protected int F; //quantidade de funcoes objetivo
	protected int np; // numero de solucoes que dominam p
	protected List<Integer> sp = new ArrayList<>();// conjunto de solucoes que p domina
	protected List<Double> f= new ArrayList<>(); // conjunto de aptidao das funcoes objetivo
	
	public P() 
	{
		P.num = P.num+1;		
		this.F=2;
		this.np=0;
		this.dist=0.;
	}
	public P(int n, int f)
	{
		P.num++;
		this.id = 0;
		this.N = n;
		this.F = f;
		this.np=0;
		this.dist=0.;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDist(Double d)
	{
		this.dist = d;
	}
	public Double getDist()
	{
		return dist; 
	}
	public int getNum()
	{
		return num;
	}

	public void setF(List<Double> f)
	{
		this.f = f;
	}
	public List<Double> getF()
	{
		return this.f;
	}
	public List<Integer> getSp()
	{
		return this.sp;
	}
	public int getNp() 
	{
		return np;
	}
	public void setNp(int np) 
	{
		this.np = np;
	}
	public void addP(int p)
	{
		this.sp.add(p);
	}
	public void addF(Double f)
	{
		this.f.add(f);
	}
	public int getTamF()
	{
		return this.F;
	}
	public int getId()
	{
		return id;
	}
	public void showNp()
	{
		System.out.printf("Numero de soluções que dominam %d: %d\n",id, this.getNp());
	}
	public void showF()
	{
		//System.out.println("Solução: " + id);
	//	System.out.print("Valores das Funções Objetivo: ");	
		
		for(int i=0; i<getF().size(); i++ )
		{
			if(i==0)
			{
				Double x = Math.abs(f.get(i));
				String numero = x.toString();
				System.out.printf("[%.15s ,", numero);
			}
			else
			{
				Double x = Math.abs(f.get(i));
				String numero = x.toString();
				System.out.printf(" -%.15s],", numero);
			}
		}
			
		System.out.print("\n");
	}
	public void showSp()
	{
		System.out.printf("Conjunto de soluções que a solução "+ id+  " domina: {");
		for(int j=0; j<sp.size(); j++)
		{
			if(j!=sp.size()-1)
				System.out.print(sp.get(j)+ ", ");
			else
				System.out.print((sp.get(j)));
		}
		System.out.println("}");

	}
	public void SortF()
	{
		this.f.sort(null);
	}
	
	public void SortP()
	{
		this.sp.sort(null);
	}
	
	@Override
	public String toString() 
	{
		String r= "Solução("+id+ "): "+ "[População=" + N + ", Nº Fcs Obtv=" + F + ", f=" + f+ ",";
		String g = " sp{";
		for(int i=0; i<sp.size(); i++)
		{
			Integer a = sp.get(i);
			String b = a.toString();
			if(i!=sp.size()-1)
				b+= ", ";
			g = g + b;
		}
			r = r + g;
			r = r + "}]";
		return r;

	}
	@Override
	public boolean equals(Object o)
	{
		if (this == o) 
			return true;
	    if (o == null || getClass() != o.getClass()) 
	    	return false;
	    P newP = (P) o;
	    if(this.getF().get(0)!=newP.getF().get(0) || this.getF().get(1)!=newP.getF().get(1))
	    	return false;
	    return true;
	}
	
	@Override
	public int compareTo(P o) 
	{
		if (this.getDist() == o.getDist())
			return 0;
		else if(this.getDist().equals(o.getDist()))
			return 0;
		else if (this.getDist()< o.getDist())
			return 1;
		else if (this.getDist()> o.getDist())
			return -1;
		else 
			return 0;


	}
	@Override 
	public P clone() 
	{ 
		P novo = new P(); 
		novo.setDist(getDist());
		novo.setId(getId());
		novo.setNp(getNp()); 
		for(int i=0; i<getF().size(); i++)
		{
			novo.addF(getF().get(i));
		}
		for(int i=0; i<sp.size(); i++)
		{
			novo.addP(getSp().get(i));
		}
		return novo;
	}

}
