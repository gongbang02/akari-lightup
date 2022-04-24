package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private ClassicMvcController controller;
  private PuzzleView puzzle;
  private ControlView control;
  private Model model;

  public View(ClassicMvcController controller, PuzzleView puzzle, ControlView control, Model model) {
    this.controller = controller;
    this.puzzle = puzzle;
    this.control = control;
    this.model = model;
  }

  @Override
  public void update(Model model) {
    puzzle.update(model);
    control.update(model);
  }

  @Override
  public Parent render() {
    VBox viewLayout = new VBox();
    viewLayout.getStylesheets().add("viewLayout");

    Label label = new Label("Light Up! ");
    label.getStylesheets().add("label");
    viewLayout.getChildren().add(label);
    Label result;
    if (model.isSolved()) {
      result = new Label("Congratulations! You have solved the puzzle");
    } else {
      result = new Label();
    }
    result.getStylesheets().add("result");
    viewLayout.getChildren().add(result);
    viewLayout.getChildren().add(puzzle.render());
    viewLayout.getChildren().add(control.render());
    return viewLayout;
  }
}
