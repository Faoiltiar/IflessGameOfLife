package com.raczyk.gameoflife.world.cell;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.raczyk.gameoflife.world.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTest {

  private static final Integer WORLD_ROW_NO = 10;
  private static final Integer WORLD_COLUMN_NO = 10;

  private Map<Point, Cell> world;

  @BeforeEach
  void setUp() {
    world = new HashMap<>();
    IntStream.range(0, WORLD_COLUMN_NO).forEach(coordinateX -> {
          IntStream.range(0, WORLD_ROW_NO).forEach(coordinateY -> {
                var point = new Point(coordinateX, coordinateY);
                world.put(point, new Cell(point));
              }
          );
        }
    );
  }

  @Test
  void setNeighbours_CellAtZeroPoint_CellContainingThreeNeighbours() {
    // Given
    var coordinateX = 0;
    var coordinateY = 0;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX, coordinateY + 1)),
        world.get(new Point(coordinateX + 1, coordinateY)),
        world.get(new Point(coordinateX + 1, coordinateY + 1))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(3).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellZeroRow_CellContainingFiveNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = 0;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX - 1, coordinateY)),
        world.get(new Point(coordinateX - 1, coordinateY + 1)),
        world.get(new Point(coordinateX, coordinateY + 1)),
        world.get(new Point(coordinateX + 1, coordinateY)),
        world.get(new Point(coordinateX + 1, coordinateY + 1))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(5).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellZeroColumn_CellContainingFiveNeighbours() {
    // Given
    var coordinateX = 0;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX, coordinateY - 1)),
        world.get(new Point(coordinateX, coordinateY + 1)),
        world.get(new Point(coordinateX + 1, coordinateY - 1)),
        world.get(new Point(coordinateX + 1, coordinateY)),
        world.get(new Point(coordinateX + 1, coordinateY + 1))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(5).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellInCenterOfTheWorld_CellContainingEightNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX - 1, coordinateY - 1)),
        world.get(new Point(coordinateX - 1, coordinateY)),
        world.get(new Point(coordinateX - 1, coordinateY + 1)),
        world.get(new Point(coordinateX, coordinateY - 1)),
        world.get(new Point(coordinateX, coordinateY + 1)),
        world.get(new Point(coordinateX + 1, coordinateY - 1)),
        world.get(new Point(coordinateX + 1, coordinateY)),
        world.get(new Point(coordinateX + 1, coordinateY + 1))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(8).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellZeroRowMaxColumn_CellContainingThreeNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO - 1;
    var coordinateY = 0;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX - 1, coordinateY)),
        world.get(new Point(coordinateX - 1, coordinateY + 1)),
        world.get(new Point(coordinateX, coordinateY + 1))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(3).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellMaxColumn_CellContainingFiveNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO - 1;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX - 1, coordinateY - 1)),
        world.get(new Point(coordinateX - 1, coordinateY)),
        world.get(new Point(coordinateX - 1, coordinateY + 1)),
        world.get(new Point(coordinateX, coordinateY - 1)),
        world.get(new Point(coordinateX, coordinateY + 1))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(5).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellMaxRowZeroColumn_CellContainingThreeNeighbours() {
    // Given
    var coordinateX = 0;
    var coordinateY = WORLD_ROW_NO - 1;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX, coordinateY - 1)),
        world.get(new Point(coordinateX + 1, coordinateY - 1)),
        world.get(new Point(coordinateX + 1, coordinateY))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(3).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellMaxRow_CellContainingFiveNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO - 1;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX - 1, coordinateY - 1)),
        world.get(new Point(coordinateX - 1, coordinateY)),
        world.get(new Point(coordinateX, coordinateY - 1)),
        world.get(new Point(coordinateX + 1, coordinateY - 1)),
        world.get(new Point(coordinateX + 1, coordinateY))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(5).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void setNeighbours_CellMaxRowMaxColumn_CellContainingThreeNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO - 1;
    var coordinateY = WORLD_ROW_NO - 1;
    var cell = world.get(new Point(coordinateX, coordinateY));
    var expectedNeighbours = List.of(
        world.get(new Point(coordinateX - 1, coordinateY - 1)),
        world.get(new Point(coordinateX - 1, coordinateY)),
        world.get(new Point(coordinateX, coordinateY - 1))
    );

    // When
    cell.setNeighbours(world);

    // Then
    assertThat(cell)
        .extracting("neighbours", LIST)
        .hasSize(3).containsExactlyInAnyOrderElementsOf(expectedNeighbours);
  }

  @Test
  void getLivingNeighboursNo_AllLivingCells_NumberOfCellNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    IntStream.range(0, WORLD_COLUMN_NO).forEach(x -> {
          IntStream.range(0, WORLD_ROW_NO).forEach(y -> {
                world.get(new Point(x, y)).setCurrentState(State.LIVING);
              }
          );
        }
    );
    cell.setNeighbours(world);

    // When
    var livingNeighbours = cell.getLivingNeighboursNo();

    // Then
    assertThat(livingNeighbours).isEqualTo(8);
  }

  @Test
  void getLivingNeighboursNo_AllDeadCells_Zero() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    IntStream.range(0, WORLD_COLUMN_NO).forEach(x -> {
          IntStream.range(0, WORLD_ROW_NO).forEach(y -> {
                world.get(new Point(x, y)).setCurrentState(State.DEAD);
              }
          );
        }
    );
    cell.setNeighbours(world);

    // When
    var livingNeighbours = cell.getLivingNeighboursNo();

    // Then
    assertThat(livingNeighbours).isEqualTo(0);
  }

  @Test
  void getLivingNeighboursNo_PartOfCellNeighboursAreLiving_ExpectedNumberOfNeighbours() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    IntStream.range(0, WORLD_COLUMN_NO).forEach(x -> {
          IntStream.range(0, WORLD_ROW_NO).forEach(y -> {
                world.get(new Point(x, y)).setCurrentState(State.DEAD);
              }
          );
        }
    );
    cell.setNeighbours(world);
    world.get(new Point(coordinateX - 1, coordinateY - 1)).setCurrentState(State.LIVING);
    world.get(new Point(coordinateX - 1, coordinateY)).setCurrentState(State.LIVING);
    world.get(new Point(coordinateX, coordinateY - 1)).setCurrentState(State.LIVING);


    // When
    var livingNeighbours = cell.getLivingNeighboursNo();

    // Then
    assertThat(livingNeighbours).isEqualTo(3);
  }
}
