package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlView implements FXComponent {
  private final ClassicMvcController controller;
  private Model model;

  public ControlView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox controlView = new VBox();

    HBox controlLayout = new HBox();

    Button reset = new Button("Reset");
    reset.getStyleClass().add("button-reset");
    reset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    controlLayout.getChildren().add(reset);

    Button random = new Button("Random Puzzle");
    random.getStyleClass().add("button-random-puzzle");
    random.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    controlLayout.getChildren().add(random);
    controlView.getChildren().add(controlLayout);

    Label result;
    if (model.isSolved()) {
      result = new Label("Congratulations! You have solved the puzzle");
    } else {
      result = new Label();
    }
    result.getStyleClass().add("label-result");
    controlView.getChildren().add(result);

    return controlView;
  }

  @Override
  public void update(Model model) {
    this.model = model;
  }
}
