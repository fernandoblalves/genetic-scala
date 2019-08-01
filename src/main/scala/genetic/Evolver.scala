package genetic

class Evolver(chromosomeSize: Int, populationSize: Int, mutationRate: Double = 0.015, mixingRatio: Double = 0.5) {

	/**
		* Evolve the population by crossover and mutation
		* @param elitist If true, the fittest organism passes to the next generation
		*/
	def evolve(population: EvaluatedPopulation, elitist: Boolean): Population = {
		val nextGeneration = new Population(chromosomeSize, populationSize)

		var offset = 0

		if (elitist) {
			val (eliteOrganism, _) = population.population.head
			nextGeneration.addOrganism(0, mutate(eliteOrganism))
			offset += 1
		}

		for(index <- offset until population.size) {
			val parent1: Organism = select(population)
			val parent2: Organism = select(population)
			val child: Organism = crossover(parent1, parent2)

			nextGeneration.addOrganism(index, mutate(child))
		}

		nextGeneration
	}

	/**
		* Mutate an organism with a random rate of 0.015
		*/
	def mutate(organism: Organism): Organism = {
		val c: Array[Byte] = organism.chromosome

		for(index <- c.indices) {

			if (Math.random <= mutationRate) {
				c(index) = Math.round(Math.random).toByte
			}
		}

		new Organism(c)
	}

	/**
		* Create a child organism from two parents
		*/
	def crossover(parent1: Organism, parent2: Organism): Organism = {
		val chromosome = new Array[Byte](parent2.chromosome.length)

		var index: Integer = 0
		for (gene <- parent1.chromosome) {

			if (Math.random <= mixingRatio) {
				chromosome(index) = gene
			} else {
				chromosome(index) = parent2.chromosome(index)
			}
			index += 1
		}

		new Organism(chromosome)
	}

	/**
		* Select an organism from the population using stochastic universal sampling
		*/
	def select(population: EvaluatedPopulation): Organism = {
		val numberOfRounds = 10

		scala.util.Random.shuffle(population.population).take(numberOfRounds).sortWith {(a, b) =>
			if (population.objectiveType == ObjectiveType.minimization) {
				a._2 < b._2
			} else { //maximization
				a._2 > b._2
			}
		}.head._1
	}
}
