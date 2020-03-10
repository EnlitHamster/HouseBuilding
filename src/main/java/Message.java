// Enumerator containing all the Actors possible messages
// Used to avoid DeadLetters and standardizing communication

public abstract class Message {
    public enum Operation {
        BuildHouse,
        PrepareFrame,
        PrepareSite,
        SitePrepared,
        BuildWalls,
        WallsBuilt,
        FramePrepared,
        PrepareInterior,
        PaintWalls,
        WallsPainted,
        FitWindows,
        WindowsFitted,
        PrepareExterior,
        InteriorPrepared,
        ExteriorPrepared
    }

    public static class Order {
        public final int Quantity;
        public Order(int q) {Quantity = q;}
    }
}