package com.raczyk.gameoflife.world.cell;

import com.raczyk.gameoflife.world.Point;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class representing cell existing in the world.
 */
public class Cell {
  private final Point point;
  private State currentState;
  private State futureState;
  private List<Cell> neighbours;

  /**
   * Constructs a cell in a certain Point of the world matrix and with given state.
   *
   * @param point Point in the world matrix.
   * @param currentState Initial state of the cell.
   *
   */
  public Cell(Point point, State currentState) {
    this.point = point;
    this.currentState = currentState;
    this.futureState = State.UNKNOWN;
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

  Point getPoint() {
    return this.point;
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
    return point.equals(cell.point) && currentState == cell.currentState;
  }

  @Override
  public int hashCode() {
    return Objects.hash(point, currentState);
  }
}
