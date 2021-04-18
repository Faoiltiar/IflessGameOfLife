package com.raczyk.gameoflife.world.cell;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.raczyk.gameoflife.world.Point;
import com.raczyk.gameoflife.world.cell.state.AliveCellState;
import com.raczyk.gameoflife.world.cell.state.DeadCellState;
import com.raczyk.gameoflife.world.cell.state.UnknownCellState;
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
                var worldCell = new Cell(point);
                worldCell.setCurrentState(new DeadCellState(worldCell));
                world.put(point, worldCell);
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
                var worldCell = world.get(new Point(x, y));
                worldCell.setCurrentState(new AliveCellState(worldCell));
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
    cell.setNeighbours(world);

    var firstNeighbour = world.get(new Point(coordinateX - 1, coordinateY - 1));
    firstNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var secondNeighbour = world.get(new Point(coordinateX - 1, coordinateY));
    secondNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var thirdNeighbour = world.get(new Point(coordinateX, coordinateY - 1));
    thirdNeighbour.setCurrentState(new AliveCellState(firstNeighbour));

    // When
    var livingNeighbours = cell.getLivingNeighboursNo();

    // Then
    assertThat(livingNeighbours).isEqualTo(3);
  }

  @Test
  void setNextGeneration_UnknownCellCurrentState_UnknownCellStateAsFutureState() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    cell.setCurrentState(new UnknownCellState(cell));
    cell.setNeighbours(world);

    // When
    cell.setNextGeneration();

    // Then
    assertThat(cell).extracting("futureState").isNotNull()
        .isEqualTo(new UnknownCellState(cell));
  }

  @Test
  void setNextGeneration_DeadCellInReproductionSubEnvironment_AliveCellStateAsFutureState() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    cell.setNeighbours(world);

    var firstNeighbour = world.get(new Point(coordinateX - 1, coordinateY - 1));
    firstNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var secondNeighbour = world.get(new Point(coordinateX - 1, coordinateY));
    secondNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var thirdNeighbour = world.get(new Point(coordinateX, coordinateY - 1));
    thirdNeighbour.setCurrentState(new AliveCellState(firstNeighbour));

    // When
    cell.setNextGeneration();

    // Then
    assertThat(cell).extracting("futureState").isNotNull()
        .isEqualTo(new AliveCellState(cell));
  }

  @Test
  void setNextGeneration_DeadCellInNormalSubEnvironment_DeadCellStateAsFutureState() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    cell.setNeighbours(world);

    var firstNeighbour = world.get(new Point(coordinateX - 1, coordinateY - 1));
    firstNeighbour.setCurrentState(new AliveCellState(firstNeighbour));

    // When
    cell.setNextGeneration();

    // Then
    assertThat(cell).extracting("futureState").isNotNull()
        .isEqualTo(new DeadCellState(cell));
  }

  @Test
  void setNextGeneration_AliveCellInOverpopulationSubEnvironment_DeadCellStateAsFutureState() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    cell.setCurrentState(new AliveCellState(cell));
    cell.setNeighbours(world);

    var firstNeighbour = world.get(new Point(coordinateX - 1, coordinateY - 1));
    firstNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var secondNeighbour = world.get(new Point(coordinateX - 1, coordinateY));
    secondNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var thirdNeighbour = world.get(new Point(coordinateX, coordinateY - 1));
    thirdNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var fourthNeighbour =  world.get(new Point(coordinateX + 1, coordinateY));
    fourthNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var fifthNeighbour = world.get(new Point(coordinateX + 1, coordinateY + 1));
    fifthNeighbour.setCurrentState(new AliveCellState(firstNeighbour));

    // When
    cell.setNextGeneration();

    // Then
    assertThat(cell).extracting("futureState").isNotNull()
        .isEqualTo(new DeadCellState(cell));
  }

  @Test
  void setNextGeneration_AliveCellInUnderpopulationSubEnvironment_DeadCellStateAsFutureState() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    cell.setCurrentState(new AliveCellState(cell));
    cell.setNeighbours(world);

    var firstNeighbour = world.get(new Point(coordinateX - 1, coordinateY - 1));
    firstNeighbour.setCurrentState(new AliveCellState(firstNeighbour));

    // When
    cell.setNextGeneration();

    // Then
    assertThat(cell).extracting("futureState").isNotNull()
        .isEqualTo(new DeadCellState(cell));
  }

  @Test
  void setNextGeneration_AliveCellInNormalSubEnvironment_AliveCellStateAsFutureState() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    cell.setCurrentState(new AliveCellState(cell));
    cell.setNeighbours(world);

    var firstNeighbour = world.get(new Point(coordinateX - 1, coordinateY - 1));
    firstNeighbour.setCurrentState(new AliveCellState(firstNeighbour));
    var secondNeighbour = world.get(new Point(coordinateX - 1, coordinateY));
    secondNeighbour.setCurrentState(new AliveCellState(firstNeighbour));

    // When
    cell.setNextGeneration();

    // Then
    assertThat(cell).extracting("futureState").isNotNull()
        .isEqualTo(new AliveCellState(cell));
  }

  @Test
  void evolve_GivenCurrentAndFutureState_CurrentStateSetToPrevFutureStateAndFutureSetToUnknown() {
    // Given
    var coordinateX = WORLD_COLUMN_NO / 2;
    var coordinateY = WORLD_ROW_NO / 2;
    var cell = world.get(new Point(coordinateX, coordinateY));
    cell.setCurrentState(new AliveCellState(cell));
    cell.setNextGeneration(); // set future cell state to DeadCellState
    cell.setNeighbours(world);

    // When
    cell.evolve();

    // Then
    assertThat(cell).extracting("currentState").isNotNull()
        .isEqualTo(new DeadCellState(cell));
    assertThat(cell).extracting("futureState").isNotNull()
        .isEqualTo(new UnknownCellState(cell));
  }

  @Test
  void display_CellWithAliveState_ProperStateRepresentation() {
    // Given
    var cell = new Cell(new Point(0, 0));
    cell.setCurrentState(new AliveCellState(cell));

    // When
    var stateDisplayed = cell.display();

    // Then
    assertThat(stateDisplayed).isEqualTo("X");
  }

  @Test
  void display_CellWithDeadState_ProperStateRepresentation() {
    // Given
    var cell = new Cell(new Point(0, 0));
    cell.setCurrentState(new DeadCellState(cell));

    // When
    var stateDisplayed = cell.display();

    // Then
    assertThat(stateDisplayed).isEqualTo("#");
  }

  @Test
  void display_CellWithUnknownState_ProperStateRepresentation() {
    // Given
    var cell = new Cell(new Point(0, 0));
    cell.setCurrentState(new UnknownCellState(cell));

    // When
    var stateDisplayed = cell.display();

    // Then
    assertThat(stateDisplayed).isEqualTo("?");
  }
}
