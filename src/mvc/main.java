/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import colasso.Cola;
import colasso.AgregarProcesos;
import colasso.EjecutarProcesos;
import colasso.Nodo;
import controlador.Control;
import java.util.ArrayList;
import vista.Vista;

/**
 *
 * @author JuanDavid
 */
public class main {

   
    public static void main(String[] args) {
        
       Cola cola=new Cola();
       Vista vista=new Vista();
       Control control=new Control(vista, cola);
       control.iniciar();
       Nodo n=new Nodo("0",10000, 1000);
       ArrayList<String[]> gantt=new ArrayList<>();
       AgregarProcesos c = new AgregarProcesos(cola,n,control,gantt);
       c.start();
       
       
       //EjecutarProcesos e=new EjecutarProcesos(cola,n,control, gantt);
       //e.start();
       //EjecutarProcesos e=new EjecutarProcesos(cola);  
    }
    
}
