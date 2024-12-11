import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Ocean class, ensuring that the functionality
 * and state management of the game are implemented correctly.
 */
class OceanTest {

    private Ocean ocean; // Instance of the Ocean class to be tested.

    /**
     * Set up a fresh instance of the Ocean class before each test.
     * Ensures each test is independent and doesn't rely on the state of another test.
     */
    @BeforeEach
    public void setUp() {
        ocean = new Ocean(); // Initialize a new Ocean object.
    }

    /**
     * Test to validate the initial state of the Ocean.
     * Ensures that the board is initialized correctly with EmptySea objects,
     * and the game state variables are set to their default values.
     */
    @Test
    public void testInitialization() {
        Ship[][] ships = ocean.getShipArray(); // Get the grid of ships.

        // Check that every cell is initialized to an EmptySea object.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(ships[i][j] instanceof EmptySea,
                        "Initial grid should contain only EmptySea objects.");
            }
        }

        // Validate initial game state variables.
        assertEquals(0, ocean.getShotsFired(), "Shots fired should be initialized to 0.");
        assertEquals(0, ocean.getHitCount(), "Hit count should be initialized to 0.");
        assertEquals(0, ocean.getShipsSunk(), "Ships sunk should be initialized to 0.");
    }

    /**
     * Test the placement of all ships randomly on the board.
     * Ensures that 20 cells are occupied (sum of ship lengths).
     */
    @Test
    public void testPlaceAllShipsRandomly() {
        ocean.placeAllShipsRandomly(); // Place all ships.

        int shipCells = 0; // Counter for occupied cells.

        // Count the cells occupied by ships (not EmptySea).
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!(ocean.getShipArray()[i][j] instanceof EmptySea)) {
                    shipCells++;
                }
            }
        }

        // Assert that exactly 20 cells are occupied by ships.
        assertEquals(20, shipCells, "Total ship cells should be 20 after placing all ships.");
    }

    /**
     * Test the `isOccupied` method to ensure it accurately identifies
     * occupied cells after placing ships randomly.
     */
    @Test
    public void testIsOccupied() {
        ocean.placeAllShipsRandomly(); // Place ships on the board.

        boolean foundOccupied = false; // Flag to detect at least one occupied cell.

        // Check if there is at least one occupied cell.
        for (int i = 0; i < 10 && !foundOccupied; i++) {
            for (int j = 0; j < 10; j++) {
                if (ocean.isOccupied(i, j)) {
                    foundOccupied = true; // Set flag if an occupied cell is found.
                    break;
                }
            }
        }

        assertTrue(foundOccupied,
                "After placing ships randomly, at least one cell should be occupied.");
    }

    /**
     * Test firing a shot at an EmptySea location.
     * Validates that the shot is counted but does not increment hit count.
     */
    @Test
    public void testShootAtEmptySea() {
        // Fire a shot at an EmptySea cell.
        assertFalse(ocean.shootAt(0, 0), "Shooting at EmptySea should return false.");
        assertEquals(1, ocean.getShotsFired(), "Shots fired should increment after firing.");
        assertEquals(0, ocean.getHitCount(),
                "Hit count should not increment when shooting at EmptySea.");
    }

    /**
     * Test firing shots at a ship, sinking it, and updating the game state.
     * Validates that hit count, shots fired, and ships sunk are updated correctly.
     */
    @Test
    public void testShootAtShip() {
        // Place a battleship on the board.
        Ship battleship = new Battleship();
        battleship.placeShipAt(2, 2, true, ocean); // Place horizontally.

        // Fire a shot at the battleship.
        assertTrue(ocean.shootAt(2, 2), "Shooting at a ship should return true.");
        assertEquals(1, ocean.getHitCount(), "Hit count should increment after hitting a ship.");
        assertEquals(1, ocean.getShotsFired(), "Shots fired should increment after firing.");
        assertFalse(battleship.isSunk(), "Ship should not be sunk after one hit.");

        // Hit all parts of the ship.
        ocean.shootAt(2, 3);
        ocean.shootAt(2, 4);
        ocean.shootAt(2, 5);

        // Validate the ship is sunk.
        assertTrue(battleship.isSunk(), "Ship should be sunk after all parts are hit.");
        assertEquals(1, ocean.getShipsSunk(), "Ships sunk should increment when a ship is sunk.");
    }

    /**
     * Test the `isGameOver` method by simulating the sinking of all ships.
     * Ensures the game ends after all ships are sunk.
     */
    @Test
    public void testGameOver() {
        ocean.placeAllShipsRandomly(); // Place ships randomly.

        // Simulate sinking all ships.
        for (Ship[] row : ocean.getShipArray()) {
            for (Ship ship : row) {
                if (!(ship instanceof EmptySea)) {
                    for (int i = 0; i < ship.getLength(); i++) {
                        if (ship.isHorizontal()) {
                            ocean.shootAt(ship.getBowRow(), ship.getBowColumn() + i);
                        } else {
                            ocean.shootAt(ship.getBowRow() + i, ship.getBowColumn());
                        }
                    }
                }
            }
        }

        // Assert that the game is over after all ships are sunk.
        assertTrue(ocean.isGameOver(), "Game should be over after all ships are sunk.");
    }

    /**
     * Test the `print` method to ensure it executes without errors.
     * This test validates the method's functionality but does not check the output.
     */
    @Test
    public void testPrint() {
        ocean.placeAllShipsRandomly(); // Place ships on the board.

        // Assert that the print method does not throw exceptions.
        assertDoesNotThrow(() -> ocean.print(),
                "Printing the ocean should not throw any exceptions.");
    }
}
