/**
 * Represents a Cruiser ship in the Battleship game.
 * A Cruiser is a type of ship that has a length of 3 cells.
 * Extends the {@link Ship} class and inherits its properties and behaviors.
 */
public class Cruiser extends Ship {

    /**
     * Constructor for creating a new Cruiser.
     * Sets the ship's length to 3 and initializes the hit array to track damage.
     */
    public Cruiser() {
        // Set the length of the Cruiser to 3 cells.
        length = 3;

        // Initialize the hit array with the specified length.
        // Each element in the array corresponds to a part of the ship and is initially false,
        // indicating that the part has not been hit.
        hit = new boolean[length];
    }

    /**
     * Returns the type of this ship as a string.
     * Overrides the abstract method from the {@link Ship} class.
     *
     * @return A string representation of the ship type, which is "Cruiser".
     */
    @Override
    public String getShipType() {
        return "Cruiser";
    }
}

