import java.util.*

fun findShortestTransformations(start: Partition, target: Partition): List<List<String>> {
    val (startEq, targetEq) = Partition.equalizeSequenceLengths(start, target)
    val initialState = Triple(startEq, startEq.sum - targetEq.sum, emptyList<String>())
    val queue: Queue<Triple<Partition, Int, List<String>>> = LinkedList()
    queue.add(initialState)
    val visited = mutableSetOf<Pair<Partition, Int>>()
    val solutions = mutableListOf<List<String>>()

    while (queue.isNotEmpty()) {
        val (partition, C, path) = queue.poll()
        val state = Pair(partition, C)

        if (visited.contains(state)) continue
        visited.add(state)

        if (partition.sequence == targetEq.sequence && C == 0) {
            solutions.add(path)
            continue
        }

        if (partition.containsPit(targetEq)) {
            val transformation = partition.shifting(targetEq);
            val newPartition = transformation.first
            val newPath =  path + "${transformation.second}: ${newPartition.sequence}"
            queue.add(Triple(newPartition, C, newPath))
        }

        if (C > 0) {
            val transformation = partition.removingLastTopBlock(targetEq)
            val newPartition = transformation.first
            val newPath = path + "${transformation.second}: ${newPartition.sequence}"
            queue.add(Triple(newPartition, C - 1, newPath))
        }
    }

    return solutions
}

fun main() {
//    val start = Partition(listOf(5, 4, 1))
//    val target = Partition(listOf(4, 3, 2, 1))
    val start = Partition(listOf(14, 9, 7, 6, 1, 1))
    val target = Partition(listOf(10, 10, 6, 3, 3, 1, 1, 1))
    val solutions = findShortestTransformations(start, target)
    for (solution in solutions) {
        println(solution.joinToString("\n"))
        println("length: ${solution.size}")
        println("\n\n")
    }
}