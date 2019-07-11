package genetic

import java.util
import java.util.{Collections, Comparator}
import java.util.concurrent.{Executors, TimeUnit}

import genetic.ObjectiveType.ObjectiveType

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

abstract class Evaluator(objectiveType: ObjectiveType) {

	/**
		* Return the fittest organism in a population
		*
	def fittest(population: Population): Organism = {
		var fittest: Organism = population.pop(0)
		var fittestFitness: Double = fitness(fittest)

		for (i <- 1 to population.size - 2) {
			val nextOrganism = population.pop(i)
			val nextFitness = fitness(nextOrganism)

			if (nextFitness > fittestFitness) {
				fittest = nextOrganism
				fittestFitness = nextFitness
			}
		}

		fittest
	}
		*/

	def fittest(population: Population): (Organism, Double) = {
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

		resultList.sort(new Comparator[(Organism, Double)] {
			override def compare(t: (Organism, Double), t1: (Organism, Double)): Int = {
				if(objectiveType == ObjectiveType.maximization) {
					if (t._2 > t1._2) return -1
					if (t._2 < t1._2) return 1
					0
				} else { //if(objectiveType == ObjectiveType.minimization) {
					if (t._2 > t1._2) return 1
					if (t._2 < t1._2) return -1
					0
				}
			}
		})

		pool.shutdown()
		pool.awaitTermination(Long.MaxValue, TimeUnit.SECONDS)
		pool.shutdown()

		resultList.get(0)
	}

	/**
		* Calculate an organism's fitness by comparing it to the optimal solution
		*/
	def fitness(organism: Organism): Double
}
