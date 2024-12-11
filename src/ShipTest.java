import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShipTest {

    private Ocean ocean;
    private Ship ship;
    private EmptySea emptySea; // Instance of EmptySea for testing
    private Battleship battleship;

    @BeforeEach
    public void setUp() {
        ocean = new Ocean();
        ship = new Battleship(); // Use Battleship as a concrete implementation of Ship
        emptySea = new EmptySea();
        battleship = new Battleship();
    }

    @Test
    public void testPlaceShipAt() {
        // Place the ship horizontally
        ship.placeShipAt(3, 4, true, ocean);
        assertEquals(3, ship.getBowRow(), "The bow row should be set correctly.");
        assertEquals(4, ship.getBowColumn(), "The bow column should be set correctly.");
        assertTrue(ship.isHorizontal(), "The ship should be horizontal.");

        // Ensure the ship occupies the correct cells in the ocean
        for (int i = 4; i < 8; i++) {
            assertEquals(ship, ocean.getShipArray()[3][i], "The ship should occupy the correct cells.");
        }
    }

    @Test
    public void testOkToPlaceShipAt() {
        // Valid placement
        assertTrue(ship.okToPlaceShipAt(5, 5, true, ocean), "The ship should be placeable at an empty location.");

        // Invalid placement (out of bounds)
        assertFalse(ship.okToPlaceShipAt(9, 8, true, ocean), "The ship should not be placeable if it goes out of bounds.");

        // Invalid placement (overlapping another ship)
        ship.placeShipAt(5, 5, true, ocean);
        assertFalse(ship.okToPlaceShipAt(5, 6, true, ocean), "The ship should not overlap with another ship.");
    }

    @Test
    public void testShootAt() {
        // Place the ship and shoot at it
        ship.placeShipAt(2, 2, true, ocean);

        // Hit the ship
        assertTrue(ship.shootAt(2, 2), "Shooting at the ship should return true.");
        assertTrue(ship.isHit(2, 2), "The ship should register a hit at the correct location.");

        // Miss the ship
        assertFalse(ship.shootAt(3, 3), "Shooting outside the ship should return false.");
        assertFalse(ship.isHit(3, 3), "The ship should not register a hit outside its location.");
    }

    @Test
    public void testIsSunk() {
        // Place a destroyer (length 2) and shoot at it
        ship = new Destroyer();
        ship.placeShipAt(4, 4, true, ocean);

        // Not sunk after one hit
        ship.shootAt(4, 4);
        assertFalse(ship.isSunk(), "The ship should not be sunk after one hit.");

        // Sunk after all parts are hit
        ship.shootAt(4, 5);
        assertTrue(ship.isSunk(), "The ship should be sunk after all parts are hit.");
    }

    @Test
    public void testIsHit() {
        // Place the ship and shoot at specific locations
        ship.placeShipAt(1, 1, false, ocean);

        // Ensure no hits initially
        for (int i = 1; i < 5; i++) {
            assertFalse(ship.isHit(i, 1), "No hits should be registered before shooting.");
        }

        // Register hits
        ship.shootAt(1, 1);
        ship.shootAt(2, 1);
        assertTrue(ship.isHit(1, 1), "The ship should register a hit at the correct location.");
        assertTrue(ship.isHit(2, 1), "The ship should register a hit at the correct location.");
        assertFalse(ship.isHit(3, 1), "No hit should be registered at unshot locations.");
    }

    @Test
    public void testShipType() {
        // Test the type of different ships
        assertEquals("Battleship", ship.getShipType(), "The ship type should be Battleship.");

        ship = new Cruiser();
        assertEquals("Cruiser", ship.getShipType(), "The ship type should be Cruiser.");

        ship = new Destroyer();
        assertEquals("Destroyer", ship.getShipType(), "The ship type should be Destroyer.");

        ship = new Submarine();
        assertEquals("Submarine", ship.getShipType(), "The ship type should be Submarine.");
    }



    /**
     * Test the initial state of an EmptySea object.
     * Ensures the length is 1 and it is not marked as sunk.
     */
    @Test
    public void testInitialization() {
        // Validate length of EmptySea
        assertEquals(1, emptySea.getLength(), "EmptySea should have a length of 1.");
        // Validate the ship type
        assertEquals("EmptySea", emptySea.getShipType(), "EmptySea should return the correct ship type.");
        // Validate that EmptySea is never sunk
        assertFalse(emptySea.isSunk(), "EmptySea should never be sunk.");
    }

    /**
     * Test firing a shot at an EmptySea.
     * Ensures that it registers as fired upon but does not increment hit count or count as sunk.
     */
    @Test
    public void testShootAtEmptySea() {
        // Fire a shot at the EmptySea
        assertFalse(emptySea.shootAt(0, 0), "Shooting at EmptySea should return false.");
        // Validate that the EmptySea is marked as fired upon
        assertTrue(emptySea.isFiredUpon(), "EmptySea should register as fired upon after a shot.");
        // Validate that EmptySea is still not sunk
        assertFalse(emptySea.isSunk(), "EmptySea should not be sunk, even after being fired upon.");
    }
    /**
     * Test the initialization of the Battleship.
     * Ensures the length is set to 4 and the hit array is properly initialized.
     */
    @Test
    public void testInitialization2() {
        assertEquals(4, battleship.getLength(), "Battleship should have a length of 4.");
        assertNotNull(battleship.hit, "Hit array should be initialized.");
        assertEquals(4, battleship.hit.length, "Hit array should have a length of 4.");
        for (boolean hitSection : battleship.hit) {
            assertFalse(hitSection, "All sections of the hit array should initially be false.");
        }
    }

    /**
     * Test the getShipType method.
     * Ensures it returns "Battleship" for this specific ship type.
     */
    @Test
    public void testGetShipType() {
        assertEquals("Battleship", battleship.getShipType(),
                "getShipType() should return 'Battleship'.");
    }



    /**
     * Test shooting at the Battleship.
     * Ensures hits are registered and the ship is sunk after all sections are hit.
     */
    @Test
    public void testShootAtAndSink() {
        Ocean ocean = new Ocean();
        battleship.placeShipAt(3, 3, true, ocean); // Place horizontally

        // Shoot at all sections of the ship
        for (int i = 3; i < 7; i++) {
            assertTrue(battleship.shootAt(3, i),
                    "Shooting at (3, " + i + ") should hit the Battleship.");
        }

        // Check that the ship is sunk
        assertTrue(battleship.isSunk(), "Battleship should be sunk after all sections are hit.");
    }

    /**
     * Test that shooting at non-occupied cells of the ocean does not affect the Battleship.
     */
    @Test
    public void testMissedShots() {
        Ocean ocean = new Ocean();
        battleship.placeShipAt(4, 4, true, ocean); // Place horizontally

        // Shoot at cells outside the Battleship's location
        assertFalse(battleship.shootAt(5, 5), "Shooting at (5, 5) should not hit the Battleship.");
        assertFalse(battleship.shootAt(3, 3), "Shooting at (3, 3) should not hit the Battleship.");

        // The ship should not be sunk
        assertFalse(battleship.isSunk(), "Battleship should not be sunk after missed shots.");
    }
}
