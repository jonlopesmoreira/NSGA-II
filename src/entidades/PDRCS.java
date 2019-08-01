package entidades;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
public class PDRCS
{
	private int N;
	private int F;	
	private int geracao;
	private int numGeracoes;
	private int crossCount=0;
	private double taxaCrossover;
	private double taxaMutacao;
	private double taxaPenalidade;
	private boolean solucoesNormalizadas = false;
	private List<List<P>> paretos = new ArrayList<List<P>>();
	private ArrayList<P> p = new ArrayList<P>();
	private List<Individuo> individuos = new ArrayList<Individuo>();
	private List<Individuo> individuos2 = new ArrayList<Individuo>();
	private Tabela tabela = new Tabela();
	
	private List<Alimento> dieta = new ArrayList<Alimento>();
	private List<Alimento> dieta2 = new ArrayList<Alimento>();
	public PDRCS()
	{
		this.numGeracoes=1;
		this.N = 10;
		this.F = 2;
	}
	public PDRCS(int n, int numGeracoes, double taxaCrossover, double taxaMutacao, double taxaPenalidade) 
	{
		this.N = n;
		this.F = 2;
		this.numGeracoes=numGeracoes;
		this.taxaCrossover=taxaCrossover;
		this.taxaMutacao=taxaMutacao;
		this.taxaPenalidade=taxaPenalidade;
	}

	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
	public int getF() {
		return this.F;
	}
	public void setF(int f) {
		this.F = f;
	}
	public int getGeracao() {
		return this.geracao;
	}
	public void setGeracao(int geracao) {
		this.geracao = geracao;
	}
	public void setNumGeracoes(int numGeracoes) {
		this.numGeracoes = numGeracoes;
	}
	public int getNumGeracoes() {
		return this.numGeracoes;
	}
	public double getPenalidade() {
		return this.taxaPenalidade;
	}
	public void setPenalidade(double penalidade) {
		this.taxaPenalidade = penalidade;
	}
	public double getTaxaCrossover() {
		return taxaCrossover;
	}
	public void setTaxaCrossover(double taxaCrossover) {
		this.taxaCrossover = taxaCrossover;
	}
	public double getTaxaMutacao() {
		return taxaMutacao;
	}
	public void setTaxaMutacao(double taxaMutacao) {
		this.taxaMutacao = taxaMutacao;
	}
	public boolean isSolucoesNormalizadas() {
		return this.solucoesNormalizadas;
	}
	public void setSolucoesNormalizadas(boolean tag) {
		this.solucoesNormalizadas = tag;
	}
	
	public Tabela getTabela() {
		return tabela;
	}
	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
	}

	public void lerTabela(boolean showInfo)
	{
		if(getGeracao()==0 && showInfo==true)
		{
				System.out.println("\n================================");
				System.out.println("Inicio leitura tabela");
				System.out.println("================================");
				this.tabela.leitura("TACOmod");
				System.out.println("\n================================");
				System.out.println("Fim leitura tabela");
				System.out.println("================================");
		}
		else if(getGeracao()==0 && showInfo==false)
		{
			this.tabela.leitura("TACOmod");
		}
		else if(getGeracao()>0 && showInfo==true)
		{
			System.out.println("\n===================================================");
			System.out.printf("Tabela não foi lida pois estamos na %dª geração!\n", getGeracao()+1);
			System.out.println("===================================================");
		}
	}
		
		
	public void gerarPopulacao(boolean showInfo)
	{
		if(showInfo==true && getGeracao()==0)
		{
				System.out.println("\n================================");
				System.out.println("Geração da População Inicial");
				System.out.println("================================");
				for(int i=0; i<getN(); i++)
				{
					Individuo ind = new Individuo(); //um individuo tem um vetor de quantidade e um de alimentos, amboms com 17 posicoes
					individuos.add(ind.PopInicial()); // preenche as 17 posicoes de cada individuo e quantidade
					individuos.get(i).setId(i);
				}
				System.out.println("\n===================================");
				System.out.println("Fim da geração da População Inicial");
				System.out.println("===================================");		
		}
		else if(showInfo==false && getGeracao()==0)
		{
			for(int i=0; i<getN(); i++)
			{
				Individuo ind = new Individuo(); //um individuo tem um vetor de quantidade e um de alimentos, amboms com 17 posicoes
				individuos.add(ind.PopInicial()); // preenche as 17 posicoes de cada individuo e quantidade
				individuos.get(i).setId(i);
			}
		}
		else 
		{
			for(int i=p.size()+crossCount; i<getN(); i++)
			{
				Individuo ind = new Individuo(); //um individuo tem um vetor de quantidade e um de alimentos, amboms com 17 posicoes
				individuos.add(ind.PopInicial()); // preenche as 17 posicoes de cada individuo e quantidade
				individuos.get(i).setId(i);
			}
			if(showInfo)
			{
				System.out.println("\n===================================");
				System.out.println("População foi preenchida!");
				System.out.println("===================================");
			}
		}
	}		

	public void lerDietas(boolean showInfo)
	{
		
		if(getGeracao()==0) //estamos na primeira geração
		{
			if(showInfo==true)
			{
				System.out.println("\n================================");
				System.out.println("Inicio Leitura Dietas 1ª geração");
				System.out.println("================================");
			}
			for(int i=0; i<getN(); i++) // getN() representa o numero de dietas
			{
				Alimento a = new Alimento();
				a.setId(i);
				for(int j=0; j<17; j++)
				{
					int indiceAlimento=individuos.get(i).getNumeroAlimento(j); // pega o indice do alimento i
					double quantidade = individuos.get(i).getQuantidadeAlimento(j); // pega a quantidade  do alimento i
					a=a.somaNutrientes(tabela.getAlimento(indiceAlimento), quantidade); //acumula os nutrientes do alimento i em a
				}
				dieta.add(a); // apos adicionar todos os 17 alimentos da dieta, ela é armazenada no array de dietas
			}
		}
		else //nao estamos na primeira geração
		{
			if(showInfo==true)
			{
				System.out.println("\n================================");
				System.out.printf("Inicio Leitura Dietas %dª geração!\n", getGeracao()+1);
				System.out.println("================================");
			}
			for(int i=p.size(); i<individuos.size(); i++) // getN() representa o numero de dietas
			{
				Alimento a = new Alimento();
				a.setId(individuos.get(i).getId());
				for(int j=0; j<17; j++)
				{
					int indiceAlimento=individuos.get(i).getNumeroAlimento(j); // pega o indice do alimento i
					double quantidade = individuos.get(i).getQuantidadeAlimento(j); // pega a quantidade  do alimento i
					a=a.somaNutrientes(tabela.getAlimento(indiceAlimento), quantidade); //acumula os nutrientes do alimento i em a
				}
				dieta.add(a); // apos adicionar todos os 17 alimentos da dieta, ela é armazenada no array de dietas
			}
		}
		if(showInfo==true)
		{
			System.out.println("\n================================");
			System.out.println("Fim Leitura Dietas");
			System.out.println("================================");
		}
	}
	public void aptidaoDieta(boolean showInfo, double penalidade) // calcula a aptidao da dieta
	{
		penalidade=penalidade/100.;
		double f1=0;
		double f2=0;
		double taxa=0;
		double valor;
		double r=0;
		if(getGeracao()==0) 
		{
			if(showInfo==true)
			{	System.out.println("\n================================");
				System.out.println("Inicio Aptidao Dieta 1ª Geração");
				System.out.println("================================");
			}
			for(int i=0; i<getN(); i++)
			{			
				if(showInfo==true)
				{
					System.out.println("\n"+individuos.get(i));
					System.out.println(dieta.get(i));
				}
				for(int j=0; j<dieta.get(i).getNutrienteSize(); j++)
				{
					valor = dieta.get(i).getNutrientes().get(j).getValor();
					switch(dieta.get(i).getNutrientes().get(j).getNome())
					{
								case CALORIAS:
										f1 = valor*-1.;
										break;
								case PROTEINA:
										f2 = valor;
										 break;
										/*r = valor/Alimento.getProteinas();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
										*/
								case CARBOIDRATO:
										r = valor/Alimento.getCarboidrato();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
								case FIBRA:
										r = valor/Alimento.getFibra();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
								case CALCIO:
										r = valor/Alimento.getCalcio();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
								case MAGNESIO:
										r = valor/Alimento.getMagnesio();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
								case MANGANES:
										r = valor/Alimento.getManganes();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
								case FOSFORO:
										r = valor/Alimento.getFosforo();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;

								case FERRO:
										r = valor/Alimento.getFerro();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
								case SODIO:
										//f2 = Math.abs(valor-Alimento.getSodio())*-1.;
										//break;
										r = Alimento.getSodio()/valor;
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
								case ZINCO:
										r = valor/Alimento.getZinco();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
					}	
				}
				if(showInfo==true)
				{
					System.out.println("F1: " + f1);
					System.out.println("F2: " + f2);
				}
				P solucao = new P();
				//f1-=taxa;
				//f2-=taxa;
				f1 = f1 + f1*taxa;
				f2 = f2 - f2*taxa;
				solucao.addF(f1);
				solucao.addF(f2);
				solucao.setId(i);
				p.add(solucao);
				if(showInfo==true)
				{
					System.out.println("F1: " + f1);
					System.out.println("F2: " + f2);
				}
				f1=0;
				f2=0;
				taxa=0;
			}
		}
		else
		{			
			if(showInfo==true)
			{
				System.out.println("\n================================");
				System.out.printf("Inicio Aptidao Dieta %dª geração\n", getGeracao()+1);
				System.out.println("================================");
			}
			for(int i=(getN()/2); i<getN(); i++)
			{
				if(showInfo==true)
				{
					System.out.println("\n"+individuos.get(i));
					System.out.println(dieta.get(i));
				}
				for(int j=0; j<dieta.get(i).getNutrienteSize(); j++)
				{
					valor = dieta.get(i).getNutrientes().get(j).getValor();
					switch(dieta.get(i).getNutrientes().get(j).getNome())
					{
							case CALORIAS:
										f1 = valor*-1.;
										break;
							case PROTEINA:
										f2 = valor;
										break;
										/*
										r = valor/Alimento.getProteinas();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
										*/
							case CARBOIDRATO:
										r = valor/Alimento.getCarboidrato();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
							case FIBRA:
										r = valor/Alimento.getFibra();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
							case CALCIO:
										r = valor/Alimento.getCalcio();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
							case MAGNESIO:
										r = valor/Alimento.getMagnesio();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
							case MANGANES:
										r = valor/Alimento.getManganes();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
							case FOSFORO:
										r = valor/Alimento.getFosforo();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
							case FERRO:
										r = valor/Alimento.getFerro();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
							case SODIO:
										//f2 = Math.abs(valor-Alimento.getSodio())*-1.;
										//	break;
										r = Alimento.getSodio()/valor;
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
										
							case ZINCO:
										r = valor/Alimento.getZinco();
										if(r<0.2)
											taxa+= (penalidade*10);
										else if(r<0.4)
											taxa+=(penalidade*8);
										else if(r<0.6)
											taxa+=(penalidade*6);
										else if(r<0.8)
											taxa+=(penalidade*4);
										else if(r<1)
											taxa+=penalidade*2;
										break;
					}	
				}
				P solucao = new P();
				//	f1-=taxa;
				//f2-=taxa;
				f1 = f1 + f1*taxa;
				f2 = f2 - f2*taxa;
				if(showInfo==true)
				{
					System.out.println("F: " + f1);
					System.out.println("F2: " + f2);
				}
				solucao.addF(f1);
				solucao.addF(f2);
				solucao.setId(i);
				p.add(solucao);
				f1=0;
				f2=0;
				taxa=0;
			}
			
		}
		if(showInfo==true)
		{	System.out.println("\n================================");
			System.out.println("Fim Aptidão Dieta");
			System.out.println("================================");
		}
	}

	public void nonDominatedSortingI(boolean showInfo)
	{
		// Conforme Deb, O(MN²), N o tamanho da populacao, e M o numero de funcoes objetivo
		// no nosso caso, F representa M
		int cont=0;
		int cont2=0;
		if(showInfo)
		{
			System.out.println("\n================================");
			System.out.println("Non-Dominated Sorting Phase I:");
			System.out.println("================================");
		}
		for(int i=0; i<getN(); i++)
		{
			p.get(i).sp.clear();
			p.get(i).setNp(0);
		}
		for(int i=0; i<getN(); i++)
		{
			for (int j=0; j<getN(); j++)
			{
				if(i!=j)
				{
					for(int k=0; k<getF(); k++)
					{
						if(p.get(i).f.get(k) >= p.get(j).f.get(k))
							cont++;
						else
						{
							break;
						}	
						if(p.get(i).f.get(k) > p.get(j).f.get(k))
							cont2++;
					}
					if(cont==getF() && cont2>0)
					{
						p.get(i).addP(j); // add a solucao j para o conjunto sp das solucoes que i domina
						p.get(j).np++;   // numero de solucoes que dominam j aumenta 1 unidade
					}
					cont=0;
					cont2=0;
				}		
			}
		}
		if(showInfo)
		{
			System.out.println("\n=====================================");
			System.out.println("Fim da Non-Dominated Sorting Phase I:");
			System.out.println("=====================================");
		}
	}

	public void nonDominatedSortingII(boolean showInfo)
	{
		ArrayList<P> q = new ArrayList<P>();
		ArrayList<P> q2 = new ArrayList<P>();
		boolean tag = true;
		int k=0;
		paretos = new ArrayList<List<P>>();
		if(showInfo)
		{	System.out.println("\n================================");
			System.out.println("Non-Dominated Sorting Phase II:");
			System.out.println("================================");
		}
		while(tag==true)
		{
			tag=false;
			for(int i=0; i<getN(); i++)
			{
				// Verifica se nao existe nenhuma solucao que domina p(i)
				if (p.get(i).getNp() == 0 || p.get(i).getNp()==-1) 
				{
					tag=true;
					if(p.get(i).getNp()==-1)
					{
						p.get(i).np=0;
						continue;
					}
					if(k==0)
						q.add(p.get(i));
					p.get(i).setNp(-999); //inutilizando valores do front atual otimo para nao gerar loop infinito
					// para cada solucao com np=0, buscar as solucoes dominadas
					for (int j = 0; j < p.get(i).sp.size(); j++) 
					{
						// posicao da solucao j(i), dominada por p(i)
						int qi = p.get(i).sp.get(j);
						p.get(qi).np--; // reduz o np da solucao j(i) dominada em 1
						if(p.get(qi).getNp()==0 && i>qi)
							q2.add(p.get(qi));						
						if(p.get(qi).getNp()==0 && qi>i)
						{
							q2.add(p.get(qi));
							p.get(qi).np= -1;
						}	
					}
				}
			}
			if(k==0)
			{
				paretos.add(q);
				k++;
			}
			if(q2.size()>0)
				paretos.add(q2);
			else
				tag=false;
			q2 = new ArrayList<P>();
		}
		if(showInfo)
		{	System.out.println("\n=====================================");
			System.out.println("Fim da Non-Dominated Sorting Phase II:");
			System.out.println("=====================================");
		}
	}

	public void CrowdingDistance(boolean showInfo)
	{
		ArrayList<P> p2 = new ArrayList<P>();
		ArrayList<P> p3 = new ArrayList<P>();
		List<Double> fs= new ArrayList<>(); 
		List<List<Double>> listFs = new ArrayList<List<Double>>();
		int tam=0;
		int NumFilhos=0;
		boolean casoOtimo=false;
		Double max;
		Double min;
		if(showInfo)
		{
			System.out.println("\n================================");
			System.out.println("Crowding Distance:");
			System.out.println("================================");
		}
		p = new ArrayList<P>();
		for(int i=0; i<paretos.size(); i++)
		{
			tam+=paretos.get(i).size();
			if(tam<=(getN()/2)) // verifica se a soma do pareto atual nao ultrapassará a quantidade de pais que serão selecionados para proxima geração
			{
				for(int j=0; j<paretos.get(i).size(); j++)
				{
					p.add(paretos.get(i).get(j));
				}	
				if(tam==(getN()/2))
				{
					if(showInfo)
					{
						System.out.println("\nCaso ótimo, ultimo pareto totalmente adicionado: ");
						System.out.println(paretos.get(i));
						System.out.println();
						System.out.printf("%d soluções foram selecionadas para a próxima geração (armazenadas em p): \n", p.size());
						System.out.println(p);
					}
					casoOtimo=true; // quando a quantidade de solucoes do pareto for exatamente o numero de filhos necessarios
					break;
				}
					
			}
			else
			{
				for(int j=0; j<paretos.get(i).size(); j++)
				{
					// adiciona as solucoes que nao podem entrar em p para calcular Crowding-Distance
					P aux = new P();
					aux = paretos.get(i).get(j).clone();
					aux.setDist(0.);
					p2.add(aux);
				}
				tam-=paretos.get(i).size();
				break;
			}
		}
		if(casoOtimo==false) 
		{
			for(int i=0; i<getF(); i++)
			{
				NumFilhos = p2.size();
				for(int j=0; j<NumFilhos; j++)
					fs.add(p2.get(j).getF().get(i));
				fs.sort(null);
				listFs.add(fs);
				fs = new ArrayList<Double>();
			}
			for(int k=0; k<getF(); k++)
			{
				for(int i=0; i<NumFilhos; i++)
				{
					for(int j=0; j<NumFilhos; j++)
					{
						if(p2.get(j).getF().get(k).equals(listFs.get(k).get(i)))
						{
							
							P aux = new P();
							aux = p2.get(j).clone();
							aux.setDist(0.);
							p3.add(aux); // em p3 ficam armazenadas as solucoes ordenadas para cada funcao objetivo
							p2.get(j).getF().set(k, -1.);
							break;
						}
					}
				}
			}
			min = Double.POSITIVE_INFINITY;
			max = Double.NEGATIVE_INFINITY;
			// verifica qual o menor e maior valor para cada funcao objetivo
			for(int j=0; j<getF(); j++)
			{
				for(int i=0; i<NumFilhos; i++)
				{
					int pos = NumFilhos*j+i;
					if( p3.get(pos).getF().get(j)<min )
						min = p3.get(pos).getF().get(j);
					if( p3.get(pos).getF().get(j)>max )
						max = p3.get(pos).getF().get(j);
				}
				
				//sabendo os valores de max e min, calcula-se
				for(int i=0; i<NumFilhos; i++)
				{
					int posa = NumFilhos*j;
					if( (i+posa)== (posa) || (i+posa)==(NumFilhos-1+posa))
						p3.get(posa+i).setDist(Double.POSITIVE_INFINITY);
					else
					{
						int pos = NumFilhos*j+i;
						Double sucessor = p3.get(pos+1).getF().get(j);
						Double antecessor = p3.get(pos-1).getF().get(j);
						Double distancia = ((sucessor - antecessor) / (max - min));
						p3.get(pos).setDist(distancia);
					}
				}
				min = Double.POSITIVE_INFINITY;
				max = Double.NEGATIVE_INFINITY;
			}
			for(int i=0; i<p3.size()/2; i++)
			{
				for(int j=p3.size()/2; j<p3.size(); j++)
				{
					if(p3.get(i).getId()==p3.get(j).getId())
					{
						double dist = p3.get(i).getDist();
						dist+= p3.get(j).getDist();
						p3.get(i).setDist(dist);
						break;
					}
				}
			}
			for(int i=p3.size()-1; i>=NumFilhos; i--)
				p3.remove(i);
			Collections.sort(p3);
			if(showInfo)
			{
				System.out.printf("\n%d Soluções que competiram no Crowding Distance: ", p3.size());
				System.out.println(p3);
				System.out.println();
			}
			for(int j=0; j<((getN()/2)-tam); j++)
				p.add(p3.get(j));
			if(showInfo)
			{
				System.out.printf("%d soluções foram selecionadas para a próxima geração (armazenadas em p): \n", p.size());
				System.out.println(p);
			}
		}
		if(showInfo)
		{	System.out.println("\n================================");
			System.out.println("Fim da Crowding Distance:");
			System.out.println("================================");
		}

	}	
	public void normalizarIndividuos(boolean showInfo)
	{
		for(int i=0; i<p.size(); i++)
		{
			int id = p.get(i).getId();
			individuos.get(id).setId(i);
			dieta.get(id).setId(i);
			individuos2.add(individuos.get(id));
			dieta2.add(dieta.get(id));
			p.get(i).setId(i);
			p.get(i).sp.clear();
			p.get(i).np=0;
		}
		individuos.clear();
		dieta.clear();
		individuos.addAll(individuos2);
		dieta.addAll(dieta2);
		dieta2.clear();
		individuos2.clear();
		if(showInfo)
		{
			System.out.println("\n====================================");
			System.out.println("As soluções foram normalizadas!");
			System.out.println("====================================");
		}
	}
	
	public void showSolucoesSelecionadas(boolean showF, boolean showNp, boolean showSp)
	{
		System.out.printf("\n%d soluções sobreviveram na %dª geração!\n", getN()/2,getGeracao()+1);
		if(isSolucoesNormalizadas()==false)
		{
			for(int i=0; i<p.size(); i++)
			{
				int id = p.get(i).getId();
				System.out.println("\n\n"+individuos.get(id));					
				System.out.println(dieta.get(id));
				if(showF)
					p.get(i).showF();
				if(showNp)
					p.get(i).showNp();
				if(showSp)	
					p.get(i).showSp();
			}
		}
		else
		{
			for(int i=0; i<p.size(); i++)
			{
				System.out.println("\n\n"+individuos.get(i));					
				System.out.println(dieta.get(i));
				if(showF)
					p.get(i).showF();
			}
		}
	}
	public void showParetos(boolean showF, boolean showNp, boolean showSp)
	{
		System.out.println("\n================================");
		System.out.println("Mostrando Paretos:");
		System.out.println("================================");
		System.out.printf("\n%d Paretos\n", paretos.size());
		for(int i=0; i<paretos.size(); i++)
		{
			System.out.printf("\nQuantidade de soluções do %dº Pareto = %d \n", i+1, paretos.get(i).size());
			for(int j=0; j<paretos.get(i).size(); j++)
			{
				paretos.get(i).get(j).showF();
				paretos.get(i).get(j).showSp();
			}
		}
		System.out.println("\n================================");
		System.out.println("Fim dos Paretos");
		System.out.println("================================");
	}
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
	    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
	    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	  }
	public void showParetoOtimo(boolean showF, boolean showDieta, boolean showIndividuo)
	{
		ArrayList<P> solucoesOtimas = new ArrayList<P>();
		ArrayList<P> solucoesOtimasDistintas = new ArrayList<P>();
		
		
		/*
		if(showF && getGeracao()!= (getNumGeracoes()-1))
		{
			System.out.println("\n================================");
			System.out.println("Mostrando Paretos Ótimo:");
			System.out.println("================================");
			System.out.printf("\nQuantidade de soluções do 1º Pareto = %d \n", paretos.get(0).size());
			for(int i=0; i<paretos.get(0).size(); i++)
			{
				paretos.get(0).get(i).showF();
			}
		}
		else if(showF==false && getGeracao()!= (getNumGeracoes()-1))
		{
			for(int i=0; i<paretos.get(0).size(); i++)
			{
				paretos.get(0).get(i).showF();
			}
		}
		
		*/
		if(showF && getGeracao()!= (getNumGeracoes()-1))
		{
			for(int i=0; i<paretos.get(0).size(); i++)
				solucoesOtimas.add(paretos.get(0).get(i));
			solucoesOtimasDistintas.addAll(solucoesOtimas.stream().filter(distinctByKey(p-> p.getF().get(0))).collect(Collectors.toList()));
			System.out.printf("Quantidade de soluções do 1º Pareto Otimal = %d \n", solucoesOtimasDistintas.size());
			for(int i=0; i<solucoesOtimasDistintas.size(); i++)
			{
				/*	
				int id = solucoesOtimasDistintas.get(i).getId();
				if(showDieta)
					System.out.println("\n"+dieta.get(id));
				if(showIndividuo)
					System.out.println(individuos.get(id));
				if(showDieta || showIndividuo)
					System.out.printf("Solução %d: ",solucoesOtimasDistintas.get(i).getId());
				*/	
				solucoesOtimasDistintas.get(i).showF();
				/*int id = solucoesOtimasDistintas.get(i).getId();
				String calorias = Double.toString(dieta.get(id).getNutrientes().get(0).getValor());
				String proteinas = Double.toString(dieta.get(id).getNutrientes().get(1).getValor());
				System.out.printf("[%s,%s],\n",calorias,proteinas);
				*/
			}
			/*
			int onvg = solucoesOtimasDistintas.size();
			System.out.printf("\nONVG = %d \n", onvg);
			for(int i=0; i<solucoesOtimasDistintas.size(); i++)
			{
			
				int id = solucoesOtimasDistintas.get(i).getId();
				System.out.println("==================================================================");
				System.out.println("\n"+dieta.get(id));
				solucoesOtimasDistintas.get(i).showF();
				System.out.println("==================================================================");
				System.out.println(individuos.get(id));
				System.out.println("Alimentos selecionados do individuo " + id+":");
				individuos.get(id).showIndividuo(tabela);
				
			}	
			*/

		}
		else
		{			
			for(int i=0; i<paretos.get(0).size(); i++)
				solucoesOtimas.add(paretos.get(0).get(i));
			solucoesOtimasDistintas.addAll(solucoesOtimas.stream().filter(distinctByKey(p-> p.getF().get(0))).collect(Collectors.toList()));
			System.out.printf("Quantidade de soluções do 1º Pareto Otimal = %d \n", solucoesOtimasDistintas.size());
			for(int i=0; i<solucoesOtimasDistintas.size(); i++)
			{
				/*	
				int id = solucoesOtimasDistintas.get(i).getId();
				if(showDieta)
					System.out.println("\n"+dieta.get(id));
				if(showIndividuo)
					System.out.println(individuos.get(id));
				if(showDieta || showIndividuo)
					System.out.printf("Solução %d: ",solucoesOtimasDistintas.get(i).getId());
				*/	
				solucoesOtimasDistintas.get(i).showF();
				/*int id = solucoesOtimasDistintas.get(i).getId();
				String calorias = Double.toString(dieta.get(id).getNutrientes().get(0).getValor());
				String proteinas = Double.toString(dieta.get(id).getNutrientes().get(1).getValor());
				System.out.printf("[%s,%s],\n",calorias,proteinas);
				*/
			}
			int onvg = solucoesOtimasDistintas.size();
			System.out.printf("\nONVG = %d \n", onvg);
			for(int i=0; i<solucoesOtimasDistintas.size(); i++)
			{
			
				int id = solucoesOtimasDistintas.get(i).getId();
				System.out.println("==================================================================");
				System.out.println("\n"+dieta.get(id));
				solucoesOtimasDistintas.get(i).showF();
				System.out.println("==================================================================");
				System.out.println(individuos.get(id));
				System.out.println("Alimentos selecionados do individuo " + id+":");
				individuos.get(id).showIndividuo(tabela);
				
			}	
			

		}
		
		if(showF==true && getGeracao()!= (getNumGeracoes()-1))
		{	System.out.println("\n================================");
			System.out.println("Fim do Pareto Ótimo com " + paretos.get(0).size()+ " soluções!");
			System.out.println("================================");
		}
		else if(showF==true && getGeracao()== (getNumGeracoes()-1))
		{
			System.out.println("\n======================================");
			System.out.println("Fim do Pareto Ótimo Final com " + solucoesOtimasDistintas.size()+ " soluções!");
			System.out.println("======================================");
		}
	}


	public void crossover(double rate,boolean showInfo)
	{
		rate = rate/100.;
		this.crossCount=0;
		if(showInfo)
		{
			System.out.println("\n================================");
			System.out.println("Inicio CrossOver");
			System.out.println("================================");
		}
		Random r = new Random();
		for(int i=0; i<=p.size(); i+=2)
		{
			Double taxa = r.nextDouble();
			if(taxa<=rate)
			{
				if(  i>= (p.size()-1) )
				{
					if(   (p.size()%2) ==1   ) 
					{

						//SP crossover
						int ponto = r.nextInt(9)+4; // [4-12]
						Individuo ind1 = new Individuo();
						for(int j=0; j<ponto; j++)
						{
							ind1.addNumAlimento(individuos.get(i).getNumeroAlimento(j));
							ind1.addQuantidade(individuos.get(i).getQuantidadeAlimento(j));
						}
						for(int j=ponto; j<17; j++)
						{
							ind1.addNumAlimento(individuos.get(0).getNumeroAlimento(j));
							ind1.addQuantidade(individuos.get(0).getQuantidadeAlimento(j));
						}
						ind1.setId((getN()/2)+crossCount++);
						individuos.add(ind1);
						break;
					}
					else
					{
						break;
					}
				}
				else
				{
					Individuo ind1 = new Individuo();
					Individuo ind2 = new Individuo();
					Individuo aux1 = new Individuo();
					Individuo aux2 = new Individuo();
					int ponto = r.nextInt(9)+4; // [4-12]
					for(int j=0; j<ponto; j++)
					{
						ind1.addNumAlimento(individuos.get(i).getNumeroAlimento(j));
						ind1.addQuantidade(individuos.get(i).getQuantidadeAlimento(j));
						ind2.addNumAlimento(individuos.get(i+1).getNumeroAlimento(j));
						ind2.addQuantidade(individuos.get(i+1).getQuantidadeAlimento(j));
						
						aux1.addNumAlimento(individuos.get(i).getNumeroAlimento(j));
						aux1.addQuantidade(individuos.get(i).getQuantidadeAlimento(j));
						aux2.addNumAlimento(individuos.get(i+1).getNumeroAlimento(j));
						aux2.addQuantidade(individuos.get(i+1).getQuantidadeAlimento(j));
	
					}
					for(int j=ponto; j<17; j++)
					{
						ind1.addNumAlimento(individuos.get(i+1).getNumeroAlimento(j));
						ind1.addQuantidade(individuos.get(i+1).getQuantidadeAlimento(j));
						ind2.addNumAlimento(individuos.get(i).getNumeroAlimento(j));
						ind2.addQuantidade(individuos.get(i).getQuantidadeAlimento(j));
						
						aux1.addNumAlimento(individuos.get(i+1).getNumeroAlimento(j));
						aux1.addQuantidade(individuos.get(i+1).getQuantidadeAlimento(j));
						aux2.addNumAlimento(individuos.get(i).getNumeroAlimento(j));
						aux2.addQuantidade(individuos.get(i).getQuantidadeAlimento(j));
					}
					ind1.setId((getN()/2)+crossCount++);
					ind2.setId((getN()/2)+crossCount++);
					double multiPonto = r.nextDouble();
					//
					if(multiPonto>=rate)
					{	
						int p1 = r.nextInt(4); //[0-3] 
						int p2 = 12+r.nextInt(3); //[12-14]
						for(int j=0; j<p1; j++)
						{
							ind1.setNumAlimento(j, aux2.getNumeroAlimento(j));
							ind1.setQuantidadeAlimento(j, aux2.getQuantidadeAlimento(j));
							ind2.setNumAlimento(j, aux1.getNumeroAlimento(j));
							ind2.setQuantidadeAlimento(j, aux1.getQuantidadeAlimento(j));	
						}
						for(int j=p2; j<17; j++)
						{
							ind1.setNumAlimento(j, aux2.getNumeroAlimento(j));
							ind1.setQuantidadeAlimento(j, aux2.getQuantidadeAlimento(j));
							ind2.setNumAlimento(j, aux1.getNumeroAlimento(j));
							ind2.setQuantidadeAlimento(j, aux1.getQuantidadeAlimento(j));	
						}
					}
					individuos.add(ind1);
					individuos.add(ind2);	
				}	
			}
		}
		if(showInfo)
		{
			System.out.println("\n================================");
			System.out.println("Fim CrossOver");
			System.out.println("================================");
		}
	}
	public void mutacao(double rate, boolean showInfo)
	{
		rate = rate/100.;
		if(showInfo)
		{
			System.out.println("\n================================");
			System.out.println("Inicio Mutação");
			System.out.println("================================");
		}
		for(int i=p.size(); i<individuos.size(); i++)
		{
			for(int j=0; j<17; j++)
			{
				Random r = new Random();
				Random r2 = new Random();
				Double taxa = r.nextDouble();
				if(taxa<=rate)
				{
					double quantidade;
					int numAlimento;
					switch(j)
						{
							case 0://bebida [0-11] 12
								quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
								numAlimento = r.nextInt(Individuo.NBebidas);
								individuos.get(i).setNumAlimento(j, numAlimento);
								individuos.get(i).setQuantidadeAlimento(j, quantidade);
								break;				
							case 1://frutas [12-89] 78
								quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
								numAlimento = r.nextInt(Individuo.NFrutas)+Individuo.NBebidas;
								individuos.get(i).setNumAlimento(j, numAlimento);
								individuos.get(i).setQuantidadeAlimento(j, quantidade);
								break;
							case 2: case 11://carboidrato1 [90-150]		61
								quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
								numAlimento = r.nextInt(Individuo.NC1)+Individuo.NBebidas+Individuo.NFrutas;
								individuos.get(i).setNumAlimento(j, numAlimento);
								individuos.get(i).setQuantidadeAlimento(j, quantidade);
								break;
							case 3: case 16://frutas [12-89] 78 ou Lacteo [151-171]	21
								r2 = new Random();
								int tag = r2.nextInt(2);
								if(tag==0) //frutas [12-89]
								{
									quantidade = (r2.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
									numAlimento = r.nextInt(Individuo.NFrutas)+Individuo.NBebidas;
									individuos.get(i).setNumAlimento(j, numAlimento);
									individuos.get(i).setQuantidadeAlimento(j, quantidade);
								}
								else //Lacteo [151-171]	21
								{
									quantidade = (r2.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
									numAlimento = r2.nextInt(Individuo.NLacteos)+Individuo.NBebidas+Individuo.NFrutas+Individuo.NC1;
									individuos.get(i).setNumAlimento(j, numAlimento);
									individuos.get(i).setQuantidadeAlimento(j, quantidade);
								}
								break;
							case 4: case 12://Carboidrato2  [172-217]	46
									quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
									numAlimento = r.nextInt(Individuo.NC2)+Individuo.NBebidas+Individuo.NFrutas+Individuo.NC1+Individuo.NLacteos;
									individuos.get(i).setNumAlimento(j, numAlimento);
									individuos.get(i).setQuantidadeAlimento(j, quantidade);	
									break;
							case 5: case 13: // Graos:		[218-248]	31
									quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
									numAlimento = r.nextInt(Individuo.NGraos)+Individuo.NBebidas+Individuo.NFrutas+Individuo.NC1+Individuo.NLacteos+Individuo.NC2;
									individuos.get(i).setNumAlimento(j, numAlimento);
									individuos.get(i).setQuantidadeAlimento(j, quantidade);
									break;
							case 6: case 7: case 14:// Vegetais:	 [249-327]	79
									quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
									numAlimento = r.nextInt(Individuo.NVegetais)+Individuo.NBebidas+Individuo.NFrutas+Individuo.NC1+Individuo.NLacteos+Individuo.NC2+Individuo.NGraos;
									individuos.get(i).setNumAlimento(j, numAlimento);
									individuos.get(i).setQuantidadeAlimento(j, quantidade);
									break;					
							case 8: case 15:// Proteinas:	 [328-429]	102
									quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
									numAlimento = r.nextInt(Individuo.NProteinas)+Individuo.NBebidas+Individuo.NFrutas+Individuo.NC1+Individuo.NLacteos+Individuo.NC2+Individuo.NGraos+Individuo.NVegetais;
									individuos.get(i).setNumAlimento(j, numAlimento);
									individuos.get(i).setQuantidadeAlimento(j, quantidade);
									break;
							case 9: // Sucos:		[430-440]	11
									quantidade = (r.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
									numAlimento = r.nextInt(Individuo.NSucos)+Individuo.NBebidas+Individuo.NFrutas+Individuo.NC1+Individuo.NLacteos+Individuo.NC2+Individuo.NGraos+Individuo.NVegetais+Individuo.NProteinas;
									individuos.get(i).setNumAlimento(j, numAlimento);
									individuos.get(i).setQuantidadeAlimento(j, quantidade);
									break;
							case 10://bebida ou suco
									r2 = new Random();
									tag = r2.nextInt(2);
									if(tag==0)//bebida [0-11]
									{
										quantidade = (r2.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
										numAlimento = r.nextInt(Individuo.NBebidas);
										individuos.get(i).setNumAlimento(j, numAlimento);
										individuos.get(i).setQuantidadeAlimento(j, quantidade);;
									}
									else// Sucos:		[430-440]	11
									{
										quantidade = (r2.nextDouble()*(Individuo.maxQuantidade-Individuo.minQuantidade)+Individuo.minQuantidade);
										numAlimento = r.nextInt(Individuo.NSucos)+Individuo.NBebidas+Individuo.NFrutas+Individuo.NC1+Individuo.NLacteos+Individuo.NC2+Individuo.NGraos+Individuo.NVegetais+Individuo.NProteinas;
										individuos.get(i).setNumAlimento(j, numAlimento);
										individuos.get(i).setQuantidadeAlimento(j, quantidade);
									}
									break;
							}//fim switch
				}//fim if
			}//fim for j
		}//fim for i
		
	}
	public void showGeracao(boolean showInfo)
	{
		if(showInfo)
		{
			System.out.printf("\n%dª Geração!\n", getGeracao()+1);
		}
	}
	
	public void exec() 
	{
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.out.println("Tamanho da populacao |N|: "+ getN());
		System.out.println("Numero de funcoes objetivo |F|: "+ getF());
		System.out.println("Numero de Gerações: "+ getNumGeracoes());
		System.out.println("Taxa de Crossover: " + getTaxaCrossover()+ "%");;
		System.out.println("Taxa de Mutação: " + getTaxaMutacao()+ "%");
		System.out.println("Taxa de Penalidade: " + getPenalidade()+ "%");
		boolean showInfo=false;
		for(int i=0; i<getNumGeracoes(); i++)
		{
			setGeracao(i);
			if(getGeracao()>0)
			{
				crossover(getTaxaCrossover(),showInfo); 
				mutacao(getTaxaMutacao(),showInfo);

			}
			showGeracao(true);
			lerTabela(showInfo); // Transfere os alimentos da  planilha para a tabela  
			gerarPopulacao(showInfo); // Preenche as 17 posicoes de cada individuo(i) da lista de individuos com um alimento
			lerDietas(showInfo); // Soma os nutrientes de cada individuo(i) e coloca no vetor de alimentos dieta(i)
			aptidaoDieta(showInfo,getPenalidade()); // calcula o fitness de cada dieta(i)
			nonDominatedSortingI(showInfo);
			nonDominatedSortingII(showInfo);
			CrowdingDistance(showInfo);
		//	showParetoOtimo(true,false,false);
			if(getGeracao()==getNumGeracoes()-1)
				continue;
			normalizarIndividuos(showInfo);
			//showSolucoesSelecionadas(true, true, true);
			setSolucoesNormalizadas(true);
			showParetoOtimo(true,true,true);

		}
		showParetoOtimo(true,true,true);

	}
	
}