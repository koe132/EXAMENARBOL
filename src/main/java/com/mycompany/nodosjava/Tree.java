package com.mycompany.nodosjava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class Tree {
	
	private boolean control; // se controla si el usuario usa un padre que ya existe
	private StringBuffer textFile = new StringBuffer(); // se escribe la estructura arbol en el archivo out.txt
	private StringBuffer firstFile = new StringBuffer(); // se escribe la estructura arbol en el archivo readText.txt
	
	public Tree(){}
	
	public boolean isControl() {
		return control;
	}

	public void setControl(boolean control) {
		this.control = control;
	}

	public StringBuffer getTextFile() {// se da el archivo out.txt
		return textFile;
	}

	public StringBuffer getFirstFile() {// se da el archivo read.txt
		return firstFile;
	}

	public Leaf addNewChild(String childName, String parentName, Leaf rootNode){
		for(int i=0; i<rootNode.getChildren().size(); i++){		
			// rootNode.getChildren().size() = numero de hijos de esa hoja
			Leaf parentNode = rootNode.getChildren().get(i);	
			//se recorren todos los hijos de esa hoja y el nodo en el que este en ese momento sera el padre
			if(parentNode.getName().equals(parentName)){		
				// se comparan los nombres	
				parentNode.insertChild(new Leaf(childName, parentNode));	
				// si es el nombre que el programa est� buscando, inserta el nodo
				control = true;
				// se encuentra un nodo con el mismo nombre que el usuario ha introducido
				break;
				// en el caso de que el usuario ponga m�s de un nodo con el mismo nombre,
				// el programa solo a�ade el hijo en el primer nodo. Otra posibilidad 
				// es quitar el "break" y el programa a�ade el nuevo hijo a todos los padres con el mismo nombre.
				
			}
			if (parentNode.getChildren().size() != 0){		
				//si el nodo tiene hijos, el programa llama al metodo recursivo
				addNewChild(childName, parentName, parentNode);	
			}
		}
		return rootNode;
	}
	
	public void removeChild(String Node, Leaf rootNode){
		
		for(int i=0; i<rootNode.getChildren().size(); i++){		
			// rootNode.getChildren().size() = numero de hijo de este subarbol
			Leaf parentNode = rootNode.getChildren().get(i);//se recorren uno a uno los nodos del arbol	
			
			if(parentNode.getName().equals(Node)){
				// se comparan los nombres	
				rootNode.getChildren().remove(i);
				// si es el nombre que el programa est� buscando, elimina el nodo
				control = true;
				// se encuentra un nodo con el mismo nombre que el usuario ha introducido
				break;
				
				
			}
			removeChild(Node,parentNode);//llamada metodo recursivo
			
		}
		
	}
	

	
	public void printChildren(Leaf rootNode, Boolean command){	
		String tab = "";
		for(int i=0;i<rootNode.getChildren().size(); i++){
			tab = "";
			for(int j=0; j<rootNode.rowofChild(rootNode); j++){	
				//con este bucle se puede ver la cantidad de nivel de profundidad que hay.
				//el programa a�ade una nueva tabulacion para cada nivel
				tab += '\t';
			}
			String str = tab + rootNode.getChildren().get(i).getName();
			if (command){	//true = actualiza el b�fer que luego se utiliza para imprimir el archivo out.txt
				textFile.append(str + " " + System.getProperty("line.separator")); 
				//el programa a�ade las lineas al bufer que escribe el arbol
			}else{	
				//false = el programa actualiza el bufer que mas tarde muestra el arbol
				System.out.println(str);
			}
			if (rootNode.getChildren().get(i).getChildren().size() != 0){		
				//si el nodo tiene hijos,el programa llama de nuevo al metodo recursivo
				printChildren(rootNode.getChildren().get(i),command);			
			}else{							
				firstFile.append(setLineOfFirstFile(rootNode.getChildren().get(i)) + System.getProperty("line.separator"));	
				// el programa obtiene la l�nea para a�adir en el b�fer del archivo readText.txt	
			}
		}
	}
	
	public void saveTreeStructureToFile(Leaf rootNode, String filename, StringBuffer strBuffer){
		try{
		    // se crea el archivo 
		    FileWriter fstream = new FileWriter(filename);	
		    BufferedWriter out = new BufferedWriter(fstream);	
		    out.write(strBuffer.toString());	
		    out.close();
		}catch (Exception e){  
		    System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	public String setLineOfFirstFile(Leaf node){		
		//se obtiene la linea que se a�ade en el archivo readText.txt con los predecesores del nodo
		String line = "";
		//se crea un objeto ArrayList
	    ArrayList<String> lineList = new ArrayList<String>();
		while (node.getName()!= "RootNode"){
			//se continua mientras el bucle no obtenga todos los predecesores
			lineList.add(node.getName());
			node = node.getParent();
		}
		Collections.reverse(lineList);
		//el bucle obtiene los nombres de los nodos desde el ultimo hasta el primero. Es necesario invertirlo.
		for(int i=0; i< lineList.size();i++){	
			line += lineList.get(i) + ",";
		}
		return line.substring(0, line.length()-1);	
		//se coge la linea sin la ultima coma
	}
	
	public Leaf sendRootNode(){
		File file = new File("readText.txt");	
        BufferedReader reader = null;
        Leaf root = new Leaf("RootNode", null);	//se crea una nueva hoja,que sera la raiz
        Leaf broot = root;	//se hace una copia para ejecutar el arbol
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            // se repite hasta que todas las lineas son leidas
            while ((line = reader.readLine()) != null) {
            	String []fields = line.split(",");
            	for(int i =0;i<fields.length;i++){
            		Leaf leaf = new Leaf(fields[i], broot);
            		//se crea una nueva hoja
            		int containsIndex = broot.ContainsNode(leaf); 
            		//se comprueba si el padre contiene esta hoja
            		if (containsIndex != -1){ 
            			//si el padre contiene este hijo,a continuaci�n se busca la fila de ese hijo, y temporalmente hace que sea la nueva ra�z 
            			broot = broot.getChildren().get(containsIndex); 
            			//containsIndex = obtiene el indice de la hoja hijo
            		}else{
            			broot.insertChild(leaf);
            			//si el padre no contiene esta hoja, entonces se a�ade esta hoja como hijo de este padre
                		broot = leaf;   
                		//esta hoja es el nuevo padre
            		}       		        		  		
            	}
            	broot = root; 
            	//para la nueva l�nea del archivo, se comienza desde el principio a hacer el mismo proceso
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no ha sido encontrado");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error");
        } finally {
            try {
                if (reader != null) {
                	reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return root;
	}
}
