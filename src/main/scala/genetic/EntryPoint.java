package genetic;

public class EntryPoint {

	public static void main(String[] args){
		GeneticEvolver ge = new GeneticEvolver(5, 200, 500, 0.0);
		Evaluator evaluator = new NetworkEvaluator(ObjectiveType.minimization());
		Organism best = ge.run(evaluator, null);
		System.out.println(best);
	}
}
