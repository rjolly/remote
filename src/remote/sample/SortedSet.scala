package remote.sample

import remote.Remote

object SortedSet {
  implicit class Stub[E](val value: Remote[java.util.SortedSet[E]]) extends Remote.Stub[java.util.SortedSet[E]] with StubLike[E]

  trait StubLike[E] extends Set.StubLike[E] with java.util.SortedSet[Remote[E]] {
    def value: Remote[java.util.SortedSet[E]]
    def comparator = ???
    def subSet(fromElement: Remote[E], toElement: Remote[E]) = Stub(for (obj <- value; fromElem <- fromElement; toElem <- toElement) yield obj.subSet(fromElem, toElem))
    def headSet(toElement: Remote[E]) = Stub(for (obj <- value; toElem <- toElement) yield obj.headSet(toElem))
    def tailSet(fromElement: Remote[E]) = Stub(for (obj <- value; fromElem <- fromElement) yield obj.tailSet(fromElem))
    def first = for (obj <- value) yield obj.first
    def last = for (obj <- value) yield obj.last
  }
}
