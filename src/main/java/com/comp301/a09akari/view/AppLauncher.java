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
    PuzzleView puzzleView = new PuzzleView(controller, model);
    ControlView controlView = new ControlView(controller, model);
    View view = new View(controller, puzzleView, controlView);
    model.addObserver(view);

    Scene scene = new Scene(view.render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.setTitle("Play Light Up!");
    stage.show();
  }
}
