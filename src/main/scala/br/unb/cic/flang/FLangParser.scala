package br.unb.cic.flang

import scala.util.parsing.combinator._
import Declarations._

object FLangParser extends RegexParsers {
  def int: Parser[CInt] = """\d+""".r ^^ { s => CInt(s.toInt) }
  def variable: Parser[Id] = """[a-zA-Z][a-zA-Z0-9]*""".r ^^ Id

  def expr: Parser[Expr] = ifExpr | funcDecl | addExpr | mulExpr | appExpr | int | variable 
  def factor: Parser[Expr] = int | variable | "(" ~> expr <~ ")"  
  def term: Parser[Expr] = factor ~ rep("*" ~ factor) ^^ {
    case firstFactor ~ restFactors => restFactors.foldLeft(firstFactor) {
      case (acc, "*" ~ factor) => Mul(acc, factor)
    }
  }

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

  //func inc(x) = x + 1
  def funcDecl: Parser[Expr] = "func" ~ variable ~ "(" ~ variable ~ ")" ~ "=" ~ expr ^^ {
    case "func" ~ name ~ "(" ~ param ~ ")" ~ "=" ~ body => FuncDecl(name.name, param.name, body)
  }

  def parseExpr(input: String): ParseResult[Expr] = parseAll(expr, input)
}
