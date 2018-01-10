/*
 * Nubank test - Dec 2017
 *
 * author : Mário de Sá Vera
 */


package com.rewards

import com.rewards.model.Entities._

/* the Rewards engine that visits the DAG and calculates each Member reward. **/
object RewardEngine {

  /** Calculates a reward based on a set with invitations in the "invitee - invited" format.
   *
   *  @param invitations the list with invitations
   *  @return a string with the list of rewards for each invitee
   */
  def calculateRewards(invitations : List[Invitation]): String = {

    return "TODO"

  }
}
