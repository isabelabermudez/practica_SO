package model.FileLoader;

import model.Process;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
    /**
     * Clase que genera un archivo de salida con los resultados de la simulación MLQ
     */
    public class ProcessFileWriter {
        /**
         * Metodo encargado de escribir el archivo de texto con los resultados de la simulación.
         * @param processes lista de procesos con métricas ya calculadas
         * @param path  ruta del archivo de salida
         */
        public static void writeResultsToFile(List<Process> processes, String path) {
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {

                bw.write("#archivo: " + path + "\n");
                bw.write("# etiqueta; BT; AT; Q; Pr; WT; CT; RT; TAT\n");
                //iniciar las variables para los promedios de las metricas

                double totalWT = 0, totalCT = 0, totalRT = 0, totalTAT = 0;

                for(Process p : processes) {
                    bw.write(p.toString());
                    bw.newLine();
                    // Acumular métricas para promedio
                    totalWT += p.getWaitingTime();
                    totalCT += p.getCompletionTime();
                    totalRT += p.getResponseTime();
                    totalTAT += p.getTurnaroundTime();
                }

                //calcular el promedio
                int n = processes.size();
                double avgWT = totalWT / n;
                double avgCT = totalCT / n;
                double avgRT = totalRT / n;
                double avgTAT = totalTAT / n;

                // Escribir línea final con los promedios
                bw.newLine();
                bw.write(String.format("WT=%.1f; CT=%.1f; RT=%.1f; TAT=%.1f;", avgWT, avgCT, avgRT, avgTAT));
                bw.newLine();

                System.out.println("Resultados guardados en: " + path);

            }catch(IOException e) {
                e.printStackTrace();
                System.out.println("Error al escribir el archivo de salida: " + path);
            }
        }
    }
