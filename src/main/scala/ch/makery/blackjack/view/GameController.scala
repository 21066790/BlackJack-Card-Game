package ch.makery.blackjack.view

import ch.makery.blackjack.MainApp
import ch.makery.blackjack.model.Game
import scalafx.event.ActionEvent
import scalafx.scene.control.{Alert, Button, Label, ProgressBar, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.HBox
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class GameController(
                      private val playerCards: HBox,
                      private val dealerCards: HBox,
                      private val playerScore: Label,
                      private val dealerScore: Label,
                      private val message: Label,
                      private val hitButton: Button,
                      private val standButton: Button,
                      private val newGameButton: Button,
                      private val betAmount: TextField,
                      private val playerMoney: Label,
                      private val progressBar: ProgressBar,
                      private val muteButton: Button // Mute button
                    ) {
  private val game = new Game()
  private var currentBet: Int = 0
  private var stage: Stage = _

  def setStage(stage: Stage): Unit = {
    this.stage = stage
  }

  def toggleMusic(event: ActionEvent): Unit = {
    if (MainApp.mediaPlayer.isMute) {
      MainApp.mediaPlayer.setMute(false)
      muteButton.text = "Mute"
    } else {
      MainApp.mediaPlayer.setMute(true)
      muteButton.text = "Unmute"
    }
  }

  def newRound(): Unit = {
    if (betAmount.text().nonEmpty && betAmount.text().toInt > 0) {
      placeBetAndStart()
    } else {
      message.text = "Please enter a valid bet amount."
    }
  }

  def hit(event: ActionEvent): Unit = {
    game.hit(game.player)
    updateUI()
    if (game.player.score > 21) { // if player reaches 21 after hitting they bust and lose
      message.text = "Player Bust! Dealer Wins!"
      disableGameButtons()
      checkForLoss()
    }
  }

  def stand(event: ActionEvent): Unit = {
    val dealerStandThreshold = MainApp.difficultyLevel match {
      case "Easy" => 13
      case "Hard" => 17
    }

    val result = game.stand(currentBet, dealerStandThreshold)  // Pass both currentBet and dealerStandThreshold
    message.text = result
    disableGameButtons()
    updateUI()
    checkForWin()
    checkForLoss()
  }

  private def updateUI(): Unit = {
    updatePlayerCards()
    updateDealerCards()
    playerScore.text = s"Score: ${game.player.score}"
    dealerScore.text = s"Score: ${game.dealer.score}"
    playerMoney.text = s"Points: ${game.player.money}"
    progressBar.progress = game.player.money / 100.0
  }

  private def updatePlayerCards(): Unit = {
    playerCards.children.clear()
    game.player.hand.foreach { card =>
      val imageView = new ImageView(new Image(getClass.getResourceAsStream(s"/ch/makery/blackjack/view/cards/${card.imageFileName}")))
      imageView.setFitWidth(100)
      imageView.setFitHeight(150)
      playerCards.children.add(imageView)
    }
  }

  private def updateDealerCards(): Unit = {
    dealerCards.children.clear()
    game.dealer.hand.foreach { card =>
      val imageView = new ImageView(new Image(getClass.getResourceAsStream(s"/ch/makery/blackjack/view/cards/${card.imageFileName}")))
      imageView.setFitWidth(100)
      imageView.setFitHeight(150)
      dealerCards.children.add(imageView)
    }
  }

  private def placeBetAndStart(): Unit = {
    val bet = betAmount.text().toInt
    if (game.player.placeBet(bet)) {
      currentBet = bet
      message.text = s"Bet placed: $bet"
      game.initialDeal()
      updateUI()
      enableGameButtons()
    } else {
      message.text = "Insufficient funds!"
    }
  }

  private def checkForWin(): Unit = {
    if (game.player.money >= 100) {
      val alert = new Alert(Alert.AlertType.Information) {
        initOwner(stage)
        title = "Congratulations!"
        headerText = "You have reached 100 points and won the game!"
        contentText = "Game over. Congratulations on your victory!"
      }
      alert.showAndWait()
      disableGameButtons()
      newGameButton.disable = true
      MainApp.showStartScreen() // Go back to the starting screen
    }
  }

  private def checkForLoss(): Unit = {
    if (game.player.money <= 0) {
      val alert = new Alert(Alert.AlertType.Information) {
        initOwner(stage)
        title = "Game Over"
        headerText = "You have lost all your points!"
        contentText = "Game over. Better luck next time!"
      }
      alert.showAndWait()
      disableGameButtons()
      newGameButton.disable = true
      MainApp.showStartScreen() // Go back to the starting screen
    }
  }

  private def disableGameButtons(): Unit = {
    hitButton.disable = true
    standButton.disable = true
    newGameButton.disable = false
  }

  private def enableGameButtons(): Unit = {
    hitButton.disable = false
    standButton.disable = false
    newGameButton.disable = true
  }
}
