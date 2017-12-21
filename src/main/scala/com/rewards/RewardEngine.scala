package rewards

import rewards.model._

import scala.collection.immutable.Stack

class Dag[T](val root : Node[T])

class Node[T](val parents: List[Node[T]], val children: List[Node[T]], val value : T)

class DagIterator[T](val dag: Dag[T]) extends Iterator {

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

object RewardEngine {

  val dag = new DAG[Member]()

  def calculateRewards(invitations : List[Invitation]): String {


    "TODO"

  }

}
