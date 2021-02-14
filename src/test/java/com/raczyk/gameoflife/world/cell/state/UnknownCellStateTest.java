package com.raczyk.gameoflife.world.cell.state;

import static org.assertj.core.api.Assertions.assertThat;

import com.raczyk.gameoflife.world.Point;
import com.raczyk.gameoflife.world.cell.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UnknownCellStateTest {

  private Cell cell;
  private UnknownCellState unknownCellState;

  @BeforeEach
  void setUp() {
    this.cell = new Cell(new Point(0, 0));
    this.unknownCellState = new UnknownCellState(cell);
  }

  @Test
  void changeState_UnknownCellWithGivenCell_CellFutureStateSetToTheSameUnknownState() {
    // When
    var futureCellState = unknownCellState.determineFutureState();

    // Then
    assertThat(futureCellState).isEqualTo(unknownCellState);
  }
}
