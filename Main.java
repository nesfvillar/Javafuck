import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        for (String instructionFilePath : args) {
            char[] instructions = new char[1_000];
            FileReader reader = new FileReader(new File(instructionFilePath));

            reader.read(instructions);
            reader.close();

            Brainfuck brainfuck = new Brainfuck(instructions);

            while (brainfuck.hasMoreInstructions())
                brainfuck.doNextInstruction();
        }
    }
}
