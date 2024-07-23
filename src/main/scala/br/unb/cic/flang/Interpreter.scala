package br.unb.cic.flang

import Declarations._
import StateMonad._
import cats.data.State
import State._


object Interpreter {

  /** This implementation relies on a state monad.
   *
   * Here we replace the substitution function (that needs to traverse the AST
   * twice during interpretation), by a 'global' state that contains the
   * current 'bindings'. The bindings are pairs from names to integers.
   *
   * We only update the state when we are interpreting a function application.
   * This implementation deals with sections 6.1 and 6.2 of the book
   * "Programming Languages: Application and Interpretation". However, here we
   * use a monad state, instead of passing the state explicitly as an agument
   * to the eval function.
   *
   * Sections 6.3 and 6.4 improves this implementation. We will left such an
   * improvements as an exercise.
   */

  def eval(expr: Expr, declarations: List[FDeclaration]): State[S,Integer] = {
    expr match {
      case CInt(v) =>  State.pure[S,Integer](v)
      case Add(lhs, rhs) =>{
        val Leval = eval(lhs,declarations)
        val Reval = eval(rhs,declarations)
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
      case Id(name) => State[S,Integer] { state =>
        val result = lookupVar(name, state)
        (state, result)
      }
      case App(name, arg) => {
        val fdecl = lookup(name, declarations)
        val EvalArg = eval(arg, declarations)
        for {
          evalarg <- EvalArg
          state <- get[S]
          _ <- set[S](declareVar(fdecl.arg, evalarg, state))
          result <- eval(fdecl.body, declarations)
        } yield result
      }
    }

  }
}

