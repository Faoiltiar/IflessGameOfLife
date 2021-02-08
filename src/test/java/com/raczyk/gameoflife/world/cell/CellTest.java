package com.raczyk.gameoflife.world.cell;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.raczyk.gameoflife.world.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTest {
  
  private Map<Point, Cell> world;

  @BeforeEach
  void setUp() {
    world = new HashMap<>();
    IntStream.range(0, 2).forEach(coordinateX -> {
          IntStream.range(0, 2).forEach(coordinateY -> {
                var point = new Point(coordinateX, coordinateY);
                world.put(point, new Cell(point, State.DEAD));
              }
          );
        }
    );
  }

  @Test
  void setNeighbours_CellAtZeroPoint_() {
    // Given
    var cell = world.get(new Point(0, 0));

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(3).containsExactlyInAnyOrder(world.get(new Point(0, 1)),
        world.get(new Point(1, 0)), world.get(new Point(1, 1)));
  }
}
