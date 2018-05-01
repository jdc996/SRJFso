/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package colasso;

import controlador.Control;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author JuanDavid
 */
public class AgregarProcesos extends Thread {

    public AgregarProcesos(Cola c,Nodo n ,Control control, ArrayList<String[]> gantt) {
        this.cola = c;
        this.nodoActual=n;
        this.control = control;
        this.lista = gantt;
        this.e=new EjecutarProcesos(cola,nodoActual,control, lista);
    }

    private int contProcesos = 0, tLlegada = 0, tRafaga, espera;
    private Random rand = new Random();
    private Cola cola;
//    private boolean bandera = false;
    //private boolean banderaHilo = true;
    private Control control;
    private ArrayList<String[]> lista;
    private int tiempoFilaAgregar = -1;
    //private PintarEspera hiloEspera;
    private Nodo nodoActual;
    private EjecutarProcesos e;
    
    public void crearProcesos() throws InterruptedException {
        //La variable contProcesos almacena el numero del proceso que se crea
        
        contProcesos += 1;
        tRafaga = rand.nextInt(15) + 1;
        espera = rand.nextInt(10)+1;
        
        //Se añade el proceso nuevo a la cola
        cola.agregar(cola.getProcesador(), contProcesos, tLlegada, tRafaga);
        if(e.getNodoActual().getTiempoRafaga()> cola.getNodo(String.valueOf(contProcesos)).getTiempoRafaga()){     
            e.setNodoActual(cola.getNodo(String.valueOf(contProcesos)));
        }
        
        
        //Se añaden los datos del nuevo proceso a una listat y se añaden a una lista que almacena todos los procesos
        String[] datos = new String[]{String.valueOf(contProcesos), String.valueOf(tLlegada), String.valueOf(tRafaga), "0", "0", "0"};
        String[] datosGantt = new String[]{String.valueOf(contProcesos)};
        lista.add(datos);
        tLlegada += espera;
        //Pinta en la vista el nuevo proceso creado
        control.actualizarTabla(lista);
        control.actualizarTablaGantt(datosGantt);
        //el hilo se duerme un tiempo aleatorio hasta que se cree un nuevo proceso
        
        aumentarTiempo(espera);
       

    }


    public void aumentarTiempo(int espera) throws InterruptedException {
        for (int i = 0; i < espera; i++) {
            tiempoFilaAgregar += 1;
            System.out.println("tiempo Agregar: " + tiempoFilaAgregar);
            Thread.sleep(1000);
        }
    }


    public void iniciar() throws InterruptedException {
        int procesoIncial = 0;
        while (procesoIncial != 2) {
            contProcesos += 1;
            tRafaga = rand.nextInt(15) + 1;
            cola.agregar(cola.getProcesador(), contProcesos, tLlegada, tRafaga);
            String[] datos = new String[]{String.valueOf(contProcesos), String.valueOf(tLlegada), String.valueOf(tRafaga), "0", "0", "0"};
            String[] datosGantt = new String[]{String.valueOf(contProcesos)};
            lista.add(datos);
            control.actualizarTabla(lista);
            control.actualizarTablaGantt(datosGantt);
            procesoIncial++;    
        }
        aumentarTiempo(1);
        tLlegada+=1;
    }

    public ArrayList<String[]> getLista() {
        return lista;
    }

    public void setLista(ArrayList<String[]> lista) {
        this.lista = lista;
    }

    public void run() {
      
            e.start();
        try {
            iniciar();
        } catch (InterruptedException ex) {
            Logger.getLogger(AgregarProcesos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (true) {
            try {
                crearProcesos();

            } catch (InterruptedException ex) {
                Logger.getLogger(AgregarProcesos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
