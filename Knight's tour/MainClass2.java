/* grafika 8x8
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

public class MainClass2 extends Applet
{
   // Thread delay measured in milliseconds.

   public final static int DELAY = 500;

   // Thread that runs the knight's tour.

   private Thread thd;

   // Initialize that applet.

   public void init ()
   {
      // Create a Label object that identifies the applet's title.

      Label lblTitle = new Label ("Knight's Tour", Label.CENTER);
      lblTitle.setFont (new Font ("Arial", Font.BOLD, 18));

      // Add the Label object to the applet's Panel container.

      add (lblTitle);

      // Create a ChessBoard object that represents a component capable of
      // displaying a chessboard and moving a knight to any square, leaving a
      // trail from the previous square.

      final ChessBoard cb = new ChessBoard (this);

      // Add the ChessBoard object to the applet's Panel container.

      add (cb);

      // Create a Panel object to hold a Label object, a Choice object, and a
      // Button object.

      Panel pnl = new Panel ();

      // Create a Label object that describes the Choice object. Add that
      // object to the Panel.

      pnl.add (new Label ("Choose starting position:"));

      // Create a Choice object that represents a component capable of
      // presenting starting positions for the tour. Add that object to the
      // Panel.

      final Choice c = new Choice ();
      c.add ("Upperleft corner");
      c.add ("Upperright corner");

      // Create the Choice's item listener. The listener resets the knight's
      // starting position based on the choice.

      c.addItemListener (new ItemListener ()
                         {
                             public void itemStateChanged (ItemEvent e)
                             {
                                Choice c = (Choice) e.getSource ();

                                if (c.getSelectedIndex () == 0)
                                    cb.moveKnight (1);
                                else
                                    cb.moveKnight (8);

                                cb.reset ();
                             }
                         });
      pnl.add (c);

      // Add the Panel to the applet's Panel container.

      add (pnl);

      // Create a Button object that represents a component for taking the
      // knight's tour.

      final Button btn = new Button ("Take the Tour");

      // Create the Button's action listener. The listener identifies the
      // tour in terms of board positions. Moving the knight from position to
      // position constitutes the knight's tour.

      ActionListener al;

      al = new ActionListener ()
           {
               public void actionPerformed (ActionEvent e)
               {
                  Runnable r;
                  r = new Runnable ()
                      {
                          int [] boardPositions1 =
                          {
                              1, 18, 33, 50, 60, 54, 64, 47,
                             32, 15,  5, 11, 17, 34, 49, 59,
                             53, 63, 48, 31, 16,  6, 12,  2,
                             19, 25, 42, 57, 51, 61, 55, 40,
                             23,  8, 14,  4, 10, 27, 44, 38,
                             21, 36, 46, 29, 35, 41, 58, 52,
                             62, 56, 39, 24,  7, 13,  3,  9,
                             26, 43, 37, 22, 28, 45, 30, 20
                          };

                          int [] boardPositions2 =
                          {
                              8, 23, 40, 55, 61, 51, 57, 42,
                             25, 10,  4, 14, 24, 39, 56, 62,
                             52, 58, 41, 26,  9,  3, 13,  7,
                             22, 32, 47, 64, 54, 60, 50, 33,
                             18,  1, 11,  5, 15, 30, 45, 35,
                             20, 37, 43, 28, 38, 48, 63, 53,
                             59, 49, 34, 17,  2, 12,  6, 16,
                             31, 46, 36, 19, 29, 44, 27, 21
                          };

                          public void run ()
                          {
                             cb.reset ();

                             // thd != null is our check for stopping the
                             // applet should the user move away from applet's
                             // Webpage.

                             for (int i = 0; i < boardPositions1.length &&
                                  thd != null; i++)
                             {
                                  if (c.getSelectedIndex () == 0)
                                      cb.moveKnight (boardPositions1 [i]);
                                  else
                                      cb.moveKnight (boardPositions2 [i]);

                                  try
                                  {
                                      Thread.sleep (DELAY);
                                  }
                                  catch (InterruptedException e2)
                                  {
                                  }
                             }

                             c.setEnabled (true);
                             btn.setEnabled (true);
                          }
                      };

                  c.setEnabled (false);
                  btn.setEnabled (false);

                  thd = new Thread (r);
                  thd.start ();
               }
           };
      btn.addActionListener (al);

      // Add the Button object to the applet's Panel container.

      add (btn);
   }

   // Stop the applet.

   public void stop ()
   {
      // Must stop the "knight's tour" thread if user moves away from Webpage.

      thd = null;
   }
}

final class ChessBoard extends Canvas
{
   // Color of non-white squares.

   private final static Color SQUARECOLOR = new Color (195, 214, 242);

   // Dimension of chessboard square.

   private final static int SQUAREDIM = 40;

   // Dimension of chessboard -- includes black outline.

   private final static int BOARDDIM = 8 * SQUAREDIM + 2;

   // Left coordinate of chessboard's upper-left corner.

   private int boardx;

   // Top coordinate of chessboard's upper-left corner.

   private int boardy;

   // Board width.

   private int width;

   // Board height.

   private int height;

   // Image buffer.

   private Image imBuffer;

   // Graphics context associated with image buffer.

   private Graphics imG;

   // Knight's image.

   private Image imKnight;

   // Knight image width.

   private int knightWidth;

   // Knight image height.

   private int knightHeight;

   // Coordinates of knight's trail.

   private ArrayList trail;

   // Left coordinate of knight rectangle origin (upper-left corner).

   private int ox;

   // Top coordinate of knight rectangle origin (upper-left corner).

   private int oy;

   // Applet that created ChessBoard object -- we call its getImage() and
   // getDocumentBase() methods; and we use it as an image observer for
   // drawImage().

   Applet a;

   // Construct the ChessBoard.

   ChessBoard (Applet a)
   {
      // Determine the component's size.

      width = BOARDDIM+1;
      height = BOARDDIM+1;

      // Initialize chessboard's origin, so that board is centered.

      boardx = (width - BOARDDIM) / 2 + 1;
      boardy = (height - BOARDDIM) / 2 + 1;

      // Use a media tracker to ensure that knight's image completely loads
      // before we get its height and width.

      MediaTracker mt = new MediaTracker (this);

      // Load knight's image.

      imKnight = a.getImage (a.getDocumentBase (), "knight.gif");
      mt.addImage (imKnight, 0);

      try
      {
         mt.waitForID (0);
      }
      catch (InterruptedException e) {}

      // Obtain knight's width and height, which helps to center the knight
      // image within a square.

      knightWidth = imKnight.getWidth (a);
      knightHeight = imKnight.getHeight (a);

      // Initialize knight image's starting origin so that knight is centered
      // in the square located in the top row and left column.

      ox = boardx + (SQUAREDIM - knightWidth) / 2 + 1;
      oy = boardy + (SQUAREDIM - knightHeight) / 2 + 1;

      // Create a datastructure to hold knight's trail as knight moves
      // around the board.

      trail = new ArrayList ();

      // Save applet reference for later use in call to drawImage().

      this.a = a;
   }

   // This method is called when the ChessBoard component's peer is created.
   // The code in this method cannot be called in the ChessBoard constructor
   // because the createImage() method returns null at that point. It doesn't
   // return a meaningful value until the ChessBoard component has been added
   // to its container. The addNotify() method is not called until the first
   // time ChessBoard is added to a container.

   public void addNotify ()
   {
      // Given this object's Canvas "layer" a chance to create a Canvas peer.

      super.addNotify ();

      // Create image buffer.

      imBuffer = createImage (width, height);

      // Retrieve graphics context associated with image buffer.

      imG = imBuffer.getGraphics ();
   }

   // This method is called by the applet's layout manager when positioning
   // its components. If at all possible, the component is displayed at its
   // preferred size.

   public Dimension getPreferredSize ()
   {
      return new Dimension (width, height);
   }

   // Move the knight to the specified board position. Throw an exception if
   // the position is less than 1 or greater than 64.

   public void moveKnight (int boardPosition)
   {
      if (boardPosition < 1 || boardPosition > 64)
          throw new IllegalArgumentException ("invalid board position: " +
                                              boardPosition);

      int rebasedBoardPosition = boardPosition-1;

      int col = rebasedBoardPosition % 8;
      int row = rebasedBoardPosition / 8;

      ox = boardx + col * SQUAREDIM + (SQUAREDIM - knightWidth) / 2 + 1;
      oy = boardy + row * SQUAREDIM + (SQUAREDIM - knightHeight) / 2 + 1;

      trail.add (new Point (ox + knightWidth / 2, oy + knightHeight / 2));

      repaint ();
   }

   // Paint the component -- first the chessboard and then the knight.

   public void paint (Graphics g)
   {
      // Paint the chessboard.

      paintChessBoard (imG, boardx, boardy);

      // Paint the knight.

      paintKnight (imG, ox, oy);

      // Paint the knight's trail.

      paintTrail (imG);

      // Draw contents of image buffer.

      g.drawImage (imBuffer, 0, 0, this);
   }

   // Paint the chessboard -- (x, y) is the upper-left corner.

   void paintChessBoard (Graphics g, int x, int y)
   {
      // Paint chessboard outline.

      g.setColor (Color.black);
      g.drawRect (x, y, 8 * SQUAREDIM + 1, 8 * SQUAREDIM + 1);

      // Paint checkerboard.

      for (int row = 0; row < 8; row++)
      {
           g.setColor (((row & 1) != 0) ? SQUARECOLOR : Color.white);

           for (int col = 0; col < 8; col++)
           {
                g.fillRect (x + 1 + col * SQUAREDIM, y + 1 + row * SQUAREDIM,
                            SQUAREDIM, SQUAREDIM);

                g.setColor ((g.getColor () == SQUARECOLOR) ? Color.white :
                            SQUARECOLOR);
           }
      }
   }

   // Paint the knight -- (x, y) is the upper-left corner.

   void paintKnight (Graphics g, int x, int y)
   {
      g.drawImage (imKnight, x, y, a);
   }

   // Paint the knight's trail.

   void paintTrail (Graphics g)
   {
      g.setColor (Color.black);

      int len = trail.size ();

      if (len == 0)
          return;

      Point pt = (Point) trail.get (0);
      int ox = pt.x;
      int oy = pt.y;

      for (int i = 1; i < len; i++)
      {
           pt = (Point) trail.get (i);
           g.drawLine (ox, oy, pt.x, pt.y);
           ox = pt.x;
           oy = pt.y;
      }
   }

   // Reset the chessboard by clearing the ArrayList.

   public void reset ()
   {
      trail.clear ();
   }

   // The AWT invokes the update() method in response to the repaint() method
   // call that is made as a knight is moved. The default implementation of 
   // this method, which is inherited from the Container class, clears the
   // applet's drawing area to the background color prior to calling paint().
   // This clearing followed by drawing causes flicker. KT overrides
   // update() to prevent the background from being cleared, which eliminates
   // the flicker.

   public void update (Graphics g)
   {
      paint (g);
   }
}

*/

//public class MainClass2 {

//}



/*
//kali lisi alla oxi swsto monopati 
import java.util.Scanner;
    import java.util.ArrayList;

    public class MainClass2
    {
    	//public boolean flag = true;
    	//while(flag){
    	private static int count = 1;
        private static int turns = 0;
        private static ArrayList<String> moves = new ArrayList<String>();

        private static int squares;
        private static int table[][];

        private static boolean takeTour(int x, int y) {
            // Checks if all squares is used. If true, algorithm will stop
            if (checkIfFinished())
                return true;
            table[x][y] = ++turns;
            // 2 Left, 1 Down
            if (x > 1 && y < squares -1 && table[x-2][y+1] == 0)
            {
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Left, 1 Down");
                if (takeTour(x-2, y+1))
                {
                    return true;
                }
            }
            // 2 Left, 1 Up
            if (x > 1 && y > 0 && table[x-2][y-1] == 0)
            {
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Left, 1 Up");
                if (takeTour(x-2, y-1))
                {
                    return true;
                }
            }
            // 2 Up, 1 Left
            if (y > 1 && x > 0 && table[x-1][y-2] == 0)
            {
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Up, 1 Left");
                if (takeTour(x-1, y-2))
                {
                    return true;
                }
            }
            // 2 Up, 1 Right
            if (y > 1 && x < squares -1 && table[x+1][y-2] == 0)
            {
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Up, 1 Right");
                if (takeTour(x+1, y-2))
                {
                    return true;
                }
            }
            // 2 Right, 1 Up
            if (x < squares -2 && y > 0 && table[x+2][y-1] == 0)
            {
                //System.out.println("x:" + x + ", y:" + y + " (2r,1u)moving to x:" + (x+2) + ", y:" + (y-1));
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Right, 1 Up");
                if (takeTour(x+2, y-1))
                {
                    return true;
                }
            }
            // 2 Right, 1 Down
            if (x < squares -2 && y < squares -1 && table[x+2][y+1] == 0)
            {
                //System.out.println("x:" + x + ", y:" + y + " (2r,1d)moving to x:" + (x+2) + ", y:" + (y+1));
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Right, 1 Down");
                if (takeTour(x+2, y+1))
                {
                    return true;
                }
            }
            // 2 Down, 1 Right
            if (y < squares -2 && x < squares-1 && table[x+1][y+2] == 0)
            {
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Down, 1 Right");
                if (takeTour(x+1, y+2))
                {
                    return true;
                }
            }
            // 2 Down, 1 Left
            if (y < squares -2 && x > 0 && table[x-1][y+2] == 0)
            {
                moves.add(count++ +") " +"X: " + x + ", Y: " + y + ". Moving 2 Down, 1 Left");
                if (takeTour(x-1, y+2))
                {
                    return true;
                }
            }
            return false;
        }

        // Checks if all squares is used
        private static boolean checkIfFinished()
        {
            for (int i = 0; i < squares; i++)
            {
                for (int j = 0; j < squares; j++)
                {
                    if (table[i][j] == 0)
                        return false;
                }
            }
            return true;
        }

        // Made this to save code from 3 duplicates
        private static void invalidNumber()
        {
            System.out.println("Invalid number! Killing proccess");
            System.exit(0);
        }

        public static void main(String[] args) {

        	
            Scanner sc = new Scanner(System.in);
            System.out.print("Number of squares: ");
            squares = Integer.parseInt(sc.nextLine());
            if (squares < 1 )
                invalidNumber();

            //System.out.println("Note: Start values is from 0 -> n-1"+ "\n0,0 is at top left side");
            System.out.println("0,0 is at top left side and 0,"+ (squares-1) +" is at down left side");
            System.out.print("X start value: ");
            int x = Integer.parseInt(sc.nextLine());
            if (x < 0 || x > squares -1)
                invalidNumber();

            System.out.print("Y start value: ");
            int y = Integer.parseInt(sc.nextLine());
            if (y < 0 || y > squares -1)
                invalidNumber();
            sc.close();

            table = new int[squares][squares];

            boolean tourComplete = takeTour(x, y);

            for (String s : moves)
            {
                System.out.println(s);
            }
            if (!tourComplete)
                System.out.println("Did not find any way to complete Knights Tour!");

            // Print the table with the move-numbers
            for (int i = 0; i < squares; i++)
            {
                for (int j = 0; j < squares; j++)
                {
                    System.out.printf("%4d", table[j][i]);
                }
                System.out.println();
            }
        }
        }
   
  */


/*
import java.util.*;

public class MainClass2 {
    private final static int base = 12;
    private final static int[][] moves = {{1,-2},{2,-1},{2,1},{1,2},{-1,2},
        {-2,1},{-2,-1},{-1,-2}};
    private static int[][] grid;
    private static int total;
 
    public static void main(String[] args) {
        grid = new int[base][base];
        total = (base - 4) * (base - 4);
 
        for (int r = 0; r < base; r++)
            for (int c = 0; c < base; c++)
                if (r < 2 || r > base - 3 || c < 2 || c > base - 3)
                    grid[r][c] = -1;
 
        int row = 2 + (int) (Math.random() * (base - 4));
        int col = 2 + (int) (Math.random() * (base - 4));
 
        grid[row][col] = 1;
 
        if (solve(row, col, 2))
            printResult();
        else System.out.println("no result");
 
    }
 
    private static boolean solve(int r, int c, int count) {
        if (count > total)
            return true;
 
        List<int[]> nbrs = neighbors(r, c);
 
        if (nbrs.isEmpty() && count != total)
            return false;
 
        Collections.sort(nbrs, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[2] - b[2];
            }
        });
 
        for (int[] nb : nbrs) {
            r = nb[0];
            c = nb[1];
            grid[r][c] = count;
            if (!orphanDetected(count, r, c) && solve(r, c, count + 1))
                return true;
            grid[r][c] = 0;
        }
 
        return false;
    }
 
    private static List<int[]> neighbors(int r, int c) {
        List<int[]> nbrs = new ArrayList<>();
 
        for (int[] m : moves) {
            int x = m[0];
            int y = m[1];
            if (grid[r + y][c + x] == 0) {
                int num = countNeighbors(r + y, c + x);
                nbrs.add(new int[]{r + y, c + x, num});
            }
        }
        return nbrs;
    }
 
    private static int countNeighbors(int r, int c) {
        int num = 0;
        for (int[] m : moves)
            if (grid[r + m[1]][c + m[0]] == 0)
                num++;
        return num;
    }
 
    private static boolean orphanDetected(int cnt, int r, int c) {
        if (cnt < total - 1) {
            List<int[]> nbrs = neighbors(r, c);
            for (int[] nb : nbrs)
                if (countNeighbors(nb[0], nb[1]) == 0)
                    return true;
        }
        return false;
    }
 
    private static void printResult() {
        for (int[] row : grid) {
            for (int i : row) {
                if (i == -1) continue;
                System.out.printf("%2d ", i);
            }
            System.out.println();
        }
    }
}
*/

/*
//package com.vipin.algorithms;
 
public class MainClass2
{
    private static final int N =10 ;
    private int soln[][];
 
    public MainClass2()
    {
        soln = new int[N][N];
    }
 
    private boolean isSafe(int x, int y)
    {
        if (x >= 0 && x < N && y >= 0 && y < N && soln[x][y] == -1)
            return true;
        return false;
    }
 
    private void printSolution()
    {
        for (int x = 0; x < N; x++)
        {
            for (int y = 0; y < N; y++)
            {
                System.out.print("  " + soln[x][y]);
            }
            System.out.println();
        }
    }
 
    private boolean solveKTUtil(int x, int y, int movei, int xMove[],int yMove[])
    {
        int k, next_x, next_y;
        if (movei == N * N)
            return true;
 
        for (k = 0; k < N; k++)
        {
            next_x = x + xMove[k];
            next_y = y + yMove[k];
            if (isSafe(next_x, next_y))
            {
                soln[next_x][next_y] = movei;
                if (solveKTUtil(next_x, next_y, movei + 1, xMove, yMove))
                    return true;
                else
                    soln[next_x][next_y] = -1;
            }
        }
        return false;
    }
 
    public boolean solveKnightTour()
    {
        for (int x = 0; x < N; x++)
        {
            for (int y = 0; y < N; y++)
            {
                soln[x][y] = -1;
            }
        }
        int xMove[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int yMove[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
        soln[0][0] = 0;
        if (!solveKTUtil(0, 0, 1, xMove, yMove))
        {
            System.out.println("the solution does not exist");
            return false;
        }
        else
        {
            printSolution();
        }
        return true;
    }
 
    public static void main(String... arg)
    {
        MainClass2 knightTour = new MainClass2();
        System.out.println("the solution is");
        knightTour.solveKnightTour();
    }
}

*/



//package Backtracking;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainClass2 {

	static int check=0;
	static int[][] solution;
	int path = 0;// me 1 allazi olo t path gt?

	public MainClass2(int N) {
		solution = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				solution[i][j] = 0;
			}
		}
	}

	public void solve() {
		if (findPath(0, 0, 0, solution.length)) {
			print();
		} else {
			System.out.println("NO PATH FOUND");
			check=1;
		}
	}

	public boolean findPath(int row, int column, int index, int N) {
		// check if current is not used already
		if (solution[row][column] != 0) {
			return false;
		}
		// mark the current cell is as used
		solution[row][column] = path++;
		// if (index == 50) {
		if (index == N * N - 1) {
			// if we are here means we have solved the problem
			return true;
		}
		// try to solve the rest of the problem recursively

		// go down and right
		if (canMove(row + 2, column + 1, N)
				&& findPath(row + 2, column + 1, index + 1, N)) {
			return true;
		}
		// go right and down
		if (canMove(row + 1, column + 2, N)
				&& findPath(row + 1, column + 2, index + 1, N)) {
			return true;
		}
		// go right and up
		if (canMove(row - 1, column + 2, N)
				&& findPath(row - 1, column + 2, index + 1, N)) {
			return true;
		}
		// go up and right
		if (canMove(row - 2, column + 1, N)
				&& findPath(row - 2, column + 1, index + 1, N)) {
			return true;
		}
		// go up and left
		if (canMove(row - 2, column - 1, N)
				&& findPath(row - 2, column - 1, index + 1, N)) {
			return true;
		}
		// go left and up
		if (canMove(row - 1, column - 2, N)
				&& findPath(row - 1, column - 2, index + 1, N)) {
			return true;
		}
		// go left and down
		if (canMove(row + 1, column - 2, N)
				&& findPath(row + 1, column - 2, index + 1, N)) {
			return true;
		}
		// go down and left
		if (canMove(row + 2, column - 1, N)
				&& findPath(row + 2, column - 1, index + 1, N)) {
			return true;
		}
		// if we are here means nothing has worked , backtrack
		solution[row][column] = 0;
		path--;
		return false;

	}

	public boolean canMove(int row, int col, int N) {
		if (row >= 0 && col >= 0 && row < N && col < N) {
			return true;
		}
		return false;
	}

	public void print() {
		DecimalFormat twodigits = new DecimalFormat("00");
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution.length; j++) {
				System.out.print("   " + twodigits.format(solution[i][j]));
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		Scanner input = new Scanner( System.in );
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		//kai monopati me System.out
		//diastaseis pinaka
		//na grafw arxiki thesi
		System.out.print("Number of squares: ");
		//System.out.println();
		int N = input.nextInt();
		int firstX = -1;
		int firstJ = -1;
		MainClass2 i = new MainClass2(N);
		i.solve();
		for (int x = 0; x < N; x++) {
			for (int j = 0; j < N; j++) {
				if(solution[x][j]== 00 & check==0){
					firstX=x;
					firstJ=j;
					System.out.println("TIP : 0,0 is at top left side ");
					System.out.println();
					System.out.println("Initial position for this path is X:" +firstJ +" and Y:"+firstX);
				}
			}
		}
		//System.out.println(solution[0][4]);
		
		// edw tha ftiaxteei to monopati
		
		int flag=(N*N)-1;
		//System.out.println(flag);
		for(int w=0;w<flag;w++){
			for (int x = 0; x < N; x++) {
				for (int j = 0; j < N; j++) {
					
				}
			}
		}
	}
		
}



