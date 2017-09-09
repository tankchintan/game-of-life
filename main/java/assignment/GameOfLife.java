package main.java.assignment;

import main.java.assignment.constants.GameConstants;
import main.java.assignment.models.Universe;

/**
 * Created by Chintan Tank on 9/2/17.
 */
public class GameOfLife {

  public static void main(String[] args) {

    InputValidator inputValidator = new InputValidator(args).invoke();

    int universeSize = inputValidator.getUniverseSize();
    int ticks = inputValidator.getTicks();

    log(String.format("Playing Game of Life with universe size %s for %s ticks.", universeSize, ticks));

    int currentTick = 0;

    Universe universe = new Universe(universeSize);
    universe.generateUniverse();

    /*
    * Print the seed state of the universe.
    * */
    renderUniverseState(currentTick, universe.getRenderedUniverseState());

    while (currentTick < ticks) {
      currentTick++;
      universe.mutate();
      renderUniverseState(currentTick, universe.getRenderedUniverseState());
    }

  }

  private static void renderUniverseState(int currentTick, String state) {
    log("Rendering state at tick: " + currentTick);
    log(state);
  }

  private static void log(String log) {
    System.out.println(log);
  }

  private static class InputValidator {
    private String[] args;
    private int universeSize;
    private int ticks;

    public InputValidator(String... args) {
      this.args = args;
    }

    public int getUniverseSize() {
      return universeSize;
    }

    public int getTicks() {
      return ticks;
    }

    public InputValidator invoke() {
      /*
      * Basically if no arguments are provided then use defaults.
      * */
      if (args.length != 0 && args.length != 2) {

        log(String.format("Please provide 2 arguments for running the program."
            + "\n\t1st argument for conveying size of the universe. Default is '%s'."
            + "\n\t2nd argument for conveying number of ticks for which to run the game of life. "
            + "Default is '%s'.", GameConstants.DEFAULT_UNIVERSE_SIZE, GameConstants.DEFAULT_TICKS));
        System.exit(-1);
      }

      universeSize = GameConstants.DEFAULT_UNIVERSE_SIZE;
      ticks = GameConstants.DEFAULT_TICKS;

      if (args.length == 2) {
        try {
          universeSize = Integer.parseInt(args[0]);
          ticks = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
          log(String.format("Please provide integer arguments for running the program."
              + "\n\t1st argument: %s"
              + "\n\t2nd argument: %s", args[0], args[1]));
          System.exit(-1);
        }
      }

      if (universeSize < 1) {
        log(String.format("Please provide positive universe size."
            + "\n\tCurrent value: %s", universeSize));
        System.exit(-1);
      }

      if (ticks < 0) {
        log(String.format("Please provide non-negative universe size."
            + "\n\tCurrent value: %s", ticks));
        System.exit(-1);
      }
      return this;
    }
  }
}
