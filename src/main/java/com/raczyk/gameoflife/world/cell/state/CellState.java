package com.raczyk.gameoflife.world.cell.state;

/**
 * Interface representing state of the cell.
 * It allows to establish new state base on current one and neighbour states.
 */
public interface CellState {

  /**
   * Method responsible for evaluating the future state of a cell.
   */
  CellState determineFutureState();
}
