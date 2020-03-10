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
        ExteriorPrepared,
        Order                   // Used in exceptions in case of Quantity messages
    }

    public static class Quantity {
        public final int Quantity;
        public  Quantity(int q) {Quantity = q;}
    }
}