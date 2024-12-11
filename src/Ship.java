public abstract class Ship {

    protected int bowRow;
    protected int bowColumn;
    protected int length;
    protected boolean horizontal;
    protected boolean[] hit;

    public Ship() {
        hit = new boolean[4];
    }

    public int getBowRow() {
        return bowRow;
    }

    public int getBowColumn() {
        return bowColumn;
    }

    public int getLength() {
        return length;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    public void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public abstract String getShipType();

    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        int endRow = row + (horizontal ? 0 : length - 1);
        int endColumn = column + (horizontal ? length - 1 : 0);

        if (row < 0 || column < 0 || endRow >= 10 || endColumn >= 10) return false;

        for (int i = row - 1; i <= endRow + 1; i++) {
            for (int j = column - 1; j <= endColumn + 1; j++) {
                if (i >= 0 && i < 10 && j >= 0 && j < 10 && !(ocean.getShipArray()[i][j] instanceof EmptySea)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        bowRow = row;
        bowColumn = column;
        this.horizontal = horizontal;

        for (int i = 0; i < length; i++) {
            if (horizontal) {
                ocean.getShipArray()[row][column + i] = this;
            } else {
                ocean.getShipArray()[row + i][column] = this;
            }
        }
    }

    public boolean shootAt(int row, int column) {
        if (isSunk()) return false;

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
        return false;
    }

    public boolean isSunk() {
        for (int i = 0; i < length; i++) {
            if (!hit[i]) return false;
        }
        return true;
    }

    public boolean isHit(int row, int column) {
        if (horizontal) {
            return row == bowRow && column >= bowColumn && column < bowColumn + length && hit[column - bowColumn];
        } else {
            return column == bowColumn && row >= bowRow && row < bowRow + length && hit[row - bowRow];
        }
    }
}
