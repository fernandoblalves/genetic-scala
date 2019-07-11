package genetic;

public class EntryPoint {

	public static void main(String[] args){
		GeneticEvolver ge = new GeneticEvolver(1000, 200, 500, 0.0);
		Evaluator evaluator = new NetworkEvaluator(ObjectiveType.minimization());
		Organism best = ge.run(evaluator);
		System.out.println(best);
	}
}
