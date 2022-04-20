package com.comp301.a09akari.view;

import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;

public interface FXComponent {
  /** Render the component and return the resulting Parent object */
  Parent render();
}
