package com.raczyk.gameoflife.world.cell.state;

import com.raczyk.gameoflife.world.cell.Cell;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Class representing known cell state (alive or dead).
 */
public abstract class KnownState implements CellState {

  protected CellState getFutureState(Cell cell,
                                     Map<List<Long>, Supplier<CellState>> envStateRuleMap) {
    var livingNeighbours = cell.getLivingNeighboursNo();
    return envStateRuleMap.entrySet().stream()
        .filter(entry -> entry.getKey().contains(livingNeighbours))
        .map(entry -> entry.getValue().get())
        .findFirst().orElse(new UnknownCellState(cell));
  }

  protected CellState createDeadCellState(Cell cell) {
    return new DeadCellState(cell);
  }

  protected CellState createAliveCellState(Cell cell) {
    return new AliveCellState(cell);
  }
}
