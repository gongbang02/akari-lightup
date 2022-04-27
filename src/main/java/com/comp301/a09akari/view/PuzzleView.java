package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PuzzleView implements FXComponent {
  private ClassicMvcController controller;
  private Model model;

  public PuzzleView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    GridPane board = new GridPane();
    board.getStyleClass().add("board");
    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        if (model.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          if (model.isLit(i, j) && !model.isLamp(i, j)) {
            Button lit = new Button();
            lit.getStyleClass().add("button-lit");
            int finalI = i;
            int finalJ = j;
            lit.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            board.add(lit, i, j);
          } else if (model.isLit(i, j) && model.isLamp(i, j)) {
            Button lamp = new Button("\uD83D\uDCA1");
            lamp.getStyleClass().add("button-lamp");
            int finalI = i;
            int finalJ = j;
            lamp.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            board.add(lamp, i, j);
          } else {
            Button corridorButton = new Button();
            corridorButton.getStyleClass().add("button");
            int finalI = i;
            int finalJ = j;
            corridorButton.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            board.add(corridorButton, i, j);
          }
        } else if (model.getActivePuzzle().getCellType(i, j) == CellType.WALL) {
          Label wall = new Label();
          wall.getStyleClass().add("label-wall");
          board.add(wall, i, j);
        } else {
          if (model.isClueSatisfied(i, j)) {
            Label satisfiedClue = new Label(String.valueOf(model.getActivePuzzle().getClue(i, j)));
            satisfiedClue.getStyleClass().add("satisfiedClue");
            board.add(satisfiedClue, i, j);
          } else {
            Label unsatisfiedClue =
                new Label(String.valueOf(model.getActivePuzzle().getClue(i, j)));
            unsatisfiedClue.getStyleClass().add("unsatisfiedClue");
            board.add(unsatisfiedClue, i, j);
          }
        }
      }
    }
    return board;
  }

  @Override
  public void update(Model model) {
    this.model = model;
  }
}
