package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class controlView implements FXComponent {
    private ClassicMvcController controller;

    public controlView(ClassicMvcController controller) {
        this.controller = controller;
    }

    @Override
    public Parent render() {
        VBox layout = new VBox();

        Button solve = new Button("Solve!");
        solve.setOnAction((ActionEvent event) -> {
            controller.clickDone();
        });
        layout.getChildren().add(solve);

        Button reset = new Button("Reset");
        reset.setOnAction((ActionEvent event) -> {
            controller.clickResetPuzzle();
        });
        layout.getChildren().add(reset);

        HBox controls = new HBox();
        Button prev = new Button("Previous Puzzle");
        prev.setOnAction((ActionEvent event) -> {
            controller.clickPrevPuzzle();
        });
        controls.getChildren().add(prev);
        Button random = new Button("Random One");
        random.setOnAction((ActionEvent event) -> {
            controller.clickRandPuzzle();
        });
        controls.getChildren().add(random);
        Button next = new Button("Next Puzzle");
        next.setOnAction((ActionEvent event) -> {
            controller.clickNextPuzzle();
        });
        controls.getChildren().add(next);
        layout.getChildren().add(controls);

        return layout;
    }
}
