package genetic;

public class NetworkEvaluator extends Evaluator {

	@Override
	public double fitness(Organism organism) {
		double sum = 0;
		for(Byte b: organism.chromosome()){
			sum += b.intValue();
		}
		return sum / organism.chromosome().length;
	}
}
