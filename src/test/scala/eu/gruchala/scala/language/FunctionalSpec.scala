package eu.gruchala.scala.language

import org.scalatest._
import org.scalatest.Matchers._

class FunctionalSpec extends FunSpec with GivenWhenThen {

  case class Pet(name: String, yob: Int, numberOfLegs: Int, owner: String)

  val pets = List(
    Pet("Azor", 2010, 4, "Mark"),
    Pet("Reksio", 2013, 4, "Alice"),
    Pet("Chewie", 1977, 2, "Mark"),
    Pet("Ara", 2014, 2, "Mark"),
    Pet("King Kong", 1992, 2, "Bob"),
    Pet("Jon Deep", 2014, 0, "Alice")
  )

  describe("Functional style works great with Lists") {
    it("allowing easy iteration ... ") {
      val result = pets.foreach(pet => println(s"${pet.owner} owns ${pet.name}"))
      result shouldBe () //foreach is about side-effects :(
    }

    it("and condition checking ... ") {
      val areAllBornAfterSeventies = pets.forall(_.yob > 1970)
      areAllBornAfterSeventies shouldBe true

      val oneWasBornBeforeSeventies = pets.exists(_.yob < 1970)
      oneWasBornBeforeSeventies shouldBe false
    }

    it("and transformation ... ") {
      val thisYearsPets = pets.map(oldPet => Pet(oldPet.name, 2015, oldPet.numberOfLegs, oldPet.owner))
      thisYearsPets.forall(_.yob == 2015) shouldBe true
      pets.forall(_.yob != 2015) shouldBe true // we didn't modify the original array
    }

    it("and grouping ... ") {
      val fourLeggedPets = pets.filter(_.numberOfLegs == 4)
      fourLeggedPets.length shouldBe 2

      val petsByNumberOdLegs = pets.groupBy(_.numberOfLegs)
      petsByNumberOdLegs shouldBe a [Map[_, _]]
      petsByNumberOdLegs(4).length shouldBe 2
      petsByNumberOdLegs(2).length shouldBe 3
      petsByNumberOdLegs(0).length shouldBe 1
    }

    it("and counting ... ") {
      val youngPetsCount = pets.count(_.yob >= 2010)
      youngPetsCount shouldBe 4

      val oldPetsCount = pets.count(_.yob < 2000)
      oldPetsCount shouldBe 2

      val totalAge = pets.map(2015 - _.yob).sum
      totalAge shouldBe 70
    }

    it("and aggregating results") {
      val totalNumerOfLegsForMarksPets = pets.filter(_.owner == "Mark").foldLeft(0)(_ + _.numberOfLegs)
      totalNumerOfLegsForMarksPets shouldBe 8
    }

    it("thanks to immutability") {
      // be default new values are returned ... one consequence is chaining
      val transformed = pets.map(p => p.copy(owner = "Joe"))
        .filter(_.numberOfLegs > 0)
        .map(p => p.owner + "'s " + p.name)
        .filter(_.length < 10)

      transformed shouldBe List("Joe's Ara")
    }
  }
}
