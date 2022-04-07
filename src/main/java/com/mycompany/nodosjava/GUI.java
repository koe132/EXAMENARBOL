package com.mycompany.nodosjava;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.io.IOException;
import javax.swing.tree.*;
public class GUI extends JFrame implements ActionListener{


	
	
	private JPanel principal,arbol,botones,h,pad,n;
	private JButton anadir,eliminar,guardar,imprimir,salir;
	private JFrame addHijo,clearNodo;
	private JButton add,clear;
	private JComboBox padres,nodos;//desplegable con todos los nodos del arbol
	private JTextArea area;
	private JTextField hijo,nodo;
	private JLabel lh,lp,ln;
	int res;

	
	private String nombreH;
	private String nombreP;
	private String nombreN;
	private String command = null;//las opciones pueden se a�adir,guardar,imprimir y salir
	private Tree tree ;
	private Leaf root;
	
	
	
	
	 public GUI(){
			super ("Árbol Genealógico ");
			initialize();	
	 }
	 
	 
	 public static void main (String args[]){
		 	GUI frame = new GUI();
		 	frame.setSize(700,500);
		 	frame.setVisible(true);
		 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
	 }//cierra main
	 
	 
	 private void initialize(){
		 
	 
	 
			//se obtiene el conntenedor asociado a la ventana
			Container c = getContentPane();
			c.setLayout(new FlowLayout());
			//se crean los paneles
			principal = new JPanel();
			arbol = new JPanel();
			
			//se crea el bordeado del area de texto
			Box cuadro = Box.createHorizontalBox();
			area = new JTextArea(25,50);
			area.setEditable(false);
			
			cuadro.add(new JScrollPane(area));
			arbol.add(cuadro);
			
			//se crean los botones y se a�aden los botones a los escuchadores
			botones = new JPanel(new GridLayout(4,1));
			anadir = new JButton("Añadir");
			anadir.addActionListener(this);
			
			//guardar = new JButton("Guardar");
			//guardar.addActionListener(this);  
			
			eliminar = new JButton("Eliminar");
			eliminar.addActionListener(this);
	
			//imprimir = new JButton("Imprimir");
			//imprimir.addActionListener(this); 
			
			salir = new JButton("Salir");
			salir.addActionListener(this);
			
			botones.add(anadir);
			botones.add(eliminar);
			//botones.add(imprimir);
			//botones.add(guardar);
			botones.add(salir);
			
			
			principal.add(arbol);
			principal.add(botones);
			c.add(principal);
	
		
			tree = new Tree();
			root = tree.sendRootNode();
			
	 }
	 
	 
	 
	 public void anadir(){
			
			addHijo = new JFrame("Añadir Hijo");
			addHijo.setSize(350,150);
			addHijo.setDefaultCloseOperation(addHijo.EXIT_ON_CLOSE);
			addHijo.setVisible(true);
			addHijo.setVisible(true);
			addHijo.setLayout(new GridLayout(3,1));
			
			h = new JPanel();
			lh = new JLabel ("Nombre Hijo");
			hijo = new JTextField(20);
			h.add(lh);
			h.add(hijo);
			

			//se obtienen los padres existentes                                             
			ArrayList<String> p = root.getChildren2();//se coge un array con todos los nodos que cuelgan de la raiz
			Iterator pi = p.iterator();//se crea un iterador para poder ir recorriendo todos los elementos del array
			String []ps = new String[p.size()+1];
			ps[0] = "Root";
			int i = 1;
			while(pi.hasNext()){//se pasa al siguiente
				ps[i] = ((String)pi.next());
				i++;
				
			}
			
			pad = new JPanel();
			padres = new JComboBox(ps);
			lp = new JLabel("Nombre Padre");
			pad.add(lp);
			pad.add(padres);
			addHijo.add(h);
			addHijo.add(pad);
			add = new JButton("Añadir");
			add.addActionListener(this);
			addHijo.add(add);
			
			
			
		}
	 
	 public void eliminar(){
		 
		 	clearNodo = new JFrame("Borrar nodo");
		 	clearNodo.setSize(350,150);
		 	clearNodo.setDefaultCloseOperation(clearNodo.EXIT_ON_CLOSE);
			clearNodo.setVisible(true);
			clearNodo.setVisible(true);
			clearNodo.setLayout(new GridLayout(2,1));
			
			n = new JPanel();
			//ln = new JLabel ("Nombre Nodo");
			//nodo = new JTextField(20);
			//n.add(ln);
			//n.add(nodo);
			
			//se obtienen los nodos existentes                                             
			ArrayList<String> p = root.getChildren2();//se coge un array con todos los nodos que cuelgan de la raiz
			Iterator pi = p.iterator();//se crea un iterador para poder ir recorriendo todos los elementos del array
			String []ps = new String[p.size()+1];
			ps[0] = "Root";
			int i = 1;
			while(pi.hasNext()){//se pasa al siguiente
				ps[i] = ((String)pi.next());
				i++;
				
			}
			
			nodos = new JComboBox(ps);
			ln = new JLabel ("Nombre Nodo");
			n.add(ln);
			n.add(nodos);
			
			clearNodo.add(n);
			clear = new JButton("Clear");
			clear.addActionListener(this);
			clearNodo.add(clear);
			
			
	 }
	 
	 
	 public void crearArbol(Leaf root,Boolean command){//metodo imprimir de la clase Tree.java
		 String tab = "";
			for(int i=0;i<root.getChildren().size(); i++){
				tab = "";
				for(int j=0; j<root.rowofChild(root); j++){	
					//con este bucle se puede ver la cantidad de nivel de profundidad que hay.
					//el programa a�ade una nueva tabulacion para cada nivel
					tab += '\t';
				}
				String str = tab + root.getChildren().get(i).getName();
				if (command){	//true = actualiza el b�fer que luego se utiliza para imprimir el archivo out.txt
					tree.getTextFile().append(str + " " + System.getProperty("line.separator"));
					//el programa a�ade las lineas al bufer que escribe el arbol
				}else{	
					//false = el programa actualiza el bufer que mas tarde muestra el arbol
					area.append(str+"\n");//para que te muestre el arbol sin que se pueda modificar
				}
				if (root.getChildren().get(i).getChildren().size() != 0){		
					//si el nodo tiene hijos,el programa llama de nuevo al metodo recursivo
					crearArbol(root.getChildren().get(i),command);
				}else{							
					tree.getFirstFile().append(tree.setLineOfFirstFile(root.getChildren().get(i)) + System.getProperty("line.separator"));	
					// el programa obtiene la l�nea para a�adir en el b�fer del archivo readText.txt	
				}
			}
	}
	 

	 
	 public void guardar(){
			System.out.println("guardando");
			tree.getTextFile().setLength(0);
			tree.getFirstFile().setLength(0);
			tree.printChildren(root, true);			
			tree.saveTreeStructureToFile(root, "out.txt", tree.getTextFile());	
			//escribe el arbol estructura en el archivo out.txt
			tree.saveTreeStructureToFile(root, "readText.txt", tree.getFirstFile()); 
			//escribe el arbol estructura en el archivo readText.txt
	}
	 
	 
	 public void salir(){
			res = JOptionPane.showConfirmDialog(null, "Desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
			 if (res==JOptionPane.YES_OPTION){				
				System.exit(0);
		 
			 }
	}
	 
	 
	public void actionPerformed (ActionEvent ae){

		
		 		
		 if (ae.getSource()== anadir){
			 
				anadir();
		 }
		 
		 
		 
		 
	  	 if (ae.getSource()== add){//este boton es el "OK" de la segunda ventana
	  		 
	  		 	nombreH = hijo.getText();
	  		 	nombreP = padres.getSelectedItem().toString();
	  		 
	  		 	if(nombreP == "Root"){
	  		 		root.insertChild(new Leaf(nombreH, root));
	  		 	}
	  		 	else{
	  		 		root = tree.addNewChild(nombreH,nombreP,root);
	  		 	}
	  		 
	  		 	guardar();
	  		 	crearArbol(root,false);
	  		 	addHijo.dispose();//cuando se pulsa el boton "OK" se guarda el hijo en el padre seleccionado y se borra lo que
	  		 	//haya en la ventana para poder introducir un nuevo hijo
	  		 	JOptionPane.showMessageDialog(addHijo,"Elemento insertado correctamente");
	  	 
	  	 }
	  	 
	  	 
	  	if (ae.getSource()== eliminar){
			 
	  			eliminar();
	  	}
	 
	  	if (ae.getSource()== clear){//este boton es el "OK" de la segunda ventana
  		 
	  			//nombreN = nodo.getText();
	  			nombreN = nodos.getSelectedItem().toString();
	  			tree.removeChild(nombreN, root);
	  			guardar();
	  			crearArbol(root,false);
	  			clearNodo.dispose();//cuando se pulsa el boton "OK" se elimina el nodo introducido y se borra lo que
	  			//haya en la ventana para poder introducir un nuevo nodo a eliminar
	  			JOptionPane.showMessageDialog(clearNodo,"Elemento eliminado correctamente");
  	 
	  	}
	  	 
	  	 /*
		 if (ae.getSource() == guardar){
			 
			guardar();//este boton se quitara de la interfaz
			
		 }
		 */
	  	
	  	/*
		 if (ae.getSource() == imprimir){
			 
				//area.setText("Arbol Genealogico\n");
				crearArbol(root,false);
		 }
		 */
	  	
	  	
		 if (ae.getSource() == salir){
			 
				salir();//este boton se quitara de la interfaz
				
			 }
		 
	 }//cierra metodo actionPerformed
	 

	 
}//cierra clase
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
