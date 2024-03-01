/**
 * алгоритм строит некоторые кратчайшие последовательности
 * длины height(λ, µ) элементарных преобразований от p1 до p2 в случае, когда
 * p1 ≥ p1.
 */
fun firstAlgorithm(p1: Partition, p2: Partition) {
    val pair = Partition.equalizeSequenceLengths(p1, p2)
    var a = pair.first
    val b = pair.second
    var c = a.sum - b.sum
    var isContainsPit = a.containsPit(b)

    var numberOfTransformations = 0

    while (c > 0 || isContainsPit) {
        if (isContainsPit) {
            a = a.shifting(b)
        } else {
            c -= 1
            a = a.removingLastTopBlock(b)
        }
        isContainsPit = a.containsPit(b)
        numberOfTransformations += 1

        println(a.sequence)
    }

    println(numberOfTransformations) // меньше height(p1, p2)
}


fun main() {
    val p1 = Partition(listOf(14, 9, 7, 6, 1, 1))
    val p2 = Partition(listOf(10, 9, 6, 3, 3, 1, 1, 1))
    firstAlgorithm(p1, p2)
}
