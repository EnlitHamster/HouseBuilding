package building;

public enum Weather {
    SUNNY(2),
    RAINY(1),
    STORMY(0);

    public final int progress;
    Weather(int v) {progress = v;}
}
