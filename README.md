How do I run the program?
---
I have packaged a `jar` file which will let you just run the Game of Life simulation without having to deal with going 
through the code and building the project. Just do,

```java -jar /path/to/downloaded/repo/dist/game-of-life.jar```

The above command will execute with defaults for both i.e. square sized with universe with `25` cells as `width`. It will 
run for `5` ticks. After each tick it will display the universe's current representation.


How do I customize my universe and it's lifetime?
---
You should specify both the **universe width** as a positive number and then the non-negative number of **ticks**. **BOTH** 
need to be specified, if at all.  
 
E.g. the below will simulate a universe with width of `10` cells for `15` ticks.
 
```java -jar /path/to/downloaded/repo/dist/game-of-life.jar 10 15```  
  

How do I read through the code?
---
You can start reading at the root of the codebase which is `/path/to/code/main/java/assignment/GameOfLife.java`.