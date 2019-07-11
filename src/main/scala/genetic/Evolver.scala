package genetic

import scala.util.Random

class Evolver(populationSize: Int, chromosomeSize: Int, mutationRate: Double = 0.015, mixingRatio: Double = 0.5) {

	/**
		* Evolve the population by crossover and mutation
		* @param elitist If true, the fittest organism passes to the next generation
		* @param evaluator The evaluator to use
		*/
	def evolve(population: Population, elitist: Boolean, evaluator: Evaluator): Population = {
		val nextGeneration = new Population(populationSize, chromosomeSize)

		var offset = 0

		if (elitist) {
			val (eliteOrganism, _) = evaluator.fittest(population)
			nextGeneration.addOrganism(0, mutate(eliteOrganism))
			offset += 1
		}

		for(index <- offset until population.size) {
			val parent1: Organism = select(population, evaluator)
			val parent2: Organism = select(population, evaluator)
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
	def select(population: Population, evaluator: Evaluator): Organism = {
		val numberOfRounds = 10

		val tournament = new Population(populationSize, chromosomeSize)

		for (i <- 0 to numberOfRounds) {
			val randomOrganism = population.population(Random.nextInt(populationSize))
			tournament.addOrganism(i, randomOrganism)
		}

		evaluator.fittest(tournament)._1
	}
}
