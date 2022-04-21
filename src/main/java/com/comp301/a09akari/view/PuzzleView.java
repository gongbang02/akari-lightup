package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    board.getStylesheets().add("board");
    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        switch (model.getActivePuzzle().getCellType(i, j)) {
          case CORRIDOR:
            Button corridorButton = new Button();
            int finalI = i;
            int finalJ = j;
            corridorButton.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            board.add(corridorButton, i, j);
          case WALL:
            Label wall = new Label();
            wall.getStylesheets().add("wall");
            board.add(wall, i, j);
          case CLUE:
            Label clue = new Label(String.valueOf(model.getActivePuzzle().getClue(i, j)));
            clue.getStylesheets().add("clue");
            board.add(clue, i, j);
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
