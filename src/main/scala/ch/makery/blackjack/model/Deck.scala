package ch.makery.blackjack.model

import scala.util.Random

class Deck {
  private val suits = List("Hearts", "Diamonds", "Clubs", "Spades")
  private val ranks = List("2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace")
  private var cards: List[Card] = List() // list to hold cards in the deck

  def shuffle(): Unit = { // Method to shuffle the deck, initializing it with all 52 cards in a random order
    cards = for {
      suit <- suits
      rank <- ranks
    } yield Card(suit, rank)
    cards = Random.shuffle(cards)// Shuffle the list of cards
  }

  def dealCard(): Card = {
    if (cards.isEmpty) {
      shuffle() // shuffle if deck is empty
    }
    val card = cards.head // Get the top card from the deck
    cards = cards.tail // Remove the top card from the deck
    card // Return the dealt card
  }
}
