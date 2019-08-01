package aplicacao;
import entidades.PDRCS;
public class Main 
{
	public static void main(String[] args) 
	{	
		PDRCS pdrcs = new PDRCS(200, 2000, 80, 10, 100);
		pdrcs.exec();
		/*pdrcs.setGeracao(0);
		pdrcs.lerTabela(true);
		for(int i=0; i<pdrcs.getTabela().getSize(); i++)
		{
			pdrcs.getTabela().getAlimento(i).setId(i+2);
			System.out.println(pdrcs.getTabela().getAlimento(i));
		}
		//pdrcs.exec();
		*/
	}
}		
		/*int pop = Integer.parseInt(args[0]);
		int numGeracoes = Integer.parseInt(args[1]);
		int taxaCrossover = Integer.parseInt(args[2]);
		int taxaMutacao = Integer.parseInt(args[3]);
		int taxaPenalidade = Integer.parseInt(args[4]);
		PDRCS pdrcs = new PDRCS(pop, numGeracoes, taxaCrossover, taxaMutacao , taxaPenalidade);*/
		
		
		/*
		 *  1) Populacao {Testado: [50-1000]}
		 *  2) Número de Gerações {Testado: [10-5000]}
		 *  3) % de Crossover {Testado [40-100]}
		 *  4) % de Mutação {Testado [5-15]}
		 *  5) %Penalidade {Testado = 20]
		 */
		
	//}
//}
/*
	 * Bebidas:		 0-11		11
	 * Frutas:		 12-96		85
	 * Carboidrato1: 97-161		65
	 * Lacteos:		 162-182	21
	 * Carboidrato2: 183-237	55
	 * Graos:		 238-275	38
	 * Vegetais:	 276-354	79
	 * Proteinas:	 355-510	156
	 * Sucos:		 512-521	10
 */