package programaProbadorPedregal;

import java.io.*;


public class ProgramaProbadorPedregal {

	private boolean matrizTerreno[][];
	private int dx;
	private int dy;
	private String siNo;
	private int[][] cordenadasPeñascos;
	private int cantPeñascos;  
	private int[] dimCasa;  //dimension de la casa "frente lateral"
	private int[] posicionCasa; //la cordenada donde esta ubicada la casa..En el OUT
	private char orientacion;   //NORTE SUR ESTE OESTE
	
	
	public ProgramaProbadorPedregal(String pathOut){
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    posicionCasa= new int[2];
	    dimCasa= new int[2];
	    //LEO ACHIVO OUT
	  	try {
	          // Apertura del fichero y creacion de BufferedReader para poder
	          // hacer una lectura comoda (disponer del metodo readLine()).
	          archivo = new File (pathOut);
	          fr = new FileReader (archivo);
	          br = new BufferedReader(fr);
	          
	          String linea;
	          // Lectura del fichero
	          linea=br.readLine();
	          siNo=linea;
	          
	          
	          if(siNo.equalsIgnoreCase("SI")){
		          	linea=br.readLine();
		          	String []dato=linea.split(" ");
		              posicionCasa[0]=Integer.parseInt(dato[0]);
		              posicionCasa[1]=Integer.parseInt(dato[1]);
		              linea=br.readLine();
		              orientacion=linea.charAt(0);
		          }
	  	}
	       catch(Exception e){
	          e.printStackTrace();
	       }finally{
	          // En el finally cerramos el fichero, para asegurarnos
	          // que se cierra tanto si todo va bien como si salta 
	          // una excepcion.
	          try{                    
	                
	                fr.close();     
	                              
	          }catch (Exception e2){ 
	             e2.printStackTrace();
	          }
	       }
	}
	
	
	
	public boolean programaProbadorPedregal(String pathIn,String siNoEsperado){

	          if(siNoEsperado.equalsIgnoreCase("NO") && siNo.equalsIgnoreCase("NO")){
	  			return true;
	  		}
	          else if (siNoEsperado.equalsIgnoreCase("NO") || siNoEsperado.equalsIgnoreCase("SI") && siNo.equalsIgnoreCase("NO")) {
	  			return false;
	  		}
	          //SI ES SI el out esperado..
	          
	          File archivo = null;
		      FileReader fr = null;
		      BufferedReader br = null;
	       
	     	  	
	      try {
	         // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).
	    	 archivo = new File (pathIn);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero IN
	         String linea;
	         linea=br.readLine();
	         String  []dato=linea.split(" ");
	         dx=Integer.parseInt(dato[0]);
	         dy=Integer.parseInt(dato[1]);
	         
	         linea=br.readLine();
	         dato=linea.split(" ");
	         dimCasa[0]=Integer.parseInt(dato[0]);
	         dimCasa[1]=Integer.parseInt(dato[1]);
	         
	         linea=br.readLine();
	         cantPeñascos=Integer.parseInt(linea);
	         
	         cordenadasPeñascos=new int[cantPeñascos][2];
	         
	         linea=br.readLine();
	       
	         for(int i=0;i<cantPeñascos;i++){
	        	 dato=linea.split(" ");
	        	 cordenadasPeñascos[i][0]=Integer.parseInt(dato[0]);
	        	 cordenadasPeñascos[i][1]=Integer.parseInt(dato[1]);
	        	 linea=br.readLine();	 
	         }  
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         // En el finally cerramos el fichero, para asegurarnos
	         // que se cierra tanto si todo va bien como si salta 
	         // una excepcion.
	         try{                    
	               
	               fr.close();     
	                              
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
	      return resolver();
	}
	
	
	public boolean resolver(){
		boolean respuesta;
		//cargo matriz
		matrizTerreno=iniciaMatrizYCompleta();
		//VERIFICO SI LA CASA ESTA BIEN UBICADA
		respuesta=verificarCasa();   //si la casa esta bien me devuelve TRUE
		return respuesta;
	}
	
	private boolean[][] iniciaMatrizYCompleta() {
		boolean matriz[][]=new boolean[ dy ][ dx ];
		//inicializo matriz con false
		for(int fila=0;fila < dy; fila++ ){
			for(int columna=0; columna < dx;columna++){
				matriz[fila][columna]=false;
			}
		}
		
		//Cargo peñascos
		for(int nPenasco=0; nPenasco < cantPeñascos; nPenasco++ ){
			matriz[ cordenadasPeñascos[nPenasco][1]-1][cordenadasPeñascos[nPenasco][0] -1]=true; //para setear resto uno a columna y fila
		}
		return matriz;
	}


	private boolean verificarCasa(){
		if(orientacion == 'N' || orientacion == 'n' || orientacion == 'S' || orientacion == 's')
			for(int fila=(posicionCasa[0])-1; fila<posicionCasa[0]; fila++){       //HORIZONTAL			
				for(int columna=(posicionCasa[1])-1; columna < posicionCasa[1] ;columna++ ){
					if(matrizTerreno[fila][columna] == true)
						return false;
		
				}	
			}
		else if(this.orientacion == 'E' || orientacion == 'e' || orientacion == 'O' || orientacion == 'o'){
			
			for(int fila=(posicionCasa[1])-1; fila< posicionCasa[1] ; fila++){       //VERTICAL			

				for(int columna=(posicionCasa[0])-1; columna < posicionCasa[0] ;columna++ ){
					
					if(matrizTerreno[fila][columna] == true)
						return false;
				}
			}	
		}
		else return false; //En caso que reciba otra cosa a S E O N, devuelve falso.
		
		return true;  //Verifico y salio todo bien.
	}
