package remote.client

trait Lazy[T] {
  def map[S](f: T => S) = Lazy(f(get))
  def flatMap[S](f: T => Lazy[S]) = f(get)
  def get: T
}

object Lazy {
  def apply[T](value: => T): Lazy[T] = new Lazy[T] {
    lazy val get = value
  }
}
