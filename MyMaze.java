// Names: Jose Hernandez , Ketim Lamessa
// x500s: Hern0492, Lames005

import java.util.Random;
import java.util.Scanner;

public class MyMaze {
    Cell[][] maze;
    int startRow;
    int endRow;
    int cols;
    int rows;

    public MyMaze(int rows, int cols, int startRow, int endRow) { // Construtor of the maze
        this.maze = new Cell[rows][cols];// set the maze and the # of rows/cols
        this.startRow = startRow;
        this.endRow = endRow;
        this.rows = rows;// created this variables so i could do print easily
        this.cols = cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = new Cell();// goes through the array and creates the into cells
            }
        }
    }

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(int rows, int cols, int startRow, int endRow) {
        MyMaze maze0 = new MyMaze(rows, cols, startRow, endRow);// Makes a new maze with the given rows, and columns
        Random ramon = new Random(); // starts a random variable for later use
        Stack1Gen<int[]> newStack = new Stack1Gen(); // create the stack
        newStack.push(new int[]{startRow, 0}); // pushes the beginning coordinate
        maze0.maze[startRow][0].setVisited(true); // sets the first cell to visited
        while (newStack.isEmpty() != true) {
            int[] coor = newStack.top(); // gets the top coordinate from the stack
            int row = coor[0]; // row
            int col = coor[1];//
            int neighbor = ramon.nextInt(4); // gets a random number between 0 and 3
            int count = 0; // the # of neighbors that are visited already
            if (col + 1 < cols) { // checks if it between bounds
                if (maze0.maze[row][col + 1].getVisited() != true) { // checks if it is visited
                    count++; // if its not visited then it adds one to the count
                }
            }
            if (0 <= col - 1) {
                if (maze0.maze[row][col - 1].getVisited() != true) {
                    count++;
                }
            }
            if (row + 1 < rows) {
                if (maze0.maze[row + 1][col].getVisited() != true) {
                    count++;
                }
            }
            if (0 <= row - 1) {
                if (maze0.maze[row - 1][col].getVisited() != true) {
                    count++;
                }
            }
            if (count == 0) { // if there are no unvisited neighbors then it will pop it from the stack
                newStack.pop();
            }

            if (col + 1 < cols && neighbor == 0) { //if the random number is 0 it will check the right
                if (maze0.maze[row][col + 1].getVisited() != true) { // if the cell has not been visited it will add it to the stack, set it as visited, and set the walls respectively to the starting cell
                    maze0.maze[row][col+1].setVisited(true);
                    newStack.push(new int[]{row, col + 1});
                    maze0.maze[row][col].setRight(false);
                }
            }
            if (0 <= col - 1 && neighbor == 1) {//if the random number is 1 it will check the left
                if (maze0.maze[row][col - 1].getVisited() != true) {
                    maze0.maze[row][col-1].setVisited(true);// if the cell has not been visited it will add it to the stack, set it as visited, and set the walls respectively to the starting cell
                    newStack.push(new int[]{row, col - 1});
                    maze0.maze[row][col-1].setRight(false);
                }
            }
            if (row + 1 < rows && neighbor == 2) {//if the random number is 2 it will check the bottom
                if (maze0.maze[row + 1][col].getVisited() != true) {
                    maze0.maze[row + 1][col].setVisited(true);// if the cell has not been visited it will add it to the stack, set it as visited, and set the walls respectively to the starting cell
                    newStack.push(new int[]{row + 1, col});
                    maze0.maze[row][col].setBottom(false);
                }
            }
            if (0 <= row - 1 && neighbor == 3) {//if the random number is 3 it will check the top
                if (maze0.maze[row - 1][col].getVisited() != true) {
                    maze0.maze[row - 1][col].setVisited(true);// if the cell has not been visited it will add it to the stack, set it as visited, and set the walls respectively to the starting cell
                    maze0.maze[row-1][col].setBottom(false);
                    newStack.push(new int[]{row - 1, col});


                }
            }

        }
        for(int i = 0; i< rows; i ++){
            for(int j = 0; j < cols; j++){
                maze0.maze[i][j].setVisited(false); // goes through the maze and sets everything to unvisited
            }
        }
        return maze0;
    }

    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze(boolean path) {
        String firstln = "|";
        for (int j = 0; j < cols; j++) { // does the first line of the maze so it is only |---|
            firstln += "---|";
        }
        System.out.println(firstln);// prints it out

        for (int i = 0; i < rows; i++) {
            String secondln = ""; // will make the second and third lines clear every loop
            String thirdln = "|";
            for (int j = 0; j < cols; j++) {
                if (startRow == i && 0 == j) {
                    secondln += " ";// if it is the starting row and the first column it will not add a wall
                }
                if (j == 0 && i != startRow) {
                    secondln += "|"; // adds a wall to the left size of the maze
                }
                if (maze[i][j].getVisited() == true) {
                    secondln += " * "; // if the cell has been visited then it will print out a *
                }
                if (maze[i][j].getVisited() == false) {
                    secondln += "   "; //else
                }
                if( i == endRow && j == cols - 1){
                    secondln += " ";// if it is the endRow and the last column it will not add a wall
                }else {
                    if (maze[i][j].getRight() == true) {
                        secondln += "|"; // if there is a wall on the right it will add a |

                    }
                    if (maze[i][j].getRight() == false) {
                        secondln += " ";
                    }
                }
                if (maze[i][j].getBottom() == false) {
                    thirdln += "   |"; // if the cell has a bottom wall it will add it
                }
                if (maze[i][j].getBottom() == true) {
                    thirdln += "---|";
                }

            }

            System.out.println(secondln); // print out the maze for every row
            System.out.println(thirdln);
        }

    }

    /* TODO: Solve the maze using the algorithm found in the writeup. */
    public void solveMaze() {
        Q1Gen<int[]> queue = new Q1Gen();
        queue.add(new int[]{startRow, 0}); // starts the queue and add the first coordinate
        this.maze[startRow][0].setVisited(true); //sets it to be visited
        while (queue.length() != 0) {
            int[] coor = queue.remove(); // gets the coordinates from the queue
            int row = coor[0];
            int col = coor[1];
            if (row == endRow && col == cols - 1) { // if it reaches the ending of the maze it will stop the loop
                break;
            }
            if (col + 1 < cols ) { // checks the bounds
                if (this.maze[row][col + 1].getVisited() != true && this.maze[row][col].getRight() == false) { // if right is visited and there is no wall:
                    queue.add(new int[]{row, col +1}); // adds to right to the queue
                    this.maze[row][col+1].setVisited(true); // sets it as visited
                }
            }
            if (0 <= col - 1 ) {// checks the bounds
                if (this.maze[row][col - 1].getVisited() != true && this.maze[row][col-1].getRight() == false) {// if right is visited and there is no wall:
                    queue.add(new int[]{row, col - 1});// adds to right to the queue
                    this.maze[row][col-1].setVisited(true);// sets it as visited
                }
            }
            if (row + 1 < rows ) {// checks the bounds
                if (this.maze[row + 1][col].getVisited() != true && this.maze[row][col].getBottom() == false) {// if right is visited and there is no wall:
                    queue.add(new int[]{row + 1, col});// adds to right to the queue
                    this.maze[row+1][col].setVisited(true);// sets it as visited
                }
            }
            if (0 <= row - 1 ) {// checks the bounds
                if (this.maze[row - 1][col].getVisited() != true && this.maze[row-1][col].getBottom() == false) {// if right is visited and there is no wall:
                    queue.add(new int[]{row - 1, col});// adds to right to the queue
                    this.maze[row-1][col].setVisited(true);// sets it as visited
                }
            }
        }
    }

        public static void main (String[]args){
            Scanner scan = new Scanner(System.in);
            int rows = 0;
            int cols = 0;
            System.out.println("Enter # of rows(limit is 5):");
            rows = Integer.parseInt(scan.nextLine()); // asks input for the rows
            while(rows >= 6){// if the input is greater than 5 it will keep asking
                System.out.println("Enter # of rows(limit is 5):");
                rows = Integer.parseInt(scan.nextLine());
            }
            System.out.println("Enter # of columns(limit is 20):");
            cols = Integer.parseInt(scan.nextLine()); // asks input for the cols
            while(cols >= 21){// if the input is greater than 5 it will keep asking
                System.out.println("Enter # of columns(limit is 20):");
                cols = Integer.parseInt(scan.nextLine());
            }
            Random rand = new Random();
            int randomStart = rand.nextInt(5);// gets a random starting row and ending row
            int randomEnd = rand.nextInt(5);
            MyMaze test = makeMaze(rows, cols, randomStart, randomEnd); // makes the maze
            test.solveMaze(); // solves maze
            test.printMaze(true); // prints maze
        }
    }
