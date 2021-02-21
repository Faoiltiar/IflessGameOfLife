package com.raczyk.gameoflife.world;

import com.raczyk.gameoflife.world.cell.Cell;
import com.raczyk.gameoflife.world.cell.state.KnownStateFactory;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

class World {
  private final int maxColumnNo;
  private final int maxRowNo;
  private final Map<Point, Cell> worldMatrix;

  public World(int maxColumnNo, int maxRowNo, Map<Point, Cell> worldMatrix) {
    this.maxColumnNo = maxColumnNo;
    this.maxRowNo = maxRowNo;
    this.worldMatrix = worldMatrix;
  }

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
    IntStream.range(0, maxColumnNo)
        .forEach(coordinateX -> IntStream.range(0, maxRowNo)
            .forEach(coordinateY -> {
                  var worldCell = worldMatrix.get(new Point(coordinateX, coordinateY));
                  worldCell.setNeighbours(worldMatrix);
                }
            )
        );
  }

  public void setNextGeneration() {
    IntStream.range(0, maxColumnNo)
        .forEach(coordinateX -> IntStream.range(0, maxRowNo)
            .forEach(coordinateY -> {
                  var worldCell = worldMatrix.get(new Point(coordinateX, coordinateY));
                  worldCell.setNextGeneration();
                }
            )
        );
  }

  public void evolveToNextGeneration() {
    IntStream.range(0, maxColumnNo)
        .forEach(coordinateX -> IntStream.range(0, maxRowNo)
            .forEach(coordinateY -> {
                  var worldCell = worldMatrix.get(new Point(coordinateX, coordinateY));
                  worldCell.evolve();
                }
            )
        );
  }

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
