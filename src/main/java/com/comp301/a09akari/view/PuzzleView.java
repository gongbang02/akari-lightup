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
    board.setStyle("width: 200px;");
    board.setStyle("height: 200px;");
    board.getStylesheets().add("board");
    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        if (model.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
          if (model.isLit(i, j) && !model.isLamp(i, j)) {
            Button lit = new Button();
            lit.getStylesheets().add("lit");
            int finalI = i;
            int finalJ = j;
            lit.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            board.add(lit, i, j);
          } else if (model.isLit(i, j) && model.isLamp(i, j)) {
            Image bulb = new Image("light-bulb.png");
            ImageView bulbView = new ImageView(bulb);
            Button lamp = new Button();
            lamp.setGraphic(bulbView);
            lamp.getStylesheets().add("lamp");
            int finalI = i;
            int finalJ = j;
            lamp.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            board.add(lamp, i, j);
          } else {
            Button corridorButton = new Button();
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
          wall.getStylesheets().add("wall");
          board.add(wall, i, j);
        } else {
          String clueNum = String.valueOf(model.getActivePuzzle().getClue(i, j));
          Label clue = new Label(clueNum);
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
