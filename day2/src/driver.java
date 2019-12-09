import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class driver {
    // puzzle 1 specific constants
    public static final int NOUN_REPLACE_OFFSET = 1;
    public static final int NOUN_REPLACE_VALUE = 12;
    public static final int VERB_REPLACE_OFFSET = 2;
    public static final int VERB_REPLACE_VALUE = 2;
    // puzzle 2 specific constants
    public static final int PART_2_EXPECTED_SOLUTION = 19690720;
    public static final int PART_2_LOWER_BOUND = 0;
    public static final int PART_2_UPPER_BOUND = 99;
    // universal constants
    public static final int ADD_OPCODE = 1;
    public static final int MULTIPLY_OPCODE = 2;
    public static final int NOUN_OFFSET = 1;
    public static final int VERB_OFFSET = 2;
    public static final int RESULT_OFFSET = 3;
    public static final int NEXT_INSTRUCTION_INCREMENT = 4;
    public static final int FINISHED_OPCODE = 99;

    public static void main(String[] args) {
        List<Integer> puzzleInput = new ArrayList<>();
        int part1Solution = 0;
        int part2NounSolution = 0;
        int part2VerbSolution = 0;

        // get filepath for the puzzle input
        System.out.print("Please provide the filepath of the text document containing the puzzle input: ");
        String filePath = new Scanner(System.in).nextLine();
        try {
            Files.readAllLines(Paths.get(filePath)).forEach(line -> {
                for (String val : line.split(",")) {
                    puzzleInput.add(Integer.parseInt(val));
                }
            });

            // PART 1
            // Init memory to puzzle 1 instructions
            List<Integer> part1PuzzleInput = new ArrayList<>(puzzleInput);
            part1PuzzleInput.set(NOUN_REPLACE_OFFSET, NOUN_REPLACE_VALUE);
            part1PuzzleInput.set(VERB_REPLACE_OFFSET, VERB_REPLACE_VALUE);

            for (int i = 0; i < part1PuzzleInput.size(); ) {
                if (part1PuzzleInput.get(i) == FINISHED_OPCODE) {
                    break;
                }

                part1PuzzleInput.set(part1PuzzleInput.get(i + RESULT_OFFSET), performOperation(part1PuzzleInput.get(i), part1PuzzleInput.get(i + NOUN_OFFSET), part1PuzzleInput.get(i + VERB_OFFSET), part1PuzzleInput));

                // update puzzle instruction position
                i += NEXT_INSTRUCTION_INCREMENT;
            }

            part1Solution = part1PuzzleInput.get(0);

            // PART 2
            outerLoop:
            for (int noun_index = PART_2_LOWER_BOUND; noun_index <= PART_2_UPPER_BOUND; noun_index++) {
                for (int verb_index = PART_2_LOWER_BOUND; verb_index <= PART_2_UPPER_BOUND; verb_index++) {
                    // Init memory to puzzle 1 instructions
                    List<Integer> part2PuzzleInput = new ArrayList<>(puzzleInput);
                    part2PuzzleInput.set(NOUN_REPLACE_OFFSET, noun_index);
                    part2PuzzleInput.set(VERB_REPLACE_OFFSET, verb_index);

                    for (int i = 0; i < part2PuzzleInput.size(); ) {
                        if (part2PuzzleInput.get(i) == FINISHED_OPCODE) {
                            break;
                        }

                        part2PuzzleInput.set(part2PuzzleInput.get(i + RESULT_OFFSET), performOperation(part2PuzzleInput.get(i), part2PuzzleInput.get(i + NOUN_OFFSET), part2PuzzleInput.get(i + VERB_OFFSET), part2PuzzleInput));

                        // update puzzle instruction position
                        i += NEXT_INSTRUCTION_INCREMENT;
                    }

                    if (part2PuzzleInput.get(0) == PART_2_EXPECTED_SOLUTION) {
                        part2NounSolution = noun_index;
                        part2VerbSolution = verb_index;

                        break outerLoop;
                    }
                }
            }

            // Print puzzle answers
            System.out.println("The solution to part 1 is: " + part1Solution);
            System.out.println("The solution to part 2 is: " + part2NounSolution + part2VerbSolution);
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
            System.out.println(ex);
        }
    }

    private static int performOperation(int instruction, int noun, int verb, List<Integer> input) {
        switch (instruction) {
            case ADD_OPCODE:
                return input.get(noun) + input.get(verb);
            case MULTIPLY_OPCODE:
                return input.get(noun) * input.get(verb);
        }

        return 0;
    }
}
