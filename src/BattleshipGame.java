import java.util.Scanner;

/**
 * The BattleshipGame class serves as the entry point for the Battleship game.
 * It manages the game loop, player interaction, and the game's overall state.
 * Players aim to sink all ships by firing shots at a 10x10 game board.
 */
public class BattleshipGame {

    /**
     * The main method initializes the game, handles the game loop, and displays the results.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        // Create an instance of the Ocean class to manage the game state.
        Ocean ocean = new Ocean();
        Scanner scanner = new Scanner(System.in);

        // Randomly place all ships on the game board.
        ocean.placeAllShipsRandomly();
        System.out.println("Welcome to Battleship!");

        // Main game loop: continues until all ships are sunk.
        while (!ocean.isGameOver()) {
            // Display the current state of the game board to the player.
            ocean.print();
            System.out.println("Shots fired: " + ocean.getShotsFired());
            System.out.println("Ships sunk: " + ocean.getShipsSunk());

            // Prompt the player to enter the row coordinate.
            System.out.print("Enter row (0-9): ");
            int row = scanner.nextInt();

            // Prompt the player to enter the column coordinate.
            System.out.print("Enter column (0-9): ");
            int column = scanner.nextInt();

            // Validate the player's input.
            if (row < 0 || row > 9 || column < 0 || column > 9) {
                System.out.println("Invalid coordinates. Please try again.");
                continue; // Skip to the next iteration of the game loop.
            }

            // Attempt to shoot at the specified coordinates.
            boolean hit = ocean.shootAt(row, column);
            if (hit) {
                System.out.println("You hit a ship!");
            } else {
                System.out.println("You missed.");
            }

            // Notify the player if a ship was sunk after their shot.
            if (ocean.getShipsSunk() > 0) {
                System.out.println("A ship has been sunk!");
            }
        }

        // Game ends when all ships are sunk.
        System.out.println("Congratulations! You've sunk all the ships!");
        System.out.println("Total shots fired: " + ocean.getShotsFired());

        // Close the scanner resource.
        scanner.close();
    }
}
