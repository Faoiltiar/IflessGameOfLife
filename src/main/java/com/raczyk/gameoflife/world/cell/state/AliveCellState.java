package com.raczyk.gameoflife.world.cell.state;

import com.raczyk.gameoflife.world.cell.Cell;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Class representing state of a cell which lives.
 * It contains constants of possible sub-environments states:
 *  - underpopulation (when only 0 or 1 neighbour cell is alive)
 *  - overpopulation (when more than 3 neighbour cells are alive)
 *  - normal (when 2 or 3 neighbour cells are alive.
 *    This sub-environment state does not change cell state).
 * Based on sub-environment state, state of a cell is changed respectively.
 */
public final class AliveCellState extends KnownState {

  private static final List<Long> UNDERPOPULATION = List.of(0L, 1L);
  private static final List<Long> OVERPOPULATION = List.of(4L, 5L, 6L, 7L, 8L);
  private static final List<Long> NORMAL = List.of(2L, 3L);

  private final Cell cell;
  private final Map<List<Long>, Supplier<CellState>> environmentStateRuleMap;
  private final State state;

  /**
   * Initializes a newly created AliveCellState object so that it represents
   * the alive state of a cell passed as a parameter.
   *
   * @param cell cell which state is represented by this class.
   *
   */
  public AliveCellState(Cell cell) {
    this.cell = cell;
    this.environmentStateRuleMap = Map.of(NORMAL, () -> createAliveCellState(cell),
        UNDERPOPULATION, () -> createDeadCellState(cell),
        OVERPOPULATION, () -> createDeadCellState(cell));
    this.state = State.ALIVE;
  }

  @Override
  public CellState determineFutureState() {
    return getFutureState(this.cell, this.environmentStateRuleMap);
  }

  @Override
  public String display() {
    return state.getRepresentation();
  }

  @Override
  public State getState() {
    return state;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AliveCellState that = (AliveCellState) o;
    return Objects.equals(cell, that.cell);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cell);
  }
}
