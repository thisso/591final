/**
 * The abstract Ship class represents a general ship in the game.
 * It defines common properties and behaviors for all types of ships,
 * including methods for placement, shooting, and determining the ship's state.
 */
public abstract class Ship {

    // Row of the bow (front) of the ship.
    protected int bowRow;

    // Column of the bow (front) of the ship.
    protected int bowColumn;

    // Number of cells occupied by the ship.
    protected int length;

    // Indicates if the ship is placed horizontally or vertically.
    protected boolean horizontal;

    // Tracks which parts of the ship have been hit.
    protected boolean[] hit;

    /**
     * Constructor for the Ship class.
     * Initializes the hit array with a maximum size of 4 (largest ship length).
     */
    public Ship() {
        hit = new boolean[4];
    }

    /**
     * @return The row of the bow of the ship.
     */
    public int getBowRow() {
        return bowRow;
    }

    /**
     * @return The column of the bow of the ship.
     */
    public int getBowColumn() {
        return bowColumn;
    }

    /**
     * @return The length of the ship (number of cells it occupies).
     */
    public int getLength() {
        return length;
    }

    /**
     * @return True if the ship is placed horizontally, false otherwise.
     */
    public boolean isHorizontal() {
        return horizontal;
    }

    /**
     * Sets the row of the bow of the ship.
     *
     * @param bowRow The row to set.
     */
    public void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    /**
     * Sets the column of the bow of the ship.
     *
     * @param bowColumn The column to set.
     */
    public void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    /**
     * Sets the orientation of the ship.
     *
     * @param horizontal True if the ship is horizontal, false otherwise.
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    /**
     * @return The type of the ship as a string. Must be implemented by subclasses.
     */
    public abstract String getShipType();

    /**
     * Checks if the ship can be placed at a given location on the board.
     * Ensures that the ship is within bounds and does not overlap or touch other ships.
     *
     * @param row       The starting row of the ship.
     * @param column    The starting column of the ship.
     * @param horizontal True if the ship is placed horizontally, false otherwise.
     * @param ocean     The ocean where the ship is being placed.
     * @return True if the ship can be placed, false otherwise.
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        int endRow = row + (horizontal ? 0 : length - 1);
        int endColumn = column + (horizontal ? length - 1 : 0);

        // Check if the ship is within bounds.
        if (row < 0 || column < 0 || endRow >= 10 || endColumn >= 10) return false;

        // Check if the ship overlaps or touches another ship.
        for (int i = row - 1; i <= endRow + 1; i++) {
            for (int j = column - 1; j <= endColumn + 1; j++) {
                if (i >= 0 && i < 10 && j >= 0 && j < 10 && !(ocean.getShipArray()[i][j] instanceof EmptySea)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places the ship on the board at the specified location and orientation.
     *
     * @param row       The starting row of the ship.
     * @param column    The starting column of the ship.
     * @param horizontal True if the ship is placed horizontally, false otherwise.
     * @param ocean     The ocean where the ship is being placed.
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        bowRow = row;
        bowColumn = column;
        this.horizontal = horizontal;

        // Place the ship in the appropriate cells of the ocean grid.
        for (int i = 0; i < length; i++) {
            if (horizontal) {
                ocean.getShipArray()[row][column + i] = this;
            } else {
                ocean.getShipArray()[row + i][column] = this;
            }
        }
    }

    /**
     * Registers a shot at the specified location.
     * Marks the part of the ship as hit if the shot hits the ship.
     *
     * @param row    The row of the shot.
     * @param column The column of the shot.
     * @return True if the shot hits the ship, false otherwise.
     */
    public boolean shootAt(int row, int column) {
        if (isSunk()) return false; // Cannot shoot at a sunk ship.

        // Check if the shot hits a part of the ship and mark it as hit.
        if (horizontal) {
            if (row == bowRow && column >= bowColumn && column < bowColumn + length) {
                hit[column - bowColumn] = true;
                return true;
            }
        } else {
            if (column == bowColumn && row >= bowRow && row < bowRow + length) {
                hit[row - bowRow] = true;
                return true;
            }
        }
        return false; // Missed shot.
    }

    /**
     * Checks if the ship has been sunk.
     * A ship is sunk if all its parts have been hit.
     *
     * @return True if the ship is sunk, false otherwise.
     */
    public boolean isSunk() {
        for (int i = 0; i < length; i++) {
            if (!hit[i]) return false; // If any part is not hit, the ship is not sunk.
        }
        return true; // All parts are hit, so the ship is sunk.
    }

    /**
     * Checks if a specific part of the ship has been hit.
     *
     * @param row    The row of the part to check.
     * @param column The column of the part to check.
     * @return True if the specified part has been hit, false otherwise.
     */
    public boolean isHit(int row, int column) {
        if (horizontal) {
            return row == bowRow && column >= bowColumn && column < bowColumn + length && hit[column - bowColumn];
        } else {
            return column == bowColumn && row >= bowRow && row < bowRow + length && hit[row - bowRow];
        }
    }
}
