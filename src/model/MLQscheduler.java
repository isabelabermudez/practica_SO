package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Clase que representa el algoritmo de planificación MLQ. Contiene las colas de procesos, la lista de todos los procesos
 * en el sistema y el reloj o contador de tiempo de la CPU.
 */
public class MLQscheduler {
    private List<QueueLevel> queues;
    private List<Process> allProcesses;
    private List<Process> finishedProcesses = new ArrayList<>();
    private int currentTime = 0;
    private List<String> executionOrder = new ArrayList<>();

    /**
     * Constructor del planificador MLQ
     * @param queues La lista de objetos QueueLevel (colas).
     */
    public MLQscheduler(List<QueueLevel> queues) {
        this.queues = queues;
    }

    /**
     * Recibe una lista de procesos, los ordena por tiempo de llegada (AT)
     * y los asigna a la QueueLevel correspondiente según su atributo 'queueId'.
     * @param processes La lista de todos los procesos que serán cargados en el sistema.
     */
    public void assignProcesses(List<Process> processes) {
        this.allProcesses = processes;
        //ordeno los procesos por orden de llegada
        this.allProcesses.sort((p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));

        // poner un metodo para ordenar los procesos por tiempo de llegada
        for(Process p: this.allProcesses){
            int queueId = p.getQueue();
            if(queueId <= queues.size()){
                queues.get(queueId -1).addProcess(p);
            }
        }
    }


    /**
     * Ejecuta el ciclo principal de la simulación MLQ.
     * Itera sobre las colas de mayor a menor prioridad y despacha el siguiente proceso disponible
     * hasta que todos los procesos se hayan ejecutado completamente.
     */
    public void execute() {
        System.out.println("=== Simulación MLQ Iniciada ===");
        while (!allProcesses.isEmpty()) {
            boolean processExecuted = false;
             for(QueueLevel q: queues){
                 if(!q.isEmpty()) {
                     String policy = q.getAlgorithm();
                     switch (policy) {
                         case "RR":
                             roundRobin(q);
                             break;
                         case "FCFS":
                             firstComeFirstServed(q);
                             break;
                     }
                     processExecuted = true;
                     break;
                 }

             }

             if(!processExecuted){
                 currentTime += 1;
             }
        }
        System.out.println("simulación finalizada");
        System.out.println("\nOrden de ejecución: " + executionOrder);

    }

    /**
     * Metodo que ejecuta el proceso al frente de la cola con el algoritmo round robin.
     * @param queue cola de procesos con política round robin
     */
    private void roundRobin(QueueLevel queue) {
        Queue<Process> processes = queue.getProcesses();
        int quantum =  queue.getQuantum();

        Process currentProcess = processes.poll(); //toma el primer proceso
        //revisa que el proceso no este vacío o ya haya llegado a la cola
        if(currentProcess == null || currentTime < currentProcess.getArrivalTime()){ return;}
        //si es la primera vez que se ejecuta
        if (currentProcess.getStartTime() == -1 ) {
            currentProcess.setStartTime(currentTime);
        }
        //ejecuta el proceso, si el burst time es menor que el quantum, ejecuta el proceso por ese tiempo, si no por el quantum
        int executeTime = Math.min(currentProcess.getRemaingBurstTime(),quantum);
        currentTime += executeTime;
        currentProcess.setRemaingBurstTime(currentProcess.getRemaingBurstTime() - executeTime);

        //registro el proceso que acabe de ejecutarse
        executionOrder.add(currentProcess.getLabel());

        //si el proceso ya termino de ejecutarse completamente, calcula las otras metricas faltantes
        if(currentProcess.getRemaingBurstTime() == 0){
            currentProcess.setCompletionTime(currentTime);
            currentProcess.calculateTurnaroundTime();
            currentProcess.calculateResponseTime();
            currentProcess.calculateWaitingTime();
            allProcesses.remove(currentProcess);
            finishedProcesses.add(currentProcess);
            System.out.println("Proceso " + currentProcess.getLabel() + " completado (RR) en t=" + currentTime + " (ejecutado por: "+ executeTime + " segundos ");
        } else {
            queue.addProcess(currentProcess); //si no vuelve a poner el proceso en la cola
            System.out.println("Proceso " + currentProcess.getLabel() + " reencolado (restante " + currentProcess.getRemaingBurstTime() + ") ( ejecutado por: " + executeTime + " segundos ");
        }

    }
    /**
     * Metodo que ejecuta el proceso usando la política First-Come, First-Served (no expropiativa).
     * El proceso se ejecuta ininterrumpidamente hasta su finalización.
     * @param queue Cola de procesos con política FCFS.
     */
    public void firstComeFirstServed(QueueLevel queue) {
        Queue<Process> processQueue = queue.getProcesses();
        if(processQueue.isEmpty()){
            return;
        }
        //obtengo el proceso que haya llegado primero, si llegaron al mismo tiempo, elijo el proceso con mayor prioridad
        Process currentProcess = null;
        for(Process p: processQueue){
            if(currentProcess == null || p.getArrivalTime() < currentProcess.getArrivalTime()){
                currentProcess = p;
            }else if(p.getArrivalTime() == currentProcess.getArrivalTime()) {
                if (currentProcess.getPriority() < p.getPriority()) {
                    currentProcess = p;
                }
            }
        }

        //verificamos que el proceso haya llegado a la cola, si no ha llegado agregamos(debo saber si hay otros procesos en la cola si ya llegaron)
        if(currentTime < currentProcess.getArrivalTime()){
            currentTime = currentProcess.getArrivalTime();
        }

        // Si es la primera vez (para RT), registra el tiempo actual
        if (currentProcess.getStartTime() == -1) {
            currentProcess.setStartTime(currentTime);
        }
        // ejecutamos el proceso, sumamos el burst time al tiempo del reloj
        int executeTime = currentProcess.getBurstTime();
        currentTime += executeTime;
        currentProcess.setRemaingBurstTime(0);
        //registro el proceso que acabe de ejecutarse
        executionOrder.add(currentProcess.getLabel());

        //Calculo de las metricas
        currentProcess.setCompletionTime(currentTime);
        currentProcess.calculateTurnaroundTime();
        currentProcess.calculateResponseTime();
        currentProcess.calculateWaitingTime();


        allProcesses.remove(currentProcess);
        finishedProcesses.add(currentProcess);
        queue.removeProcess(currentProcess); //remuevo el proceso de la cola
        System.out.println("Proceso " + currentProcess.getLabel() + " completado (FCFS) en t=" + currentTime + " (ejecutado por: "+ executeTime + " segundos ");

    }
    /**
     * Obtiene la lista de todos los procesos que han completado su ejecución.
     * @return La lista de procesos finalizados.
     */
    public List<Process> getFinishedProcesses() {
        return finishedProcesses;
    }

}
