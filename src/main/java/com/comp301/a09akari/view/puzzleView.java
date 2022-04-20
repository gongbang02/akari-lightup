package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class puzzleView implements FXComponent {
  private ClassicMvcController controller;

  public puzzleView(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane layout = new GridPane();
    for (int i = 0; i < controller.getPuzzleWidth(); i++) {
      for (int j = 0; j < controller.getPuzzleHeight(); j++) {
        switch (controller.getCellType(i, j)) {
          case CORRIDOR:
            Button corridorButton = new Button();
            int finalI = i;
            int finalJ = j;
            corridorButton.setOnAction(
                (ActionEvent event) -> {
                  controller.clickCell(finalI, finalJ);
                });
            layout.add(corridorButton, i, j);
          case WALL:
            Button wallButton = new Button();
            layout.add(wallButton, i, j);
          case CLUE:
            Button clueButton = new Button(String.valueOf(controller.getClue(i, j)));
            layout.add(clueButton, i, j);
        }
      }
    }
    return layout;
  }
}
