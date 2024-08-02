package br.unb.cic.flang

import org.scalatest._
import flatspec._
import matchers._

import Interpreter._
import Declarations._
import EH_and_StateMonad._

class InterpreterTest extends AnyFlatSpec with should.Matchers {

  val inc = FDeclaration("inc", "x", Add(Id("x"), CInt(1)))
  val bug = FDeclaration("bug", "x", Add(Id("y"), CInt(1)))

  val declarations = List(inc, bug)


  val initialState: List[(String,Int)] = List(("",0))

  "eval CInt(5)" should "return an integer value 5." in {
    val c5 = CInt(5)
    val res = eval(c5, declarations).value.runA(initialState).value
    res should be (Right(5))
  }

  "eval Add(CInt(5), CInt(10)) " should "return an integer value 15." in {
    val c5  = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, c10)
    val res = eval(add, declarations).value.runA(initialState).value
    res should be (Right(15))
  }

  "eval Add(CInt(5), Add(CInt(5), CInt(10))) " should "return an integer value 20." in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, Add(c5, c10))
    val res = eval(add, declarations).value.runA(initialState).value
    res should be(Right(20))
  }

  "eval Mul(CInt(5), CInt(10))" should "return an integer value 50" in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val mul = Mul(c5, CInt(10))
    val res = eval(mul, declarations).value.runA(initialState).value
    res should be(Right(50))
  }

  "eval App(inc, 99) " should "return an integer value 100" in {
    val app = App("inc", CInt(99))
    val res = eval(app, declarations).value.runA(initialState).value
    res should be (Right(100))
  }

  "eval App(foo, 10) " should "raise an error." in {
    val app = App("foo", CInt(10))
    assertError(eval(app, declarations)) should be (true)
  }

  "eval Add(5, App(bug, 10)) " should "raise an error." in {
    val c5  = CInt(5)
    val app = App("bug", CInt(10))
    val add = Add(c5, app)
    assertError(eval(add, declarations)) should be (true)
  }

  "eval Add(App(inc, CInt(5)), CInt(2)) " should "return an integer value 8." in {
    val c2 = CInt(2)
    val app = App("inc", CInt(5))
    val add = Add(app, c2)
    val res = eval(add, declarations).value.runA(initialState).value
    res should be(Right(8))
  }

  "eval IfThenElse(CInt(0), CInt(0), Add(CInt(5), CInt(1)))" should "return an integer value 6" in { //condição falsa
    val c5 = CInt(5)
    val c1 = CInt(1)
    val c0 = CInt(0)
    val add = Add(c5, c1)
    val iF = IfThenElse(c0, c0, add)
    val res = eval(iF, declarations).value.runA(initialState).value
    res should be(Right(6))
  }

  "eval IfThenElse(CInt(1), App(inc, 12) , Add(CInt(5), CInt(1)))" should "return an integer value 13" in { //condição verdadeira
    val c5 = CInt(5)
    val c1 = CInt(1)
    val c12 = CInt(12)
    val add = Add(c5, c1)
    val app = App("inc", c12)
    val iF = IfThenElse(c1, app, add)
    val res = eval(iF, declarations).value.runA(initialState).value
    res should be(Right(13))
  }

}
