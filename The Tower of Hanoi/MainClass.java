import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class MainClass {
	public static int count=1;
	//public static String plus=""; 
	public static int[] A;
	public static int[] B;
	public static int[] C;
	public static int arrayLength;
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner( System.in );
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		boolean flag=true;
		int n =0;
		String anser=" ";
		//String helpA=" ";
		//String helpB=" ";
		//String helpC=" ";
		//String anserA=" ";
		//String anserB=" ";
		//String anserC=" ";
		while(flag){
			System.out.println("how much disks you want from 1 until 10");
			 n = input.nextInt();
			 A = new int[n];
			 for (int i=0;i<n;i++){
				 A[i]=i+1;
				 //System.out.println(A[i]);
				 //B[i]=null;
				//C[i]=null;
			 }
			 
			 B = new int[n];
			 C = new int[n];
			 arrayLength = A.length;//all have same lenght
			if(n>0 & n<11){
				ht('A', 'B', 'C',n);
			}else{
				System.out.println("disks are out of limit");
			}
			System.out.println("do you want to continue? (Y/N)");
			anser = br.readLine();
			if(anser.equals("Y")||anser.equals("y")){
				flag=true;
				count=1;
			}else{
				flag=false;
				System.out.println("THANKS");
				//for(int i=0;i<n;i++){
					//System.out.println(A[i]);
					//System.out.println(B[i]);
					//System.out.println(C[i]);
				//}
			}
		}
	}
	public static void ht(char from, char inter, char to,int N) {
	     
		  if (N == 1) {
			  if(from=='A'&&to=='B'){
		        for(int i=0;i<arrayLength;i++){
		        	if(A[i]==N){
	        			 B[N-1]=N;
	        			 A[i]=0;
	        		 }
		        }	 
		      }else if(from=='A'&&to=='C'){
		    	  for(int i=0;i<arrayLength;i++){
		    		  if(A[i]==N){
		        			 C[N-1]=N;
		        			 A[i]=0;
		        		 }
			        }	 
		      }else if(from=='B'&&to=='A'){
		    	  for(int i=0;i<arrayLength;i++){
		    		  if(B[i]==N){
		        			 A[N-1]=N;
		        			 B[i]=0;
		        		 }
			        }	 
		      }else if(from=='B'&&to=='C'){
		    	  for(int i=0;i<arrayLength;i++){
		    		  if(B[i]==N){
		        			 C[N-1]=N;
		        			 B[i]=0;
		        		 }
			        }	 
		      }else if(from=='C'&&to=='A'){
		    	  for(int i=0;i<arrayLength;i++){
		    		  if(C[i]==N){
		        			 A[N-1]=N;
		        			 C[i]=0;
		        		 }
			        }	 
		      }else if(from=='C'&&to=='B'){
		    	  for(int i=0;i<arrayLength;i++){
		    		  if(C[i]==N){
		        			 B[N-1]=N;
		        			 C[i]=0;
		        		 }
			        }	 
		      }
			  System.out.print(count++ +")"+"Disk 1 from " + from + " to " + to + " A(");
			  //count++;
			  for (int i=arrayLength; i >=1;i--){
				  System.out.print(A[i] + ",");
				  
			  }
			  System.out.print(") B(");
			  for (int i=arrayLength; i >=1;i--){
				  System.out.print(B[i] + ",");
				  
			  }
			  System.out.print(") C(");
			  for (int i=arrayLength; i >=1;i--){
				  System.out.print(C[i] + ",");
				  
			  }
			  System.out.println(")");
	         //System.out.println(count++ +")"+"Disk 1 from " + from + " to " + to);
	      } else {
	    	  //a() b() c()
	         ht(from, to, inter,N-1);
	         if(from=='A'&&to=='B'){
	        	 for(int i=0;i<arrayLength;i++){
	        		 if(A[i]==N){
	        			 B[N-1]=N;
	        			 A[i]=0;
	        		 }
			        } 
	         }else if(from=='A'&&to=='C'){
	        	 for(int i=0;i<arrayLength;i++){
	        		 if(A[i]==N){
	        			 C[N-1]=N;
	        			 A[i]=0;
	        		 }
			        }
	         }else if(from=='B'&&to=='A'){
	        	 for(int i=0;i<arrayLength;i++){
	        		 if(B[i]==N){
	        			 A[N-1]=N;
	        			 B[i]=0;
	        		 }
			        }
	         }else if(from=='B'&&to=='C'){
	        	 for(int i=0;i<arrayLength;i++){
	        		 if(B[i]==N){
	        			 C[N-1]=N;
	        			 B[i]=0;
	        		 }
			        }
	         }else if(from=='C'&&to=='A'){
	        	 for(int i=0;i<arrayLength;i++){
	        		 if(C[i]==N){
	        			 A[N-1]=N;
	        			 C[i]=0;
	        		 }
			        }
	         }else if(from=='C'&&to=='B'){
	        	 for(int i=0;i<arrayLength;i++){
	        		 if(C[i]==N){
	        			 B[N-1]=N;
	        			 C[i]=0;
	        		 }
			        }
	         }
	         
	         System.out.print(count++ +")"+"Disk " + N + " from " + from + " to " + to +" A(");
	         //count++; 
	         for (int i=arrayLength; i >=1;i--){
				  System.out.print(A[i] + ",");
				  
			  }
			  System.out.print(") B(");
			 for (int i=arrayLength; i >=1;i--){
				  System.out.print(B[i] + ",");
				  
			  }
			  System.out.print(") C(");
			  for (int i=arrayLength; i >=1;i--){
				  System.out.print(C[i] + ",");
				  
			  }
			  System.out.println(")");
	         
	         //System.out.println(count++ +")"+"Disk " + N + " from " + from + " to " + to);
	        
	         ht(inter, from, to,N-1);
	      }
	   }
	
}
