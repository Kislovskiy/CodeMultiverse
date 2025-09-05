Notes from "Programming in Scala, Fifth Edition"

# Chapter 10: Composition and Inheritance

Composition means one clas holds a reference to another, using hte referenced class to help it fulfill its missions.
Inheritance is the superclass/subclass relationship.

Combinators are composing operators that combine elements of some domain into new elements.

Thinking in terms of combinators is generally a good way to approach library design: it pays to think about the fundamental ways to construct objects in an application domain.
What are the simple objects?
In what ways can more interesting objects be constructed out of simpler ones?
How do combinators hang together?
What are the most general combinations?
Do they satisfy any interesting laws?

Methods that have implementation are called concrete.