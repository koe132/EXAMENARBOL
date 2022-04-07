package com.mycompany.nodosjava;

//package readfile;
//package readfile;
import java.util.ArrayList;
import java.util.Iterator;


public class Leaf {

	private String name;
	private Leaf parent;
	public ArrayList<Leaf> children=null;// array de hijos
	
	//constructor
	public Leaf(String name, Leaf parent){//un hijo tiene un nombre y un padre
		this.name = name;
		this.parent = parent;
		children = new ArrayList<Leaf>();
	}
	
	public String getName() {//se da el nombre
		return name;
	}

	public void setName(String name) {//se cambia el nombre 
		this.name = name;
	}

	public Leaf getParent() {//se da el padre
		return parent;
	}

	public void setParent(Leaf parent) {//se cambia el nombre del padre
		this.parent = parent;
	}
	
	public ArrayList<String> getChildren2() {//se da la lista de los nombres de los hijos
			ArrayList<String> aux= new ArrayList<String>();
			if(!name.equals("RootNode"))aux.add((String)name);
			if(children==null){
				return aux;
			}else{
				Iterator pi = children.iterator();//se crea un iterador para poder ir recorriendo todos los elementos del array
				while(pi.hasNext()){//mientras que ese nodo tenga hijos 
						ArrayList<String> aux2=((Leaf)pi.next()).getChildren2();// recorro los hijos que tenga
						Iterator pi2 = aux2.iterator();//vuelvo a recorrer esa lista
						while(pi2.hasNext()){//paso al siguiente
							aux.add((String)pi2.next());
						}
				}		
				return aux;
			}
	}
	
	public ArrayList<Leaf> getChildren() {//se da la lista de hijos
		return children;
	}

	public void setChildren(ArrayList<Leaf> children) {
		this.children = children;
	}

	public void insertChild(Leaf node) {//se inserta un hijo
		if (!this.children.contains(node)){// si este hijo no contiene el nodo que se le esta pasando se le a�ade en la siguiente linea
			this.children.add(node);
			
		}
	}
	
	public int rowofChild(Leaf node){
		int row = 0;
		while (node.name!= "RootNode"){
			row +=1;
			node = node.parent;
		}
		return row;	
	}
	
	public int ContainsNode(Leaf node){
		for(int i=0; i<this.children.size(); i++){//children.size() = da la cantidad de hijos
			if(this.children.get(i).getName().equals(node.getName())){//se compara si el nombre de ese hijo es igual al nombre del nodo que te pasa
				return i;
			}
		}
		return -1;
	}
}
