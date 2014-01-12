package remote.sample

import remote.Remote
import java.util.TreeSet
import Implicits.remote2comparator

object SortedSet {
  def apply[E](comparator: java.util.Comparator[Remote[E]])(implicit context: Remote[Company]): java.util.SortedSet[Remote[E]] = comparator match {
    case comparator: Comparator.Stub[E] => (for (cmp <- Lazy(comparator.value)) yield {
      for (obj <- context; comp <- cmp) yield new TreeSet(comp)
    }).get
  }

  implicit class Stub[E](val value: Remote[java.util.SortedSet[E]]) extends Remote.Stub[java.util.SortedSet[E]] with StubLike[E]

  trait StubLike[E] extends Set.StubLike[E] with java.util.SortedSet[Remote[E]] {
    def value: Remote[java.util.SortedSet[E]]
    def comparator = for (obj <- value) yield obj.comparator.asInstanceOf[java.util.Comparator[E]]
    def subSet(fromElement: Remote[E], toElement: Remote[E]) = for (obj <- value; fromElem <- fromElement; toElem <- toElement) yield obj.subSet(fromElem, toElem)
    def headSet(toElement: Remote[E]) = for (obj <- value; toElem <- toElement) yield obj.headSet(toElem)
    def tailSet(fromElement: Remote[E]) = for (obj <- value; fromElem <- fromElement) yield obj.tailSet(fromElem)
    def first = for (obj <- value) yield obj.first
    def last = for (obj <- value) yield obj.last
  }
}
