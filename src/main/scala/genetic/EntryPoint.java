package genetic;

public class EntryPoint {

	public static void main(String[] args){
		GeneticEvolver ge = new GeneticEvolver(100, 100, 100);
		Evaluator evaluator = new NetworkEvaluator();
		Organism best = ge.run(evaluator);
	}
}
