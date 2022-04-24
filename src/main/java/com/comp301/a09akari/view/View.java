package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
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
    VBox viewLayout = new VBox();

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

    PuzzleView puzzleView = new PuzzleView(controller, model);
    viewLayout.getChildren().add(puzzleView.render());

    ControlView controlView = new ControlView(controller, model);
    viewLayout.getChildren().add(controlView.render());

    return viewLayout;
  }
}
