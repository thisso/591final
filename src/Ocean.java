import java.util.Random;

/**
 * This class manages the game state and interactions with the ships on the board.
 */
public class Ocean implements OceanInterface {

	protected Ship[][] ships;
	protected int shotsFired;
	protected int hitCount;
	protected int shipsSunk;

	public Ocean() {
		ships = new Ship[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				ships[i][j] = new EmptySea();
			}
		}
		shotsFired = 0;
		hitCount = 0;
		shipsSunk = 0;
	}

	@Override
	public void placeAllShipsRandomly() {
		Ship[] fleet = {
				new Battleship(), new Cruiser(), new Cruiser(),
				new Destroyer(), new Destroyer(), new Destroyer(),
				new Submarine(), new Submarine(), new Submarine(), new Submarine()
		};
		Random random = new Random();

		for (Ship ship : fleet) {
			boolean placed = false;
			while (!placed) {
				int row = random.nextInt(10);
				int column = random.nextInt(10);
				boolean horizontal = random.nextBoolean();

				if (ship.okToPlaceShipAt(row, column, horizontal, this)) {
					ship.placeShipAt(row, column, horizontal, this);
					placed = true;
				}
			}
		}
	}

	@Override
	public boolean isOccupied(int row, int column) {
		return !(ships[row][column] instanceof EmptySea);
	}

	@Override
	public boolean shootAt(int row, int column) {
		shotsFired++;
		Ship target = ships[row][column];

		if (target.shootAt(row, column)) {
			hitCount++;
			if (target.isSunk()) {
				shipsSunk++;
			}
			return true;
		}
		return false;
	}

	@Override
	public int getShotsFired() {
		return shotsFired;
	}

	@Override
	public int getHitCount() {
		return hitCount;
	}

	@Override
	public int getShipsSunk() {
		return shipsSunk;
	}

	@Override
	public boolean isGameOver() {
		return shipsSunk == 10;
	}

	@Override
	public Ship[][] getShipArray() {
		return ships;
	}

	@Override
	public void print() {
		System.out.print("  ");
		for (int col = 0; col < 10; col++) {
			System.out.print(col + " ");
		}
		System.out.println();

		for (int row = 0; row < 10; row++) {
			System.out.print(row + " ");
			for (int col = 0; col < 10; col++) {
				Ship ship = ships[row][col];
				if (ship.isSunk()) {
					System.out.print("x ");
				} else if (ship.isHit(row, col)) {
					System.out.print("S ");
				} else if (ship instanceof EmptySea && ((EmptySea) ship).isFiredUpon()) {
					System.out.print("- ");
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
	}
}
