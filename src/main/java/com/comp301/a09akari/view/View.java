package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private ClassicMvcController controller;
  private Model model;

  public View(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public void update(Model model) {
    this.model = model;
  }

  @Override
  public Parent render() {
    BorderPane viewLayout = new BorderPane();

    VBox labelLayout = new VBox();
    Label title = new Label("LIGHT UP THE CELLS! ");
    title.getStyleClass().add("label-title");
    labelLayout.getChildren().add(title);
    Label total = new Label(String.valueOf(model.getPuzzleLibrarySize()) + " Puzzles in Total");
    total.getStyleClass().add("label-total");
    labelLayout.getChildren().add(total);
    Label index = new Label("Puzzle " + String.valueOf(model.getActivePuzzleIndex() + 1));
    index.getStyleClass().add("label-index");
    labelLayout.getChildren().add(index);

    viewLayout.setTop(labelLayout);

    Button prev = new Button("\u25C0");
    prev.getStyleClass().add("button-prev");
    prev.setOnAction(
        (ActionEvent event) -> {
          controller.clickPrevPuzzle();
        });
    viewLayout.setLeft(prev);
    Button next = new Button("\u25B6");
    next.getStyleClass().add("button-next");
    next.setOnAction(
        (ActionEvent event) -> {
          controller.clickNextPuzzle();
        });
    viewLayout.setRight(next);

    PuzzleView puzzleView = new PuzzleView(controller, model);
    viewLayout.setCenter(puzzleView.render());

    ControlView controlView = new ControlView(controller, model);
    viewLayout.setBottom(controlView.render());

    return viewLayout;
  }
}
