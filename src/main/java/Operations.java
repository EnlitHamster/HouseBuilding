// Enumerator containing all the Actors possible messages
// Used to avoid DeadLetters and standardizing communication

public enum Operations {
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
