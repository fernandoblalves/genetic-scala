package genetic

class GeneticEvolver(chromosomeSize: Int, numIterations: Int = 100, populationSize: Int = 100){

	def run(evaluator: Evaluator): Organism = {
		var pop = new Population(chromosomeSize, populationSize)
		var fittest: Organism = null
		val evolver: Evolver = new Evolver(chromosomeSize, populationSize)
		for(iteration <- 1 to numIterations) {
			fittest = evaluator.fittest(pop)
			val fitness = evaluator.fitness(fittest)

			println(f"generation: $iteration%03d chromosome: $fittest%s fitness: $fitness%2.2f")

			if (fitness >= 1.0)
				return fittest
			else
				pop = evolver.evolve(pop, elitist = true, evaluator)
		}
		fittest
	}
}
