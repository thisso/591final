/**
 * The EmptySea class represents a single cell on the ocean grid that does not contain a ship.
 * This class extends the abstract Ship class but behaves differently since it does not represent an actual ship.
 * It is used to indicate locations where no ship is placed.
 */
public class EmptySea extends Ship {

    /**
     * Constructor for the EmptySea class.
     * Initializes the length to 1 (since EmptySea represents a single cell) and
     * the hit array to track whether this cell has been fired upon.
     */
    public EmptySea() {
        length = 1; // An EmptySea occupies one cell.
        hit = new boolean[length]; // Initialize the hit array for a single cell.
    }

    /**
     * Overrides the shootAt method from the Ship class.
     * Marks this EmptySea as "fired upon" but always returns false since an EmptySea cannot be "hit".
     *
     * @param row    The row of the cell being fired upon.
     * @param column The column of the cell being fired upon.
     * @return Always returns false because EmptySea cannot be hit.
     */
    @Override
    public boolean shootAt(int row, int column) {
        hit[0] = true; // Mark the cell as fired upon.
        return false; // Return false because EmptySea does not represent a ship.
    }

    /**
     * Overrides the isSunk method from the Ship class.
     * Always returns false because an EmptySea cannot be sunk.
     *
     * @return Always returns false.
     */
    @Override
    public boolean isSunk() {
        return false; // EmptySea cannot be sunk.
    }

    /**
     * Overrides the getShipType method from the Ship class.
     * Provides the type of this "ship" as "EmptySea".
     *
     * @return The string "EmptySea" indicating this object represents an empty cell.
     */
    @Override
    public String getShipType() {
        return "EmptySea"; // Return the type as "EmptySea".
    }

    /**
     * Checks if this EmptySea has been fired upon.
     * This method is specific to EmptySea and is not part of the Ship class.
     *
     * @return true if this EmptySea has been fired upon, false otherwise.
     */
    public boolean isFiredUpon() {
        return hit[0]; // Return whether this EmptySea cell has been fired upon.
    }
}
