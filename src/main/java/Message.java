// Enumerator containing all the Actors possible messages
// Used to avoid DeadLetters and standardizing communication

import akka.actor.ActorRef;

public abstract class Message {
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
        public final ActorRef From;
        public final int Quantity;
        public  Quantity(int q, ActorRef f) {Quantity = q; From = f;}
    }
}