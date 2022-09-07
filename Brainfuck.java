import java.io.Console;
import java.io.IOException;

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

    public void doNextInstruction() throws IOException {
        char instruction = instructionsArray[instructionsPointer];
        switch (instruction) {
            case '>':
                incrementDataPointer();
                break;
            case '<':
                decrementDataPointer();
                break;
            case '+':
                incrementDataValue();
                break;
            case '-':
                decrementDataValue();
                break;
            case '.':
                outputData();
                break;
            case ',':
                inputData();
                break;
            case '[':
                forwardConditionalJump();
                break;
            case ']':
                backwardConditionalJump();
                break;
            default:
                break;
        }
        ++instructionsPointer;
    }

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