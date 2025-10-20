package model;

/**
 * Esta clase representa los procesos que van a ser ejecutados.
 * Esta clase provee getters y setters de todos sus atributos y el metodo toString() para imprimir las métricas solicitadas
 */
public class Process {
    private String label;
    private int burstTime;
    private int remaingBurstTime; //almacena el tiempo restante de CPU que le queda al proceso
    private int arrivalTime;
    private int queue;
    private int priority;
    private int waitingTime;
    private int completionTime;
    private int responseTime;
    private int turnaroundTime;
    private int startTime = -1; //tiempo en que comieza a ejecutarse por primera vez el proceso


    /**
     * Construrtor de proceso, recibe la etiqueta, el burst Time, el arrival Time, la cola y la prioridad del proceso que se creará.
     * @param label etiqueta del proceso
     * @param burstTime timpo total de CPU requerido
     * @param arrivalTime tiempo de llegada a la cola
     * @param queue cola a la que pertenece el proceso
     * @param priority prioridad dentro de su cola
     */
    public Process(String label, int burstTime, int arrivalTime, int queue, int priority) {
        this.label = label;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.queue = queue;
        this.priority = priority;
        this.remaingBurstTime = burstTime;
    }

    // GETTERS Y SETTERS

    public String getLabel() {
        return label;
    }
    public int getBurstTime() {
        return burstTime;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getQueue() {
        return queue;
    }
    public int getPriority() {
        return priority;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public int getCompletionTime() {
        return completionTime;
    }
    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }
    public int getResponseTime() {
        return responseTime;
    }
    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getRemaingBurstTime() {
        return remaingBurstTime;
    }

    public void setRemaingBurstTime(int remaingBurstTime) {
        this.remaingBurstTime = remaingBurstTime;
    }

    public int getStartTime() {
        return startTime;
    }
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    // ------------------------------------------------------


    /**
     * Metodo para calcular el turnaround time del proceso
     */
    public void calculateTurnaroundTime() {
        // TAT = CT - AT
        this.turnaroundTime = this.completionTime - this.arrivalTime;
    }
    /**
     * Metodo para calcular el waiting time del proceso
     */
    public void calculateWaitingTime() {
        // WT = TAT - OriginalBT
        this.waitingTime = this.turnaroundTime - this.burstTime;
    }
    /**
     * Metodo para calcular él response time del proceso
     */
    public void calculateResponseTime() {
        // RT = StartTime - AT
        this.responseTime = this.startTime - this.arrivalTime;
    }

    /**
     * Metodo que devuelve un String para el archivo de Salida. Siguiendo la estructura
     * requerida de los procesos: # etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT
     */
    @Override
    public String toString() {
        return label + ";" + burstTime + ";" + arrivalTime + ";" + queue + ";" + priority + ";" +
                waitingTime + ";" + completionTime + ";" + responseTime + ";" + turnaroundTime;
    }




}
