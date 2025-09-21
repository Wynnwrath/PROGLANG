import java.util.Scanner;

public class Simpletron {
    private Memory memory;
    private int accumulator;
    private int programCounter;
    private boolean running;
    private Scanner scanner;

    public Simpletron(Memory memory) {
        this.memory = memory;
        this.accumulator = 0;
        this.running = true;
        this.scanner = new Scanner(System.in);

        // Start at first non-null instruction
        for (int i = 0; i < memory.size; i++) {
            if (memory.getitem(i) != null) {
                this.programCounter = i;
                break;
            }
        }
    }

    public void run() {
        System.out.println("Simpletron execution begins...\n");

        while (running) {
            // FETCH
            String rawInstruction = memory.getitem(programCounter);
            if (rawInstruction == null) rawInstruction = "0000"; 
            int instruction = Integer.parseInt(rawInstruction.trim());

            int opcode = instruction / 100; // first 2 digits
            int operand = instruction % 100; // last 2 digits

            System.out.printf("PC=%02d | Instruction=%s | Opcode=%02d | Operand=%02d%n",
                              programCounter, rawInstruction, opcode, operand);

            programCounter++; // move forward unless branch changes it

            // EXECUTE
            execute(opcode, operand);
        }

        System.out.println("\nSimpletron execution terminated.");
        dumpRegisters();
        memory.dump();
    }

    private void execute(int opcode, int operand) {
        switch (opcode) {
            case 10: // READ
                System.out.print("Enter a number: ");
                try {
                    int inputVal = scanner.nextInt();
                    memory.additem(operand, String.format("%04d", inputVal));
                } catch (Exception e) {
                    System.out.println("Invalid input! Storing 0 instead.");
                    memory.additem(operand, "0000");
                    scanner.nextLine(); // clear buffer
                }
                break;

            case 11: // WRITE
                String outputStr = memory.getitem(operand);
                System.out.println("Output: " + Integer.parseInt(outputStr));
                break;

            case 20: // LOAD
                accumulator = Integer.parseInt(memory.getitem(operand));
                break;

            case 21: // STORE
                memory.additem(operand, String.format("%04d", accumulator));
                break;

            case 30: // ADD
                accumulator += Integer.parseInt(memory.getitem(operand));
                break;

            case 31: // SUBTRACT
                accumulator -= Integer.parseInt(memory.getitem(operand));
                break;

            case 32: // DIVIDE
                int divisor = Integer.parseInt(memory.getitem(operand));
                if (divisor == 0) {
                    System.out.println("Error: Division by zero!");
                    running = false;
                } else {
                    accumulator /= divisor;
                }
                break;

            case 33: // MODULO
                int modDivisor = Integer.parseInt(memory.getitem(operand));
                if (modDivisor == 0) {
                    System.out.println("Error: Division by zero in MODULO!");
                    running = false;
                } else {
                    accumulator %= modDivisor;
                }
                break;

            case 34: // MULTIPLY
                accumulator *= Integer.parseInt(memory.getitem(operand));
                break;

            case 35: // ADDI - Add immediate operand to accumulator
                int addImmediate = operand; // operand is the actual value
                accumulator += addImmediate;
                break;

            case 36: // SUBI - Subtract immediate operand from accumulator
                int subImmediate = operand;
                accumulator -= subImmediate;
                break;

            case 37: // DIVI - Divide accumulator by immediate operand
                int divImmediate = operand;
                if (divImmediate == 0) {
                    System.out.println("Error: Division by zero in DIVI!");
                    running = false;
                } else {
                    accumulator /= divImmediate;
                }
                break;

            case 38: // MODI - Modulo accumulator by immediate operand
                int modImmediate = operand;
                if (modImmediate == 0) {
                    System.out.println("Error: Division by zero in MODI!");
                    running = false;
                } else {
                    accumulator %= modImmediate;
                }
                break;

            case 39: // MULI - Multiply accumulator by immediate operand
                int mulImmediate = operand;
                accumulator *= mulImmediate;
                break;

            case 40: // BRANCH
                programCounter = operand;
                break;

            case 41: // BRANCHNEG
                if (accumulator < 0) programCounter = operand;
                break;

            case 42: // BRANCHZERO
                if (accumulator == 0) programCounter = operand;
                break;

            case 43: // HALT
                running = false;
                break;

            default:
                System.out.println("Invalid opcode: " + opcode + " at PC=" + (programCounter - 1));
                running = false;
        }
    }

    private void dumpRegisters() {
        System.out.println("\nREGISTERS:");
        System.out.printf("accumulator: %+05d%n", accumulator);
        System.out.printf("programCounter: %02d%n", programCounter);
    }

    public static void main(String[] args) {
        // Load memory from file
        MemoryLoader loader = new MemoryLoader();
        loader.memoryLoader("test.txt"); // your program file
        Memory memory = loader.mem;

        // Run the Simpletron
        Simpletron s = new Simpletron(memory);
        s.run();
    }
}
