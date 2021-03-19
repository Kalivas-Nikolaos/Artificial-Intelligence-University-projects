import java.util.*;

import java.lang.*;
import java.io.*;

public class alogo2 {

    private static final int[] POSSIBLE_X_MOVE_OFFSETS = {-1, -2, -2, -1, 1, 2, 2, 1};
    private static final int[] POSSIBLE_Y_MOVE_OFFSETS = {2, 1, -1, -2, -2, -1, 1, 2};
    private static final int DEFAULT_UNVISITED_VALUE = -1;
    private static int STEP =1 ;

    public static void solve(int boardSize) {
        int[][] board = new int[boardSize][boardSize];
        int help=0;
        fillBoardWithUnVisited(board);

        board[boardSize-1][0] = 0;
        boolean isSolved = solveByBacktracking(board, boardSize-1, 0, 1, "-");

        if (isSolved) {
        	System.out.println(" ");
        	System.out.println("PART 3. Results");
            System.out.println("1)Path is found");
            System.out.println("2) Path graphically");

            for (int x = 0; x < boardSize; x++) {
                for (int y = 0; y < boardSize; y++) {
                	board[x][y]=board[x][y]+1;
                	
                	//help=board[x][y];
                	//System.out.print(" " + (help+1));
                   // System.out.print(" " + board[x][y]);//board[x][y]+1
                }
                //System.out.println();
            }
            
            
            for (int x = 0; x < boardSize; x++) {
                for (int y = 0; y < boardSize; y++) {
                	//help=board[x][y];
                	//System.out.print(" " + (help+1));
                    System.out.print(" " + board[x][y]);//board[x][y]+1
                }
                System.out.println();
            }
        } else {
            System.out.println("Path not found!");
        }
    }

    public static void fillBoardWithUnVisited(int[][] board) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                board[x][y] = DEFAULT_UNVISITED_VALUE;
            }
        }
    }

    private static boolean solveByBacktracking(int[][] board, int xPosition, int yPosition, int move, String step) {
        if (move == board.length * board.length) {
            return true;
        }

        for (int offset = 0; offset < POSSIBLE_X_MOVE_OFFSETS.length; offset++) {

            int nextXMove = xPosition + POSSIBLE_X_MOVE_OFFSETS[offset];
            int nextYMove = yPosition + POSSIBLE_Y_MOVE_OFFSETS[offset];

            if (isNextValidMove(board, nextXMove, nextYMove)) {

                board[nextXMove][nextYMove] = move;
                if (solveByBacktracking(board, nextXMove, nextYMove, move + 1, step+"-")) {
                	if(STEP==1){
                		System.out.println("PART 1. Data");
                		System.out.println("1) Board "+board.length+"X"+board.length);
                		System.out.println("Initial position X=1, Y=1. L=1");
                		System.out.println("PART 2. Trace");
                	}
                	System.out.println(STEP++ + ")" + step + " R. U = " + nextXMove + " V = " + nextYMove + " L= " + move);
                    return true;
                }
                else
                	System.out.println(STEP++ + ")" + step + " R. U = " + nextXMove + " V = " + nextYMove + " L= " + move + " Out.");

                board[nextXMove][nextYMove] = DEFAULT_UNVISITED_VALUE;
            }
            else
            	System.out.println(STEP++ + ")" + step + " R. U = " + nextXMove + " V = " + nextYMove + " L= " + move + " Thread.!");
        }

        return false;
    }

    private static boolean isNextValidMove(int[][] board, int xPosition, int yPosition) {

        return isInsideBoard(board, xPosition, yPosition) &&
                isNotVisited(board, xPosition, yPosition);
    }

    private static boolean isInsideBoard(int[][] board, int xPosition, int yPosition) {
        return xPosition >= 0 && xPosition < board.length &&
                yPosition >= 0 && yPosition < board[xPosition].length;
    }

    public static boolean isNotVisited(int[][] board, int xPosition, int yPosition) {
        return board[xPosition][yPosition] == DEFAULT_UNVISITED_VALUE;
    }
    
    public static void main(String[] args) {
    	
    	Scanner sc = new Scanner(System.in);
    	System.out.println("board dimension?");
    	int N = sc.nextInt();
		Scanner tc = new Scanner(System.in);
		System.out.println("Initial position X=");
    	int X = tc.nextInt();
		Scanner bc = new Scanner(System.in);
		System.out.println("Y=");
    	int Y = bc.nextInt();
		Scanner vc = new Scanner(System.in);
		System.out.println("L=");
    	int L = vc.nextInt();
		
    	if(N>4 && N<10)// dioti den vriski path
    		solve(N);
    	else
    		System.out.println("faulty input");
    }
}
