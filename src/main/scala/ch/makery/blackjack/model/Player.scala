package ch.makery.blackjack.model

class Player(name: String) extends Person(name) { // 
  var money: Int = 40 // amount of money player starts with

  def placeBet(amount: Int): Boolean = {
    if (amount > money) { // to prevent players from betting more than they have
      false
    } else {
      money -= amount
      true
    }
  }

  def winBet(amount: Int): Unit = {
    money += amount * 2 // add the amount betted back to players money
  }
}
