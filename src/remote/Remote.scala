package remote

import java.rmi.{Naming, RemoteException}
import java.rmi.server.UnicastRemoteObject

trait Remote[T] extends java.rmi.Remote {
  @throws[RemoteException]
  def map[S](f: T => S): Remote[S]

  @throws[RemoteException]
  def flatMap[S](f: T => Remote[S]): Remote[S]

  @throws[RemoteException]
  def get: T
}

object Remote {
  def apply[T](value: T): Remote[T] = new Impl(value)
  def rebind[T](name: String, value: T) = Naming.rebind(name, apply(value))
  def lookup[T](name: String) = Naming.lookup(name).asInstanceOf[Remote[T]]

  class Impl[T](val get: T) extends UnicastRemoteObject with Remote[T] {
    def map[S](f: T => S) = apply(f(get))
    def flatMap[S](f: T => Remote[S]) = f(get)
  }
}
