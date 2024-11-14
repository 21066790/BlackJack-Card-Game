package ch.makery.blackjack.view

import ch.makery.blackjack.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml

@sfxml
class StartController(
                       private val easyButton: Button,
                       private val hardButton: Button,
                       private val newGameButton: Button,
                       private val muteButton: Button // Adding the mute button
                     ) {

  def initialize(): Unit = {
    if (MainApp.difficultyLevel == "Easy") {
      easyButton.disable = true
      hardButton.disable = false
    } else {
      easyButton.disable = false
      hardButton.disable = true
    }

    // Set the initial state of the mute button based on the media player state
    if (MainApp.mediaPlayer.isMute) {
      muteButton.text = "Unmute"
    } else {
      muteButton.text = "Mute"
    }
  }

  def setEasyDifficulty(event: ActionEvent): Unit = {
    MainApp.difficultyLevel = "Easy"
    easyButton.disable = true
    hardButton.disable = false
  }

  def setHardDifficulty(event: ActionEvent): Unit = {
    MainApp.difficultyLevel = "Hard"
    easyButton.disable = false
    hardButton.disable = true
  }

  def startNewGame(event: ActionEvent): Unit = {
    MainApp.showGameScreen()
  }

  def showInformation(event: ActionEvent): Unit = {
    MainApp.showInformationScreen()
  }

  // Method to toggle the background music
  def toggleMusic(event: ActionEvent): Unit = {
    if (MainApp.mediaPlayer.isMute) {
      MainApp.mediaPlayer.setMute(false)
      muteButton.text = "Mute"
    } else {
      MainApp.mediaPlayer.setMute(true)
      muteButton.text = "Unmute"
    }
  }
}
