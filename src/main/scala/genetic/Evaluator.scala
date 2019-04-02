package genetic

abstract class Evaluator {

	/**
		* Return the fittest organism in a population
		*/
	def fittest(population: Population): Organism = {
		var fittest: Organism = population.pop(0)

		for (i <- 1 to population.size - 2) {
			val nextOrganism = population.pop(i)

			if (fitness(nextOrganism) > fitness(fittest)) {
				fittest = nextOrganism
			}
		}

		fittest
	}

	/**
		* Calculate an organism's fitness by comparing it to the optimal solution
		*/
	def fitness(organism: Organism): Double
}
