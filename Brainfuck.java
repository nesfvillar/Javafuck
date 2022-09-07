import java.io.Console;
import java.io.IOException;
import java.util.Map;

public class Brainfuck {
    private byte[] dataArray = new byte[30_000];
    private char[] instructionsArray;
    private int dataPointer;
    private int instructionsPointer;

    public Brainfuck() {
    }

    public Brainfuck(char[] inInstructions) {
        loadInstructions(inInstructions);
    }

    public void loadInstructions(char[] inInstructions) {
        instructionsArray = inInstructions;
    }

    public boolean hasMoreInstructions() {
        return instructionsPointer < instructionsArray.length;
    }

    public void doNextInstruction() {
        char instruction = instructionsArray[instructionsPointer];
        BrainfuckOperator op = operations.get(instruction);
        if (op != null) {
            op.run();
        }
        ++instructionsPointer;
    }

    private interface BrainfuckOperator {
        public void run();
    }

    private final Map<Character, BrainfuckOperator> operations = Map.ofEntries(
            Map.entry('>', new BrainfuckOperator() {
                public void run() {
                    incrementDataPointer();
                }
            }),
            Map.entry('<', new BrainfuckOperator() {
                public void run() {
                    decrementDataPointer();
                }
            }),
            Map.entry('+', new BrainfuckOperator() {
                public void run() {
                    incrementDataValue();
                }
            }),
            Map.entry('-', new BrainfuckOperator() {
                public void run() {
                    decrementDataValue();
                }
            }),
            Map.entry('.', new BrainfuckOperator() {
                public void run() {
                    outputData();
                }
            }),
            Map.entry(',', new BrainfuckOperator() {
                public void run() {
                    try {
                        inputData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }),
            Map.entry('[', new BrainfuckOperator() {
                public void run() {
                    forwardConditionalJump();
                }
            }),
            Map.entry(']', new BrainfuckOperator() {
                public void run() {
                    backwardConditionalJump();
                }
            }));

    // > Increment the data pointer (to point to the next cell to the right).
    private void incrementDataPointer() {
        ++dataPointer;
    }

    // < Decrement the data pointer (to point to the next cell to the left).
    private void decrementDataPointer() {
        --dataPointer;
    }

    // + Increment (increase by one) the byte at the data pointer.
    private void incrementDataValue() {
        ++dataArray[dataPointer];
    }

    // - Decrement (decrease by one) the byte at the data pointer.
    private void decrementDataValue() {
        --dataArray[dataPointer];
    }

    // . Output the byte at the data pointer.
    private void outputData() {
        byte output = dataArray[dataPointer];
        System.out.print((char) output);
    }

    // , Accept one byte of input, storing its value in the byte at the data
    // pointer.
    private void inputData() throws IOException {
        Console con = System.console();
        if (con == null)
            throw new IOException("No input console available");

        String input = con.readLine();
        dataArray[dataPointer] = Byte.parseByte(input);

    }

    // [ If the byte at the data pointer is zero, then instead of moving the
    // instruction pointer forward to the next command, jump it forward to the
    // command after the matching ] command.
    private void forwardConditionalJump() {
        if (dataArray[dataPointer] != 0)
            return;

        while (instructionsArray[instructionsPointer] != ']') {
            ++instructionsPointer;
        }
    }

    // ] If the byte at the data pointer is nonzero, then instead of moving the
    // instruction pointer forward to the next command, jump it back to the command
    // after the matching [ command.
    private void backwardConditionalJump() {
        if (dataArray[dataPointer] == 0)
            return;

        while (instructionsArray[instructionsPointer] != '[') {
            --instructionsPointer;
        }
    }
}
