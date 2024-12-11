
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

    private Ocean ocean;

    @BeforeEach
    public void setUp() {
        ocean = new Ocean();
    }

    @Test
    public void testInitialization() {
        // Ensure the ocean is initialized with EmptySea objects
        Ship[][] ships = ocean.getShipArray();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertTrue(ships[i][j] instanceof EmptySea, "Initial grid should contain only EmptySea objects.");
            }
        }

        // Check initial state variables
        assertEquals(0, ocean.getShotsFired(), "Shots fired should be initialized to 0.");
        assertEquals(0, ocean.getHitCount(), "Hit count should be initialized to 0.");
        assertEquals(0, ocean.getShipsSunk(), "Ships sunk should be initialized to 0.");
    }

    @Test
    public void testPlaceAllShipsRandomly() {
        ocean.placeAllShipsRandomly();

        int shipCells = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (!(ocean.getShipArray()[i][j] instanceof EmptySea)) {
                    shipCells++;
                }
            }
        }

        // Validate that 20 cells (sum of ship lengths) are occupied
        assertEquals(20, shipCells, "Total ship cells should be 20 after placing all ships.");
    }

    @Test
    public void testIsOccupied() {
        ocean.placeAllShipsRandomly();

        boolean foundOccupied = false;
        for (int i = 0; i < 10 && !foundOccupied; i++) {
            for (int j = 0; j < 10; j++) {
                if (ocean.isOccupied(i, j)) {
                    foundOccupied = true;
                    break;
                }
            }
        }

        assertTrue(foundOccupied, "After placing ships randomly, at least one cell should be occupied.");
    }

    @Test
    public void testShootAtEmptySea() {
        // Ensure firing at EmptySea increments shots fired and returns false
        assertFalse(ocean.shootAt(0, 0), "Shooting at EmptySea should return false.");
        assertEquals(1, ocean.getShotsFired(), "Shots fired should increment after firing.");
        assertEquals(0, ocean.getHitCount(), "Hit count should not increment when shooting at EmptySea.");
    }

    @Test
    public void testShootAtShip() {
        Ship battleship = new Battleship();
        battleship.placeShipAt(2, 2, true, ocean);

        assertTrue(ocean.shootAt(2, 2), "Shooting at a ship should return true.");
        assertEquals(1, ocean.getHitCount(), "Hit count should increment after hitting a ship.");
        assertEquals(1, ocean.getShotsFired(), "Shots fired should increment after firing.");
        assertFalse(battleship.isSunk(), "Ship should not be sunk after one hit.");

        // Hit all parts of the ship
        ocean.shootAt(2, 3);
        ocean.shootAt(2, 4);
        ocean.shootAt(2, 5);
        assertTrue(battleship.isSunk(), "Ship should be sunk after all parts are hit.");
        assertEquals(1, ocean.getShipsSunk(), "Ships sunk should increment when a ship is sunk.");
    }

    @Test
    public void testGameOver() {
        // Simulate sinking all ships
        ocean.placeAllShipsRandomly();

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

        assertTrue(ocean.isGameOver(), "Game should be over after all ships are sunk.");
    }

    @Test
    public void testPrint() {
        ocean.placeAllShipsRandomly();

        // Ensure the print method runs without errors
        assertDoesNotThrow(() -> ocean.print(), "Printing the ocean should not throw any exceptions.");
    }
}
