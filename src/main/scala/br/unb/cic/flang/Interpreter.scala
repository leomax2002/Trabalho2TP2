package br.unb.cic.flang

import Declarations._
import EH_and_StateMonad._

import cats.instances.list._
import cats.syntax.applicative._
import cats.data.OptionT
import cats.data.{EitherT, State, StateT}


object Interpreter {

  def eval(expr: Expr, declarations: List[FDeclaration]): StateError[Int] = {
    expr match {
      case CInt(v) => EitherT.pure(v)
      case Add(lhs, rhs) => {
        val Leval = eval(lhs, declarations)
        val Reval = eval(rhs, declarations)
        for {
          left <- Leval
          rigth <- Reval
        } yield left + rigth
      }

      case Mul(lhs, rhs) =>{
        val Leval = eval(lhs,declarations)
        val Reval = eval(rhs,declarations)
        for {
          left <- Leval
          rigth <- Reval
        } yield left * rigth
      }
     case Id(name) => lookupVar(name)

      case App(name, arg) => {
        val fdecl = lookup(name, declarations)
        val EvalArg = eval(arg, declarations)
        for {
          fdecl <- lookup(name,declarations)
          evalarg <- EvalArg
          declared <- declareVar((fdecl.arg,evalarg), Right(evalarg))
          result <- eval(fdecl.body, declarations)
        } yield result
      }
    }

    }
}

