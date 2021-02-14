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
class AliveCellStateTest {

  private AliveCellState aliveCellState;

  @Mock
  private Cell cell;

  @BeforeEach
  void setUp() {
    this.aliveCellState = new AliveCellState(cell);
  }

  @Test
  void changeState_OverpopulationInCellSubEnvironment_DeadCellStateAsFutureState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(7L);

    // When
    var futureCellState = aliveCellState.determineFutureState();

    // Then
    assertThat(futureCellState).isEqualTo(new DeadCellState(cell));
  }

  @Test
  void changeState_UnderpopulationInCellSubEnvironment_DeadCellStateAsFutureState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(0L);

    // When
    var futureCellState = aliveCellState.determineFutureState();

    // Then
    assertThat(futureCellState).isEqualTo(new DeadCellState(cell));
  }

  @Test
  void changeState_NormalStateInCellSubEnvironment_AliveCellStateAsFutureState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(3L);

    // When
    var futureCellState = aliveCellState.determineFutureState();

    // Then
    assertThat(futureCellState).isEqualTo(new AliveCellState(cell));
  }
}
