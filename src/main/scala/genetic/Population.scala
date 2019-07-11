package genetic

class Population(populationSize: Int, chromosomeSize: Int) {
	var population: Array[Organism] = new Array[Organism](populationSize)
	populate()

	/**
		* Populate with organisms
		*/
	def populate(): Unit = {
		population = new Array[Organism](populationSize)

		for( i <- 0 until populationSize) {

			val bytes = new Array[Byte](chromosomeSize)

			for( j <- 0 until chromosomeSize) {
				bytes(j) = Math.round(Math.random).toByte
			}

			val organism = new Organism(bytes)
			population(i) = organism
		}
	}

	/**
		* Return the population size
		*/
	def size: Integer = {
		population.length
	}

	override def toString: String = {
		val sb: StringBuilder = new StringBuilder

		sb.append("[")
		for (organism <- population) {
			sb.append(organism + ", ")
		}
		sb.dropRight(2)
		sb.append("]")

		sb.toString
	}

	/**
		* Add an organism to a particular location in a population
		*/
	def addOrganism(index: Int, organism: Organism): Unit = {
		population(index) = organism
	}
}
