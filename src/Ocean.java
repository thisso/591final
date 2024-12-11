import java.util.Random;

/**
 * This class manages the game state and interactions with the ships on the board.
 * It implements the OceanInterface to define the behavior required for the game.
 * Responsibilities:
 * - Managing the board state (grid of ships).
 * - Tracking game statistics (shots fired, hit count, ships sunk).
 * - Providing functionality to place ships and interact with them.
 */
public class Ocean implements OceanInterface {

	// A 10x10 grid of ships, where each cell represents a part of a ship or EmptySea.
	protected Ship[][] ships;

	// Tracks the total number of shots fired by the user.
	protected int shotsFired;

	// Tracks the total number of hits on ships, including repeated hits on the same part.
	protected int hitCount;

	// Tracks the total number of ships that have been sunk.
	protected int shipsSunk;

	/**
	 * Constructor initializes the game board with EmptySea objects in every position.
	 * Also initializes the game state variables to track progress.
	 */
	public Ocean() {
		ships = new Ship[10][10]; // Create a 10x10 grid.

		// Fill each cell of the grid with an EmptySea object.
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ships[i][j] = new EmptySea();
			}
		}

		// Initialize game statistics.
		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;
	}

	/**
	 * Randomly places all ships on the board. Larger ships are placed first to
	 * minimize placement conflicts.
	 * This method ensures that all ships are placed according to the rules of the game.
	 */
	@Override
	public void placeAllShipsRandomly() {
		// Create an array representing the fleet of ships to be placed.
		Ship[] fleet = {
				new Battleship(), new Cruiser(), new Cruiser(),
				new Destroyer(), new Destroyer(), new Destroyer(),
				new Submarine(), new Submarine(), new Submarine(), new Submarine()
		};

		Random random = new Random(); // For generating random positions and orientations.

		// Place each ship in the fleet.
		for (Ship ship : fleet) {
			boolean placed = false; // Tracks whether the ship has been successfully placed.
			while (!placed) {
				int row = random.nextInt(10); // Random row (0 to 9).
				int column = random.nextInt(10); // Random column (0 to 9).
				boolean horizontal = random.nextBoolean(); // Random orientation (horizontal or vertical).

				// Check if the ship can be placed at the specified location.
				if (ship.okToPlaceShipAt(row, column, horizontal, this)) {
					ship.placeShipAt(row, column, horizontal, this); // Place the ship.
					placed = true; // Mark the ship as placed.
				}
			}
		}
	}

	/**
	 * Checks if the given location on the board is occupied by a ship.
	 *
	 * @param row    The row coordinate (0 to 9).
	 * @param column The column coordinate (0 to 9).
	 * @return true if the location contains a ship, false otherwise.
	 */
	@Override
	public boolean isOccupied(int row, int column) {
		return !(ships[row][column] instanceof EmptySea);
	}

	/**
	 * Fires a shot at the specified location. Updates game statistics such as
	 * shots fired, hit count, and ships sunk. Returns whether the shot hits a ship.
	 *
	 * @param row    The row coordinate to shoot at (0 to 9).
	 * @param column The column coordinate to shoot at (0 to 9).
	 * @return true if the shot hits a ship, false otherwise.
	 */
	@Override
	public boolean shootAt(int row, int column) {
		shotsFired++; // Increment the total number of shots fired.

		Ship target = ships[row][column]; // Get the ship at the specified location.

		// Check if the shot hits the ship.
		if (target.shootAt(row, column)) {
			hitCount++; // Increment the hit count.
			if (target.isSunk()) {
				shipsSunk++; // Increment the count of sunk ships if the target is sunk.
			}
			return true; // Shot hit a ship.
		}
		return false; // Shot missed.
	}

	/**
	 * @return The total number of shots fired by the user during the game.
	 */
	@Override
	public int getShotsFired() {
		return shotsFired;
	}

	/**
	 * @return The total number of hits recorded during the game.
	 * Note: Includes repeated hits on the same location.
	 */
	@Override
	public int getHitCount() {
		return hitCount;
	}

	/**
	 * @return The total number of ships sunk during the game.
	 */
	@Override
	public int getShipsSunk() {
		return shipsSunk;
	}

	/**
	 * Checks if the game is over, i.e., all ships have been sunk.
	 *
	 * @return true if all ships are sunk, false otherwise.
	 */
	@Override
	public boolean isGameOver() {
		return shipsSunk == 10; // All 10 ships must be sunk for the game to end.
	}

	/**
	 * Provides access to the 10x10 grid of ships.
	 * This allows the Ship class to interact with the Ocean.
	 *
	 * @return A 10x10 2D array of ships representing the board state.
	 */
	@Override
	public Ship[][] getShipArray() {
		return ships;
	}

	/**
	 * Prints the current state of the board to the console.
	 * Display rules:
	 * - 'x': A sunken ship.
	 * - 'S': A ship part that has been hit.
	 * - '-': A missed shot (EmptySea that was fired upon).
	 * - '.': An unfired location.
	 */
	@Override
	public void print() {
		// Print column headers (0 to 9).
		System.out.print("  ");
		for (int col = 0; col < 10; col++) {
			System.out.print(col + " ");
		}
		System.out.println();

		// Print each row of the board.
		for (int row = 0; row < 10; row++) {
			System.out.print(row + " "); // Print row headers.

			for (int col = 0; col < 10; col++) {
				Ship ship = ships[row][col]; // Get the ship at the current cell.

				if (ship.isSunk()) {
					System.out.print("x "); // Display 'x' for a sunken ship.
				} else if (ship.isHit(row, col)) {
					System.out.print("S "); // Display 'S' for a hit ship part.
				} else if (ship instanceof EmptySea && ((EmptySea) ship).isFiredUpon()) {
					System.out.print("- "); // Display '-' for a missed shot.
				} else {
					System.out.print(". "); // Display '.' for an unfired location.
				}
			}
			System.out.println(); // Move to the next row.
		}
	}
}
