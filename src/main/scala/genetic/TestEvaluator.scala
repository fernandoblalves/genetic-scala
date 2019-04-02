package genetic

class TestEvaluator extends Evaluator {

	def fitness(organism: Organism): Double = {
		val score: Double = organism.chromosome.map(_.toInt).sum

		score / organism.chromosome.length
	}
}
