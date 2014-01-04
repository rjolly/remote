package remote

import java.rmi.server.UnicastRemoteObject

class RemoteImpl[T](val get: T) extends UnicastRemoteObject with Remote[T] {
  def map[S](f: T => S) = Remote(f(get))
  def flatMap[S](f: T => Remote[S]) = f(get)
}
