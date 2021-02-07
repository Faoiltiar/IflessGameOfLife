package com.raczyk.gameoflife.world;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
   * Points can be negative (negative coordinate x and y).
   *
   * @return List of neighbour points.
   *
   */
  public List<Point> getNeighbourPoints() {
    final var neighbourDistance = 1;
    final var coordinatesX = List.of(coordinateX - neighbourDistance, coordinateX,
        coordinateX + neighbourDistance);
    final var coordinatesY = List.of(coordinateY - neighbourDistance, coordinateY,
        coordinateY + neighbourDistance);
    return coordinatesX.stream()
        .flatMap(x -> coordinatesY.stream().map(y -> new Point(x, y)))
        .filter(point -> !point.equals(this))
        .collect(Collectors.toList());
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
