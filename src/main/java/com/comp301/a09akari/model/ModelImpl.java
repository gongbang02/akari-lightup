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
    this.map = new int[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    this.observers = new ArrayList<>();
    resetPuzzle();
  }

  private boolean pathHasLamp(int r, int c) {
    for (int i = r - 1; i >= 0; i--) {
      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (map[i][c] == 1) {
        return true;
      }
    }
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      if (getActivePuzzle().getCellType(i, c) != CellType.CORRIDOR) {
        break;
      }
      if (map[i][c] == 1) {
        return true;
      }
    }
    for (int i = c - 1; i >= 0; i--) {
      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
      if (map[r][i] == 1) {
        return true;
      }
    }
    for (int i = c + 1; i < getActivePuzzle().getWidth(); i++) {
      if (getActivePuzzle().getCellType(r, i) != CellType.CORRIDOR) {
        break;
      }
      if (map[r][i] == 1) {
        return true;
      }
    }
    return false;
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
    map[r][c] = 1;
    notifyObservers();
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
    return pathHasLamp(r, c) || isLamp(r, c);
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
    return map[r][c] == 1;
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
    return pathHasLamp(r, c);
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
    this.map = new int[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < library.getPuzzle(activePuzzle).getHeight(); i++) {
      for (int j = 0; j < library.getPuzzle(activePuzzle).getWidth(); j++) {
        map[i][j] = 0;
      }
    }
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < library.getPuzzle(activePuzzle).getHeight(); i++) {
      for (int j = 0; j < library.getPuzzle(activePuzzle).getWidth(); j++) {
        if (library.getPuzzle(activePuzzle).getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            return false;
          }
        } else if (library.getPuzzle(activePuzzle).getCellType(i, j) == CellType.CORRIDOR) {
          if (!isLit(i, j)) {
            return false;
          } else if (isLamp(i, j) && isLampIllegal(i, j)) {
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
    if ((r + 1) < library.getPuzzle(activePuzzle).getHeight() && map[r + 1][c] == 1) {
      count += 1;
    }
    if ((c - 1) >= 0 && map[r][c - 1] == 1) {
      count += 1;
    }
    if ((c + 1) < library.getPuzzle(activePuzzle).getWidth() && map[r][c + 1] == 1) {
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
