package br.unb.cic.flang

import cats.instances.list._
import cats.syntax.applicative._
import cats.data.OptionT
import cats.data.{EitherT, State, StateT}

package object EH_and_StateMonad {
  type S[A] = State[(String,Int), A]
  type S2 = List[(String, Int)]
  type StateError[A] = EitherT[S, String, A]
  type MonadList = List[StateError[Int]]



  def declareVar(state: (String,Int), value: Either[String, Int]): StateError[Int] = EitherT {
    State {
      currentState => (state, value)
    }
  }


  def assertError[A](m: StateError[A]): Boolean = m.value.runEmptyA.value match {
    case Left(_) => true
    case Right(_) => false
  }

  def getCurrentState(name: String): StateError[Int] = EitherT {
    State { currentState =>
      println(currentState._2)
      if (currentState._1 == name) {
        (currentState, Right(currentState._2))
      }
      else{
        (currentState,Left(s"Variable $name is not declared"))
      }
    }
  }
}
