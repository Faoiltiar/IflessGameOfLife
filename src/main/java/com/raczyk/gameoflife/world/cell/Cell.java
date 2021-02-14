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

  void setCurrentState(CellState currentState) {
    this.currentState = currentState;
  }

  /**
   * Method setting the list of neighbour cells base on cell's point.
   *
   * @param world Map representing Points in the world matrix and corresponding cells.
   *
   */
  void setNeighbours(Map<Point, Cell> world) {
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
  void setNextGeneration() {
    this.futureState = currentState.determineFutureState();
  }

  /**
   * Method which evolves cell into next generation.
   * It changes the current state of a cell with a future state.
   */
  void evolve() {
    currentState = futureState;
    futureState = new UnknownCellState(this);
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
    return point.equals(cell.point);
  }

  @Override
  public int hashCode() {
    return Objects.hash(point);
  }
}
