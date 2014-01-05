package remote

import java.lang.ref.{Reference, WeakReference}
import java.rmi.Naming
import java.util.{Map, WeakHashMap}

@remote trait Remote[T] extends java.rmi.Remote {
  def map[S](f: T => S): Remote[S]
  def flatMap[S](f: T => Remote[S]): Remote[S]
  def get: T
}

object Remote {
  val cache: Map[java.rmi.Remote, Reference[Object]] = new WeakHashMap[java.rmi.Remote, Reference[Object]]()

  def apply[T](value: T): Remote[T] = {
    val obj = new RemoteImpl(value)
    cache.put(obj, new WeakReference(obj))
    obj
  }

  def replace[T](obj: Remote[T]): Object = {
    val w = cache.get(obj)
    if (w == null) obj else w.get()
  }

  def rebind[T](name: String, value: T) = Naming.rebind(name, apply(value))
  def lookup[T](name: String) = Naming.lookup(name).asInstanceOf[Remote[T]]

  trait Stub[T] {
    def value: Remote[T]
    override def toString = (for (obj <- value) yield obj.toString).get
  }
}
