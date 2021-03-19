import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class towers {
	
	
	
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner( System.in );
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean flag=true;
		int n =0;
		
		
	
		String anser=" ";
		while(flag){
			System.out.println("how much disks you want from 1 until 10");
			 n = input.nextInt(); //n - the number of discs in the puzzle
			if(n>0 & n<11){
			int[] A = new int[n];
			int[] B = new int[n];
			int[] C = new int[n];
				for(int i=0; i<n; i++){
				A[i]=i+1;
					System.out.println("A"+ A[i] + "B" + B[i] + "C" + C[i]);
			}
					

				ht('A', 'B', 'C',n); //A B,C--->the names of the three poles which will be used for printing the solution 
				
			}else{
				System.out.println("disks are out of limit");
			}
			System.out.println("do you want to continue? (Y/N)");
			anser = br.readLine();
			if(anser.equals("Y")||anser.equals("y")){
				flag=true;
			}else{
				flag=false;
				System.out.println("THANKS");
			}
		}
	}
	
	//ht(A,B,C,n);
	
	public static void ht(char from, char inter, char to,int N) {
	      if (N == 1) {                                               //We first check if the number of poles, n is equal to one. If so, the base case
	         System.out.println("Move disk 1 from " + from + " to " + to + "." + from + "(" + N + "," + (N-1) + ")" );//solution will be used which consists of moving a disc from the start peg to the end peg.
	      } else {
	         ht(from, to, inter,N-1);                                         //When we need to move n-1 discs from the start pole to the auxiliary pole,
	         System.out.println("Move disk " + N + " from " + from + " to " + to); // the auxiliary pole becomes the end pole and the end pole becomes the auxiliary pole.
	         ht(inter, from, to,N-1);
	      }
	   }

}
