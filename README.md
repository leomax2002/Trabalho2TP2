# Trabalho2TP2
Repositório para o Projeto da Disciplina (Primeira Entrega) Técnicas de Programação 2 da Universidade de Brasília (UnB)

Esse Projeto tem como objetivo aprimorar a Linguagem de Programação ilustrativa FLang por meio do uso dos idiomas Monads e Monads Transformers. A implementação foi realizada em Scala.

Foram Implementadas 4 Atividades contidas em 4 Branches presentes nesse Repositório:

1.state-with-cats: Implementa uma Monad State pertencente à biblioteca Scala With Cats para se solucionar a declaração de variáveis na linguagem FLang;

2.State-and-EH-with-cats: Considerando o idioma de Monad Transformers e a biblioteca Scala With Cats, utiliza uma stack de monads composta por uma State Monad e uma Error Monad para se lidar com a declaração de variáveis e possíveis erros na FLang; 

3.ifThenElse: Inclue o suporte a uma nova expressão ifThenElse à branch anterior. Também foi elaborada uma forma de se representar tipos booleanos na linguagem FLang;

4.Parser: Definiu-se uma sintaxe concreta para a linguagem FLang e, assim, implementou-se um parser por meio das bibliotecas de Parser Combinator para Scala.

## Link para a VideoAula e para o repositório
[Link para a videoaula:] ()

[Link para o repositório:] (https://github.com/leomax2002/Trabalho2TP2)
## Integrantes
Lucas Fernandes - 180022563 

Matheus Guaraci - 180046951 

Leonardo Maximo - 200022172 

João Vitor Dickmann - 211042757 

Lucas Seabra - 170039951 
## Execução do Código
Para executar os programas basta utilizar o procedimento de compilação padrão do sbt do Scala: Dentro da pasta correta, utilizar o comando compile para compilar o código e test para executar a suite de testes.

O código foi implementado por meio da plataforma inteij utilizando Scala 2.13.14. A IDE inteij apresenta as estruturas necessárias de compilação, de modo que utilizou-se o terminal contido na IDE para se executar os comandos para se compilar e executar o código.

(obs: para a leitura de arquivo é necessário o nome completo do arquivo, ou seja seu PATH Absoluto)

## Referências
[Noel Welsh. Functional Programming Strategies In Scala with Cats. e-book,
2024.] (https://scalawithcats.com)

[Wadler, P. (1993). Monads for functional programming. In: Broy, M. (eds) Program Design Calculi. NATO ASI Series, vol 118. Springer, Berlin, Heidelberg. https://doi.org/10.1007/978-3-662-02880-3_8] (https://cs.brown.edu/courses/cs173/2012/book/book.pdf)

[Monads for functional programming] (https://homepages.inf.ed.ac.uk/wadler/papers/marktoberdorf/baastad.pdf)

[Repositório FLanguage. Rodrigo Bonifácio. 2024] (https://github.com/rbonifacio/FLanguage/tree/main)

