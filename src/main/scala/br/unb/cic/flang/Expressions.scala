package br.unb.cic.flang

sealed trait Expr

case class CInt(v: Int) extends Expr
case class Add(lhs: Expr, rhs: Expr) extends Expr
case class Mul(lhs: Expr, rhs: Expr) extends Expr
case class Id(name: String) extends Expr
case class App(name: String, arg: Expr) extends Expr
case class IfThenElse(cond: Expr, th: Expr, el: Expr) extends Expr
case class FuncDecl(name: String, param: String, body: Expr) extends Expr
