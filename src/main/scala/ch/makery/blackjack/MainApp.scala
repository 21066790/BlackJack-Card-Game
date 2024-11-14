package ch.makery.blackjack

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.AnchorPane
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.scene.media.{Media, MediaPlayer}

object MainApp extends JFXApp {

  var difficultyLevel: String = "Easy" // Default difficulty level

  private var primaryStage: PrimaryStage = _
  // Centralized background music player
  val musicFile = "/ch/makery/blackjack/view/backgroundJazz.mp3"
  val media = new Media(getClass.getResource(musicFile).toString)
  val mediaPlayer = new MediaPlayer(media)
  mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE) // Loop the music
  mediaPlayer.play() // Start playing the music

  stage = new PrimaryStage {
    title = "Blackjack Game"
    primaryStage = this
    resizable = false
    scene = new Scene(loadFXML("/ch/makery/blackjack/view/StartScreen.fxml"), 800, 800)
  }

  def showGameScreen(): Unit = {
    primaryStage.scene = new Scene(loadFXML("/ch/makery/blackjack/view/GameScreen.fxml"), 800, 800)
  }

  def showStartScreen(): Unit = {
    primaryStage.scene = new Scene(loadFXML("/ch/makery/blackjack/view/StartScreen.fxml"), 800, 800)
  }

  def showInformationScreen(): Unit = {
    primaryStage.scene = new Scene(loadFXML("/ch/makery/blackjack/view/InformationScreen.fxml"), 800, 800)
  }

  private def loadFXML(fxml: String): AnchorPane = {
    val resource = getClass.getResource(fxml)
    if (resource == null) {
      throw new RuntimeException(s"Cannot load FXML file: $fxml")
    }
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    val root = loader.load().asInstanceOf[javafx.scene.layout.AnchorPane]
    new AnchorPane(root) // Convert JavaFX AnchorPane to ScalaFX AnchorPane
  }
}
