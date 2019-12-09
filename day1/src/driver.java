import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class driver {
    public static void main(String[] args) {
        int totalFuelRequired = 0;
        int totalFuelRequiredRecursive = 0;

        // get filepath for the puzzle input
        System.out.print("Please provide the filepath of the text document containing the puzzle input: ");
        String filePath = new Scanner(System.in).nextLine();
        try {
            for (String line : Files.readAllLines(Paths.get(filePath))) {
                int mass = Integer.parseInt(line);
                // PART 1
                totalFuelRequired += calculateFuelRequired(mass);

                // PART 2
                totalFuelRequiredRecursive += calculateFuelRequiredRecursive(mass);
            }

            System.out.println("The solution to part 1 is: " + totalFuelRequired);
            System.out.println("The solution to part 2 is: " + totalFuelRequiredRecursive);
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
        }
    }

    private static int calculateFuelRequired(int mass) {
        return (mass / 3) - 2;
    }

    private static int calculateFuelRequiredRecursive(int mass) {
        int fuelRequired = (mass / 3) - 2;

        if (fuelRequired > 0) {
            return fuelRequired + calculateFuelRequiredRecursive(fuelRequired);
        }

        return 0;
    }
}
