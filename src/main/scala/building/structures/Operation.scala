package building.structures

object Operation extends Enumeration {
  type Operation = Value
  val
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

      //--------------
      // COMMUNICATION
      //--------------
      dayPassed: Operation = Value
}
