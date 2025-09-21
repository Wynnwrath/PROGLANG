// Seth A. Pinca
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SimpletronLoader {

    public int size = 100;
    String[] mem;
    
    public SimpletronLoader(String[] value){
        mem = new String[size];
         for(int i = 0; i < value.length; i++)
         {
                mem[i] = value[i];
         }
    }
      
    public SimpletronLoader(){
      mem = new String[size];
    }
    
    public void dump() {
    System.out.print(" "); 
    for (int i = 0; i < 10; i++) {
        System.out.printf("%11d", i);
    }
    System.out.println();
    
    for (int row = 0; row < size / 10; row++) {
        System.out.printf("%02d    ", row * 10);
        for (int col = 0; col < 10; col++) {
            int index = row * 10 + col;
            if (mem[index] == null) {
                mem[index] = "0000";
            }
            System.out.printf(" +%2s     ", mem[index]);
        }
        System.out.println();
      }
    }
    public void additem(int address, String data) {
       mem[address] = data;
    }
    
    public String getitem(int address){
      return mem[address];  
    }
    
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
                  
                  additem(location, data);
               }
            }
        } catch(Exception e){
            System.err.println("Couldn't read the file");
        }
    }
    
    public static void main(String[] args) {
            String filePath = "test.txt"; 
            
            SimpletronLoader core = new SimpletronLoader();
            
            core.memoryLoader(filePath);
            core.dump();
     }
}
