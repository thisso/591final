public class Destroyer extends Ship {
    public Destroyer() {
        length = 2;
        hit = new boolean[length];
    }

    @Override
    public String getShipType() {
        return "Destroyer";
    }
}
