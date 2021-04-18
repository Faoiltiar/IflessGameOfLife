package com.raczyk.gameoflife.world.cell.state;

import com.raczyk.gameoflife.world.cell.Cell;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Class responsible for generating cell states.
 * It contains map of Suppliers, helping get one of the possible states,
 * based on given KnownStateType enum state.
 */
public class KnownStateFactory {

  private final Map<KnownStateType, Supplier<KnownState>> knownStateSupplierMap;
  private final Random random;

  /**
   * Initializes a newly created KnownStateFactory object so that it allows
   * to get one of the KnownState objects representing provided cell state.
   *
   * @param cell cell which state needs to be established.
   * @param random object used to generate random state for provided cell.
   *
   */
  public KnownStateFactory(Cell cell, Random random) {
    this.knownStateSupplierMap = Map.of(
        KnownStateType.DEAD, () -> new DeadCellState(cell),
        KnownStateType.ALIVE, () -> new AliveCellState(cell)
    );
    this.random = random;
  }

  /**
   * Method responsible for generating random KnownState object representing provided cell state.
   *
   * @return one of KnownState object provided in knownStateSupplierMap supplier.
   */
  public CellState getRandomKnownState() {
    return knownStateSupplierMap.get(KnownStateType.getRandom(random)).get();
  }

  private enum KnownStateType {
    DEAD,
    ALIVE;

    private static KnownStateType getRandom(Random random) {
      return values()[random.nextInt(values().length)];
    }
  }
}
