package main.java.assignment.models;

import main.java.assignment.constants.RulesConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Chintan Tank on 9/2/17.
 */
public class Cell {

  private int rowIndex;
  private int colIndex;
  private boolean isAlive = false;
  private Set<Cell> aliveNeighbors = new HashSet<>();

  public Cell(int rowIndex, int colIndex) {
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }

  public boolean addAliveNeighbor(Cell aliveNeighbor) {
    return this.aliveNeighbors.add(aliveNeighbor);
  }

  public boolean setNextGenerationState() {

    int aliveNeighborCount = aliveNeighbors.size();
    this.aliveNeighbors = new HashSet<>();

    boolean isGoingToBeAliveNextTick = this.isAlive;

    if (this.isAlive) {
      if (aliveNeighborCount > RulesConstants.MAX_ALIVE_NEIGHBORS_FOR_SELF_ALIVENESS) {
//        this.isAlive = false;
        isGoingToBeAliveNextTick = false;

      } else if (aliveNeighborCount < RulesConstants.MIN_ALIVE_NEIGHBORS_FOR_SELF_ALIVENESS) {
//        this.isAlive = false;
        isGoingToBeAliveNextTick = false;
      }
    } else {
      if (aliveNeighborCount == RulesConstants.ALIVE_NEIGHBORS_FOR_REGENERATION) {
//        this.isAlive = true;
        isGoingToBeAliveNextTick = true;
      }
    }

    boolean nextGenerationStateDifferent = (isGoingToBeAliveNextTick != this.isAlive);

    this.isAlive = isGoingToBeAliveNextTick;

    return nextGenerationStateDifferent;
  }

  public void setIsAlive(boolean isAlive) {
    this.isAlive = isAlive;
  }

  public boolean getIsAlive() {
    return this.isAlive;
  }

  public int getRowIndex() {
    return rowIndex;
  }

  public int getColIndex() {
    return colIndex;
  }
}
