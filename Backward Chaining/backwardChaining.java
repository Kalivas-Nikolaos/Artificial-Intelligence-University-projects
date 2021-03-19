import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.lang.String;
import java.util.Stack;

class backwardChaining{

	static ArrayList<ArrayList<String>> rules = new ArrayList<ArrayList<String>>(); 
	static List<String> initialFacts = new ArrayList<String>();
	static Stack<String> facts = new Stack<String>();
	static Stack<String> goals = new Stack<String>();
	static List<Integer> path = new ArrayList<>();
	static String goal;
	static int trial;

	
	static void initialize(){
		loadFile(getCustomPath());
		printData();
		trial=0;
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
				initialFacts.add(temp[i]);
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
		for (int i=0;i<rules.size();i++) {
			System.out.format("     R%d: ",i+1);
			for (int j=1;j<rules.get(i).size()-1;j++)	
					System.out.format("%s, ",rules.get(i).get(j));
			System.out.format("%s -> %s\n",rules.get(i).get(rules.get(i).size()-1),rules.get(i).get(0));
		}
		System.out.println("\n  2) Facts");
			System.out.format("     %s",initialFacts.toString().replace("[","").replace("]",""));
		System.out.println("\n\n  3) Goal");
			System.out.format("     %s\n",goal);
	}

	static void prefix(String goal,int backtrackLevel){
		System.out.format(" %2d) ",(trial));
		for (int i=0;i<backtrackLevel;i++ ) {
			System.out.print("-");
		}
		System.out.print("Goal "+goal+". ");
	}


	static boolean findFacts(String goal,int backtrackLevel){
		 if(goals.search(goal)==-1) {
            if(initialFacts.contains(goal)) {

                trial++;
                prefix(goal,backtrackLevel);
				System.out.println("Fact (initial), as facts "+
					initialFacts.toString().replace("[","").replace("]","")+". Back, OK.");
                return true;

            } else if (facts.search(goal)!=-1) {

                trial++;
                prefix(goal,backtrackLevel);
               	System.out.print("Fact (earlier inferred), as facts ");
				for (int i=0;i<initialFacts.size() ;i++ ) {
					System.out.print(initialFacts.get(i));
				}
				System.out.print(" and "+facts.toString().replace("[","").replace("]","."));
				System.out.println(" Back, OK.");
                return true;

            } else {
                goals.add(goal);
                boolean ruleFound = false;
                for(int i = 0; i < rules.size(); i++) {
                    if(rules.get(i).get(0).equals(goal)) {
                    	ruleFound = true;
                        trial++;
                        prefix(goal,backtrackLevel);
                        System.out.print("Find R"+(i+1)+":");
						for (int j=1;j<rules.get(i).size()-1;j++)	System.out.format("%s,",rules.get(i).get(j));
						System.out.format("%s -> %s. ",rules.get(i).get(rules.get(i).size()-1),rules.get(i).get(0));
						System.out.print("New goals");
						for (int j=1;j<rules.get(i).size()-1;j++)	System.out.format(" %s,",rules.get(i).get(j));
						System.out.format(" %s.\n",rules.get(i).get(rules.get(i).size()-1));
                        boolean success = true;
                        Stack<String> tempFacts = new Stack<String>();
                        ArrayList<Integer> tempPath = new ArrayList<>();
                       	tempFacts.addAll(facts);
                        tempPath.addAll(path);

						for(int j=1;j<rules.get(i).size();j++){
							if(!findFacts(rules.get(i).get(j), backtrackLevel+1)){
                                success = false;
                                facts = tempFacts;
                                path = tempPath;
                                break;
                            }
						}
 
                        if(success) {
                            facts.add(rules.get(i).get(0));
                            path.add(i+1);
                            trial++;
                            prefix(goal,backtrackLevel);
                          	System.out.print("Fact (presently inferred). Facts ");
							for (int k=0;k<initialFacts.size() ;k++ ) {
								System.out.print(initialFacts.get(k));
							}
							System.out.print(" and "+facts.toString().replace("[","").replace("]","."));
							System.out.print(" Back, OK.\n");

                            for(int k = 0; k < goals.size(); k++){
                                if(goals.get(k) == goal){
                                	goals.remove(goals.get(k));
                                    break;
                                }
                            }
                            return true;
                        }
                    }
                }
              

                trial++;
                prefix(goal,backtrackLevel);

                if (ruleFound){
					System.out.print("No more rules. Back, FAIL.\n");
                }
                else {
					System.out.print("No rules. Back, FAIL.\n");
                }

                for(int i = 0; i < goals.size(); i++){
                    if(goals.get(i) == goal){
              		  	goals.remove(goals.get(i));
                        break;
                    }
                }
                return false;
            }
        }else {
            trial++;
            prefix(goal,backtrackLevel);
            System.out.println("Cycle. Back, FAIL.");
            return false;
        }
	}
	
static void results(){
	System.out.println("\nPART 2. Trace");
		if (findFacts(goal,0)) {
			System.out.println("\n\nPART 3. Results");
			System.out.format("  1) Goal %s achieved.\n",goal);
			System.out.format("  2) Path: %s\n",path.toString().replace("[","R").replace(", ",", R").replace("]","."));	
		}
		else {
			System.out.println("\n\nPART 3. Results");
			System.out.format("  1) Goal %s not achieved.\n",goal);
			System.out.format("  2) Empty path.");
		}	
}

public static void main(String[] args) {
		initialize();
		for (String s:initialFacts){
			if (s.equals(goal)){
				System.out.println("\n\nPART 3. Results");
				System.out.format("  1) Goal %s among facts. Empty path.\n",goal);
				System.exit(0);
			}
		}
		results();	
	}
}