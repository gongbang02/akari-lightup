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
    model.setActivePuzzleIndex((model.getActivePuzzleIndex() + 1) % model.getPuzzleLibrarySize());
  }

  @Override
  public void clickPrevPuzzle() {
    model.setActivePuzzleIndex((model.getActivePuzzleIndex() - 1) % model.getPuzzleLibrarySize());
  }

  @Override
  public void clickRandPuzzle() {
    Random index = new Random();
    model.setActivePuzzleIndex(index.nextInt(model.getPuzzleLibrarySize()));
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

  @Override
  public int getPuzzleHeight() {
    return model.getActivePuzzle().getHeight();
  }

  @Override
  public int getPuzzleWidth() {
    return model.getActivePuzzle().getWidth();
  }

  @Override
  public int getClue(int r, int c) {
    return model.getActivePuzzle().getClue(r, c);
  }

  @Override
  public CellType getCellType(int r, int c) {
    return model.getActivePuzzle().getCellType(r, c);
  }

  @Override
  public void update(Model model) {
    this.model = model;
  }
}
