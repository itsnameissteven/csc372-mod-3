package menu_interface;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

// When a user selects option 1 the date and time should show in a printed text box
// when a user selects option 2 the text box contents should be written to a text file named 'log.txt'
// when a user selects option 3 the frame background changes to a random color hue of green
// when a user selects option 4 the program exits.
// style the application

public class Main extends Application  {
  MenuItem showDate, save, changeBackground, exit;
  MenuButton menuButton;
  Pane pane;
  TextField dateText;
  Label homeScreenLabel;
  HBox hbox;
  GridPane gridPane;
  @Override
  public void start(Stage primaryStage) throws Exception {
    gridPane = new GridPane();
    Scene scene = new Scene(gridPane);

    // Menu Button
    showDate = new MenuItem("Show Date");
    save = new MenuItem("Save");
    save.isDisable();
    changeBackground = new MenuItem("Change Background");
    exit = new MenuItem("Exit");
    // Menu Button Container
    menuButton = new MenuButton("Menu", null, showDate, save, changeBackground, exit);
    hbox = new HBox(menuButton);

    homeScreenLabel = new Label("Select a menu option");
    dateText = new TextField("");

    // pane = new Pane();
    gridPane.add(hbox, 0,0);
    gridPane.add(homeScreenLabel, 0, 1);

    showDate.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Date date = new Date();
        dateText.setText(String.valueOf(date));
        gridPane.getChildren().remove(homeScreenLabel);
        gridPane.add(dateText, 0,1);
      }
    });

    save.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if(dateText.getLength() == 0) {
          Alert alert =  new Alert(AlertType.ERROR, "Select a date first");
          alert.showAndWait();
        }
        try {
          String fileName = "log.txt";
          FileOutputStream fileStream = new FileOutputStream(fileName);
          PrintWriter outFS = new PrintWriter(fileStream);
          outFS.println(dateText.getText());
          outFS.close();
        } catch (Exception e) {
          Alert errorAlert = new Alert(AlertType.ERROR, "Issue saving file, check path.");
          errorAlert.showAndWait();
        }
      }
    });

    changeBackground.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int red = new Random().nextInt(256);
        int green =255;
        int blue = new Random().nextInt(256);
        BackgroundFill backgroundColor = new BackgroundFill(Color.rgb(red, green, blue), null, null);
        Background background = new Background(backgroundColor);
        gridPane.setBackground(background);
      }
    });

    exit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        primaryStage.close();
      }
    });



    primaryStage.setTitle("User Interface");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}