public enum Material {
    Batch(50),
    Concrete(20),
    Bricks(15),
    Paint(5),
    Windows(5),
    Logs(20);

    public final int Cost;
    Material(int c) {Cost = c;}
}
