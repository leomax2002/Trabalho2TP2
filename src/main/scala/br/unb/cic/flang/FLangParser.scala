package br.unb.cic.flang

import scala.util.parsing.combinator._
import Declarations._
import EH_and_StateMonad._

object FLangParser extends RegexParsers {
  def int: Parser[CInt] = """\d+""".r ^^ { s => CInt(s.toInt) }
  def variable: Parser[Id] = """[a-zA-Z][a-zA-Z0-9]*""".r ^^ Id

  def expr: Parser[Expr] = ifExpr | addExpr | mulExpr | appExpr | int | variable
  
  def addExpr: Parser[Expr] = term ~ rep("+" ~ term) ^^ {
    case firstTerm ~ restTerms => restTerms.foldLeft(firstTerm) {
      case (acc, "+" ~ term) => Add(acc, term)
    }
  }

  def mulExpr: Parser[Expr] = factor ~ rep("*" ~ factor) ^^ {
    case firstFactor ~ restFactors => restFactors.foldLeft(firstFactor) {
      case (acc, "*" ~ factor) => Mul(acc, factor)
    }
  }

  def appExpr: Parser[Expr] = variable ~ "(" ~ expr ~ ")" ^^ {
    case func ~ "(" ~ arg ~ ")" => App(func.name, arg)
  }

  def ifExpr: Parser[Expr] = "if" ~ expr ~ "then" ~ expr ~ "else" ~ expr ^^ {
    case "if" ~ cond ~ "then" ~ th ~ "else" ~ el => IfThenElse(cond, th, el)
  }

  def term: Parser[Expr] = factor ~ rep("*" ~ factor) ^^ {
    case firstFactor ~ restFactors => restFactors.foldLeft(firstFactor) {
      case (acc, "*" ~ factor) => Mul(acc, factor)
    }
  }

  def factor: Parser[Expr] = int | variable | "(" ~> expr <~ ")"

  def parseExpr(input: String): ParseResult[Expr] = parseAll(expr, input)
}

