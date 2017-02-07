package eu.gruchala.scala.language

package object variances {

  trait Human
  trait Parent extends Human
  trait Child extends Parent

  object invariance {

    //invariant, can return only type of A
    class Box[A] {

      def set(a: A): Box[A] = new Box[A]
    }

    val parent: Box[Parent] = new Box[Parent]

    //Not allowed, because Child is a subtype of Parent
    //  val parentToChild: Box[Child] = parent
    //  val child: Box[Child] = parent.set(new Child {})

    //Not allowed, because Human is a supertype of Parent
    //  val parentToHuman: Box[Human] = parent
    //  val human: Box[Human] = parent.set(new Human {})

    //Allowed, because Parent is a type of Parent
    val parentToParent: Box[Parent] = parent
    val anotherParent: Box[Parent] = parent.set(new Parent {})
  }

  object contravariance {

    //contravariant, can result in type of A and it's subtypes
    class Box[-A] {

      //upper bound (B as subtype of A)
      def set[B <: A](b: B): Box[B] = new Box[B]
    }

    val parent: Box[Parent] = new Box[Parent]

    //Allowed, because Child is a subtype of Parent
    val parentToChild: Box[Child] = parent
    val child: Box[Child] = parent.set(new Child {})

    //Not allowed, because Human is a supertype of Parent
    //  val parentToHuman: Box[Human] = parent
    //  val human: Box[Human] = parent.set(new Human {})
  }

  object covariance {

    //covariant, can result in type of A and its subtypes
    class Box[+A] {

      //lower bound (B as supertype of A)
      def set[B >: A](b: B): Box[B] = new Box[B]
    }

    val parent: Box[Parent] = new Box[Parent]

    //Not allowed, because Child is a subtype of Parent
    //  val parentToChild: Box[Child] = parent
    //  val child: Box[Child] = parent.set(new Child {})

    //Allowed, because Human is a supertype of Parent
    val parentToHuman: Box[Human] = parent
    val human: Box[Human] = parent.set(new Human {})

    //List is covariant - abstract class List[+A]
    val parents: List[Parent] = List(new Parent {})
    val a: List[Parent] = new Child {} :: parents //compiles, because we added a Child which contains Parent properties (Child is a subtype of Parent) AND the result type is Parent (List[+A])
    val a1: List[Human] = new Child {} :: parents //same as above, plus compiles because we defined result type as supertype of Parent
//    val b: List[Child] = new Child {} :: parents //can't compile, because we added a Child which contains Parent properties (Child is a subtype of Parent), BUT cannot "cast" to subtype of Parents (List[+A])
//    val c: List[Parent] = new Human {} :: parents //can't compile, because Human is a supertype of Parent, BUT we cannot "cast" to Parent from Human
    val d: List[Human] = new Human {} :: parents //compiles, because Human is a supertype of Parent (List[+A]) AND we "cast" to that supertype
  }
}
