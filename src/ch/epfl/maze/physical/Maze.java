package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Maze in which an animal starts from a starting point and must find the exit.
 * Every animal added will have its position set to the starting point. The
 * animal is removed from the maze when it finds the exit.
 * 
 */

public final class Maze extends World {

	/* 
	 * List of animals one will be modified, the other not, animEnd will be used for the reset methode
	 */
	private List<Animal> animals = new ArrayList<>();
	private List<Animal> animEnd = new ArrayList<>();

	/**
	 * Constructs a Maze with a labyrinth structure.
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */

	public Maze(int[][] labyrinth) {
		super(labyrinth);
	}

	@Override
	public boolean isSolved() {
		if (animals.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Animal> getAnimals() {
		return new ArrayList<>(animals);
	}

	/**
	 * Determines if the maze contains an animal.
	 * 
	 * @param a
	 *            The animal in question
	 * @return <b>true</b> if the animal belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasAnimal(Animal a) {
		return animals.contains(a);
	}

	/**
	 * Adds an animal to the maze.
	 * 
	 * @param a
	 *            The animal to add
	 */

	public void addAnimal(Animal a) {
		Animal copyA = a.copy();

		animals.add(a);
		animEnd.add(copyA);
	}

	/**
	 * Removes an animal from the maze.
	 * 
	 * @param a
	 *            The animal to remove
	 */

	public void removeAnimal(Animal a) {
		animals.remove(a);
	}

	@Override
	public void reset() {
		animals.clear();

		if (!animEnd.isEmpty()) {
			for (int i = 0; i < animEnd.size(); i++) {
				animals.add(animEnd.get(i).copy());
				animals.get(i).setPosition(getStart());
			}
		}
	}
}
