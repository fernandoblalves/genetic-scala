package genetic

object ObjectiveType extends Enumeration {

	type ObjectiveType = Value

	val maximization: Value = Value("max")
	val minimization: Value = Value("Min")
}
