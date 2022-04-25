package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if (model.getActivePuzzleIndex() + 1 < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    if (model.getActivePuzzleIndex() - 1 >= 0) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    Random rn = new Random();
    int index = model.getActivePuzzleIndex();
    while (index == model.getActivePuzzleIndex()) {
      index = rn.nextInt(0, model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(index);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR && model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR
        && !model.isLamp(r, c)) {
      model.addLamp(r, c);
    }
  }

  @Override
  public boolean clickDone() {
    return model.isSolved();
  }
}
