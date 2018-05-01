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

/**
 *
 * @author JuanDavid
 */
public class EjecutarProcesos extends Thread {

    private Cola cola;
    private ArrayList<String[]> gantt;
    private Control control;
    private int ejecucion, tiempoFila = 0;
    private String[] datosGantt;
    private Nodo nodoActual;

    public EjecutarProcesos(Cola c, Nodo n, Control con, ArrayList<String[]> gantt) {
        this.cola = c;
        this.nodoActual = n;
        this.control = con;
        this.gantt = gantt;

    }

    public Nodo getNodoActual() {
        return nodoActual;
    }

    public void setNodoActual(Nodo nodoActual) {
        this.nodoActual = nodoActual;
    }

    public void ejecutar() throws InterruptedException {
        if (!cola.colaVacia()) {
            System.out.println("entro a ejecutor**********************");

            //Se obtienen el tiempo de rafaga, tiempo de llegada y nombre del proceso que se ejecturara
            ejecucion = (cola.getProcesador().getSiguiente().getTiempoRafaga());
            int fila = Integer.parseInt(cola.getProcesador().getSiguiente().getNombre());

            //Se pinta en la tabla de Gantt la ejecucion del proceso, de acuerdo al tiempo de rafaga
            nodoActual = cola.getNodo(String.valueOf(fila));
            System.out.println("nodo actual ejecutar :" + nodoActual);
            pintarGanttNodoActual(ejecucion, Integer.parseInt(nodoActual.getNombre()), tiempoFila);
            //if (bandera) {
            datosGantt = cola.atender();
            //Se almacenan los datos de la cola en un arreglo, nombre, tiempo de llegada, Rafaga, Retorno, Espera y final
            //Se pinta el tiempo de espera del proceso en el diagrama de gantt
            pintarGanttEspera(Integer.parseInt(datosGantt[1]), Integer.parseInt(nodoActual.getNombre()),Integer.parseInt(datosGantt[5]) );
            //Se modifican en la lista de procesos gantt los datos de tiempo de Retorno, Espera y final del proceso en ejecucion
            gantt.set(Integer.parseInt(datosGantt[0]) - 1, datosGantt);
            //Se pinta en la tabla de procesos los datos de tRetorno, espera y final del proceso
            control.actualizarFila(datosGantt[3], Integer.parseInt(datosGantt[0]) - 1, 3);
            control.actualizarFila(datosGantt[4], Integer.parseInt(datosGantt[0]) - 1, 4);
            control.actualizarFila(datosGantt[5], Integer.parseInt(datosGantt[0]) - 1, 5);
            System.out.println("************Diagrama Gantt Ejecutado ************");
            System.out.println("**************************************************");

            //}
        } else {
            Thread.sleep(1000);
            aumentarTiempo();
        }
    }

    public int getTiempoFila() {
        return tiempoFila;
    }

    public void pintarGanttNodoActual(int tiempo, int fila, int columna) throws InterruptedException {

        while (nodoActual.getTiempoRafaga() != 0) {

            //Se pinta en la vista la ejecion del proceso de gantt
            control.actualizarFilaGantt("Ejecutando", Integer.parseInt(nodoActual.getNombre()), columna);
            cola.getNodo(nodoActual.getNombre()).setTiempoRafaga(cola.getNodo(nodoActual.getNombre()).getTiempoRafaga() - 1);
            System.out.println(cola.getNodo(String.valueOf(fila)).getTiempoRafaga());
            System.out.println("nodo actual: " + nodoActual.getTiempoRafaga());
            columna++;
            Thread.sleep(1000);
            aumentarTiempo();

        }
        cola.getNodo(nodoActual.getNombre()).setTiempoFinal(tiempoFila);

    }

    public void pintarGanttEspera(int tiempoLlegada, int fila, int tiempoFinal) throws InterruptedException {
        for (int i = tiempoLlegada; i < tiempoFinal; i ++) {
            control.actualizarFilaGanttEspera("Espera", fila, i);
            
        }
    }

    public void aumentarTiempo() {
        tiempoFila += 1;
        System.out.println(tiempoFila);
    }

    public void run() {
        while (true) {
            try {
                ejecutar();

            } catch (InterruptedException ex) {
                Logger.getLogger(EjecutarProcesos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
