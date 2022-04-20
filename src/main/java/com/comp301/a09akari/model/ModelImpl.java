package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary library;
  private int activePuzzle;
  private int[][] map;
  private List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    this.library = library;
    this.activePuzzle = 0;
    this.map =
        new int[library.getPuzzle(activePuzzle).getWidth()]
            [library.getPuzzle(activePuzzle).getHeight()];
    this.observers = new ArrayList<>();
  }

  private void onOff(String action, int r, int c) {
    int light;
    int r_left = r;
    int r_right = r;
    int c_above = c;
    int c_below = c;
    while (library.getPuzzle(activePuzzle).getCellType(r_left, c) == CellType.CORRIDOR) {
      if (r_left - 1 < 0) {
        break;
      }
      r_left--;
    }
    while (library.getPuzzle(activePuzzle).getCellType(r_right, c) == CellType.CORRIDOR) {
      if (r_right + 1 >= library.getPuzzle(activePuzzle).getHeight()) {
        break;
      }
      r_right++;
    }
    while (library.getPuzzle(activePuzzle).getCellType(r, c_above) == CellType.CORRIDOR) {
      if (c_above - 1 < 0) {
        break;
      }
      c_above--;
    }
    while (library.getPuzzle(activePuzzle).getCellType(r, c_below) == CellType.CORRIDOR) {
      if (c_below + 1 >= library.getPuzzle(activePuzzle).getWidth()) {
        break;
      }
      c_below++;
    }
    if (action == "on") {
      light = 2;
    } else if (action == "off") {
      light = 0;
    } else {
      throw new IllegalArgumentException();
    }
    for (int i = r_left; i < r; i++) {
      map[i][c] = light;
    }
    for (int i = r_right; i > r; i--) {
      map[i][c] = light;
    }
    for (int i = c_above; i < r; i++) {
      map[i][c] = light;
    }
    for (int i = c_below; i > r; i--) {
      map[i][c] = light;
    }
  }

  private void notifyObservers() {
    for (ModelObserver o : observers) {
      o.update(this);
    }
  }

  @Override
  public void addLamp(int r, int c) {
    if (r < 0
        || c < 0
        || r >= library.getPuzzle(activePuzzle).getHeight()
        || c >= library.getPuzzle(activePuzzle).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (library.getPuzzle(activePuzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (map[r][c] == 2) {
      map[r][c] = -1;
    } else {
      map[r][c] = 1;
      onOff("on", r, c);
      notifyObservers();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0
        || c < 0
        || r >= library.getPuzzle(activePuzzle).getHeight()
        || c >= library.getPuzzle(activePuzzle).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (library.getPuzzle(activePuzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    map[r][c] = 0;
    onOff("off", r, c);
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0
        || c < 0
        || r >= library.getPuzzle(activePuzzle).getHeight()
        || c >= library.getPuzzle(activePuzzle).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (library.getPuzzle(activePuzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return map[r][c] == 1 || map[r][c] == 2;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0
        || c < 0
        || r >= library.getPuzzle(activePuzzle).getHeight()
        || c >= library.getPuzzle(activePuzzle).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (library.getPuzzle(activePuzzle).getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return map[r][c] == 1 || map[r][c] == -1;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r < 0
        || c < 0
        || r >= library.getPuzzle(activePuzzle).getHeight()
        || c >= library.getPuzzle(activePuzzle).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException();
    }
    return map[r][c] == -1;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(activePuzzle);
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzle;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= library.size()) {
      throw new IndexOutOfBoundsException();
    }
    this.activePuzzle = index;
    this.map = new int[getActivePuzzle().getWidth()][getActivePuzzle().getHeight()];
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < library.getPuzzle(activePuzzle).getWidth(); i++) {
      for (int j = 0; j < library.getPuzzle(activePuzzle).getHeight(); j++) {
        if (library.getPuzzle(activePuzzle).getCellType(i, j) == CellType.CORRIDOR) {
          map[i][j] = 0;
        }
      }
    }
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < library.getPuzzle(activePuzzle).getWidth(); i++) {
      for (int j = 0; j < library.getPuzzle(activePuzzle).getHeight(); j++) {
        if (library.getPuzzle(activePuzzle).getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        } else if (library.getPuzzle(activePuzzle).getCellType(i, j) == CellType.CORRIDOR) {
          if (!isLit(i, j)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r < 0
        || c < 0
        || r >= library.getPuzzle(activePuzzle).getHeight()
        || c >= library.getPuzzle(activePuzzle).getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (library.getPuzzle(activePuzzle).getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    int count = 0;
    if ((r - 1) >= 0 && map[r - 1][c] == 1) {
      count += 1;
    }
    if ((r + 1) < library.getPuzzle(activePuzzle).getWidth() && map[r + 1][c] == 1) {
      count += 1;
    }
    if ((c - 1) >= 0 && map[r][c - 1] == 1) {
      count += 1;
    }
    if ((c + 1) < library.getPuzzle(activePuzzle).getHeight() && map[r][c + 1] == 1) {
      count += 1;
    }
    return count == library.getPuzzle(activePuzzle).getClue(r, c);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (observer == null || !observers.contains(observer)) {
      throw new IllegalArgumentException();
    }
    observers.remove(observer);
  }
}
