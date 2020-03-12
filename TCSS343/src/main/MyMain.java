package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


//	Dung Thai and Mercedes Chea
//  TCSS343 Final Project
//
// 	There are n trading posts numbered 1 to n, as you travel downstream. At any trading post i,
//	you can rent a canoe to be returned at any of the downstream trading posts j>i. You are given
//	a cost array R(i,j) = infinity if i> j. The problem is to find a solution that computes the cheapest
//	sequence of rentals taking you from post 1 down to post n.


public class MyMain {
	
	//The HashSet to keep track of the sequence for Divide and Conquer
	private static HashSet<Integer> dncSequence;

	// This is the main method where we will parse the file and make the calls to the different algorithm 
	//	methods and print out the time it takes to get the solution from the algorithm.  You can select 
	//	which algorithm to call by uncommenting the calls. 
	public static void main(String[] args) {
		// A try and catch to be able to run and read the text file
		try {
			// This is Was used to originally generate our test files
			// Change test from to "test" or "testpseudo"
//			String test = "testpseudo";
//          OutputText(randMatrix(20, test), 20, test);
//            OutputText(randMatrix(25, test), 25, test);
//            OutputText(randMatrix(50, test), 50, test);
//            OutputText(randMatrix(100, test), 100, test);
//			OutputText(randMatrix(200, test), 200, test);
//            OutputText(randMatrix(400, test), 400, test);
//            OutputText(randMatrix(800, test), 800, test);
			

			// Change Test File Name HERE!!
			String testFileInput = "testpseudo10.txt";
			// Change the n X n HERE ALSO!!
			int n = 10;
			// Buffered reader to read in the file
			BufferedReader rentalCosts = Files.newBufferedReader(Paths.get(testFileInput));
			// Making the int array for the problem
			String[][] arr = new String[n][n];
			String tempString = rentalCosts.readLine();
			// For loop to put all the values into the array
			int m = 0;
			while (tempString != null) {
				String[] tempparse = tempString.split("	");
				for(int j = 0; j < n; j++) {
					arr[m][j] = tempparse[j];
				}
				m++;
				tempString = rentalCosts.readLine();
			}
			
			// This just prints out the original text thats being tested.
//			for (int i = 0; i < n; i++) {
//				for (int j = 0; j < n; j++){
//					System.out.print(arr[i][j] + "	");
//				}
//				System.out.println();
//			}
			
			// The calls to each of the methods
			
			// To Call the Brute Force Method
			// Start the timer
			long timer = System.currentTimeMillis();
			bruteForce(arr);
			// End the timer for the method.
			long endtimer = System.currentTimeMillis(); 
			long totaltime = endtimer - timer;
			System.out.println("Running time: " + totaltime/1000 + " seconds");
			
			
			//Start Dynamic Programming
			timer = System.currentTimeMillis();
			dynamicProgramming(arr);
			// End the timer for the method.
			endtimer = System.currentTimeMillis(); 
			totaltime = endtimer - timer;
			System.out.println("\nRunning time: " + totaltime/1000 + " seconds");
			
			
			// To call the divide and conquer method
			// Start the timer
			timer = System.currentTimeMillis();
			dncSequence = new HashSet<>();
			System.out.println("\n" + "Divide and Conquer total minimum cost: " + divideAndConquer(arr, 0, arr.length-1));
//			//DIVIDE AND CONQUOR ATTEMPT TO RECOVER FAILED.
			//System.out.print("Sequence of the minimum path: ");
//			//Traversing to print out the solution
//			Iterator<Integer> itr = dncSequence.iterator();
//			while(itr.hasNext()){ 
//				System.out.print(itr.next() + " "); 
//				}
			System.out.println();
			// End the timer for the method.
			endtimer = System.currentTimeMillis();
			totaltime = endtimer - timer;
			System.out.println("Running time: " + totaltime/1000 + " seconds");
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// Test method to create a random matrix with values from 1 to 10 
    public static String[][] randMatrix(int size, String test) {
        Random number = new Random();
        String[][] arr = new String[size][size];
        if(test.contentEquals("testpseudo")){
        // For loops to generate random incrementing values
        	for(int i = 0; i < size; i++) {
        		int n = 2;
        		for(int j = 0; j < size; j++) {
        			if( i > j ) {
        				arr[i][j] = "NA";
        			} else if(i == j){
        				arr[i][j] = Integer.toString(0);
        			} else{
        				arr[i][j] = Integer.toString(number.nextInt(n) + Integer.parseInt(arr[i][j-1]) + 1);
        			}
        			n = n + 1;
        		}
        	}
        } else {
//        	For loops to generate random values
        	for(int i = 0; i < size; i++) {
        		for(int j = 0; j < size; j++) {
        			if( i > j ) {
        				arr[i][j] = "NA";
        			} else if(i == j){
        				arr[i][j] = Integer.toString(0);
        			} else{
        				arr[i][j] = Integer.toString(number.nextInt(size) + 1);
        			}
        		}
        	}
        }
        return arr;
    }
    
    // Test method to output the array input to text.
    private static void OutputText(String[][] arr, int size, String test) throws IOException {
    	//Filenames
		File outputFile = new File(test + Integer.toString(size) + ".txt");
//		File outputFile = new File("test" + Integer.toString(size) + ".txt");
		PrintStream matrixOutput = new PrintStream(outputFile);
        int n = arr.length;
        int m = arr[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
            	matrixOutput.append(arr[i][j] + "	");
                }
            matrixOutput.println();
            }
        matrixOutput.close();
    }

    
	
	// This method is the Brute Force solution to solve the problem.
	public static int bruteForce(String[][] costArr) {
		// Initializing Array to store the the sequences
		ArrayList<String> sequence = new ArrayList<>();
		// Initializing Array to store the total cost
		ArrayList<Integer> totalCost = new ArrayList<>();
		// Storing the current initial value of the binary path.
		int initial = (int) Math.pow(2,costArr.length-1) +1;
		// Storing the number of possible paths that we can traverse.
		int possible = (int) Math.pow(2,costArr.length-2 );

		// First we add the sequence to our list as binary, then we increment to the next path.
		for(int i = 0; i < possible; i++) {
			sequence.add(Integer.toBinaryString(initial));
			initial += 2;
		}

		// Next we have to loop through to get the cost of each of the sequences
		for(int i = 0; i < sequence.size(); i++) {
			int tempCost = 0;
			int tempRow = 0;
			// The second loop goes and checks the value of the next sequence starting from that column
			for(int j = 0; j < sequence.get(i).length(); j++) {
				// Checks if the current binary string is 1 and if the row is smaller than the
				// length of the string
				if(sequence.get(i).substring(j, j+1).equals("1")  && tempRow < sequence.get(i).length()) {
					// Checks if the cost of the current post is greater than 0 than it will add up the cost.
					if(Integer.parseInt(costArr[tempRow][j])>0) {
						tempCost += Integer.parseInt(costArr[tempRow][j]);
					} 
					// If the column is greater than the row, Swap the indexes.
					if(j > tempRow) { 
						int t = tempRow;
						tempRow = j;
						j = t;
					}
				} 
			}
			// Now you add the total cost to the total cost and increment to the next row.
			totalCost.add(tempCost);
			tempRow++;
		}
		
		// Using Collections to get the minimum from the Total cost list.
		int cheapestCost = Collections.min(totalCost);
		// Print out the Brute Force total minimum cost
		System.out.println("\n" + "Brute Force total minimum cost: " + cheapestCost);
		// We have to get the index of the totalCost so that we can trace back the path of our solution
		int index = totalCost.indexOf(Collections.min(totalCost));
		System.out.print("Sequence of the minimum path: ");
		
		// Loop to get the Posts that added up to the solution from the index of the totalCost.
		for(int i = 0; i < sequence.get(index).length(); i++) {
			// Checks if the item equals 1, it means that we traversed that path, print it out.
			if(sequence.get(index).substring(i, i+1).equals("1")) {
				System.out.print(i + 1 + " ");
			}
		}
		System.out.println();
		return cheapestCost;
	}
	
	// This method is the Divide and Conquer solution to solve the problem.
	public static int divideAndConquer(String[][] costArr, int row, int post) {
		// Initialize with current cost
		int cheapestCost = Integer.parseInt(costArr[row][post]);
		// BASE CASE:
		// If the row equals post, return 0, or if the row + 1 equals post, return the cost to get to that post.
		if(row == post || row + 1 == post) {
			return cheapestCost;
		}
		else {
			// Recurse the costArr with the first half and then add it with the second half
			for(int temp = row+1; temp < post; temp++) {
				// ATTEMPT TO RECOVER FOR DNC FAILED
//				int left = divideAndConquer(costArr, row, temp);
//				int right = divideAndConquer(costArr, temp, post);
//				if (left > right && left < cheapestCost) {
//					dncSequence.add(post-1);
//				} else {
//					dncSequence.add(row);
//				}
				int recurseCost = divideAndConquer(costArr, row, temp) + divideAndConquer(costArr, temp, post);
				// if the minimum value is less than the cheap, replace the values, then add to sequence.
				if(recurseCost < cheapestCost) {
					cheapestCost = recurseCost;
//					dncSequence.add(post -1);
				} 
			}
		}
//		//This adds the last post to the sequence list.
//		dncSequence.add(costArr.length);
		return cheapestCost;	
	}
	
	// This method is the Dynamic Programming solution to solve the problem.
	public static int dynamicProgramming(String[][] costArr) {
		// Initializing the size of the array
		int size = costArr.length;
		// Initializing an array to store the cheapest rentals stops
		int[] minCost = new int[size];
		// Initializing the array to store the posts that contain the minimum value;
		int[] savedposts = new int[size];
		// Initializing the array to store the sequence.
		List<Integer> sequence = new ArrayList<>();
		// First loop to go through the row
		for (int i = 0; i < size; i++) {
			// Setting the currentMin to the post for comparison 
			int currentMin = Integer.parseInt(costArr[0][i]);
			int currentpost = 0;
			for (int j = 0; j < i; j++) {
				if(minCost[j] + Integer.parseInt(costArr[j][i]) < currentMin) {
					currentMin = minCost[j] + Integer.parseInt(costArr[j][i]);
					currentpost =  j;
				}
			}
			savedposts[i] = currentpost;
			minCost[i] = currentMin;
		}
		
		// To recover the solution
		int currentpost = size -1;
		for(int i = 0; i < savedposts.length -1; i++) {
			if(!sequence.contains(currentpost+1)) {
				sequence.add(currentpost+1);
				currentpost = savedposts[currentpost];
			}
		}
		Collections.sort(sequence);
		System.out.print("\n" + "Dynamic Programming total minimum cost: " + minCost[size - 1]);
		System.out.print("\n" + "Sequence of the minimum path: ");
		// To print out the sequence we go through and print out the items in the array
		for (int i = 0; i < sequence.size(); i++) {
		System.out.print(sequence.get(i) + " ");
	}
		return minCost[size - 1];
		
	}
//	public static int dynamicProgramming(String[][] costArr) {
//		// Initializing the size of the array
//		int size = costArr.length;
//		// Initializing an array to store the cheapest rentals stops
//		int[][] minCost = new int[size][size];
//		// Fill it in with zeroes
//		for (int i = 0; i < size; i++) {
//			for (int j = 0; j < size; j++) {
//				minCost[i][j] = 0;
//			}
//		}
//		// Initializing the array to store the sequence.
//		List<Integer> sequence = new ArrayList<>();
//		// First loop to go through the row
//		for (int i = 1; i < size; i++) {
//			for (int j = 0; j < size - i; j++) {
//				minCost[j][j+i] = Math.min(minCost[j][j+1] + minCost[j+1][j+i], minCost[j][j+i]);
//				sequence.add(i);
//			}
//		}
//		
//		// To recover the solution
//		Collections.sort(sequence);
//		for(int i = 0; i < sequence.size(); i++) {
//			System.out.print(sequence.get(i) + " ");
//		}
//		System.out.println(minCost[size - 1][0]);
//		return minCost[size - 1][0];
//		
//	}

}
