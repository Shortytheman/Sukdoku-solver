import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class SudokuSolver {
  final int boardSize = 9;
  int[][] gameboard;

  public void setGameboard(int[][] gameboard) {
    this.gameboard = gameboard;
  }

  public int[][] getGameboard() {
    return gameboard;
  }

  public void makeGameboard() {
    Scanner in = new Scanner(System.in);
    int[][] gameboard = new int[9][9];
    boolean numberIsOk = false;
    Random rand = new Random();
    System.out.println("Do you want to make the board manually or let me make it for you?");
    System.out.println("""
        1: Make it myself
        2: Let the system make it for me""");
    int choice = in.nextInt();
    if (choice == 1) {
      for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
          numberIsOk = false;
          while (!numberIsOk) {
            System.out.println("\nNæste tal i rækken: ");
            int f = in.nextInt();
            if (f < 10 && f > -1) {
              gameboard[i][j] = f;
              for (int start = 0; start <= j; start++) {
                if (start == j && start == i){
                  break;
                }
                if (start == 0 || start == 3 || start == 6) {
                  System.out.print("[ " + gameboard[i][start]);
                }
                if (start == 2 || start == 5 || start == 8) {
                  System.out.print(gameboard[i][start] + " ]");
                } else if (start != 0 && start != 3 && start != 6)
                  System.out.print(gameboard[i][start]);
              }
              numberIsOk = true;
            } else {
              System.out.println("Wrong input, try again.");
            }
          }
          System.out.println("\n");
        }
      }
    } else {
      for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
          int number = rand.nextInt(10);
          if (number > 7) {


            int upLeftRow = i - i % 3;
            int upLeftColumn = j - j % 3;
            boolean checking;

            do {
              checking = false;
              number = rand.nextInt(10);
              for (int row = 0; row < boardSize; row++) {
                if (number == gameboard[i][row]) {
                  checking = true;
                  break;
                }
              }
              if (!checking) {
                for (int column = 0; column < boardSize; column++) {
                  if (number == gameboard[column][j]) {
                    checking = true;
                    break;
                  }
                }
              }
                if (!checking) {
                  for (int q = upLeftRow; q < upLeftRow + 2; q++) {
                    for (int w = upLeftColumn; w < upLeftColumn + 2; w++) {
                      if (gameboard[i][j] == number) {
                        checking = true;
                        break;
                      }
                    }
                  }
                }
              System.out.println(number);
            } while (checking);
            gameboard[i][j] = number;
          } else {
            gameboard[i][j] = 0;
          }
        }
      }
    }
    printGameboard(gameboard);
    setGameboard(gameboard);
  }

  public void printGameboard(int[][] gameboard) {

    for (int i = 0; i < gameboard.length; i++) {
      System.out.print("[ ");
      for (int j = 0; j < gameboard.length; j++) {
        System.out.print(gameboard[i][j] + " ");
        if (j == 2 || j == 5) {
          System.out.print("][ ");
        }
      }
      System.out.print("]");
      System.out.println();
      if (i == 2 || i == 5) {
        System.out.println("---------------------------");
      }
    }
  }

  public boolean solveSukoku(int[][] board) {
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        if (board[i][j] == 0) {
          for (int l = 1; l <= boardSize; l++) {
            if (!check(l, i, j)) {
              board[i][j] = l;
              if (solveSukoku(board)) {
                return true;
              } else {
                board[i][j] = 0;
              }
            }
          }
          return false;
        }
      }
    }
    return true;
  }


  // [0][0]
  // [0][1], [0][2]
  // [1][0], [1][1],[1][2]
  // [2][0], [2][1],[2][2]

  // Row _____
  // Column ||

  public boolean check(int check, int row, int column) {
    int upLeftRow = row - row % 3;
    int upLeftColumn = column - column % 3;

    boolean checking = false;
    for (int i = 0; i < boardSize; i++) {
      if (check == getGameboard()[row][i]) {
        checking = true;
      }
    }
    if (!checking) {
      for (int i = 0; i < boardSize; i++) {
        if (check == getGameboard()[i][column]) {
          checking = true;
        }
      }
      if (!checking) {
        for (int i = upLeftRow; i < upLeftRow + 2; i++) {
          for (int j = upLeftColumn; j < upLeftColumn + 2; j++) {
            if (i == row && j == column) {
              j++;
            }
            if (getGameboard()[i][j] == check) {
              checking = true;
            }
          }
        }
      }
    }
    return checking;
  }
        /*
        if (column < 1 && row > 0 && row < 8){
          for (int i = row; i <= row + 1; i++) {
            for (int j = column; j <= column + 2; j++) {
              if(i == row && j == column){
                j++;
              }
              if (check == makeGameboard()[i][j]) {
                checking = true;
              }
            }
            for (int j = column; j <= column + 2; j++){
              if (check == makeGameboard()[i - 1][j]) {
                checking = true;
              }
            }
          }
        }

        if (row > 7){
          for (int i = row; i < 3; i++) {
            for (int j = column; j < 4; j++) {
              if(i == row && j == column){
                j++;
              }
              if (check == makeGameboard()[i][j]) {
                checking = true;
              }
            }
          }
        }
        else {
          for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
              if (check == makeGameboard()[i][j]) {
                checking = true;
              }
            }
          }
        }
      }
    }
    */


  public static void main(String[] args) {
    SudokuSolver sudoku = new SudokuSolver();
    sudoku.makeGameboard();

    if (sudoku.solveSukoku(sudoku.getGameboard())) {
      System.out.println("You solved the sukdoku");
      sudoku.printGameboard(sudoku.getGameboard());
    } else {
      System.out.println("You couldn't solve the sudoku");
    }
  }
}

