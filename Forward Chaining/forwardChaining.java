import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.String;

class forwardChaining {

	static ArrayList<ArrayList<String>> rules = new ArrayList<ArrayList<String>>(); 
	static List<String> facts = new ArrayList<String>();
	static int iniFactsN;
	static String goal;
	static List<Integer> path = new ArrayList<>();

	
	static void initialize(){
		//loadFile("input.txt");
		loadFile(getCustomPath());
		deleteFlags();
		printData();
	}

	static String getCustomPath(){
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter path to your file: ");
		return in.nextLine();
	}

	static void loadFile(String path){
		try {
			File f = new File(path);
			BufferedReader rd = new BufferedReader(new FileReader(f));
			String line = "";
			for (int i=0;i<3;i++) line = rd.readLine();
			while (!((line = rd.readLine()).isEmpty())) {
				String[] temp = line.split(" ");
				rules.add(new ArrayList<String>());
				rules.get(rules.size()-1).addAll(Arrays.asList(temp));
			}
			for (int i=0;i<2;i++) line = rd.readLine();
			String[] temp = line.split(" ");
			for (int i=0;i<temp.length;i++)
				facts.add(temp[i]);
			iniFactsN = facts.size();
			for (int i=0;i<3;i++) line = rd.readLine();
				goal = line;
			rd.close();
		}
		catch (IOException e) {
			System.err.println(e);
		}
	}

	static void printData(){
		System.out.println("PART 1. Data\n");
		System.out.println("  1) Rules");
		for (int i=1;i<=rules.size();i++) {
			System.out.format("     R%d: ",i);
			for (int j=1;j<rules.get(i-1).size()-3;j++)	
					System.out.format("%s, ",rules.get(i-1).get(j));
			System.out.format("%s -> %s\n",rules.get(i-1).get(rules.get(i-1).size()-3),rules.get(i-1).get(0));
		}
		System.out.println("\n  2) Facts");
			System.out.format("     %s",facts.toString().replace("[","").replace("]",""));
		System.out.println("\n\n  3) Goal");
			System.out.format("     %s\n",goal);
	}

	static void deleteFlags(){
		for (int i=0;i<rules.size();i++){
			rules.get(i).add("");
			rules.get(i).add("");
		}
	}

	static void findGoal(){
		int lacking = 0;
		int iteration = 0;
		int allIn = 0;

		if (facts.contains(goal)){
			System.out.println("\nPART 3. Results");
			System.out.format("  Goal %s in facts. Empty path.\n",goal);
			System.exit(0);
		}


		System.out.println("\nPART 2. Trace");
		while (!facts.contains(goal)) {
			iteration++;
			System.out.format("\n  ITERATION %d\n",iteration);

			for (int i=0;i<rules.size();i++){
				System.out.format("     R%d:",i+1);
				for (int j=1;j<rules.get(i).size()-3;j++)
					System.out.format("%s,",rules.get(i).get(j));
				System.out.format("%s -> %s ",rules.get(i).get(rules.get(i).size()-3),rules.get(i).get(0));
				
				

				if (rules.get(i).get(rules.get(i).size()-2).equals("flag1") && (rules.get(i).get(rules.get(i).size()-1).equals("flag2"))) {
						System.out.print("skip, because flag2 raised.\n");
				}
				else if (rules.get(i).get(rules.get(i).size()-2).equals("flag1")) {
						System.out.print("skip, because flag1 raised.\n");
				}
				allIn = 0;
				for (int j=1;j<rules.get(i).size()-2;j++){
					if (facts.contains(rules.get(i).get(j))){
						allIn++;
					}
					else {
						System.out.format("not applied, because of lacking %s.\n",rules.get(i).get(j));
						if (lacking++ > 10) {
							System.out.format("\nPART 3. Results\n  There is no path. Statement %s unreachable. Exiting. \n",goal);
							System.exit(0);
						}
						break;
					}
				}
				if (allIn != rules.get(i).size()-3) continue;

				if (!rules.get(i).get(rules.get(i).size()-2).equals("flag1")) {
					if (!facts.contains(rules.get(i).get(0))) {
							System.out.print("apply. Raise flag1. Facts ");
							rules.get(i).set(rules.get(i).size()-2,"flag1");
							for (int x=0;x<iniFactsN-1;x++) 
								System.out.print(facts.get(x)+", ");
							System.out.format("%s and ",facts.get(iniFactsN-1));
							facts.add(rules.get(i).get(0));
							for (int x=iniFactsN;x<facts.size()-1;x++)
								System.out.print(facts.get(x)+", ");
							System.out.format("%s.\n",facts.get(facts.size()-1));
							path.add(i+1);
							break;
					}
					else {
						System.out.println("not applied, because RHS in facts. Raise flag2.");
						rules.get(i).set(rules.get(i).size()-2,"flag1");
						rules.get(i).set(rules.get(i).size()-1,"flag2");
					}
				}
			}		
		}
		System.out.println("     Goal achieved.");
	}
	
	static void results(){
		System.out.println("\nPART 3. Results");
		System.out.format("  1) Goal %s achieved.\n",goal);
		System.out.format("  2) Path: %s\n",path.toString().replace("[","R").replace(", ",", R").replace("]","."));
	}

	public static void main(String[] args) {
		initialize();
		findGoal();	
		results();
	}
}