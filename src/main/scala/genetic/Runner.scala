package genetic

object Runner extends Evolver {

	def main(args: Array[String]): Unit = {
		val evaluator = new Evaluator
		val candidate = "0101010101010101010101010101010101010101010101010101010101010101"
		evaluator.load(candidate)

		val population = new Population(50, 100)
		population.populate()

		val solution: Organism = run(candidate, evaluator, population)

		println("\ncandidate:  " + candidate)
		println("solution:   " + solution)
	}
}
