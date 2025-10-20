package model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Esta clase representa una cola perteneciente a MLQ, con su politica de planificación
 * y su lista de procesos listos.
 */

public class QueueLevel {
    private int queueId; //nivel de cola (sirve como identificador y define la prioridad)
    private String algorithm; //algoritmo de planificación que se usa en esta cola
    private int quantum; // quantum de tiempo. Solo se usa si el algoritmo es  RR
    private Queue<Process> processes; //cola de procesos que están listos para ejecutarse

    /**
     * Constructor de la clase QueueLevel. Inicializa la cola con su ID, algoritmo y quantum.
     * @param queueId el identificador de la cola, indica su prioridad
     * @param algorithm el algoritmo de planificación que usa la cola
     * @param quantum el quantum de tiempo. Se usa en caso de que el algoritmo de planificación sea RR
     */
    public QueueLevel(int queueId, String algorithm, int quantum) {
        this.queueId = queueId;
        this.algorithm = algorithm;
        this.quantum = quantum;
        this.processes = new LinkedList<>();
    }

    /**
     * Metodo que obtiene y elimina el proceso que está al frente de la cola, es decir el siguiente a ejecutar.
     * @return el objeto Process que está al inicio de la cola, o null si la cola está vacía.
     */
    public Process getNextProcess() {
        return this.processes.poll();
    }

    /**
     * Añade un proceso al final de la cola.
     * @param process El proceso que será añadido a la cola.
     */
    public void  addProcess(Process process) {
        this.processes.add(process);
    }

    /**
     * Verifica si la cola de listos esta vacía.
     * @return true si no hay procesos esperando en la cola, false en caso contrario.
     */
    public boolean isEmpty(){
        return this.processes.isEmpty();
    }

    /**
     * Elimina un proceso específico de la cola.
     * @param process El proceso a eliminar de la cola.
     */
    public void removeProcess(Process process) {
        this.processes.remove(process);
    }

    // GETTERS

    public Queue<Process> getProcesses() {
        return processes;
    }
    public String getAlgorithm() {
        return algorithm;
    }
    public int getQuantum() {
        return quantum;
    }
    public int getQueueId() {
        return queueId;
    }


}
