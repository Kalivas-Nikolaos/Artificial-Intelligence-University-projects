import java.util.Scanner;

class count_test {

	public static Scanner input = new Scanner(System.in);
	public static int board_n;
	public static int ini_pos_x,ini_pos_y,steps,i,j,try_count,track_count;
	public static int[][] board = new int[board_n][board_n];
	public static int[] cx = new int[9], cy = new int[9];
	public static boolean yes;
	

	private static void hardcode_init() {
		board_n = 5;					//HARCODE SIZE 
		board_n++;
		ini_pos_x = ini_pos_y = 1;
		board = new int[board_n][board_n];
		board_n--;
		board[ini_pos_x][ini_pos_y] = 1;
	}

	private static void get_board() {
		System.out.print("Enter the size of the chess board: ");
		board_n = Integer.parseInt(input.next());
		board_n++;
		board = new int[board_n][board_n];
		board_n--;
	}

	private static void get_init_pos() {

		do {
			System.out.print("\nEnter the initial position (X,Y): ");
		 	ini_pos_x = Integer.parseInt(input.next());
			ini_pos_y = Integer.parseInt(input.next());		
		
			if ((ini_pos_x <= board_n) && (ini_pos_y <= board_n)) {
				board[ini_pos_x][ini_pos_y] = 1;
				return;
			}

			System.out.println("One of the position indexes is beyond the bounds the board. Try again.");
		} while (!((ini_pos_x <= board_n) && (ini_pos_y <= board_n)));
	}

	private static void reset_board() {
		for (int i=1;i<board_n+1;i++) 
			for (int j=1;j<board_n+1;j++) 
				board[i][j]=0;
	}

	public static void initialize() {
		get_board();
		reset_board();
		get_init_pos();
		
		//hardcode_init();	//DELETE IN FINAL VERSION !!!

		yes = false;
		steps = 1;
		try_count = 0;
		track_count = -1;

		System.out.println("PART 1. Data");
		System.out.println("1) Board "+board_n+"x"+board_n);	
		System.out.println("2) Initial position X="+ini_pos_x+", Y="+ini_pos_y+". L="+steps+".\n");	

		cx[1] = 2;		cy[1] = 1;
		cx[2] = 1;		cy[2] = 2;
		cx[3] = -1;		cy[3] = 2;
		cx[4] = -2;		cy[4] = 1;
		cx[5] = -2;		cy[5] = -1;
		cx[6] = -1;		cy[6] = -2;
		cx[7] = 1;		cy[7] = -2;
		cx[8] = 2;		cy[8] = -1;			
	}

	public static void try_me(int steps,int x,int y) {
		int k,u,v;
		k = 0;
		track_count++;
	
		do {
			yes = false;
			try_count++;
			k++;
			u = x + cx[k]; 
			v = y + cy[k];	
			if (((u>=1) && (u<=board_n)) && ((v>=1) && (v<=board_n))) {
				
				if (board[u][v] == 0) {
					board[u][v] = steps;
					
					if (steps == (board_n*board_n)) {							// MANUAL KILL PROCESS - BETA 
						yes= true;
						results();	
						System.exit(0);
					}

					if (steps < (board_n*board_n)) {
						try_me(steps+1,u,v);
						if (!(yes)) {
							board[u][v] = 0;
							track_count--;
						}
					}
					else {  yes = true; } 
				}
			}
		} while (!((yes) || (k >= 8)));
	}

	public static void print_board(int arr[][]) {
		System.out.println("\n  ^");
			for (i=board_n;i>=1;i--) {
				System.out.print(i+" | ");
				for (j=1;j<=board_n;j++) {
					System.out.format("%3d ",arr[i][j]); 
				}
				System.out.print("\n");						
			}
			System.out.println("  ----------------------->");
			System.out.print("    ");
			for(i=1;i<=board_n;i++) System.out.format("%3d ",i);
			System.out.println("\n");
	}


	public static void results() {
		System.out.println("\nPART 2. Results");
		System.out.println("1) Path is found.");
		System.out.println("2) Path graphically");
		print_board(board);
		System.out.println("\n3) Number of steps: "+try_count);
	}

	public static void main(String[] args) {
		initialize();
		try_me(2,ini_pos_x,ini_pos_y);	
		if (yes) results();
		else System.out.println("Path does not exist.");
	}
}