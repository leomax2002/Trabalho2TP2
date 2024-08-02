package br.unb.cic.flang

import cats.instances.list._
import cats.syntax.applicative._
import cats.data.OptionT
import cats.data.{EitherT, State, StateT}

package object EH_and_StateMonad {
  type S[A] = State[List[(String,Int)], A]
  type StateError[A] = EitherT[S, String, A]



  def declareVar(state:(String,Int), value: Either[String, Int]): StateError[Int] = EitherT.liftF {
    State[List[(String,Int)],Int] { list=>
      (list:+state,state._2)
    }
  }

  def assertError[A](m: StateError[A]): Boolean = m.value.runEmptyA.value match {
    case Left(_) => true
    case Right(_) => false
  }

  def lookupVar(name: String): StateError[Int] = EitherT{
    State.get[List[(String,Int)]].map{list=>
      val variable = list.zipWithIndex.collect{
        case (state, idx) if state._1 == name => state._2
      }
      if (variable.isEmpty) Left(variable.mkString(s"Variable $name is not declared"))
      else Right(variable.last)
    }
  }

}
