package eu.gruchala.scala.language

object ForComprehensionsWithOption {
  
  trait Ingredient {
    def name: String
  }

  trait Pizza {

    def getCheese: Option[Ingredient]
    def getSauce: Option[Ingredient]
    def getMushrooms: Option[Ingredient]
    def getHam: Option[Ingredient]
    def getOnion: Option[Ingredient]

    def blend(ingredients: Ingredient*)

    def prepare =
      for {
        cheese   <- getCheese
        sauce    <- getSauce if sauce.name=="tomato"
        mushroom <- getMushrooms
        ham      <- getHam
        onion    <- getOnion
        blended  <- blend(cheese, sauce, mushroom, ham, onion)
      } yield blended
  }

  //for-comp is syntactic sugar for map, flatMap, foreach, optionally withFilter so any object (Monad) with these properties
  //can be used in for-comp
}
