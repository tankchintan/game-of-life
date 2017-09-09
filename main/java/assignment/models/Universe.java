package main.java.assignment.models;

import main.java.assignment.constants.UniverseConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Created by Chintan Tank on 9/2/17.
 */
public class Universe {

  private int universeWidth;
  private Map<String, Cell> cellLocationToObject = new HashMap<>();

  private boolean skipMutationWork = false;

  private Random random = new Random();

  public Universe(int universeWidth) {
    this.universeWidth = universeWidth;
  }


  public void generateUniverse() {

    for (int ii=0; ii<universeWidth; ii++) {
      for (int jj=0; jj<universeWidth; jj++) {
        String cellId = generateCellId(ii, jj);
        cellLocationToObject.put(cellId, new Cell(ii, jj));
      }
    }

    /*
    * If the universe size is big enough to place the glider pattern, then let's do that.
    * */
    if (this.universeWidth >= UniverseConstants.MIN_UNIVERSE_WIDTH_SIZE_FOR_GLIDER_PLACEMENT) {
      int startGliderBoxIndex = this.universeWidth / 2 - 1;

      getCellAt(startGliderBoxIndex, startGliderBoxIndex + 1).setIsAlive(true);
      getCellAt(startGliderBoxIndex + 1, startGliderBoxIndex + 2).setIsAlive(true);

      for (int ii=0; ii<3; ii++) {
        getCellAt(startGliderBoxIndex + 2, startGliderBoxIndex + ii).setIsAlive(true);
      }

    } else {

      int currentCount = 0;
      while (currentCount < universeWidth) {
        int currentRow = random.nextInt(universeWidth);
        int currentCol = random.nextInt(universeWidth);

        getCellAt(currentRow, currentCol).setIsAlive(true);

        currentCount++;
      }
    }
  }

  public void mutate() {

    /*
    * We should skip mutation work, if there is not going to be any changes in any cell's state.
    * */
    if (skipMutationWork) {
      return;
    }

    traverseUniverseByRowAndPerformOperationOnCell(cell -> {

      /*
      * Only if a cell is active go through all the neighbors & let them know that
      * they have at least one alive neighbor.
      * */
      if (cell.getIsAlive()) {

        /*
        * Start from the top-left neighbor, traverse row by row.
        * */
        for (int mm = -1; mm <= 1; mm++) {
          for (int nn = -1; nn <= 1; nn++) {

            int neighborRow = cell.getRowIndex() + mm;
            int neighborCol = cell.getColIndex() + nn;

            /*
            * While traversal ignore the current subject cell's location.
            * */
            if (neighborRow == cell.getRowIndex() && neighborCol == cell.getColIndex()) {
              continue;
            }

            /*
            * Bound check.
            * */
            if (neighborRow >= 0 && neighborRow < universeWidth
                && neighborCol >= 0 && neighborCol < universeWidth) {
              getCellAt(neighborRow, neighborCol)
                  .addAliveNeighbor(cell);
            }
          }
        }
      }

    }, rowIndex -> {});

    /*
    * Keep track of number of cells changing their state in the next tick.
    * */
    AtomicInteger cellStateChangingNextTickCount = new AtomicInteger();

    traverseUniverseByRowAndPerformOperationOnCell(cell -> {
      boolean stateChangingNextTick = cell.setNextGenerationState();

      if (stateChangingNextTick) {
        cellStateChangingNextTickCount.incrementAndGet();
      }
    }, (rowIndex) -> {});

    /*
    * If the num of cells, not changing states is zero; then let's set skipMutationWork
    * */
    this.skipMutationWork = cellStateChangingNextTickCount.get() == 0;
  }

  public String getRenderedUniverseState() {
    StringBuilder universeDisplay = new StringBuilder();

    traverseUniverseByRowAndPerformOperationOnCell(cell -> {
      if (cell.getIsAlive()) {
        universeDisplay.append(UniverseConstants.ALIVE_CELL_REPRESENTATION);
      } else {
        universeDisplay.append(UniverseConstants.DEAD_CELL_REPRESENTATION);
      }
      universeDisplay.append(" ");
    }, rowIndex -> {
      universeDisplay.append("\n");
    });

    return universeDisplay.toString();
  }

  private void traverseUniverseByRowAndPerformOperationOnCell(Consumer<Cell> operateOnCellFunction, Consumer<Integer> endOfRowFunction) {

    for (int ii=0; ii<universeWidth; ii++) {
      for (int jj = 0; jj < universeWidth; jj++) {
        operateOnCellFunction.accept(getCellAt(ii, jj));
      }
      endOfRowFunction.accept(ii);
    }
  }

  private Cell getCellAt(int rowIndex, int colIndex) {
    return this.cellLocationToObject.get(generateCellId(rowIndex, colIndex));
  }

  private String generateCellId(int row, int col) {
    return String.format("%s:%s", row, col);
  }

}
