package com.raczyk.gameoflife.world;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.raczyk.gameoflife.world.cell.Cell;
import com.raczyk.gameoflife.world.cell.state.AliveCellState;
import com.raczyk.gameoflife.world.cell.state.DeadCellState;
import com.raczyk.gameoflife.world.cell.state.UnknownCellState;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * World uses Random to generate cells with random state. To predict state of each cell, tests use
 * Random with fixed seed equal to 4.
 * Initial state of that world is represented as following ("X" - alive state, "#" - dead state)
 *  _______
 * | X X X |
 * | X X # |
 * | X # # |
 * ¯¯¯¯¯¯¯¯¯
 * After first generation world turns into following representation:
 *  _______
 * | X # X |
 * | # # X |
 * | X X # |
 * ¯¯¯¯¯¯¯¯¯
 */
@ExtendWith(MockitoExtension.class)
class WorldTest {
  private static final int ALIVE_CELL_STATE = 1;
  private static final int COLUMN_NO = 3;
  private static final int DEAD_CELL_STATE = 0;
  private static final int ROW_NO = 3;
  private static final int SEED = 4;
  private static final int WORLD_SIZE = COLUMN_NO * ROW_NO;

  @Mock
  private Random random;

  private Map<Point, Cell> worldMap;
  private Map<Point, Cell> expectedWorld;
  private World world;

  @BeforeEach
  void setUp() {
    this.worldMap = new HashMap<>();
    this.world = new World(COLUMN_NO, ROW_NO, worldMap);
    this.expectedWorld = new HashMap<>();
    IntStream.range(0, COLUMN_NO)
        .forEach(coordinateX -> IntStream.range(0, ROW_NO)
            .forEach(coordinateY ->
                this.expectedWorld.put(new Point(coordinateX, coordinateY),
                    new Cell(new Point(coordinateX, coordinateY)))));
  }

  @Test
  void initWorld_RandomReturningDeadCellState_WorldWithAllDeadCellsAndProperNeighbourNumber() {
    // Given
    when(random.nextInt(anyInt())).thenReturn(DEAD_CELL_STATE);
    initializeDeadWorld(expectedWorld);

    // When
    world.initWorld(random);

    // Then
    assertThat(worldMap).isNotEmpty()
        .hasSize(WORLD_SIZE)
        .containsExactlyInAnyOrderEntriesOf(expectedWorld);
  }

  @Test
  void initWorld_RandomReturningAliveCellState_WorldWithAllAliveCellsAndProperNeighbourNumber() {
    // Given
    when(random.nextInt(anyInt())).thenReturn(ALIVE_CELL_STATE);
    initializeAliveWorld(expectedWorld);

    // When
    world.initWorld(random);

    // Then
    assertThat(worldMap).isNotEmpty()
        .hasSize(WORLD_SIZE)
        .containsExactlyInAnyOrderEntriesOf(expectedWorld);
  }

  @Test
  void initWorld_RandomWithGivenSeed_WorldWithExpectedCellsAndTheirNeighbourNumbers() {
    // Given
    var randomWithSeed = new Random(SEED);
    initializeWorldWithSeed(expectedWorld);

    // When
    world.initWorld(randomWithSeed);

    // Then
    assertThat(worldMap).isNotEmpty()
        .hasSize(WORLD_SIZE)
        .containsExactlyInAnyOrderEntriesOf(expectedWorld);
  }

  @Test
  void setNextGeneration_WorldInitializedWithRandomWithGivenSeed_ExpectedWorld() {
    // Given
    var randomWithSeed = new Random(SEED);
    world.initWorld(randomWithSeed);
    initializeWorldWithSeed(expectedWorld);
    setSecondGenerationFutureState(expectedWorld);

    // When
    world.setNextGeneration();

    // Then
    assertThat(worldMap).isNotEmpty()
        .hasSize(WORLD_SIZE)
        .containsExactlyInAnyOrderEntriesOf(expectedWorld);
  }

  @Test
  void evolveToNextGeneration_WorldInitializedWithRandomWithGivenSeed_ExpectedWorld() {
    // Given
    var randomWithSeed = new Random(SEED);
    world.initWorld(randomWithSeed);
    world.setNextGeneration();
    initializeWorldWithSeed(expectedWorld);
    setSecondGenerationCurrentState(expectedWorld);

    // When
    world.evolveToNextGeneration();

    // Then
    assertThat(worldMap).isNotEmpty()
        .hasSize(WORLD_SIZE)
        .containsExactlyInAnyOrderEntriesOf(expectedWorld);
  }

  @Test
  void getWorldRepresentation_WorldInitializedWithGivenSeed_ExpectedWorldRepresentation() {
    // Given
    var randomWithSeed = new Random(SEED);
    world.initWorld(randomWithSeed);
    var expectedSeedWorldRepresentation = "X X X \n"
        + "X X # \n"
        + "X # # \n";

    // When
    var worldRepresentation = world.getWorldRepresentation();

    // Then
    assertThat(worldRepresentation).isNotNull()
        .isEqualTo(expectedSeedWorldRepresentation);
  }

  private void initializeDeadWorld(Map<Point, Cell> expectedWorldMap) {
    expectedWorldMap.values().forEach(cell -> cell.setCurrentState(new DeadCellState(cell)));
    setNeighbours(expectedWorldMap);
  }

  private void initializeAliveWorld(Map<Point, Cell> expectedWorldMap) {
    expectedWorldMap.values().forEach(cell -> cell.setCurrentState(new AliveCellState(cell)));
    setNeighbours(expectedWorldMap);
  }

  private void initializeWorldWithSeed(Map<Point, Cell> expectedWorldMap) {
    setAliveCellsForSeed(expectedWorldMap);
    setDeadCellsForSeed(expectedWorldMap);
    setNeighbours(expectedWorldMap);
  }

  private void setNeighbours(Map<Point, Cell> expectedWorldMap) {
    expectedWorldMap.values().forEach(cell -> cell.setNeighbours(expectedWorldMap));
  }

  private void setAliveCellsForSeed(Map<Point, Cell> expectedWorldMap) {
    final var initColumn = 0;
    final var initRow = 0;
    var maxRowsInColumn = 3;
    for (int coordinateX = initColumn; coordinateX < COLUMN_NO; coordinateX++) {
      for (int coordinateY = initRow; coordinateY < maxRowsInColumn; coordinateY++) {
        var cell = expectedWorldMap.get(new Point(coordinateX, coordinateY));
        cell.setCurrentState(new AliveCellState(cell));
      }
      maxRowsInColumn--;
    }
  }

  private void setDeadCellsForSeed(Map<Point, Cell> expectedWorldMap) {
    final var initColumn = 1;
    final var initRow = 2;
    final var maxColumn = 2;
    var coordinateX = initColumn;
    for (int i = 0; i < maxColumn; i++) {
      var coordinateY = initRow;
      for (int k = 0; k < coordinateX; k++) {
        var cell = expectedWorldMap.get(new Point(coordinateX, coordinateY));
        cell.setCurrentState(new DeadCellState(cell));
        coordinateY--;
      }
      coordinateX++;
    }
  }

  private void setSecondGenerationFutureState(Map<Point, Cell> expectedWorldMap) {
    setAliveFutureState(expectedWorldMap, new Point(0, 0));
    setDeadFutureState(expectedWorldMap, new Point(0, 1));
    setAliveFutureState(expectedWorldMap, new Point(0, 2));
    setDeadFutureState(expectedWorldMap, new Point(1, 0));
    setDeadFutureState(expectedWorldMap, new Point(1, 1));
    setAliveFutureState(expectedWorldMap, new Point(1, 2));
    setAliveFutureState(expectedWorldMap, new Point(2, 0));
    setAliveFutureState(expectedWorldMap, new Point(2, 1));
    setDeadFutureState(expectedWorldMap, new Point(2, 2));
  }

  private void setSecondGenerationCurrentState(Map<Point, Cell> expectedWorldMap) {
    setSecondGenerationAliveState(expectedWorldMap, new Point(0, 0));
    setSecondGenerationDeadState(expectedWorldMap, new Point(0, 1));
    setSecondGenerationAliveState(expectedWorldMap, new Point(0, 2));
    setSecondGenerationDeadState(expectedWorldMap, new Point(1, 0));
    setSecondGenerationDeadState(expectedWorldMap, new Point(1, 1));
    setSecondGenerationAliveState(expectedWorldMap, new Point(1, 2));
    setSecondGenerationAliveState(expectedWorldMap, new Point(2, 0));
    setSecondGenerationAliveState(expectedWorldMap, new Point(2, 1));
    setSecondGenerationDeadState(expectedWorldMap, new Point(2, 2));
  }

  private void setDeadFutureState(Map<Point, Cell> expectedWorldMap, Point point) {
    var cell = expectedWorldMap.get(point);
    cell.setFutureState(new DeadCellState(cell));
  }

  private void setAliveFutureState(Map<Point, Cell> expectedWorldMap, Point point) {
    var cell = expectedWorldMap.get(point);
    cell.setFutureState(new AliveCellState(cell));
  }

  private void setSecondGenerationAliveState(Map<Point, Cell> expectedWorldMap, Point point) {
    var cell = expectedWorldMap.get(point);
    cell.setCurrentState(new AliveCellState(cell));
    cell.setFutureState(new UnknownCellState(cell));
  }

  private void setSecondGenerationDeadState(Map<Point, Cell> expectedWorldMap, Point point) {
    var cell = expectedWorldMap.get(point);
    cell.setCurrentState(new DeadCellState(cell));
    cell.setFutureState(new UnknownCellState(cell));
  }
}
