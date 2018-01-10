/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */


package com.rewards.model

import scala.collection.immutable.Stack
import scala.collection.Iterator

/*
 * a scratch of a DAG implementation. It is still missing :
 *
 * - adding method with cycle detection exceptions
 * - the rewards calculation algo while visiting the DAG topology (please see the README)
 */
class Dag[T](val root : Node[T])

class Node[T](val parents: List[Node[T]], val children: List[Node[T]], val value : T)

class DagIterator[T](val dag: Dag[T]) extends Iterator[T] {

  val nodes : Stack[Node[T]] = new Stack[Node[T]]

  nodes.push(dag.root)

  override def hasNext: Boolean = !nodes.isEmpty

  override def next(): T = {
    val node = nodes.top
    for (child <- node.children){
      nodes.push(child);
    }
    node.value
  }
}
