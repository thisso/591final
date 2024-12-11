/**
 * Represents a Destroyer ship in the Battleship game.
 * A Destroyer is a type of ship that occupies 2 cells on the game board.
 * This class extends the {@link Ship} class and implements its specific properties.
 */
public class Destroyer extends Ship {

    /**
     * Constructs a new Destroyer object.
     * Initializes the ship's length to 2 cells and sets up the hit array
     * to track whether each part of the ship has been hit.
     */
    public Destroyer() {
        length = 2; // Set the length of the Destroyer to 2 cells.
        hit = new boolean[length]; // Initialize the hit array for the ship's parts.
    }

    /**
     * Returns the type of the ship as a string.
     * Overrides the abstract method {@link Ship#getShipType()} in the parent class.
     *
     * @return A string representing the type of this ship ("Destroyer").
     */
    @Override
    public String getShipType() {
        return "Destroyer";
    }
}
