package ch.makery.blackjack.model

import scala.collection.mutable.ListBuffer

abstract class Person(val name: String) {
  var hand: ListBuffer[Card] = ListBuffer()

  def receiveCard(card: Card): Unit = {
    hand += card
  }

  def clearHand(): Unit = {
    hand.clear()
  }

  def score: Int = {
    var total = 0
    var aces = 0

    hand.foreach { card =>
      if (card.rank == "Ace") aces += 1
      total += (card.rank match {
        case "Ace" => 11
        case "King" | "Queen" | "Jack" => 10
        case _ => card.rank.toInt
      }) // to calculate score to keep track easily in game
    }

    while (total > 21 && aces > 0) {
      total -= 10
      aces -= 1
    } // if score is over 21 and player has an ace, the ace can act as a 1

    total
  }

}
