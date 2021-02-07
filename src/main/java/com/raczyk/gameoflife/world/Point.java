package com.raczyk.gameoflife.world;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class representing point in the world, where cell exists.
 */
public final class Point {
  private final Integer coordinateX;
  private final Integer coordinateY;

  public Point(Integer coordinateX, Integer coordinateY) {
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
  }

  /**
   * Method returning all Points that are neighbours to current point.
   * @return List of neighbour points.
   *
   */
  public List<Point> getNeighboursPoints() {
    return Collections.emptyList();
  }

  public Integer getCoordinateX() {
    return coordinateX;
  }

  public Integer getCoordinateY() {
    return coordinateY;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Point point = (Point) o;
    return coordinateX.equals(point.coordinateX) && coordinateY.equals(point.coordinateY);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinateX, coordinateY);
  }
}
