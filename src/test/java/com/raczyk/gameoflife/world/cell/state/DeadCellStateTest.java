package com.raczyk.gameoflife.world.cell.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.raczyk.gameoflife.world.cell.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeadCellStateTest {

  private DeadCellState deadCellState;

  @Mock
  private Cell cell;

  @BeforeEach
  void setUp() {
    this.deadCellState = new DeadCellState(cell);
  }

  @Test
  void changeState_ReproductionInCellSubEnvironment_AliveCellStateAsFutureState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(3L);

    // When
    var futureCellState = deadCellState.determineFutureState();

    // Then
    assertThat(futureCellState).isEqualTo(new AliveCellState(cell));
  }

  @Test
  void changeState_NormalStateInCellSubEnvironment_DeadCellStateAsFutureState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(6L);

    // When
    var futureCellState = deadCellState.determineFutureState();

    // Then
    assertThat(futureCellState).isEqualTo(new DeadCellState(cell));
  }

  @Test
  void display_DeadCellState_ProperStateRepresentation() {
    // When
    var stateDisplayed = deadCellState.display();

    // Then
    assertThat(stateDisplayed).isEqualTo("#");
  }

  @Test
  void getState_DeadCellState_DeadState() {
    // When
    var state = deadCellState.getState();

    // Then
    assertThat(state).isEqualTo(State.DEAD);
  }
}
