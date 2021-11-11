package wordpuzzlesolver;

import java.util.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class WordPuzzle {

	// the below method loadDictionary is to load the dictionary into our hash
	// table.
	public void loadDictionaryTextFile(String path, QuadraticProbingHashTable<String> hashTable,
			HashMap<String, String> hashMap, int algorithm) {
		Scanner scanner1 = null;// initializing scanner scanner1 to null
		try {// using try catch to give error in case invalid path is given
			scanner1 = new Scanner(new File(path));// giving path to dictionary.txt as path. giving dictionary.txt to
													// scanner1 as input.
		} catch (FileNotFoundException e) {
			e.printStackTrace();// print stack trace in the event a file is not found
		}

		int wC = 0; // wC is our word count
		if (algorithm == 1) {// algorithm == 1 means that we are running the algorithm with enhancement
			while (scanner1.hasNext()) {
				String dictionaryWord = scanner1.nextLine();
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < dictionaryWord.length(); i++) {
					stringBuilder.append(dictionaryWord.charAt(i));

					if (i == dictionaryWord.length() - 1) {
						hashMap.put(stringBuilder.toString(), "word");
						wC++;
					} else {
						if (hashMap.get(stringBuilder.toString()) != "word") {
							hashMap.put(stringBuilder.toString(), "prefix");
						}
					}
				}
			}
		} else {
			while (scanner1.hasNext()) {
				String dictionaryWord = scanner1.nextLine();
				hashTable.insert(dictionaryWord);
				wC++;
			}
		}
		scanner1.close();
		System.out.println("Dictionary loaded, total words = " + wC);

	}

	public void setHashMapHashTableBasedOnAlgorithm(QuadraticProbingHashTable<String> hashTable,
			HashMap<String, String> hashMap, ArrayList<String> resultsArrayList, char m[][], int algorithm,
			StringBuilder stringBuilder) {
		if (algorithm == 1) {
			boolean hashMapContains = hashMap.containsKey(stringBuilder.toString());
			if (!hashMapContains) {
				stringBuilder.setLength(0);
				return;
			} else if (hashMapContains && hashMap.get(stringBuilder.toString()) != "prefix") {
				String wordToAdd = new String(stringBuilder);
				if (!resultsArrayList.contains(wordToAdd)) {
					resultsArrayList.add(wordToAdd);
				}
			}
		} else {
			boolean hashTableContains = hashTable.contains(stringBuilder.toString());
			if (hashTableContains) {
				String wordToAdd = new String(stringBuilder);
				if (!resultsArrayList.contains(wordToAdd)) {
					resultsArrayList.add(wordToAdd);
				}
			}
		}
	}

	public void checkHorizontalLeftRight(QuadraticProbingHashTable<String> hashTable, HashMap<String, String> hashMap,
			ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int a = 0; a < m.length; a++) {
			for (int b = 0; b < m[a].length; b++) {
				stringBuilder.setLength(0);
				for (int c = 0; c + b < m[a].length; c++) {
					stringBuilder.append(m[a][c + b]);
					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
				}
			}
		}

	}

	public void checkHorizontalRightLeft(QuadraticProbingHashTable<String> hashTable, HashMap<String, String> hashMap,
			ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int a = 0; a < m.length; a++) {
			for (int b = m[a].length - 1; b >= 0; b--) {
				stringBuilder.setLength(0);
				for (int c = b; c >= 0; c--) {
					stringBuilder.append(m[a][c]);
					if (stringBuilder.length() < 2) {
						continue;
					}
					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
				}
			}
		}
	}

	public void checkVerticalTopBottom(QuadraticProbingHashTable<String> hashTable, HashMap<String, String> hashMap,
			ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int a = 0; a < m[0].length; a++) {
			for (int b = 0; b < m.length; b++) {
				stringBuilder.setLength(0);
				for (int c = 0; c + b < m.length; c++) {
					stringBuilder.append(m[c + b][a]);
					if (stringBuilder.length() < 2) {
						continue;
					}
					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
				}
			}
		}
	}

	public void checkVerticalBottomTop(QuadraticProbingHashTable<String> hashTable, HashMap<String, String> hashMap,
			ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int a = 0; a < m[0].length; a++) {
			for (int b = 0; b < m.length; b++) {
				stringBuilder.setLength(0);
				for (int c = m.length - 1; (c - b) >= 0; c--) {
					stringBuilder.append(m[c - b][a]);
					if (stringBuilder.length() < 2) {
						continue;
					}
					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
				}
			}
		}
	}

	public void checkDiagonallyTopLeftBottomRight(QuadraticProbingHashTable<String> hashTable,
			HashMap<String, String> hashMap, ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int a = 0; a < m.length; a++) {
			for (int b = 0; b < m[0].length; b++) {
				int d = a;
				stringBuilder.setLength(0);
				for (int c = b; c < m[0].length; c++) {
					stringBuilder.append(m[d][c]);
					if (stringBuilder.length() < 2) {
						d--;
						if (d < 0) {
							break;
						}
						continue;
					}
					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
					d--;
					if (d < 0) {
						break;
					}
				}
			}
		}
	}

	public void checkDiagonallyBottomRightTopLeft(QuadraticProbingHashTable<String> hashTable,
			HashMap<String, String> hashMap, ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int a = 0; a < m.length; a++) {
			for (int b = 0; b < m[0].length; b++) {
				int d = a;
				stringBuilder.setLength(0);
				for (int c = b; c >= 0; c--) {
					stringBuilder.append(m[d][c]);
					if (stringBuilder.length() < 2) {
						d++;
						if (d > m.length - 1) {
							break;
						}
						continue;
					}
					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
					d++;
					if (d > m.length - 1) {
						break;
					}
				}
			}
		}
	}

	public void checkDiagonallyBottomLeftTopRight(QuadraticProbingHashTable<String> hashTable,
			HashMap<String, String> hashMap, ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int a = 0; a < m.length; a++) {
			for (int b = 0; b < m[0].length; b++) {
				int d = a;
				stringBuilder.setLength(0);
				for (int c = b; c < m[0].length; c++) {
					stringBuilder.append(m[d][c]);
					if (stringBuilder.length() < 2) {
						d++;
						if (d > m.length - 1) {
							break;
						}
						continue;
					}

					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
					d++;
					if (d > m.length - 1) {
						break;
					}
				}
			}
		}
	}

	public void checkDiagonallyTopRightBottomLeft(QuadraticProbingHashTable<String> hashTable,
			HashMap<String, String> hashMap, ArrayList<String> resultsArrayList, char m[][], int algorithm) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int a = 0; a < m.length; a++) {
			for (int b = 0; b < m[0].length; b++) {
				int d = a;
				stringBuilder.setLength(0);
				for (int c = b; c >= 0; c--) {
					stringBuilder.append(m[d][c]);
					if (stringBuilder.length() < 2) {
						d--;
						if (d < 0) {
							break;
						}
						continue;
					}

					setHashMapHashTableBasedOnAlgorithm(hashTable, hashMap, resultsArrayList, m, algorithm,
							stringBuilder);
					d--;
					if (d < 0) {
						break;
					}
				}
			}
		}
	}

	public static void main(String args[]) {
		// main function
		Scanner scanner1 = new Scanner(System.in);
		QuadraticProbingHashTable<String> hashTable = new QuadraticProbingHashTable<>();
		HashMap<String, String> hashMap = new HashMap<>();
		WordPuzzle wP = new WordPuzzle();

		System.out.println("Enter the number of rows and columns: ");
		System.out.println("Range of row and col is 10 to 40. Row and column cannot be same.");
		int row = scanner1.nextInt();
		int col = scanner1.nextInt();

		if (row < 10 || row > 40 || col < 10 || col > 40) {
			System.out.println("Range of rows and colums should be give in between 10, 40.");
			scanner1.close();
		} else if (row == col) {
			System.out.println("Row and column cannot have the same number");
			scanner1.close();
		} else {
			System.out.println(
					"Please enter the number 0 for normal algorithm or enter the number 1 for enhanced algorithm: ");
			System.out.println(
					"You can uncomment the code in main function to run the other algorithm in the same run to verify that the results is same ");
			int algorithm = scanner1.nextInt();

			char[][] m = new char[row][col];

			Random rdm = new Random();
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					m[i][j] = (char) (97 + rdm.nextInt(26));
				}
			}

			// display generated puzzle
			System.out.println("************************************************");
			System.out.println("The word puzzle is: ");
			for (int i = 0; i < m.length; i++) {
				for (int j = 0; j < m[i].length; j++)
					System.out.print(m[i][j] + " ");
				System.out.println();
			}
			System.out.println("Running for algorithm = " + algorithm);
			// load dictionary file into hash table

			/********
			 * Select one of the path(and edit as required) from below depending on the
			 * location of dictionary.txt file
			 ********/
			String dictionaryPath = "C:\\Users\\makin\\OneDrive\\Desktop\\Others\\dictionary.txt";
			System.out.println("************************************************");
			wP.loadDictionaryTextFile(dictionaryPath, hashTable, hashMap, algorithm);

			// check for words
			ArrayList<String> wordsFound = new ArrayList<>();

			long start = System.currentTimeMillis();

			wP.checkHorizontalLeftRight(hashTable, hashMap, wordsFound, m, algorithm);
			wP.checkHorizontalRightLeft(hashTable, hashMap, wordsFound, m, algorithm);
			wP.checkVerticalTopBottom(hashTable, hashMap, wordsFound, m, algorithm);
			wP.checkVerticalBottomTop(hashTable, hashMap, wordsFound, m, algorithm);
			wP.checkDiagonallyTopLeftBottomRight(hashTable, hashMap, wordsFound, m, algorithm);
			wP.checkDiagonallyBottomRightTopLeft(hashTable, hashMap, wordsFound, m, algorithm);
			wP.checkDiagonallyTopRightBottomLeft(hashTable, hashMap, wordsFound, m, algorithm);
			wP.checkDiagonallyBottomLeftTopRight(hashTable, hashMap, wordsFound, m, algorithm);

			long end = System.currentTimeMillis();

			System.out.println("************************************************");
			System.out.println("Printing words found: ");
			int wordIndex = 1;
			Collections.sort(wordsFound);
			for (String a : wordsFound) {
				System.out.println(wordIndex + ": " + a);
				wordIndex++;
			}
			System.out.println("************************************************");
			System.out.println("Total " + wordsFound.size() + " words found, time taken = " + (end - start) + " ms");

// 			uncomment below code to run for the other algorithm also
//			System.out.println("");
//			System.out.println("");
//			System.out.println("");
//			System.out.println("************************************************");
//			System.out.println("******* RUNNING FOR THE OTHER ALGORITHM ********");
//			
//			System.out.println("************************************************");
//			if(algorithm == 0) {
//				algorithm = 1;
//			}else {
//				algorithm = 0;
//			}
//			System.out.println("Running for algorithm = " + algorithm);
//			wP.loadDictionaryTextFile(dictionaryPath, hashTable, hashMap, algorithm);
//			
//			 //check for words
//			wordsFound = new ArrayList<>();
//
//			start = System.currentTimeMillis();
//
//			wP.checkHorizontalLeftRight(hashTable, hashMap, wordsFound, m, algorithm);
//			wP.checkHorizontalRightLeft(hashTable, hashMap, wordsFound, m, algorithm);
//			wP.checkVerticalTopBottom(hashTable, hashMap, wordsFound, m, algorithm);
//			wP.checkVerticalBottomTop(hashTable, hashMap, wordsFound, m, algorithm);
//			wP.checkDiagonallyTopLeftBottomRight(hashTable, hashMap, wordsFound, m, algorithm);
//			wP.checkDiagonallyBottomRightTopLeft(hashTable, hashMap, wordsFound, m, algorithm);
//			wP.checkDiagonallyTopRightBottomLeft(hashTable, hashMap, wordsFound, m, algorithm);
//			wP.checkDiagonallyBottomLeftTopRight(hashTable, hashMap, wordsFound, m, algorithm);
//
//			end = System.currentTimeMillis();
//
//			System.out.println("************************************************");
//			System.out.println("Printing words found: ");
//			wordIndex = 1;
//			Collections.sort(wordsFound);
//			for (String a : wordsFound) {
//				System.out.println(wordIndex + ": " + a);
//				wordIndex++;
//			}
//			System.out.println("************************************************");
//			System.out.println("Total " + wordsFound.size() + " words found, time taken = " + (end - start) + " ms");
		}
	}
}
