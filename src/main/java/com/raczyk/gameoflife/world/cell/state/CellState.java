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

  /**
   * Method responsible for returning a String representing cell state.
   *
   * @return String object which represents state.
   */
  String display();

  /**
   * Method returning State enum representation of cell state.
   *
   * @return State enum representation of cell state.
   */
  State getState();
}
