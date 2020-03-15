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
}