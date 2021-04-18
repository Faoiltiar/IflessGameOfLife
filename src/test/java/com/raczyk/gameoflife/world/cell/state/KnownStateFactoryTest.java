package com.raczyk.gameoflife.world.cell.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.raczyk.gameoflife.world.Point;
import com.raczyk.gameoflife.world.cell.Cell;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KnownStateFactoryTest {

  @Mock
  private Random random;

  private final Cell cell;
  private KnownStateFactory knownStateFactory;

  KnownStateFactoryTest() {
    cell = new Cell(new Point(0, 0));
  }

  @BeforeEach
  void setUp() {
    knownStateFactory = new KnownStateFactory(cell, random);
  }

  @Test
  void getRandomKnownState_RandomReturningZero_DeadCellState() {
    // Given
    when(random.nextInt(anyInt())).thenReturn(0);

    // When
    var state = knownStateFactory.getRandomKnownState();

    // Then
    assertThat(state).isEqualTo(new DeadCellState(cell));
  }

  @Test
  void getRandomKnownState_RandomReturningOne_AliveCellState() {
    // Given
    when(random.nextInt(anyInt())).thenReturn(1);

    // When
    var state = knownStateFactory.getRandomKnownState();

    // Then
    assertThat(state).isEqualTo(new AliveCellState(cell));
  }
}
