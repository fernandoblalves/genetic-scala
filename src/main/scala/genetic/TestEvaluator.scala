package genetic

class TestEvaluator extends Evaluator {

	def fitness(organism: Organism): Double = organism.chromosome.map(_.toInt).sum / organism.chromosome.length
}
