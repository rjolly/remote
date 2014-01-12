package remote.sample

import remote.Remote

object Implicits {
  implicit def remote2iterator[E](value: Remote[java.util.Iterator[E]]) = Iterator.Stub(value)
  implicit def remote2comparator[T](value: Remote[java.util.Comparator[T]]) = Comparator.Stub(value)
}
