package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Monkey A.I. that puts its hand on the left wall and follows it.
 * 
 */

public class Monkey extends Animal {

	/* Previous choice */
	private Direction previousChoice;

	/**
	 * Constructs a monkey with a starting position.
	 * 
	 * @param position
	 *            Starting position of the monkey in the labyrinth
	 */

	public Monkey(Vector2D position) {
		super(position);
	}

	/**
	 * Second constructor creating a copy of the previous Monkey
	 * 
	 * @param autre
	 * 			preious Monkey
	 */

	public Monkey(Monkey autre){
		super(autre);
	}

	/**
	 * Moves according to the relative left wall that the monkey has to follow.
	 */

	@Override
	public Direction move(Direction[] choices) {
		int length = choices.length;
		ArrayList<Direction> choices2 = new ArrayList<>(length);

		for (int i = 0; i < length; i++) {
			Direction dir = choices[i];
			choices2.add(i, dir);
		}

		if (length == 4) {
			previousChoice = Direction.DOWN;
			return previousChoice;
		} else if (length == 1 || (previousChoice == null && length == 2)) {
			previousChoice = choices[0];
			return previousChoice;
		} else if (length == 2 && previousChoice != null) {
			for (int i = 0; i < 2; i++) {
				if (choices[i] != previousChoice.reverse()) {
					previousChoice = choices[i];
					return previousChoice;
				}
			}
		} else {
			if (previousChoice == Direction.DOWN) {
				if (choices2.contains(Direction.RIGHT)) {
					previousChoice = Direction.RIGHT;
				} else if (choices2.contains(Direction.DOWN)) {
					previousChoice = Direction.DOWN;
				} else if (choices2.contains(Direction.LEFT)) {
					previousChoice = Direction.LEFT;
				} else {
					previousChoice = Direction.UP;
				}
			} else if (previousChoice == Direction.LEFT) {
				if (choices2.contains(Direction.DOWN)) {
					previousChoice = Direction.DOWN;
				} else if (choices2.contains(Direction.LEFT)) {
					previousChoice = Direction.LEFT;
				} else if (choices2.contains(Direction.UP)) {
					previousChoice = Direction.UP;
				} else {
					previousChoice = Direction.RIGHT;
				}
			} else if (previousChoice == Direction.UP) {
				if (choices2.contains(Direction.LEFT)) {
					previousChoice = Direction.LEFT;
				} else if (choices2.contains(Direction.UP)) {
					previousChoice = Direction.UP;
				} else if (choices2.contains(Direction.RIGHT)) {
					previousChoice = Direction.RIGHT;
				} else {
					previousChoice = Direction.DOWN;
				}
			} else if (previousChoice == Direction.RIGHT) {
				if (choices2.contains(Direction.UP)) {
					previousChoice = Direction.UP;
				} else if (choices2.contains(Direction.RIGHT)) {
					previousChoice = Direction.RIGHT;
				} else if (choices2.contains(Direction.DOWN)) {
					previousChoice = Direction.DOWN;
				} else {
					previousChoice = Direction.LEFT;
				}
			}
		}
		return previousChoice;
	}

	@Override
	public Animal copy() {
		return new Monkey(this);
	}
}
