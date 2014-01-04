package remote

import java.rmi.Naming

@remote trait Remote[T] extends java.rmi.Remote {
  def map[S](f: T => S): Remote[S]
  def flatMap[S](f: T => Remote[S]): Remote[S]
  def get: T
}

object Remote {
  def apply[T](value: T): Remote[T] = new RemoteImpl(value)
  def rebind[T](name: String, value: T) = Naming.rebind(name, apply(value))
  def lookup[T](name: String) = Naming.lookup(name).asInstanceOf[Remote[T]]
}
