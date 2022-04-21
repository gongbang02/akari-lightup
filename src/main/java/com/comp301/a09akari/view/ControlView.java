package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    VBox controlLayout = new VBox();
    controlLayout.getStylesheets().add("controlLayout");

    Button solve = new Button("Solve!");
    solve.setOnAction(
        (ActionEvent event) -> {
          controller.clickDone();
        });
    controlLayout.getChildren().add(solve);

    Button reset = new Button("Reset");
    reset.setOnAction(
        (ActionEvent event) -> {
          controller.clickResetPuzzle();
        });
    controlLayout.getChildren().add(reset);

    HBox controls = new HBox();
    Button prev = new Button("\u25C0");
    prev.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    controls.getChildren().add(prev);
    Button random = new Button("Random Puzzle");
    random.setOnAction(
        (ActionEvent event) -> {
          controller.clickRandPuzzle();
        });
    controls.getChildren().add(random);
    Button next = new Button("\u25B6");
    next.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    controls.getChildren().add(next);
    controlLayout.getChildren().add(controls);

    return controlLayout;
  }

  @Override
  public void update(Model model) {
    this.model = model;
  }
}
