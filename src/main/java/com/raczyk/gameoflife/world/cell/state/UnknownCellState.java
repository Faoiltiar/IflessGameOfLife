package com.raczyk.gameoflife.world.cell.state;

import com.raczyk.gameoflife.world.cell.Cell;
import java.util.Objects;

/**
 * Class representing unknown cell state.
 * Cell in that state cannot be changed with changeState method.
 */
public final class UnknownCellState implements CellState {

  private final Cell cell;
  private final State state;

  /**
   * Initializes a newly created UnknownCellState object so that it represents
   * the unknown state of a cell passed as a parameter.
   *
   * @param cell cell which state is represented by this class.
   *
   */
  public UnknownCellState(Cell cell) {
    this.cell = cell;
    this.state = State.UNKNOWN;
  }

  @Override
  public CellState determineFutureState() {
    return this;
  }

  @Override
  public String display() {
    return state.getRepresentation();
  }

  @Override
  public State getState() {
    return this.state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UnknownCellState that = (UnknownCellState) o;
    return Objects.equals(cell, that.cell);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cell);
  }
}
