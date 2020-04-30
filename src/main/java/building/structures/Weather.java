package building.structures;

public enum Weather {
    Sunny(2),
    Rainy(1),
    Stormy(0);

    public final int Progress;
    Weather(int p) {Progress = p;}
}
