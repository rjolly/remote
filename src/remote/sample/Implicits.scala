package remote.sample

import remote.Remote

object Implicits {
  implicit def remote2iterator[E](value: Remote[java.util.Iterator[E]]) = Iterator.Stub(value)
  implicit def remote2collection[E](value: Remote[java.util.Collection[E]]) = Collection.Stub(value)
  implicit def remote2set[E](value: Remote[java.util.Set[E]]) = Set.Stub(value)
  implicit def remote2sortedSet[E](value: Remote[java.util.SortedSet[E]]) = SortedSet.Stub(value)
  implicit def remote2comparator[T](value: Remote[java.util.Comparator[T]]) = Comparator.Stub(value)
}
