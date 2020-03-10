public abstract class Message {
    // Enumerator containing all the Actors possible messages
    // Used to avoid DeadLetters and standardizing communication
    public enum Operation {
        //---------
        // BUILDING
        //---------

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

        //----------
        // MATERIALS
        //----------

        Order,                  // Used in exceptions in case of Quantity messages
        Delivered
    }

    public static class Quantity {
        public final int Quantity;
        public  Quantity(int q) {Quantity = q;}
    }
}