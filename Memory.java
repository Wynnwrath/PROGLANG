public class Memory {
    public int size = 100;
    String[] mem;
    public Memory(String[] value){
        mem = new String[size];
         for(int i = 0; i < value.length; i++)
         {
                mem[i] = value[i];
         }
    }

    public Memory() {
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
}
