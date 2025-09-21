import java.io.BufferedReader;
import java.io.FileReader;

public class MemoryLoader {

    Memory mem = new Memory();

    public void memoryLoader(String filePath){
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            
            while((line = reader.readLine()) != null){
               String[] parts = line.split(";");
               String dataPart = parts[0].trim();
               
               String[] tokens = dataPart.split("\\s+");
               
               if(tokens.length >= 2) {
                  int location = Integer.parseInt(tokens[0]);
                  String data = tokens[1];
                  
                  mem.additem(location, data);
               }
            }
        } catch(Exception e){
            System.err.println("Couldn't read the file");
        }
    }
}
