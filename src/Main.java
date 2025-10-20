import model.Process;
import model.FileLoader.ProcessReader;
import model.FileLoader.ProcessFileWriter;
import model.MLQscheduler;
import model.QueueLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase main que inicializa y ejeucuta el simulador de planificacion MLQ
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Simulador de Planificación MLQ ===");
        System.out.print("Ingrese el número del archivo (1-5): ");
        int fileNumber = scanner.nextInt();

        String inputPath = "resources/input/mlq00" + fileNumber + ".txt";
        String outputPath = "resources/output/mlq00" + fileNumber + ".txt";

        //creacion de las colas
        List<QueueLevel> queues = new ArrayList<>();
        queues.add(new QueueLevel(1, "RR", 3));    // Cola 1: RR con quantum 3
        queues.add(new QueueLevel(2, "RR", 5));    // Cola 2: RR con quantum 5
        queues.add(new QueueLevel(3, "FCFS", 0));  // Cola 3: FCFS

        //creación de los procesos
        List<Process> processes = ProcessReader.readProcessesFromFile(inputPath);
        if (processes.isEmpty()) {
            System.out.println("No se encontraron procesos en el archivo: " + inputPath);
            return;
        }

        //inicializacion del MLQ scheduler
        MLQscheduler scheduler = new MLQscheduler(queues);
        scheduler.assignProcesses(processes); //asignacion de los procesos a las colas
        scheduler.execute(); //ejecucion de la simuulación

        //creación del archivo de salida
        ProcessFileWriter.writeResultsToFile(scheduler.getFinishedProcesses(), outputPath);

        System.out.println(" Simulación completada. Revisa el archivo de salida: " + outputPath);
    }
}

