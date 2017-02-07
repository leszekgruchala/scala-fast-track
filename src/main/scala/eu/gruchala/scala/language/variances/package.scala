package eu.gruchala.scala.language

package object variances {

  trait Human
  trait Parent extends Human
  trait Child extends Parent

  object invariance {

    //invariant, only type A
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

    //contravariant, can hold A and it's subtypes
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

    //covariant, can hold A and it's supertypes
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
  }
}
