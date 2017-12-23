/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */


package com.rewards.model

import scala.collection.mutable.ListBuffer


object Entities {

  final case class Member(id : Int,isPart : Boolean)
  final case class Invitation(promoter : Int,invited : Int) {

     implicit def toInvitationsList(list : Iterator[String]) : List[Invitation] = {

       var invitations = ListBuffer[Invitation]()

       while (list.hasNext) {

         val element = list.next
         invitations += Invitation(element.split(" ")(0).toInt,element.split(" ")(1).toInt)

       }

       return invitations.toList

     }
  }
}

