package br.unb.cic.flang

import EH_and_StateMonad._

import cats.instances.list._
import cats.syntax.applicative._
import cats.data.OptionT
import cats.data.{EitherT, State, StateT}

case class FDeclaration(name: String, arg: String, body: Expr)

object Declarations {

  def lookup(
      name: String,
      declarations: List[FDeclaration]
  ): StateError[FDeclaration] = declarations match {
    case List()                                          => EitherT.left(s"Function $name is not declared".pure[S])//(_.isFinite)
    case ((f @ FDeclaration(n, a, b)) :: _) if n == name => {EitherT.pure(f)}
    case (_ :: fs)                                       => {lookup(name, fs)}
  }

}
