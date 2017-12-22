package com.rewards.model

object Entities {

  final case class Member(id : Int,isPart : Boolean)
  final case class Invitation(promoter : Int,invited : Int)

}

