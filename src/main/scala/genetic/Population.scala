package genetic

import util.Random

class Population(populationSize: Int, val chromosomeSize: Int, mutationRate: Double = 0.015, mixingRatio: Double = 0.5) {
	var pop: Array[Organism] = _

	private def this(populationSize: Int) = {
		this(populationSize, 100)
	}

	/**
		* Create the space for the population but don't populate
		*/
	def initialise(): Unit = {
		pop = new Array[Organism](populationSize + 1)
	}

	/**
		* Populate with organisms
		*/
	def populate(): Unit = {
		pop = new Array[Organism](populationSize + 1)

		for( i <- 0 until populationSize) {

			val bytes = new Array[Byte](chromosomeSize)

			for( j <- 0 until chromosomeSize) {
				bytes(j) = Math.round(Math.random).toByte
			}

			val organism = new Organism(bytes)
			pop(i) = organism
		}
	}

	/**
		* Return the population size
		*/
	def size: Integer = {
		pop.length
	}

	override def toString: String = {
		val sb: StringBuilder = new StringBuilder

		sb.append("[")
		for (organism <- pop) {
			sb.append(organism + ", ")
		}
		sb.dropRight(2)
		sb.append("]")

		sb.toString
	}

	/**
		* Add an organism to a particular location in a population
		*/
	def addOrganism(index: Integer, organism: Organism): Unit = {
		pop(index) = organism
	}

	/**
		* Evolve the population by crossover and mutation
		* @param elitist If true, the fittest organism passes to the next generation
		* @param evaluator The evaluator to use
		*/
	def evolve(elitist: Boolean, evaluator: Evaluator): Population = {
		val nextGeneration = new Population(pop.length)
		nextGeneration.initialise()

		var offset = 0

		if (elitist) {
			val eliteOrganism = evaluator.fittest(this)
			nextGeneration.addOrganism(0, mutate(eliteOrganism))
			offset += 1
		}

		for(index <- offset to pop.length) {
			val parent1: Organism = select(evaluator)
			val parent2: Organism = select(evaluator)
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
		val otherParentGenes = parent2.chromosome
		val chromosomes = new Array[Byte](otherParentGenes.length)

		var index: Integer = 0
		for (gene <- parent1.chromosome) {

			if (Math.random <= mixingRatio) {
				chromosomes(index) = gene
			} else {
				chromosomes(index) = otherParentGenes(index)
			}
			index += 1
		}

		new Organism(chromosomes)
	}

	/**
		* Select an organism from the population using stochastic universal sampling
		*/
	def select(evaluator: Evaluator): Organism = {
		val numberOfRounds = 10

		val tournament = new Population(numberOfRounds)
		tournament.initialise()

		for (i <- 0 to numberOfRounds) {
			val randomOrganism = pop(Random.nextInt(populationSize))
			tournament.addOrganism(i, randomOrganism)
		}

		evaluator.fittest(tournament)
	}
}
