package genetic

import java.util
import java.util.Collections
import java.util.concurrent.{Executors, TimeUnit}

import genetic.ObjectiveType.ObjectiveType

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

abstract class Evaluator(val objectiveType: ObjectiveType) {

	def evaluate(population: Population, objectiveType: ObjectiveType): EvaluatedPopulation = {
		val numThreads = Runtime.getRuntime.availableProcessors() * 2
		val pool = Executors.newFixedThreadPool(numThreads)
		implicit val ec: ExecutionContextExecutor = ExecutionContext.fromExecutor(pool)

		val resultList = Collections.synchronizedList(new util.LinkedList[(Organism, Double)]())

		population.population foreach { organism =>
			ec.execute(new Runnable {
				override def run(): Unit = {
					val fitnessValue = fitness(organism)
					resultList.add((organism, fitnessValue))
				}
			})
		}

		pool.shutdown()
		pool.awaitTermination(Long.MaxValue, TimeUnit.SECONDS)
		pool.shutdown()

		new EvaluatedPopulation(resultList, objectiveType)
	}

	protected def fitness(organism: Organism): Double
}
