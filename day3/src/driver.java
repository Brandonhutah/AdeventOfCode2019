import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class driver {
    public static void main(String[] args) {
        List<HashSet<AbstractMap.SimpleEntry<Integer, Integer>>> wiresCoordinates = new ArrayList<>();

        System.out.print("Please provide the filepath of the text document containing the puzzle input: ");
        String filePath = new Scanner(System.in).nextLine();

        try {
            Files.readAllLines(Paths.get(filePath)).forEach(line -> {
                // add every coordinate for a wire into a hash set
                HashSet<AbstractMap.SimpleEntry<Integer, Integer>> coordinates = new HashSet<>();

                AbstractMap.SimpleEntry<Integer, Integer> currentPosition = new AbstractMap.SimpleEntry<>(0, 0);

                for (String movement : line.split(",")) {
                    AbstractMap.SimpleEntry<Integer, Integer> newPosition = calculateNewPosition(currentPosition, movement);

                    coordinates.addAll(calculatePointsInBetween(currentPosition, newPosition));
                }

                // add all the wires coordinates into a list with all other wires coordinates
                wiresCoordinates.add(coordinates);
            });

            // PART 1
            // loop through all provided wires and only retain common coordinates in the first set of coordinates in the list
            wiresCoordinates.forEach(coordinates -> {
                wiresCoordinates.get(0).retainAll(coordinates);
            });

            // first 'wire' in wires list now only contains coordinates shared with other wires. Calculate shortest manhattan distance from 0, 0'
            int shortestDistance = -1;
            for (var coordinate : wiresCoordinates.get(0)) {
                int distance = Math.abs(coordinate.getKey() - 0) + Math.abs(coordinate.getValue() - 0);
                if (shortestDistance < 0 || distance < shortestDistance) {
                    shortestDistance = distance;
                }
            }


            System.out.println("The solution to part 1 is: " + shortestDistance);
        } catch (Exception ex) {
            System.out.println("An error occurred attempting to read your input file.");
            System.out.println(ex);
        }
    }

    private static AbstractMap.SimpleEntry<Integer, Integer> calculateNewPosition(AbstractMap.SimpleEntry<Integer, Integer> currentPosition, String movement) {
        char movementDirection = movement.charAt(0);
        int movementAmount = Integer.parseInt(movement.substring(1));

        switch (movementDirection) {
            case 'U':
                return new AbstractMap.SimpleEntry<>(currentPosition.getKey(), currentPosition.getValue() + movementAmount);
            case 'R':
                return new AbstractMap.SimpleEntry<>(currentPosition.getKey() + movementAmount, currentPosition.getValue());
            case 'L':
                return new AbstractMap.SimpleEntry<>(currentPosition.getKey() - movementAmount, currentPosition.getValue());
            case 'D':
                return new AbstractMap.SimpleEntry<>(currentPosition.getKey(), currentPosition.getValue() - movementAmount);
        }

        return currentPosition;
    }

    private static HashSet<AbstractMap.SimpleEntry<Integer, Integer>> calculatePointsInBetween(AbstractMap.SimpleEntry<Integer, Integer> startPoint, AbstractMap.SimpleEntry<Integer, Integer> endPoint) {
        return null;
    }
}
