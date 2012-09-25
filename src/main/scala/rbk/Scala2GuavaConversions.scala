package rbk

import com.google.common.base.{Function => GuavaFunction}
object Scala2GuavaConversions {

  implicit def scalaFunction2GuavaFunction[K, V](fn: (K) => V) =
    new GuavaFunction[K, V] {
      def apply(key: K) = fn(key)
    }
}