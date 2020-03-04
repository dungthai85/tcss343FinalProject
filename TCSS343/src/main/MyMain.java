package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;


//	Dung Thai and Mercedes Chea
//  TCSS343 Final Project
//
// 	There are n trading posts numbered 1 to n, as you travel downstream. At any trading post i,
//	you can rent a canoe to be returned at any of the downstream trading posts j>i. You are given
//	a cost array R(i,j) = infinity if i> j. The problem is to find a solution that computes the cheapest
//	sequence of rentals taking you from post 1 down to post n.


public class MyMain {

	// This is the main method where we will parse the file and make the calls to the different algorithm 
	//	methods and print out the time it takes to get the solution from the algorithm.  You can select 
	//	which algorithm to call by uncommenting the calls. 
	public static void main(String[] args) {
		// A try and catch to be able to run and read the text file
		try {
			// This is Was used to originally generate our test files
//            OutputText(randMatrix(25), 25);
//            OutputText(randMatrix(50), 50);
//            OutputText(randMatrix(100), 100);
//			OutputText(randMatrix(200), 200);
//            OutputText(randMatrix(400), 400);
//            OutputText(randMatrix(800), 800);
			// Start the timer
			long timer = System.currentTimeMillis();
			// Change Test File Name HERE!!
			String testFileInput = "sample_input.txt";
			// Change the n X n HERE ALSO!!
			int n = 4;
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
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++){
					System.out.print(arr[i][j] + "	");
				}
				System.out.println();
			}
			// The calls to each of the methods
			
			// End the timer for the method.
			long endtimer = System.currentTimeMillis();
			long totaltime = endtimer - timer;
			System.out.println("Running time: " + totaltime/1000 + " seconds");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// Test method to create a random matrix with values from 1 to 10 
    public static String[][] randMatrix(int size) {
        Random number = new Random();
        String[][] arr = new String[size][size];
        // For loops to generate random incrementing values
        for(int i = 0; i < size; i++) {
        	int n = 2;
        	int randomNumber = number.nextInt(n) + 1;
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
//         For loops to generate random values
//        for(int i = 0; i < size; i++) {
//            for(int j = 0; j < size; j++) {
//                if( i > j ) {
//                    arr[i][j] = "NA";
//                }else if(i == j){
//                    arr[i][j] = Integer.toString(0);
//                }else{
//                    arr[i][j] = Integer.toString(number.nextInt(size) + 1);
//                }
//            }
//        }
        return arr;
    }
    
    // Test method to output the array input to text.
    private static void OutputText(String[][] arr, int size) throws IOException {
    	//Filenames
		File outputFile = new File("testpseudo" + Integer.toString(size) + ".txt");
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
	public static void bruteForce(String[][] arr) {
		
		
	}
	
	// This method is the Divide and Conquer solution to solve the problem.
	public static void divideAndConquer(String[][] arr) {
		
		
	}
	
	// This method is the Dynamic Programming solution to solve the problem.
	public static void dynamicProgramming(String[][] arr) {
		
		
	}

}
