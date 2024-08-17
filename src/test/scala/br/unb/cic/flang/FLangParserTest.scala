package br.unb.cic.flang

import org.scalatest._
import flatspec._
import matchers._
import Declarations._
import FLangParser._

class FLangParserTest extends AnyFlatSpec with should.Matchers {
  val inc = FuncDecl("inc", "x", Add(Id("x"), CInt(1)))

  "The FLangParser" should "parse integer literals" in {
    val input = "5"
    val result = FLangParser.parseExpr(input)
    result shouldBe a[FLangParser.Success[_]]
    result.get shouldEqual CInt(5)
  }

  it should "parse variable identifiers" in {
    val input = "x"
    val result = FLangParser.parseExpr(input)
    result shouldBe a[FLangParser.Success[_]]
    result.get shouldEqual Id("x")
  }

  it should "parse addition expressions" in {
    val input = "1 + 2"
    val result = FLangParser.parseExpr(input)
    result shouldBe a[FLangParser.Success[_]]
    result.get shouldEqual Add(CInt(1), CInt(2))
  }

  it should "parse multiplication expressions" in {
    val input = "3 * 4"
    val result = FLangParser.parseExpr(input)
    result shouldBe a[FLangParser.Success[_]]
    result.get shouldEqual Mul(CInt(3), CInt(4))
  }

  it should "parse if-then-else expressions" in {
    val input = "if 0 then 1 else 2"
    val result = FLangParser.parseExpr(input)
    result shouldBe a[FLangParser.Success[_]]
    result.get shouldEqual IfThenElse(CInt(0), CInt(1), CInt(2))
  }

  it should "parse function declarations" in {
    val input = "func inc(x) = x + 1"
    val result = FLangParser.parseExpr(input)
    result shouldBe a[FLangParser.Success[_]]
    result.get shouldEqual FuncDecl("inc", "x", Add(Id("x"), CInt(1)))
  }

  it should "parse nested function declarations" in {
    val input = "func double(x) = func add(y) = y + y"
    val result = FLangParser.parseExpr(input)
    result shouldBe a[FLangParser.Success[_]]
    result.get shouldEqual 
      FuncDecl("double", "x", 
        FuncDecl("add", "y", Add(Id("y"), Id("y"))))
  }
}
