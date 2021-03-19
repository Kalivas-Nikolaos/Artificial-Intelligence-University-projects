import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class tower-of-hanoi {
	
	
	
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner( System.in );
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean flag=true;
		int n =0;
		String anser=" ";
		while(flag){
			System.out.println("how much disks you want from 1 until 10");
			 n = input.nextInt();
			if(n>0 & n<11){
				ht('A', 'B', 'C',n);
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
	public static void ht(char from, char inter, char to,int N) {
	      if (N == 1) {
	         System.out.println("Disk 1 from " + from + " to " + to);
	      } else {
	         ht(from, to, inter,N-1);
	         System.out.println("Disk " + N + " from " + from + " to " + to);
	         ht(inter, from, to,N-1);
	      }
	   }

}
	

