package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.ModelObserver;

public interface ClassicMvcController extends ModelObserver {
  /** Handles the click action to go to the next puzzle */
  void clickNextPuzzle();

  /** Handles the click action to go to the previous puzzle */
  void clickPrevPuzzle();

  /** Handles the click action to go to a random puzzle */
  void clickRandPuzzle();

  /** Handles the click action to reset the currently active puzzle */
  void clickResetPuzzle();

  /** Handles the click event on the cell at row r, column c */
  void clickCell(int r, int c);

  /** Turns in the answer and tests if the puzzle is solved */
  boolean clickDone();

  int getPuzzleHeight();

  int getPuzzleWidth();

  int getClue(int r, int c);

  CellType getCellType(int r, int c);
}
