import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Ship} and its subclasses.
 * These tests validate the behavior and functionality of different ship types,
 * including placement, shooting, and sinking logic.
 */
public class ShipTest {

    private Ocean ocean; // Instance of the Ocean class for testing.
    private Ship ship; // A generic Ship instance for general tests.
    private EmptySea emptySea; // Instance of EmptySea for specific tests.
    private Battleship battleship; // Instance of Battleship for specific tests.

    /**
     * Sets up the test environment before each test method.
     * Initializes the Ocean, EmptySea, and Battleship objects.
     */
    @BeforeEach
    public void setUp() {
        ocean = new Ocean();
        ship = new Battleship(); // Use Battleship as a concrete implementation of Ship.
        emptySea = new EmptySea();
        battleship = new Battleship();
    }

    /**
     * Test the placement of a ship on the Ocean.
     * Ensures that the ship is correctly placed and occupies the expected cells.
     */
    @Test
    public void testPlaceShipAt() {
        // Place the ship horizontally.
        ship.placeShipAt(3, 4, true, ocean);
        assertEquals(3, ship.getBowRow(), "The bow row should be set correctly.");
        assertEquals(4, ship.getBowColumn(), "The bow column should be set correctly.");
        assertTrue(ship.isHorizontal(), "The ship should be horizontal.");

        // Ensure the ship occupies the correct cells in the Ocean.
        for (int i = 4; i < 8; i++) {
            assertEquals(ship, ocean.getShipArray()[3][i], "The ship should occupy the correct cells.");
        }
    }

    /**
     * Test the {@code okToPlaceShipAt} method to validate the placement logic.
     * Ensures that ships cannot overlap or go out of bounds.
     */
    @Test
    public void testOkToPlaceShipAt() {
        // Valid placement.
        assertTrue(ship.okToPlaceShipAt(5, 5, true, ocean),
                "The ship should be placeable at an empty location.");

        // Invalid placement (out of bounds).
        assertFalse(ship.okToPlaceShipAt(9, 8, true, ocean),
                "The ship should not be placeable if it goes out of bounds.");

        // Invalid placement (overlapping another ship).
        ship.placeShipAt(5, 5, true, ocean);
        assertFalse(ship.okToPlaceShipAt(5, 6, true, ocean),
                "The ship should not overlap with another ship.");
    }

    /**
     * Test firing at a ship to ensure hits are registered correctly.
     */
    @Test
    public void testShootAt() {
        // Place the ship and shoot at it.
        ship.placeShipAt(2, 2, true, ocean);

        // Hit the ship.
        assertTrue(ship.shootAt(2, 2), "Shooting at the ship should return true.");
        assertTrue(ship.isHit(2, 2), "The ship should register a hit at the correct location.");

        // Miss the ship.
        assertFalse(ship.shootAt(3, 3), "Shooting outside the ship should return false.");
        assertFalse(ship.isHit(3, 3), "The ship should not register a hit outside its location.");
    }

    /**
     * Test the sinking logic of a ship.
     * Ensures that a ship is sunk only when all its parts are hit.
     */
    @Test
    public void testIsSunk() {
        // Place a Destroyer (length 2) and shoot at it.
        ship = new Destroyer();
        ship.placeShipAt(4, 4, true, ocean);

        // Not sunk after one hit.
        ship.shootAt(4, 4);
        assertFalse(ship.isSunk(), "The ship should not be sunk after one hit.");

        // Sunk after all parts are hit.
        ship.shootAt(4, 5);
        assertTrue(ship.isSunk(), "The ship should be sunk after all parts are hit.");
    }

    /**
     * Test the {@code isHit} method to validate hit registration at specific locations.
     */
    @Test
    public void testIsHit() {
        // Place the ship vertically and shoot at specific locations.
        ship.placeShipAt(1, 1, false, ocean);

        // Ensure no hits initially.
        for (int i = 1; i < 5; i++) {
            assertFalse(ship.isHit(i, 1), "No hits should be registered before shooting.");
        }

        // Register hits.
        ship.shootAt(1, 1);
        ship.shootAt(2, 1);
        assertTrue(ship.isHit(1, 1), "The ship should register a hit at the correct location.");
        assertTrue(ship.isHit(2, 1), "The ship should register a hit at the correct location.");
        assertFalse(ship.isHit(3, 1), "No hit should be registered at unshot locations.");
    }

    /**
     * Test the {@code getShipType} method for various ship types.
     * Ensures that the correct type is returned for each ship subclass.
     */
    @Test
    public void testShipType() {
        // Test the type of different ships.
        assertEquals("Battleship", ship.getShipType(), "The ship type should be Battleship.");

        ship = new Cruiser();
        assertEquals("Cruiser", ship.getShipType(), "The ship type should be Cruiser.");

        ship = new Destroyer();
        assertEquals("Destroyer", ship.getShipType(), "The ship type should be Destroyer.");

        ship = new Submarine();
        assertEquals("Submarine", ship.getShipType(), "The ship type should be Submarine.");
    }

    /**
     * Test the behavior of an {@link EmptySea}.
     * Ensures that it behaves as expected, including registering shots.
     */
    @Test
    public void testEmptySeaBehavior() {
        // Test initialization of EmptySea.
        assertEquals(1, emptySea.getLength(), "EmptySea should have a length of 1.");
        assertEquals("EmptySea", emptySea.getShipType(), "EmptySea should return the correct type.");
        assertFalse(emptySea.isSunk(), "EmptySea should never be sunk.");

        // Test shooting at EmptySea.
        assertFalse(emptySea.shootAt(0, 0), "Shooting at EmptySea should return false.");
        assertTrue(emptySea.isFiredUpon(), "EmptySea should register as fired upon after a shot.");
        assertFalse(emptySea.isSunk(), "EmptySea should not be sunk, even after being fired upon.");
    }
}
