package genetic

class Organism(val chromosome: Array[Byte]) {

	override def toString: String = {

		val sb: StringBuilder = new StringBuilder

		for (gene <- chromosome) {
			if (gene == 1) { sb.append("1") } else { sb.append("0") }
		}

		sb.toString
	}
}
