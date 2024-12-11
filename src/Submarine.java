public class Submarine extends Ship {
    public Submarine() {
        length = 1;
        hit = new boolean[length];
    }

    @Override
    public String getShipType() {
        return "Submarine";
    }
}
