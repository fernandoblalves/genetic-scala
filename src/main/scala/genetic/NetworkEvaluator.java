package genetic;

import scala.Enumeration;

public class NetworkEvaluator extends Evaluator {

	NetworkEvaluator(Enumeration.Value objectiveType) {
		super(objectiveType);
	}

	@Override
	public double fitness(Organism organism) {
		double sum = 0;
		for(Byte b: organism.chromosome()){
			sum += b.intValue();
		}
		return sum / organism.chromosome().length;
	}
}
