package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.model.*;
import com.comp301.a09akari.controller.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.ConcurrentNavigableMap;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    PuzzleLibrary puzzles = new PuzzleLibraryImpl();
    puzzles.addPuzzle(puzzle1);
    puzzles.addPuzzle(puzzle2);
    puzzles.addPuzzle(puzzle3);
    puzzles.addPuzzle(puzzle4);
    puzzles.addPuzzle(puzzle5);

    Model model = new ModelImpl(puzzles);
    ClassicMvcController controller = new ControllerImpl(model);
    model.addObserver(controller);

    FXComponent board = new puzzleView(controller);
    FXComponent controls = new controlView(controller);

    Pane layout = new VBox();

    Label label = new Label("Light Up!");
    layout.getChildren().add(label);
    layout.getChildren().add(board.render());
    layout.getChildren().add(controls.render());

    Scene scene = new Scene(layout, 500, 500);
    stage.setScene(scene);
    stage.show();
  }
}
