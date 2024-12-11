//crusier subclass w lengh 3
public class Cruiser extends Ship {
    public Cruiser() {
        length = 3;
        hit = new boolean[length];
    }

    @Override
    public String getShipType() {
        return "Cruiser";
    }
}
