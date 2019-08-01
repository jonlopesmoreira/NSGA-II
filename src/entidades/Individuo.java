package entidades;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Individuo 
{
	private int id=0;
	public static Double minQuantidade=0.5;
	public static Double maxQuantidade=3.0;
	private List<Double> quantidade = new ArrayList<Double>();
	private List<Integer> numAlimento = new ArrayList<Integer>();
	protected static int NBebidas=12;
	protected static int NFrutas=78;
	protected static int NC1=61;
	protected static int NLacteos=21;
	protected static int NC2=46;
	protected static int NGraos=31;
	protected static int NVegetais=79;
	protected static int NProteinas=102;
	protected static int NSucos=11;
	public Individuo()
	{
		
	}
	public Individuo PopInicial()
	{
		for(int i=0; i<17; i++)
		{
			Random r = new Random();
			Random r2 = new Random();
			double quantidade;
			int numAlimento;
			switch(i)
			{
				case 0://bebida [0-11] 12
					quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
					numAlimento = r.nextInt(NBebidas);
					this.addQuantidade(quantidade);
					this.addNumAlimento(numAlimento);
					break;				
				case 1://frutas [12-89] 78
					quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
					numAlimento = r.nextInt(NFrutas)+NBebidas;
					this.addQuantidade(quantidade);
					this.addNumAlimento(numAlimento);
					break;
				case 2: case 11://carboidrato1 [90-150]		61
					quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
					numAlimento = r.nextInt(NC1)+NBebidas+NFrutas;
					this.addQuantidade(quantidade);
					this.addNumAlimento(numAlimento);
					break;
				case 3: case 16://frutas [12-89] 78 ou Lacteo [151-171]	21
					r2 = new Random();
					int tag = r2.nextInt(2);
					if(tag==0) //frutas [12-89] 78
					{
						quantidade = (r2.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
						numAlimento = r2.nextInt(NFrutas)+NBebidas;
						this.addQuantidade(quantidade);
						this.addNumAlimento(numAlimento);
					}
					else //Lacteo [151-171]	21
					{
						quantidade = (r2.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
						numAlimento = r2.nextInt(NLacteos)+NBebidas+NFrutas+NC1;
						this.addQuantidade(quantidade);
						this.addNumAlimento(numAlimento);
					}
					break;
				case 4: case 12://Carboidrato2  [172-217]	46
						quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
						numAlimento = r.nextInt(NC2)+NBebidas+NFrutas+NC1+NLacteos;
						this.addQuantidade(quantidade);
						this.addNumAlimento(numAlimento);	
						break;
				case 5: case 13: // Graos:		[218-248]	31
						quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
						numAlimento = r.nextInt(NGraos)+NBebidas+NFrutas+NC1+NLacteos+NC2;
						this.addQuantidade(quantidade);
						this.addNumAlimento(numAlimento);
						break;
				case 6: case 7: case 14:// Vegetais:	 [249-327]	79
						quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
						numAlimento = r.nextInt(NVegetais)+NBebidas+NFrutas+NC1+NLacteos+NC2+NGraos;
						this.addQuantidade(quantidade);
						this.addNumAlimento(numAlimento);
						break;					
				case 8: case 15:// Proteinas:	 [328-429]	102
						quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
						numAlimento = r.nextInt(NProteinas)+NBebidas+NFrutas+NC1+NLacteos+NC2+NGraos+NVegetais;
						this.addQuantidade(quantidade);
						this.addNumAlimento(numAlimento);
						break;
				case 9: // Sucos:		[430-440]	11
						quantidade = (r.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
						numAlimento = r.nextInt(NSucos)+NProteinas+NBebidas+NFrutas+NC1+NLacteos+NC2+NGraos+NVegetais;
						this.addQuantidade(quantidade);
						this.addNumAlimento(numAlimento);
						break;
				case 10://bebida ou suco
						r2 = new Random();
						tag = r2.nextInt(2);
						if(tag==0)//bebida [0-11]
						{
							quantidade = (r2.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
							numAlimento = r2.nextInt(NBebidas);
							this.addQuantidade(quantidade);
							this.addNumAlimento(numAlimento);
						}
						else// Sucos:		[430-440]	11
						{
							quantidade = (r2.nextDouble()*(getMaxQuantidade()-getMinQuantidade()))+getMinQuantidade();
							numAlimento = r.nextInt(NSucos)+NProteinas+NBebidas+NFrutas+NC1+NLacteos+NC2+NGraos+NVegetais;
							this.addQuantidade(quantidade);
							this.addNumAlimento(numAlimento);
						}
					break;
			}
		}
		return this;
	}
	//Individuo(18): [Vetor Quantidade=[1.0436418263179825, 2.5436374671635424, 0.5934730746924881, 2.7400230807946615, 0.5096118110666923, 0.5280874763554375, 1.1837908408042899, 2.9974231096184045, 1.5014849781184079, 1.9193486069665777, 2.7295320824890994, 0.87560867263123, 1.4483465713230588, 0.7238828790507177, 0.8062254600401237, 0.5752573467599962, 2.970033027687123]
		//      [NumeroAlimento=[8, 61, 142, 61, 214, 258, 331, 344, 506, 517, 517, 123, 231, 242, 346, 468, 61]]
    public void showIndividuo(Tabela tabela)
    {
		for(int i=0; i<getNumAlimento().size(); i++)
		{
			
			int numAlimento = this.getNumeroAlimento(i);
			double quantidadeAlimento = this.getQuantidadeAlimento(i);
			Alimento a = tabela.getAlimento(numAlimento);
			if(i==0)
			{
				System.out.println("=======================================================================");
				System.out.println("Café da manhã:");
				System.out.println("=======================================================================");
			}
			else if(i==3)
			{
				System.out.println("=======================================================================");
				System.out.println("Lanche Matinal");
				System.out.println("=======================================================================");
			}
			else if(i==4)
			{
				System.out.println("=======================================================================");
				System.out.println("Almoço");
				System.out.println("=======================================================================");
			}
			else if(i==10)
			{
				System.out.println("=======================================================================");
				System.out.println("Lanche da tarde");
				System.out.println("=======================================================================");
			}
			else if(i==12)
			{
				System.out.println("=======================================================================");
				System.out.println("Janta");
				System.out.println("=======================================================================");
			}
			else if(i==16)
			{
				System.out.println("=======================================================================");
				System.out.println("Ceia");
				System.out.println("=======================================================================");
			}
			
			System.out.printf("\nAlimento (%d): Nome alimento: %s ,", numAlimento, a.getNome());
			System.out.printf("Quantidade alimento: %.0f g\n", quantidadeAlimento*100);
		}
    }
	
	

			
	@Override
	public String toString() {
		return "Individuo("+id+ "): [Vetor Quantidade="+ quantidade + "\n\t      [NumeroAlimento=" + numAlimento + "]\n";
	}
	public Double getMinQuantidade() {
		return minQuantidade;
	}
	public void setMinQuantidade(Double minQuantidad) {
		Individuo.minQuantidade = minQuantidad;
	}
	public Double getMaxQuantidade() {
		return maxQuantidade;
	}
	public void setMaxQuantidade(Double maxQuantidade) {
		Individuo.maxQuantidade = maxQuantidade;
	}
	public List<Double> getQuantidade() {
		return quantidade;
	}
	public List<Integer> getNumAlimento() {
		return this.numAlimento;
	}
	public void setQuantidade(List<Double> quantidade) {
		this.quantidade = quantidade;
	}
	public void addQuantidade(Double quantidade)
	{
		this.quantidade.add(quantidade);
	}
	public void addNumAlimento(Integer numAlimento)
	{
		this.numAlimento.add(numAlimento);
	}
	public void setNumAlimento(List<Integer> numAlimento) {
		this.numAlimento = numAlimento;
	}
	public void setNumAlimento(int i, int numAlimento ) {
		this.numAlimento.set(i, numAlimento);
	}
	public int getNumeroAlimento(int i)
	{
		return this.numAlimento.get(i);
	}
	public void setQuantidadeAlimento(int i, double quantAlimento)
	{
		this.quantidade.set(i, quantAlimento);
	}
	public Double getQuantidadeAlimento(int i)
	{
		return this.quantidade.get(i);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

/*
 * 	private int NBebidas= 	[0-11]
	private int NFrutas=  	[12-89]
	private int NC1=     	[90-150]
	private int NLacteos=   [151-171]
	private int NC2=46;		[172-217]
	private int NGraos=     [218-248]
	private int NVegetais=  [249-327] 
	private int NProteinas= [328-429]
	private int NSucos= 	[430-440]
 */