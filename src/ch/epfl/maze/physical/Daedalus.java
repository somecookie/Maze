package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

/**
 * Daedalus in which predators hunt preys. Once a prey has been caught by a
 * predator, it will be removed from the daedalus.
 * 
 */

public final class Daedalus extends World {

	/*
	 * List of predators in the daedalus
	 */
	private List<Predator> predators = new ArrayList<>();
	/*
	 * List of preys in the daedalus
	 */
	private List<Prey> preys = new ArrayList<>();
	/*
	 * Previous lists but won't be modified, used for the reset method
	 */
	private List<Predator> predEnd = new ArrayList<>();
	private List <Prey> preyEnd = new ArrayList<>();

	/**
	 * Constructs a Daedalus with a labyrinth structure
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */

	public Daedalus(int[][] labyrinth) {
		super(labyrinth);
	}

	@Override
	public boolean isSolved() {
		return preys.isEmpty();
	}

	/**
	 * Adds a predator to the daedalus.
	 * 
	 * @param p
	 *            The predator to add
	 */

	public void addPredator(Predator p) {
		Predator copyP = (Predator) p.copy();
		predators.add(p);
		predEnd.add(copyP);
	}

	/**
	 * Adds a prey to the daedalus.
	 * 
	 * @param p
	 *            The prey to add
	 */

	public void addPrey(Prey p) {
		Prey copyP =  (Prey) p.copy();
		preys.add( p);
		preyEnd.add(copyP);
	}

	/**
	 * Removes a predator from the daedalus.
	 * 
	 * @param p
	 *            The predator to remove
	 */

	public void removePredator(Predator p) {
		predators.remove(p);
	}

	/**
	 * Removes a prey from the daedalus.
	 * 
	 * @param p
	 *            The prey to remove
	 */

	public void removePrey(Prey p) {
		preys.remove(p);
	}

	@Override
	public List<Animal> getAnimals() {
		List<Animal> allAnimals = new ArrayList<>();

		for (int i = 0; i < predators.size(); i++) {
			allAnimals.add(predators.get(i).copy());
		}
		for (int i = 0; i < preys.size(); i++) {
			allAnimals.add(preys.get(i).copy());
		}

		return allAnimals;
	}

	/**
	 * Returns a copy of the list of all current predators in the daedalus.
	 * 
	 * @return A list of all predators in the daedalus
	 */

	public List<Predator> getPredators() {
		List<Predator> preds = new ArrayList<>();

		for (int i = 0; i < predators.size(); i++) {
			preds.add( predators.get(i));
		}

		return new ArrayList<>(preds);
	}

	/**
	 * Returns a copy of the list of all current preys in the daedalus.
	 * 
	 * @return A list of all preys in the daedalus
	 */

	public List<Prey> getPreys() {
		List<Prey> proie = new ArrayList<>();

		for (int i = 0; i < preys.size(); i++) {
			proie.add(preys.get(i));
		}

		return new ArrayList<>(proie);
	}

	/**
	 * Determines if the daedalus contains a predator.
	 * 
	 * @param p
	 *            The predator in question
	 * @return <b>true</b> if the predator belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPredator(Predator p) {
		return predators.contains(p);
	}

	/**
	 * Determines if the daedalus contains a prey.
	 * 
	 * @param p
	 *            The prey in question
	 * @return <b>true</b> if the prey belongs to the world, <b>false</b>
	 *         otherwise.
	 */

	public boolean hasPrey(Prey p) {
		return preys.contains(p);
	}

	@Override
	public void reset() {
		predators.clear();
		preys.clear();

		for (int i = 0; i < predEnd.size(); i++) {
			predators.add((Predator) predEnd.get(i).copy());
		}
		for (int i = 0; i < preyEnd.size(); i++) {
			preys.add((Prey) preyEnd.get(i).copy());
		}
	}
}
