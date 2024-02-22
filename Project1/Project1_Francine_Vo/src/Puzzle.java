/*
 * Francine Vo
 * Project 1
 */

import java.util.*;
import java.io.*;

public class Puzzle {

	private final int[][] SOLVED_3X3 = { { 1, 2, 3 },
										 { 4, 5, 6 },
										 { 7, 8, 0 } };
	

	private final int[][] SOLVED_4X4 = { { 1, 2, 3, 4 },
										 { 5, 6, 7, 8 },
										 { 9, 10, 11, 12 },
										 { 13, 14, 15, 0 } };

	private final int[][] SOLVED_5X5 = { { 1, 2, 3, 4, 5 },
										 { 6, 7, 8, 9, 10 },
										 { 11, 12, 13, 14, 15 },
										 { 16, 17, 18, 19, 20 },
										 { 21, 22, 23, 24, 0 } };

	// Task 1
	public int[][] fileReader(int x) {

		String fileName = x + "x" + x + ".txt";

		int[][] values = new int[x][x];
		try (Scanner inData = new Scanner(new FileReader(fileName))) {
			// Set the delimiter to handle commas
			inData.useDelimiter(",");

			for (int i = 0; i < values.length; i++) {
				for (int j = 0; j < values[i].length; j++) {
					if (inData.hasNextInt()) {
						values[i][j] = inData.nextInt();
					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return values;
	}

	public void puzzleSelector() {

		try (Scanner in = new Scanner(System.in)) {
			System.out.println("Pick a puzzle:\n1. 3x3\t2. 4x4\t3. 5x5");
			int answer = in.nextInt();
			if (answer > 0 && answer < 4)
				solver(fileReader(answer + 2));
			else {
				System.err.println("Invalid input. Please try again");
				puzzleSelector();
			}
		}
	}

	// Task 2
	public void puzzlePrinter(int[][] puzzle) {

		for (int i = 0; i < puzzle.length; i++) {
			System.out.print("+----".repeat(puzzle.length));
			System.out.println("+");
			for (int j = 0; j < puzzle[i].length; j++) {
				if (puzzle[i][j] == 0) {
					System.out.print("|    ");
				} else {
					System.out.printf("|%3s ", puzzle[i][j]);
				}
			}
			System.out.println("|");
		}
		System.out.print("+----".repeat(puzzle.length));
		System.out.println("+");
	}

	// Task 3
	public boolean puzzleChecker(int[][] array) {
		// Check if the puzzle array matches the solved puzzle for appropriate size
		if (array.length == 3) {
			return Arrays.deepEquals(array, SOLVED_3X3);
		} else if (array.length == 4) {
			return Arrays.deepEquals(array, SOLVED_4X4);
		} else if (array.length == 5) {
			return Arrays.deepEquals(array, SOLVED_5X5);
		} else {
			System.err.println("Invalid puzzle size.");
			return false;
		}
	}

	// Task 4
	public void solver(int[][] values) {
		try (Scanner in = new Scanner(System.in)) {

			while (!puzzleChecker(values)) {
				puzzlePrinter(values);
				System.out.println("Puzzle is NOT solved.");

				try {
					System.out.printf("%s%n%10s%10s%10s%10s%10s%n", "Please select your move:", "1. Up", "2. Down",
							"3. Left", "4. Right", "0. Exit");
					int move = in.nextInt();

					switch (move) {
					case 0:
						System.out.println("Goodbye!");
						return; // Exit the method and return to the caller
					case 1:
						moveUp(values);
						break;
					case 2:
						moveDown(values);
						break;
					case 3:
						moveLeft(values);
						break;
					case 4:
						moveRight(values);
						break;
					default:
						System.err.println("Invalid move");
						break;
					}
				} catch (InputMismatchException e) {
					System.err.println("Invalid input. Please enter a valid option.");
					in.next(); // Clear the invalid input from the scanner
				}
			}
			puzzlePrinter(values);
			System.out.println("Puzzle has been solved! Great job!");
		}
	}

	// Create methods that allow movement of the matrix elements
	public void moveDown(int[][] values) {
		int zeroRow = -1;
		int zeroCol = -1;

		// Find the position of 0
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				if (values[i][j] == 0) {
					zeroRow = i;
					zeroCol = j;
					break;
				}
			}
			if (zeroRow != -1) {
				break;
			}
		}

		// Check if it's possible to move down
		if (zeroRow > 0) {
			int temp = values[zeroRow - 1][zeroCol];
			values[zeroRow - 1][zeroCol] = 0;
			values[zeroRow][zeroCol] = temp;
		} else {
			System.err.println("Cannot move down. Invalid move.");
		}
	}

	public void moveUp(int[][] values) {
		int zeroRow = -1;
		int zeroCol = -1;

		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				if (values[i][j] == 0) {
					zeroRow = i;
					zeroCol = j;
					break;
				}
			}
			if (zeroRow != -1) {
				break;
			}
		}

		// Check if it's possible to move up
		if (zeroRow < values.length - 1) {
			int temp = values[zeroRow + 1][zeroCol];
			values[zeroRow + 1][zeroCol] = 0;
			values[zeroRow][zeroCol] = temp;
		} else {
			System.err.println("Cannot move up. Invalid move.");
		}
	}

	public static void moveRight(int[][] values) {
		int zeroRow = -1;
		int zeroCol = -1;

		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				if (values[i][j] == 0) {
					zeroRow = i;
					zeroCol = j;
					break;
				}
			}
			if (zeroRow != -1) {
				break;
			}
		}

		// Check if it's possible to move right
		if (zeroCol > 0) {
			int temp = values[zeroRow][zeroCol - 1];
			values[zeroRow][zeroCol - 1] = 0;
			values[zeroRow][zeroCol] = temp;
		} else {
			System.err.println("Cannot move right. Invalid move.");
		}
	}

	public void moveLeft(int[][] values) {
		int zeroRow = -1;
		int zeroCol = -1;

		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[i].length; j++) {
				if (values[i][j] == 0) {
					zeroRow = i;
					zeroCol = j;
					break;
				}
			}
			if (zeroRow != -1) {
				break;
			}
		}

		// Check if it's possible to move left
		if (zeroCol < values[zeroRow].length - 1) {
			int temp = values[zeroRow][zeroCol + 1];
			values[zeroRow][zeroCol + 1] = 0;
			values[zeroRow][zeroCol] = temp;
		} else {
			System.err.println("Cannot move left. Invalid move.");
		}
	}

	// Task 5. Create a method that outputs all puzzles to the console
	public void printAllPuzzles() {
		System.out.println("3x3");
		puzzlePrinter(fileReader(3));
		System.out.println();
		System.out.println("4x4");
		puzzlePrinter(fileReader(4));
		System.out.println();
		System.out.println("5x5");
		puzzlePrinter(fileReader(5));
	}

	// Task 6
	public void menu() {
		try (Scanner in = new Scanner(System.in)) {
			int option;

			while (true) {
				System.out.printf("%s%n%s%20s%13s%n", "Menu:", "1. Print unsolved puzzles", "2. Solve Puzzle",
						"3. Exit");
				option = in.nextInt();

				switch (option) {
				case 1:
					printAllPuzzles();
					break;
				case 2:
					puzzleSelector();
					return;
				case 3:
					System.out.println("Goodbye!");
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
				}
			}
		} catch (InputMismatchException e) {
			System.err.println("Invalid input. Please enter a valid option.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("An error occurred");
		}
	}
}
