package com.mycompany.nodosjava;


public class test {
	
	public static void main (String args[]) {
		String child = null;
		String parent = null;
		String node = null;
		String command = null;//las opciones pueden se a�adir,guardar,imprimir y salir
		System.out.println("Bienvenido\n\n");
		Tree tree = new Tree();		
		Leaf root = tree.sendRootNode();
		do{			
			System.out.println("Introduce el comando(a�adir,eliminar,imprimir,guardar,salir): ");
			command = Teclado.readString();
			if(command.equals("a�adir")){
				System.out.println( "Inserta el nombre del hijo: " );
				child = Teclado.readString();
				System.out.println( "Inserta el nombre del padre: " );
				parent = Teclado.readString();
				if (parent.equals("null")){ 
					//si el archivo readText.txt esta vacio. Se inserta un hijo
					root.insertChild(new Leaf(child, root));
					tree.setControl(true);
				}else{
					//si el archivo readText.txt no esta vacio el programa ha de buscar al padre e insertar al hijo
					root = tree.addNewChild(child, parent,root);	
				}							
				if (tree.isControl()){	
					System.out.println("El nodo ha sido a�adido satisfactoriamente...");
					tree.setControl(false);
				}else{
					System.out.println("El padre no ha sido encontrado!!!");
				}	
			}								
			else if (command.equals("imprimir")){
				tree.getTextFile().setLength(0);// va a la primera linea
				tree.getFirstFile().setLength(0);
				tree.printChildren(root, false);
				
			}else if(command.equals("guardar")){
				tree.getTextFile().setLength(0);
				tree.getFirstFile().setLength(0);
				tree.printChildren(root, true);			
				tree.saveTreeStructureToFile(root, "out.txt", tree.getTextFile());	
				//escribe el arbol estructura en el archivo out.txt
				tree.saveTreeStructureToFile(root, "readText.txt", tree.getFirstFile()); 
				//escribe el arbol estructura en el archivo readText.txt
				
			}else if(command.equals("eliminar")){
				System.out.println( "Inserta el nombre del nodo a borrar: " );
				node = Teclado.readString();
				tree.removeChild(node,root);
				
				if (tree.isControl()){	
					System.out.println("El nodo ha sido eliminado...");
					tree.setControl(false);
				}else{
					System.out.println("El nodo no ha sido encontrado!!!");
				}	
				
			}
		}while((!command.equals("salir")) && (!command.equals("" +"")));
		System.out.println("El programa ha sido cerrado. Vuelva a ejecutarlo");
	}

}