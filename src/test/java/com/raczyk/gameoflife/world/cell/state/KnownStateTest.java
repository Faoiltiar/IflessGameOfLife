package com.raczyk.gameoflife.world.cell.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

import com.raczyk.gameoflife.world.cell.Cell;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KnownStateTest {

  @Mock
  private KnownState knownState;

  @Mock
  private Cell cell;

  @BeforeEach
  void setUp() {
    Mockito.doCallRealMethod().when(this.knownState).getFutureState(any(Cell.class), anyMap());
  }

  @Test
  void getFutureState_EmptyEnvironmentStateRuleMap_UnknownCellState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(0L);
    Map<List<Long>, Supplier<CellState>> environmentStateRuleMap = Collections.emptyMap();

    // When
    var futureState = knownState.getFutureState(cell, environmentStateRuleMap);

    // Then
    assertThat(futureState).isNotNull().isEqualTo(new UnknownCellState(cell));
  }

  @Test
  void getFutureState_LivingNumberOfNeighboursNotInEnvironmentStateRuleMap_UnknownCellState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(0L);
    Map<List<Long>, Supplier<CellState>> environmentStateRuleMap =
        Map.of(List.of(1L, 2L), () -> new AliveCellState(cell));

    // When
    var futureState = knownState.getFutureState(cell, environmentStateRuleMap);

    // Then
    assertThat(futureState).isNotNull().isEqualTo(new UnknownCellState(cell));
  }

  @Test
  void getFutureState_NumberOfLivingNeighboursMatchesAliveCellState_AliveCellState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(2L);
    Map<List<Long>, Supplier<CellState>> environmentStateRuleMap =
        Map.of(List.of(1L, 2L), () -> new AliveCellState(cell),
            List.of(0L, 3L, 4L, 5L, 6L, 7L, 8L), () -> new DeadCellState(cell));

    // When
    var futureState = knownState.getFutureState(cell, environmentStateRuleMap);

    // Then
    assertThat(futureState).isNotNull().isEqualTo(new AliveCellState(cell));
  }

  @Test
  void getFutureState_NumberOfLivingNeighboursMatchesDeadCellState_DeadCellState() {
    // Given
    when(cell.getLivingNeighboursNo()).thenReturn(7L);
    Map<List<Long>, Supplier<CellState>> environmentStateRuleMap =
        Map.of(List.of(1L, 2L), () -> new AliveCellState(cell),
            List.of(0L, 3L, 4L, 5L, 6L, 7L, 8L), () -> new DeadCellState(cell));

    // When
    var futureState = knownState.getFutureState(cell, environmentStateRuleMap);

    // Then
    assertThat(futureState).isNotNull().isEqualTo(new DeadCellState(cell));
  }
}
