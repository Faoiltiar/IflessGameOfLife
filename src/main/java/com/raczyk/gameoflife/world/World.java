package com.raczyk.gameoflife.world;

import com.raczyk.gameoflife.world.cell.Cell;
import com.raczyk.gameoflife.world.cell.state.KnownStateFactory;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Class representing world build with matrix of cells.
 */
public class World {
  private final int maxColumnNo;
  private final int maxRowNo;
  private final Map<Point, Cell> worldMatrix;

  /**
   * Constructs a world represented as matrix of cells with
   * given number of columns and rows passed as parameter.
   *
   * @param maxColumnNo number of columns in matrix of cells.
   * @param maxRowNo number of rows in matrix of cells.
   * @param worldMatrix matrix used as a container for cells.
   */
  public World(int maxColumnNo, int maxRowNo, Map<Point, Cell> worldMatrix) {
    this.maxColumnNo = maxColumnNo;
    this.maxRowNo = maxRowNo;
    this.worldMatrix = worldMatrix;
  }

  /**
   * Method initializing world matrix. It firstly create cells and pick random state for each one.
   * When all cells are created, each cell register its neighbours.
   *
   * @param random object used to generate random states of cells.
   */
  public void initWorld(Random random) {
    initializeWorldWithCells(random);
    registerNeighbour();
  }

  private void initializeWorldWithCells(Random random) {
    IntStream.range(0, maxColumnNo)
        .forEach(coordinateX -> IntStream.range(0, maxRowNo)
            .forEach(coordinateY -> {
                  var point = new Point(coordinateX, coordinateY);
                  var worldCell = new Cell(point);
                  var knownStateFactory = new KnownStateFactory(worldCell, random);
                  worldCell.setCurrentState(knownStateFactory.getRandomKnownState());
                  worldMatrix.put(point, worldCell);
                }
            )
        );
  }

  private void registerNeighbour() {
    worldMatrix.values().forEach(cell -> cell.setNeighbours(worldMatrix));
  }

  /**
   * Method establishing next generation of cells by setting future state
   * for each cell in world matrix.
   */
  public void setNextGeneration() {
    worldMatrix.values().forEach(Cell::setNextGeneration);
  }

  /**
   * Method evolving cells to the new generation.
   * It does that by changing current state of each cell to the future state.
   */
  public void evolveToNextGeneration() {
    worldMatrix.values().forEach(Cell::evolve);
  }

  /**
   * Method returning String representing state of cells in world matrix.
   *
   * @return String presenting current world state.
   */
  public String getWorldRepresentation() {
    final var stateSeparator = " ";
    final var rowSeparator = "\n";
    var worldState = new StringBuilder();
    IntStream.range(0, maxColumnNo)
        .forEach(coordinateX -> {
          IntStream.range(0, maxRowNo).forEach(coordinateY -> {
                var worldCell = worldMatrix.get(new Point(coordinateX, coordinateY));
                worldState.append(worldCell.display());
                worldState.append(stateSeparator);
              }
          );
          worldState.append(rowSeparator);
        }
        );
    return worldState.toString();
  }
}
