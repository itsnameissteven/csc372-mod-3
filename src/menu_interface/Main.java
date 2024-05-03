package menu_interface;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class Main extends Application  {
  MenuItem showDate, save, changeBackground, exit;
  MenuButton menuButton;
  Pane pane;
  TextField dateText;
  Label homeScreenLabel;
  HBox hbox;
  BorderPane borderPane;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    borderPane = new BorderPane();

    // Menu Button(s)
    showDate = new MenuItem("Show Date");
    save = new MenuItem("Save");
    changeBackground = new MenuItem("Change Background");
    exit = new MenuItem("Exit");

    // Menu Button Container
    menuButton = new MenuButton("Menu", null, showDate, save, changeBackground, exit);
    hbox = new HBox(menuButton);
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
    // Add background color
    hbox.setStyle("-fx-background-color: #3b67ad;");
    
    // Labels / txt
    homeScreenLabel = new Label("Select a menu option");
    homeScreenLabel.setAlignment(Pos.CENTER);
    dateText = new TextField("");
    dateText.setMaxWidth(250);
    
    // Add components to pane
    borderPane.setTop(hbox);
    borderPane.setCenter(homeScreenLabel);
    Scene scene = new Scene(borderPane, 500, 300);
    
    // Get todays date and set value to the screen
    showDate.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Date date = new Date();
        dateText.setText(String.valueOf(date));
        borderPane.getChildren().remove(homeScreenLabel);
        borderPane.setCenter(dateText);
      }
    });

    // Save current date to file
    save.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        // Prevent save if no date is currently displayed.
        if(dateText.getLength() == 0) {
          Alert alert =  new Alert(AlertType.ERROR, "There is nothing to save. Please select 'Show Date' from the menu bar and try again.");
          alert.showAndWait();
          return;
        }

        // Save file and display success message to user
        try {
          String fileName = "log.txt";
          FileOutputStream fileStream = new FileOutputStream(fileName);
          PrintWriter outFS = new PrintWriter(fileStream);
          outFS.println(dateText.getText());
          outFS.close();
          Alert alert =  new Alert(AlertType.CONFIRMATION, "File saved");
          alert.show();
        } catch (Exception e) {
          Alert errorAlert = new Alert(AlertType.ERROR, "Issue saving file, please check path.");
          errorAlert.showAndWait();
        }
      }
    });

    // Generate a new shade of green for each click using random numbers
    changeBackground.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        int red = new Random().nextInt(256);
        int green =255;
        int blue = new Random().nextInt(256);
        // Create background color and set style.
        BackgroundFill backgroundColor = new BackgroundFill(Color.rgb(red, green, blue), null, null);
        Background background = new Background(backgroundColor);
        borderPane.setBackground(background);
      }
    });

    // Close application.
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