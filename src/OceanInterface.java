/**
 * The OceanInterface defines the contract that the Ocean class must follow.
 * It describes methods for managing the state of the game, interacting with ships,
 * and tracking gameplay progress.
 *
 * This interface is particularly useful for testing, abstraction, and ensuring
 * consistency across different implementations of the Ocean class.
 *
 * @author Dave
 */
public interface OceanInterface {

	/**
	 * Randomly places all ten ships on the ocean.
	 * Ships must be placed such that larger ships are positioned before smaller ones
	 * to avoid placement conflicts.
	 */
	void placeAllShipsRandomly();

	/**
	 * Checks if the specified location contains a ship (not an EmptySea).
	 *
	 * @param row    The row coordinate (0 to 9).
	 * @param column The column coordinate (0 to 9).
	 * @return {@code true} if the location contains a ship, {@code false} otherwise.
	 */
	boolean isOccupied(int row, int column);

	/**
	 * Fires a shot at the specified location.
	 * Updates the number of shots fired and hit count.
	 *
	 * If the location contains a ship that is not sunk, returns {@code true}.
	 * If the ship is already sunk or the location is EmptySea, returns {@code false}.
	 *
	 * @param row    The row coordinate to shoot at (0 to 9).
	 * @param column The column coordinate to shoot at (0 to 9).
	 * @return {@code true} if the shot hits a ship, {@code false} otherwise.
	 */
	boolean shootAt(int row, int column);

	/**
	 * Gets the total number of shots fired by the user.
	 *
	 * @return The total number of shots fired.
	 */
	int getShotsFired();

	/**
	 * Gets the total number of hits recorded.
	 * Note that this includes multiple hits on the same location.
	 *
	 * @return The total number of hits recorded.
	 */
	int getHitCount();

	/**
	 * Gets the total number of ships sunk during the game.
	 *
	 * @return The number of ships sunk.
	 */
	int getShipsSunk();

	/**
	 * Checks if the game is over, i.e., all ships have been sunk.
	 *
	 * @return {@code true} if all ships have been sunk, {@code false} otherwise.
	 */
	boolean isGameOver();

	/**
	 * Provides access to the 10x10 grid of ships.
	 * This allows methods in the Ship class to read and modify the game state.
	 *
	 * @return A 10x10 2D array of ships.
	 */
	Ship[][] getShipArray();

	/**
	 * Prints the current state of the ocean.
	 *
	 * Display rules:
	 * - 'S': A ship part that has been hit.
	 * - '-': A missed shot (EmptySea that was fired upon).
	 * - 'x': A sunken ship.
	 * - '.': An unfired location.
	 *
	 * Row numbers (0 to 9) should appear on the left.
	 * Column numbers (0 to 9) should appear at the top.
	 *
	 * This is for user guidance during gameplay and debugging.
	 */
	void print();
}
