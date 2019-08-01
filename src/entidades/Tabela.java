package entidades;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Tabela
{
	private List<Alimento> tabela = new ArrayList<Alimento>();
	public Tabela() 
	{
		
	}

	public Alimento leitura (String NomePlanilha)
	{
		Alimento a = new Alimento();
		NomePlanilha = NomePlanilha + ".xlsx";
		int id=0;
		String nome;
		boolean tag=true;
		try
		{
			FileInputStream file = new FileInputStream(new File(NomePlanilha));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);	
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) 
			{
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				tag = true;
				while (cellIterator.hasNext() && tag==true ) 
				{
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) 
					{
						case Cell.CELL_TYPE_FORMULA:
							//System.out.println("Formula");
							break;
						case Cell.CELL_TYPE_BLANK:
							tag=false;
							//System.out.println("Blank");
							break;
						case Cell.CELL_TYPE_ERROR:
							//System.out.println("Error");
							break;
						case Cell.CELL_TYPE_STRING:
							if(cell.getStringCellValue().contentEquals("end"))
							{
								//System.out.printf("Fim da linha %d\n", cont);
								break;
								
							}
							else if(cell.getStringCellValue().contentEquals("*"))
							{
								//System.out.printf("Celula [%d]: 0.0\n", cell.getColumnIndex());
								continue;
							}
							else if(cell.getStringCellValue().contentEquals("Tr") || cell.getStringCellValue().contentEquals("NA"))
							{
								 id = cell.getColumnIndex();
								 Nutriente n = new Nutriente();
								 switch (id) 
								 {
									case 2:
											n.setNomeNutriente(NomeNutriente.CALORIAS);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 3:
											n.setNomeNutriente(NomeNutriente.PROTEINA);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 4:
											n.setNomeNutriente(NomeNutriente.CARBOIDRATO);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 5:
											n.setNomeNutriente(NomeNutriente.FIBRA);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 6:
											n.setNomeNutriente(NomeNutriente.CALCIO);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 7:
											n.setNomeNutriente(NomeNutriente.MAGNESIO);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 8:
											n.setNomeNutriente(NomeNutriente.MANGANES);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 9:
											n.setNomeNutriente(NomeNutriente.FOSFORO);
											n.setValor(0.);
											a.addNutriente(n);
											continue;
									case 10:
											n.setNomeNutriente(NomeNutriente.FERRO);
											n.setValor(0.);
											a.addNutriente(n);
											continue;	
									case 11:
											n.setNomeNutriente(NomeNutriente.SODIO);
											n.setValor(0.);
											a.addNutriente(n);
											continue;	
									case 12:
											n.setNomeNutriente(NomeNutriente.ZINCO);
											n.setValor(0.);
											a.addNutriente(n);
											continue;	
									default:
											break;
							}// nutrientes e valores
								//System.out.printf("Celula [%d]: 0.0\n", cell.getColumnIndex());
								continue;
							}
							else 
							{
								nome = cell.getStringCellValue();
								id = cell.getColumnIndex();
								if(id==0) // tipoAlimento
								{
									if(nome.contains("Bebidas")==true)
									{
										//System.out.println(TipoAlimento.BEBIDAS);
										a.setTipo(TipoAlimento.BEBIDAS);
										continue;
									}
									else if(nome.contains("Frutas")==true)
									{
										//System.out.println(TipoAlimento.FRUTAS);
										a.setTipo(TipoAlimento.FRUTAS);
										continue;

									}
									else if(nome.contains("Carboidrato1")==true)
									{
										//System.out.println(TipoAlimento.CARBOIDRATO1);
										a.setTipo(TipoAlimento.CARBOIDRATO1);
										continue;

									}
									else if(nome.contains("Lacteos")==true)
									{
										//System.out.println(TipoAlimento.LACTEOS);
										a.setTipo(TipoAlimento.LACTEOS);
										continue;

									}
									else if(nome.contains("Carboidrato2")==true)
									{
										//System.out.println(TipoAlimento.CARBOIDRATO2);
										a.setTipo(TipoAlimento.CARBOIDRATO2);
										continue;

									}
									else if(nome.contains("Grãos")==true)
									{
										//System.out.println(TipoAlimento.GRAOS);
										a.setTipo(TipoAlimento.GRAOS);
										continue;
									}
									else if(nome.contains("Vegetais")==true)
									{
										//System.out.println(TipoAlimento.VEGETAIS);
										a.setTipo(TipoAlimento.VEGETAIS);
										continue;

									}
									else if(nome.contains("Proteinas")==true)
									{
										//System.out.println(TipoAlimento.PROTEINAS);
										a.setTipo(TipoAlimento.PROTEINAS);
										continue;

									}
									else if(nome.contains("Sucos")==true)
									{
										//System.out.println(TipoAlimento.SUCOS);
										a.setTipo(TipoAlimento.SUCOS);
										continue;

									}
									break;
								}
								else if(id==1)
								{
									//System.out.println(nome);
									a.setNome(nome); //nome alimento
								}
							}//fim case celula tipo string	
							break;
						case Cell.CELL_TYPE_NUMERIC:
							 id = cell.getColumnIndex();
							 Double valor = cell.getNumericCellValue();
							 if(valor<0)
								 valor = 0.;
							 //System.out.println("Celula ["+(cell.getColumnIndex())+"]: "+cell.getNumericCellValue() + " ");
							 Nutriente n = new Nutriente();
							 switch (id) 
							 {
								case 2:
										n.setNomeNutriente(NomeNutriente.CALORIAS);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 3:
										n.setNomeNutriente(NomeNutriente.PROTEINA);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 4:
										n.setNomeNutriente(NomeNutriente.CARBOIDRATO);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 5:
										n.setNomeNutriente(NomeNutriente.FIBRA);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 6:
										n.setNomeNutriente(NomeNutriente.CALCIO);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 7:
										n.setNomeNutriente(NomeNutriente.MAGNESIO);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 8:
										n.setNomeNutriente(NomeNutriente.MANGANES);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 9:
										n.setNomeNutriente(NomeNutriente.FOSFORO);
										n.setValor(valor);
										a.addNutriente(n);
										break;	
								case 10:
										n.setNomeNutriente(NomeNutriente.FERRO);
										n.setValor(valor);
										a.addNutriente(n);
									break;
								case 11:
										n.setNomeNutriente(NomeNutriente.SODIO);
										n.setValor(valor);
										a.addNutriente(n);
										break;
								case 12:
										n.setNomeNutriente(NomeNutriente.ZINCO);
										n.setValor(valor);
										a.addNutriente(n);
										break;	
								default:
										break;
						}// nutrientes e valores
						break;
					}//switch da celula
				}//while acaba a linha atual
				if(a.getNutrienteSize()==11 )
				{
					Alimento.num++;
					//tab.addAlimento(a);
					this.addAlimento(a);
				}
				a = new Alimento();

					
			}//while acaba a planilha
			

			file.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return a;
	}

	
	
	public Map<Integer, Alimento> mapear(String nomeTabela)
	{
		Map<Integer, Alimento> mapa = new HashMap<Integer, Alimento>();
		leitura(nomeTabela);		
		for(int i=0; i<getSize(); i++)
		{
			mapa.put(i, getAlimento(i));
		}
		return mapa;
	}
	
	public void addAlimento(Alimento a)
	{
		tabela.add(a);
	}
	public Alimento getAlimento(int indice)
	{
		return tabela.get(indice);
	}
	public int getSize()
	{
		return tabela.size();
	}
	@Override
	public String toString() {
		return "Tabela: " + tabela;
	}
	
}
