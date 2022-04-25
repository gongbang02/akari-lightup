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
    HBox controlLayout = new HBox();
    controlLayout.getStylesheets().add("controlLayout");

    Button reset = new Button("Reset");
    reset.getStylesheets().add("button");
    reset.getStylesheets().add("button-reset");
    reset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    controlLayout.getChildren().add(reset);

    Button random = new Button("Random Puzzle");
    reset.getStylesheets().add("button");
    reset.getStylesheets().add("button-random-puzzle");
    random.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    controlLayout.getChildren().add(random);

    Button solve = new Button("Click to Solve!");
    solve.setOnAction(
        (ActionEvent event) -> {
          controller.clickDone();
        });
    solve.getStylesheets().add("button");
    solve.getStylesheets().add("button-solve");
    controlLayout.getChildren().add(solve);

    Label result;
    if (model.isSolved()) {
      result = new Label("Congratulations! You have solved the puzzle");
    } else {
      result = new Label();
    }
    result.getStylesheets().add("label");
    result.getStylesheets().add("label-result");
    controlLayout.getChildren().add(result);

    return controlLayout;
  }

  @Override
  public void update(Model model) {
    this.model = model;
  }
}
