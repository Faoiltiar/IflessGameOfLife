package com.raczyk.gameoflife.world.cell;

import com.raczyk.gameoflife.world.Point;
import com.raczyk.gameoflife.world.cell.state.AliveCellState;
import com.raczyk.gameoflife.world.cell.state.CellState;
import com.raczyk.gameoflife.world.cell.state.UnknownCellState;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class representing cell existing in the world.
 */
public class Cell {
  private final Point point;
  private CellState currentState;
  private CellState futureState;
  private List<Cell> neighbours;

  /**
   * Constructs a cell in a certain Point of the world matrix and with given state.
   *
   * @param point Point in the world matrix.
   *
   */
  public Cell(Point point) {
    this.point = point;
    this.currentState = new UnknownCellState(this);
    this.futureState = new UnknownCellState(this);
    this.neighbours = new ArrayList<>();
  }

  public void setCurrentState(CellState currentState) {
    this.currentState = currentState;
  }

  public void setFutureState(CellState futureState) {
    this.futureState = futureState;
  }

  /**
   * Method setting the list of neighbour cells base on cell's point.
   *
   * @param world Map representing Points in the world matrix and corresponding cells.
   *
   */
  public void setNeighbours(Map<Point, Cell> world) {
    var neighbourPoints = point.getNeighbourPoints();
    this.neighbours = world.entrySet().stream()
        .filter(pointCellEntry -> neighbourPoints.contains(pointCellEntry.getKey()))
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
  }

  /**
   * Method getting the number of cells in neighbour of this cell which are in alive state.
   *
   * @return number of living neighbour cells.
   *
   */
  public long getLivingNeighboursNo() {
    return neighbours.stream()
        .filter(neighbour -> neighbour.currentState instanceof AliveCellState)
        .count();
  }

  /**
   * Method setting future state of a cell.
   */
  public void setNextGeneration() {
    this.futureState = currentState.determineFutureState();
  }

  /**
   * Method which evolves cell into next generation.
   * It changes the current state of a cell with a future state.
   */
  public void evolve() {
    currentState = futureState;
    futureState = new UnknownCellState(this);
  }

  /**
   * Method responsible for returning a String representing cell state.
   *
   * @return String object which represents state.
   *
   */
  public String display() {
    return currentState.display();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell cell = (Cell) o;
    return Objects.equals(point, cell.point)
        && Objects.equals(currentState.getState(), cell.currentState.getState())
        && Objects.equals(futureState.getState(), cell.futureState.getState())
        && Objects.equals(neighbours.size(), cell.neighbours.size());
  }

  @Override
  public int hashCode() {
    return Objects.hash(point);
  }
}
