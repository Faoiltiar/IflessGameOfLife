package com.raczyk.gameoflife.world;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PointTest {

  @Test
  void getNeighbourPoints_PointWithZeroCoordinates_EightNeighbours() {
    // Given
    var point = new Point(0, 0);

    // When
    var neighbours = point.getNeighbourPoints();

    // Then
    assertThat(neighbours).isNotNull().hasSize(8);
  }

  @Test
  void getNeighbourPoints_PointWithZeroCoordinates_NeighboursWithoutGivenPoint() {
    // Given
    var point = new Point(0, 0);

    // When
    var neighbours = point.getNeighbourPoints();

    // Then
    assertThat(neighbours).isNotNull().doesNotContain(point);
  }

  @Test
  void getNeighbourPoints_PointWithZeroCoordinates_NeighboursWithNegativeCoordinates() {
    // Given
    var point = new Point(0, 0);

    // When
    var neighbours = point.getNeighbourPoints();

    // Then
    assertThat(neighbours).isNotNull().containsExactlyInAnyOrder(new Point(-1, -1),
        new Point(-1, 0), new Point(-1, 1), new Point(0, -1), new Point(0, 1),
        new Point(1, -1), new Point(1, 0), new Point(1, 1));
  }

  @Test
  void getNeighbourPoints_PointWithPositiveCoordinates_CorrectNeighbours() {
    // Given
    var point = new Point(2, 2);

    // When
    var neighbours = point.getNeighbourPoints();

    // Then
    assertThat(neighbours).isNotNull().containsExactlyInAnyOrder(new Point(1, 1),
        new Point(1, 2), new Point(1, 3), new Point(2, 1), new Point(2, 3),
        new Point(3, 1), new Point(3, 2), new Point(3, 3));
  }
}
