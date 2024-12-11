/**
 * Represents a Submarine vessel in the Battleship game.
 * This class extends the Ship class and implements a submarine
 * with a length of 1 cell on the game board.
 *
 * @author David
 * @see Ship
 */
public class Submarine extends Ship {

    /**
     * Constructs a new Submarine object.
     * Initializes the submarine with a length of 1 cell and
     * creates an array to track hits on the submarine.
     * The hit array is initialized with false values, indicating
     * no hits on any part of the submarine.
     */
    public Submarine() {
        length = 1;
        hit = new boolean[length];
    }

    /**
     * Gets the type of this ship.
     *
     * @return A String representing the ship type, specifically "Submarine"
     * @override Ship.getShipType()
     */
    @Override
    public String getShipType() {
        return "Submarine";
    }
}