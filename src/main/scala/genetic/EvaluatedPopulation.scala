package genetic

import genetic.ObjectiveType.ObjectiveType

import scala.collection.JavaConverters._

class EvaluatedPopulation(resultList: Seq[(Organism, Double)], val objectiveType: ObjectiveType) {

	var population: Seq[(Organism, Double)] = resultList
	sort()

	def this(resultList: java.util.List[(Organism, Double)], objectiveType: ObjectiveType){
		this(asScalaIteratorConverter(resultList.iterator()).asScala.toSeq, objectiveType)
	}


	def size: Integer = {
		population.length
	}

	override def toString: String = {
		val sb: StringBuilder = new StringBuilder

		sb.append("[")
		for (organism <- population) {
			sb.append(organism + ", ")
		}
		sb.dropRight(2)
		sb.append("]")

		sb.toString
	}

	def sort(): Unit = {
		population.sortWith{(a,b) =>
			if(objectiveType == ObjectiveType.minimization){
				a._2 < b._2
			}else { //maximization
				a._2 > b._2
			}
		}
	}
}
