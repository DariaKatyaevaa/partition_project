import kotlin.random.Random

/**
 * алгоритм строит некоторые кратчайшие последовательности
 * длины height(p1, p2) элементарных преобразований от разбиения p1 до разбиения p2 в случае, когда
 * p1 ≥ p1.
 */
fun searchShortestSequence(p1: Partition, p2: Partition) {
    val pair = Partition.equalizeSequenceLengths(p1, p2)
    var a = pair.first
    val b = pair.second
    var c = a.sum - b.sum
    var isContainsPit = a.containsPit(b)

    var numberOfTransformations = 0

    while (c > 0 || isContainsPit) {
        if (isContainsPit) {
            val transformation = a.shifting(b)
            a = transformation.first
            println(transformation.second)
        } else {
            c -= 1
            val transformation = a.removingLastTopBlock(b)
            a = transformation.first
            println(transformation.second)
        }
        isContainsPit = a.containsPit(b)
        numberOfTransformations += 1

        println(a.sequence)
        println()
    }

    println(numberOfTransformations) // меньше height(p1, p2)
}


fun main() {
    val p1 = Partition(listOf(14, 9, 7, 6, 1, 1))
    val p2 = Partition(listOf(10, 10, 6, 3, 3, 1, 1, 1))
    searchShortestSequence(p1, p2)
}
