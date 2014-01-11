package remote.sample

import remote.Remote

object Set {
  implicit class Stub[E](val value: Remote[java.util.Set[E]]) extends Remote.Stub[java.util.Set[E]] with StubLike[E]

  trait StubLike[E] extends Collection.StubLike[E] with java.util.Set[Remote[E]] {
    def value: Remote[java.util.Set[E]]
  }
}
