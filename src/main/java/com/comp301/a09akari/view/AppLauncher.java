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

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);
    Puzzle puzzle6 = new PuzzleImpl(SamplePuzzles.PUZZLE_06);
    Puzzle puzzle7 = new PuzzleImpl(SamplePuzzles.PUZZLE_07);
    Puzzle puzzle8 = new PuzzleImpl(SamplePuzzles.PUZZLE_08);
    Puzzle puzzle9 = new PuzzleImpl(SamplePuzzles.PUZZLE_09);
    Puzzle puzzle10 = new PuzzleImpl(SamplePuzzles.PUZZLE_10);
    Puzzle puzzle11 = new PuzzleImpl(SamplePuzzles.PUZZLE_11);
    Puzzle puzzle12 = new PuzzleImpl(SamplePuzzles.PUZZLE_12);
    Puzzle puzzle13 = new PuzzleImpl(SamplePuzzles.PUZZLE_13);
    PuzzleLibrary puzzles = new PuzzleLibraryImpl();
    puzzles.addPuzzle(puzzle1);
    puzzles.addPuzzle(puzzle2);
    puzzles.addPuzzle(puzzle3);
    puzzles.addPuzzle(puzzle4);
    puzzles.addPuzzle(puzzle5);
    puzzles.addPuzzle(puzzle6);
    puzzles.addPuzzle(puzzle7);
    puzzles.addPuzzle(puzzle8);
    puzzles.addPuzzle(puzzle9);
    puzzles.addPuzzle(puzzle10);
    puzzles.addPuzzle(puzzle11);
    puzzles.addPuzzle(puzzle12);
    puzzles.addPuzzle(puzzle13);

    Model model = new ModelImpl(puzzles);
    ClassicMvcController controller = new ControllerImpl(model);
    View view = new View(controller, model);

    Scene scene = new Scene(view.render());
    model.addObserver((Model m) -> {
      scene.setRoot(view.render());
      stage.sizeToScene();
    });
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.setTitle("Play Light Up!");
    stage.show();
  }
}
