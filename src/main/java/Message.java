public abstract class Message {
    // Enumerator containing all the Actors possible messages
    // Used to avoid DeadLetters and standardizing communication
    public enum Operation {
        //---------
        // BUILDING
        //---------

        BuildHouse,
        SitePrepared,
        WallsBuilt,
        FramePrepared,
        WallsPainted,
        WindowsFitted,
        InteriorPrepared,
        ExteriorPrepared,
        HouseBuilt,

        //----------
        // MATERIALS
        //----------

        Delivered
    }

    public static class Quantity {
        public final int Quantity;
        public  Quantity(int q) {Quantity = q;}
    }
}