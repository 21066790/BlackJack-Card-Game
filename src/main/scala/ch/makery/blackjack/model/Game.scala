package ch.makery.blackjack.model

class Game {
  val player = new Player("Player") //initializing player,dealer and deck
  val dealer = new Dealer("Dealer")
  private val deck = new Deck()

  def initialDeal(): Unit = {
    player.clearHand() //clear hand of player and dealer
    dealer.clearHand()
    player.receiveCard(deck.dealCard()) // deal first 2 cards to both player and dealer
    player.receiveCard(deck.dealCard())
    dealer.receiveCard(deck.dealCard())
    dealer.receiveCard(deck.dealCard())
  }

  def hit(person: Person): Unit = {
    person.receiveCard(deck.dealCard())  // method to deal card to player or dealer
  }

  def stand(bet: Int, dealerStandThreshold: Int): String = {
    while (dealer.shouldHit(dealerStandThreshold)) {
      dealer.receiveCard(deck.dealCard())  // Dealer hits until they reach the threshold
    }

    val playerScore = player.score
    val dealerScore = dealer.score

    // determining outcomes of the game based on player and dealer scores after player stands
    if (dealerScore > 21) {
      player.winBet(bet)
      s"Dealer Bust! Player Wins! You won $bet points."
    } else if (playerScore > dealerScore) {
      player.winBet(bet)
      s"Player Wins! You won $bet points."
    } else if (playerScore < dealerScore) {
      s"Dealer Wins! You lost $bet points."
    } else {
      player.money += bet
      s"It's a draw! You get your $bet points back."
    }
  }
}
