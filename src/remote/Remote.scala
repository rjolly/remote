package remote

trait Remote[T] extends java.rmi.Remote {
  @throws[java.rmi.RemoteException]
  def map[S](f: T => S): Remote[S]

  @throws[java.rmi.RemoteException]
  def flatMap[S](f: T => Remote[S]): Remote[S]

  @throws[java.rmi.RemoteException]
  def get: T
}

object Remote {
  def apply[T](get: T): Remote[T] = new RemoteImpl(get)
}
