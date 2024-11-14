package ch.makery.blackjack.view

import ch.makery.blackjack.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml

@sfxml
class InformationController(
                             private val muteButton: Button // Mute button
                           ) {

  def goBack(event: ActionEvent): Unit = {
    MainApp.showStartScreen() // go back to main screen
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
}
