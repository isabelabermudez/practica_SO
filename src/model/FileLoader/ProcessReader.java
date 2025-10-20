package model.FileLoader;
import model.Process;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase utlitaria encargada de leer archivos de texto que contienen los procesos definidos y crear una
 * lista de objetos Procesos
 */
public class ProcessReader {
    /**
     * Lee un archivo de texto y devuelve la lista de procesos.
     * @param filePatch ruta del archivo .txt
     * @return lista de objetos Process
     */
    public static List<Process> readProcessesFromFile(String filePatch) {
        List<Process> processes = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePatch))){
            String line;

            while((line = reader.readLine()) != null){
                line = line.trim();
                //ignorar las lineas vacias o que comienzan por #
                if(line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                //Separar los campos por el punto y coma
                String[] parts = line.split(";");

                if(parts.length < 5) {
                    System.out.println("Linea con formato incorrecto, saltando: " + line);
                    continue;
                }
                //Extraer y limpiar los valores
                String label = parts[0].trim();
                int burstTime = Integer.parseInt(parts[1].trim());
                int arrivalTime = Integer.parseInt(parts[2].trim());
                int queue = Integer.parseInt(parts[3].trim());
                int priority = Integer.parseInt(parts[4].trim());

                //crear los procesos y añadirlos a la lista
                Process process = new Process(label, burstTime, arrivalTime, queue, priority) {
                };
                processes.add(process);

            }
        }catch (IOException e){
            System.out.println("Error al leer el archivo" + e.getMessage());
        }
        //devolver los procesos creados
        return processes;
    }
}
