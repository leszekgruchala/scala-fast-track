package eu.gruchala.scala.language

package object variances {

  trait Human
  trait Parent extends Human
  trait Child extends Parent

  trait invariance {

    //invariant - Bus can take only A type, nothing else
    class Bus[A] {}
    def drive(a: Bus[Parent]): Unit

    //drive(new Bus[Human])
    drive(new Bus[Parent])
    //drive(new Bus[Child])
  }

  trait contravariance {

    //contravariant - can take type of A and its supertypes
    class Bus[-A] {}
    def drive(a: Bus[Parent]): Unit

    drive(new Bus[Human])
    drive(new Bus[Parent])
    //drive(new Bus[Child])
  }

  trait covariance {

    //covariant - can take type of A and its subtypes
    class Bus[+A] {}
    def drive(a: Bus[Parent]): Unit

    //drive(new Bus[Human])
    drive(new Bus[Parent])
    drive(new Bus[Child])
  }
}
