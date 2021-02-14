package com.raczyk.gameoflife.world.cell.state;

import com.raczyk.gameoflife.world.cell.Cell;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Class representing state of a cell which is dead.
 * It contains constants of possible sub-environments states:
 *  - reproduction (when 3 neighbour cells are alive)
 *  - normal (when the number of living neighbour cells is different than 3.
 *    This sub-environment state does not change cell state).
 *  Based on sub-environment state, state of a cell is changed respectively.
 */
public final class DeadCellState extends KnownState {

  private static final List<Long> REPRODUCTION = List.of(3L);
  private static final List<Long> NORMAL = List.of(0L, 1L, 2L, 4L, 5L, 6L, 7L, 8L);

  private final Cell cell;
  private final Map<List<Long>, Supplier<CellState>> environmentStateRuleMap;

  /**
   * Initializes a newly created DeadCellState object so that it represents
   * the state of a cell which is passed as a parameter.
   *
   * @param cell cell which state is represented by this class.
   *
   */
  public DeadCellState(Cell cell) {
    this.cell = cell;
    this.environmentStateRuleMap = Map.of(REPRODUCTION, () -> createAliveCellState(cell),
        NORMAL, () -> createDeadCellState(cell));
  }

  @Override
  public CellState determineFutureState() {
    return getFutureState(this.cell, this.environmentStateRuleMap);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeadCellState that = (DeadCellState) o;
    return Objects.equals(cell, that.cell);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cell);
  }
}
