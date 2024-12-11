/**
 * Represents a Battleship type ship in a Battleship game.
 * Extends the base Ship class and defines specific characteristics of a Battleship.
 */
public class Battleship extends Ship {

    /**
     * Constructor for creating a new Battleship.
     * Initializes the ship with its standard length of 4 cells and creates
     * an array to track hits on the ship.
     */
    public Battleship() {
        // Set the length of the Battleship to 4 cells
        length = 4;

        // Initialize the hit array with the specified length
        // Each element is false by default, indicating no hits
        // When a section is hit, the corresponding element becomes true
        hit = new boolean[length];
    }

    /**
     * Returns the type of this ship as a String.
     * Overrides the abstract method from the Ship parent class.
     *
     * @return String "Battleship" - the type identifier for this ship
     */
    @Override
    public String getShipType() {
        return "Battleship";
    }
}