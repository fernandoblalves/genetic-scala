package genetic

import genetic.ObjectiveType.ObjectiveType

class TestEvaluator(objectiveType: ObjectiveType) extends Evaluator(objectiveType: ObjectiveType) {

	def fitness(organism: Organism): Double = organism.chromosome.map(_.toInt).sum / organism.chromosome.length
}
