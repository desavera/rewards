/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */

package com.rewards.model

import scala.collection.mutable.ListBuffer


/*
 * a place holder for model entities and some utility methods to manipulate them.
 */
object Entities {

  /* 
   * a Member is part of the network. The isPart flag indicates it has received an invitation already.
   */
  final case class Member(id : Int,isPart : Boolean)

  /*
   * an Invitation means a tuple of {M,N} with M != N.
   */
  final case class Invitation(promoter : Int,invited : Int) {

     // the tuple separator as an env var so we can adapt a new value with no code change...
     val SEPARATOR:Option[String] = sys.env.get("REWARD_TUPLE_SEPARATOR")

     /** an implicit casting for formating a List of Invitation entities
      *
      *  @param it an iterator holding a String representation of tuples
      *  @return a list of Invitations
      */

 
     implicit def toInvitationsList(it : Iterator[String]) : List[Invitation] = {

       var invitations = ListBuffer[Invitation]()

       while (it.hasNext) {

         val element = it.next

         invitations += Invitation(element.split(SEPARATOR.get)(0).toInt,element.split(SEPARATOR.get)(1).toInt)

       }

       return invitations.toList

     }
  }
}

