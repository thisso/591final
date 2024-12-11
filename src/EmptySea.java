public class EmptySea extends Ship {
    public EmptySea() {
        length = 1;
        hit = new boolean[length];
    }

    @Override
    public boolean shootAt(int row, int column) {
        hit[0] = true;
        return false;
    }

    @Override
    public boolean isSunk() {
        return false;
    }

    @Override
    public String getShipType() {
        return "EmptySea";
    }

    public boolean isFiredUpon() {
        return hit[0];
    }
}
