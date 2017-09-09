package main.java.assignment.models;

import main.java.assignment.constants.RulesConstants;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Chintan Tank on 9/2/17.
 */
public class Cell {

  private int rowIndex;
  private int colIndex;
  private boolean isAlive = false;
  private AtomicInteger aliveNeighborsCount = new AtomicInteger(0);

  public Cell(int rowIndex, int colIndex) {
    this.rowIndex = rowIndex;
    this.colIndex = colIndex;
  }

  public int registerAliveNeighbor() {
    return this.aliveNeighborsCount.incrementAndGet();
  }

  public boolean setNextGenerationState() {

    boolean isGoingToBeAliveNextTick = this.isAlive;

    if (this.isAlive) {
      if (this.aliveNeighborsCount.get() > RulesConstants.MAX_ALIVE_NEIGHBORS_FOR_SELF_ALIVENESS) {
        isGoingToBeAliveNextTick = false;

      } else if (this.aliveNeighborsCount.get() < RulesConstants.MIN_ALIVE_NEIGHBORS_FOR_SELF_ALIVENESS) {
        isGoingToBeAliveNextTick = false;
      }
    } else {
      if (this.aliveNeighborsCount.get() == RulesConstants.ALIVE_NEIGHBORS_FOR_REGENERATION) {
        isGoingToBeAliveNextTick = true;
      }
    }

    this.aliveNeighborsCount.set(0);

    /*
    * The cell's state in next generation is going to be different if the new value for aliveness
     * is different from current isAlive.
    * */
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
