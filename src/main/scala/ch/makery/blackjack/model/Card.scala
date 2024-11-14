package ch.makery.blackjack.model

case class Card(suit: String, rank: String) {
  def value: Int = rank match { // assigning cards value based of their rank
    case "2" => 2
    case "3" => 3
    case "4" => 4
    case "5" => 5
    case "6" => 6
    case "7" => 7
    case "8" => 8
    case "9" => 9
    case "10" | "Jack" | "Queen" | "King" => 10
    case "Ace" => 11 // initially worth 11 but if score over 21 can be changed to a 1
  }

  def imageFileName: String = s"${rank.toLowerCase}_of_${suit.toLowerCase}.png" // to create the name to the file in Cards

}