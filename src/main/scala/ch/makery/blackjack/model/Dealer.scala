package ch.makery.blackjack.model

class Dealer(name: String) extends Person(name) {

  def shouldHit(threshold: Int): Boolean = {
    score < threshold
  }
}
