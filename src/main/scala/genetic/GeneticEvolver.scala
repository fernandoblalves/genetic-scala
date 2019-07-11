package genetic

class GeneticEvolver(chromosomeSize: Int, numIterations: Int = 100, populationSize: Int = 100, targetValue: Double = 1.0){

	def run(evaluator: Evaluator): Organism = {
		var pop = new Population(chromosomeSize, populationSize)
		var fittest: Organism = null
		var fitness: Double = 0.0
		val evolver: Evolver = new Evolver(chromosomeSize, populationSize)

		for(iteration <- 1 to numIterations) {
			val (org, value) = evaluator.fittest(pop)
			fittest = org
			fitness = value

			println(f"generation: $iteration%03d chromosome: $fittest%s fitness: $fitness%2.2f")

			if (fitness == targetValue)
				return fittest
			else
				pop = evolver.evolve(pop, elitist = true, evaluator)
		}
		fittest
	}
}
