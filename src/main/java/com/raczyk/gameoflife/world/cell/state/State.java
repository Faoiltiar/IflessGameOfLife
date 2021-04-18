package com.raczyk.gameoflife.world.cell.state;

enum State {
  ALIVE("X"),
  DEAD("#"),
  UNKNOWN("?");

  private final String representation;

  State(String representation) {
    this.representation = representation;
  }

  public String getRepresentation() {
    return representation;
  }
}
