package genetic

class GeneticEvolver(chromosomeSize: Int, numIterations: Int = 100, populationSize: Int = 100, targetValue: Double = 1.0){

	/**
		* Runs the genetic optimizer.
		* @param evaluator The evaluator used to evaluate the obtained organisms.
		* @param prevBest Can be null if no previous best organism is to be used.
		* @return
		*/
	def run(evaluator: Evaluator, prevBest: Organism = null): Organism = {
		var pop: Population = null
		if(prevBest == null)
			pop = new Population(chromosomeSize, populationSize)
		else
			pop = new Population(chromosomeSize, populationSize, prevBest)
		var fittest: Organism = null
		var fitness: Double = Double.MinValue
		val evolver: Evolver = new Evolver(chromosomeSize, populationSize)
		val target = evaluator.objectiveType
		if(target == ObjectiveType.minimization){
			fitness = Double.MaxValue
		}

		for(iteration <- 1 to numIterations) {
			val evaluatedPop: EvaluatedPopulation = evaluator.evaluate(pop, evaluator.objectiveType)
			val newFittest = evaluatedPop.population.head._1
			val newFitness = evaluatedPop.population.head._2

			println(f"generation: $iteration%03d chromosome: $newFittest%s fitness: $newFitness%2.5f")

			if (newFitness == targetValue){
				return newFittest
			} else {
				if(target == ObjectiveType.minimization){
					if(newFitness < fitness){
						fitness = newFitness
						fittest = newFittest
					}
				}else{ // maximization
					if(newFitness > fitness){
						fitness = newFitness
						fittest = newFittest
					}
				}
			}

			pop = evolver.evolve(evaluatedPop, elitist = true)
		}

		println
		println(f"Fittest: chromosome: $fittest%s fitness: $fitness%2.5f")
		fittest
	}
}
