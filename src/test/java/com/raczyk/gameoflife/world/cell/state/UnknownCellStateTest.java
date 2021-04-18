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
  void changeState_UnknownCellState_CellFutureStateSetToTheSameUnknownState() {
    // When
    var futureCellState = unknownCellState.determineFutureState();

    // Then
    assertThat(futureCellState).isEqualTo(unknownCellState);
  }

  @Test
  void display_UnknownCellState_ProperStateRepresentation() {
    // When
    var stateDisplayed = unknownCellState.display();

    // Then
    assertThat(stateDisplayed).isEqualTo("?");
  }

  @Test
  void getState_UnknownCellState_UnknownState() {
    // When
    var state = unknownCellState.getState();

    // Then
    assertThat(state).isEqualTo(State.UNKNOWN);
  }
}
