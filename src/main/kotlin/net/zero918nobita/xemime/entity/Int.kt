package net.zero918nobita.xemime.entity

import net.zero918nobita.xemime.NodeType
import net.zero918nobita.xemime.ast.FatalException
import net.zero918nobita.xemime.ast.Node
import kotlin.Int as KInt
import kotlin.Double as KDouble

class Int(location: KInt, val value: KInt) : Numeric(location) {
  constructor(value: KInt) : this(0, value)

  override fun recognize() = NodeType.INT

  override fun toString() = value.toString()

  override fun equals(other: Any?): Boolean {
    return if (other is Int) {
      other.value == value
    } else {
      (other is Double) && value.toDouble() == other.value
    }
  }

  override fun hashCode() = value.hashCode()

  @Throws(FatalException::class)
  override fun add(location: KInt, rhs: Node) =
      when (rhs) {
        is Int -> Int(value + rhs.value)
        is Double -> Double(value + rhs.value)
        else -> throw FatalException(location, 106)
      }

  @Throws(FatalException::class)
  override fun sub(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> Int(value - rhs.value)
        is Double -> Double(value - rhs.value)
        else -> throw FatalException(location, 107)
      }

  @Throws(FatalException::class)
  override fun multiply(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> Int(value * rhs.value)
        is Double -> Double(value * rhs.value)
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun divide(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> Int(value / rhs.value)
        is Double -> Double(value / rhs.value)
        else -> throw FatalException(location, 109)
      }

  @Throws(FatalException::class)
  override fun mod(location: kotlin.Int, rhs: Node) =
      if (rhs is Int) {
        Int(value % rhs.value)
      } else {
        throw FatalException(location, 141)
      }

  @Throws(FatalException::class)
  override fun less(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value < rhs.value) Bool.T else Bool.Nil
        is Double -> if (value < rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun le(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value <= rhs.value) Bool.T else Bool.Nil
        is Double -> if (value <= rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun greater(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value > rhs.value) Bool.T else Bool.Nil
        is Double -> if (value > rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }

  @Throws(FatalException::class)
  override fun ge(location: kotlin.Int, rhs: Node) =
      when (rhs) {
        is Int -> if (value >= rhs.value) Bool.T else Bool.Nil
        is Double -> if (value >= rhs.value) Bool.T else Bool.Nil
        else -> throw FatalException(location, 108)
      }
}
