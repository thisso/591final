import java.util.Scanner;

public class BattleshipGame {
    public static void main(String[] args) {
        // Create an instance of the Ocean class
        Ocean ocean = new Ocean();
        Scanner scanner = new Scanner(System.in);

        // Place all ships randomly
        ocean.placeAllShipsRandomly();
        System.out.println("Welcome to Battleship!");

        // Game loop
        while (!ocean.isGameOver()) {
            ocean.print(); // Display the board to the player
            System.out.println("Shots fired: " + ocean.getShotsFired());
            System.out.println("Ships sunk: " + ocean.getShipsSunk());

            // Prompt the user for input
            System.out.print("Enter row (0-9): ");
            int row = scanner.nextInt();
            System.out.print("Enter column (0-9): ");
            int column = scanner.nextInt();

            // Check if the shot is valid
            if (row < 0 || row > 9 || column < 0 || column > 9) {
                System.out.println("Invalid coordinates. Please try again.");
                continue;
            }

            // Shoot at the given coordinates
            boolean hit = ocean.shootAt(row, column);
            if (hit) {
                System.out.println("You hit a ship!");
            } else {
                System.out.println("You missed.");
            }

            // Check if a ship was sunk
            if (ocean.getShipsSunk() > 0) {
                System.out.println("A ship has been sunk!");
            }
        }

        // End of game
        System.out.println("Congratulations! You've sunk all the ships!");
        System.out.println("Total shots fired: " + ocean.getShotsFired());
        scanner.close();
    }
}
