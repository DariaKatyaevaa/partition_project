import java.util.*


fun findShortestTransformationsOpt(start: Partition, target: Partition): List<List<Pair<Partition, String>>> {
    val (startEq, targetEq) = Partition.equalizeSequenceLengths(start, target)
    val solutions = mutableListOf<List<Pair<Partition, String>>>()
    val minSteps = findMinSteps(startEq, targetEq)

    fun backtrack(partition: Partition, C: Int, path: List<Pair<Partition, String>>) {
        if (partition.sequence == targetEq.sequence && C == 0) {
            if (path.size == minSteps) {
                solutions.add(path)
            }
            return
        }

        if (path.size > minSteps) return

        if (partition.containsPit(targetEq)) {
            val (newPartition, transformation) = partition.shifting(targetEq)
            backtrack(newPartition, C, path + Pair(newPartition, transformation))
        }

        if (C > 0) {
            val (newPartition, transformation) = partition.removingLastTopBlock(targetEq)
            backtrack(newPartition, C - 1, path + Pair(newPartition, transformation))
        }
    }

    backtrack(startEq, startEq.sum - targetEq.sum, emptyList())
    return solutions
}

fun findMinSteps(start: Partition, target: Partition): Int {
    val (startEq, targetEq) = Partition.equalizeSequenceLengths(start, target)
    val initialState = Triple(startEq, startEq.sum - targetEq.sum, 0)
    val queue: Queue<Triple<Partition, Int, Int>> = LinkedList()
    queue.add(initialState)
    val visited = mutableSetOf<Pair<Partition, Int>>()

    while (queue.isNotEmpty()) {
        val (partition, C, steps) = queue.poll()
        val state = Pair(partition, C)

        if (visited.contains(state)) continue
        visited.add(state)

        if (partition.sequence == targetEq.sequence && C == 0) {
            return steps
        }

        if (partition.containsPit(targetEq)) {
            val (newPartition, _) = partition.shifting(targetEq)
            queue.add(Triple(newPartition, C, steps + 1))
        }

        if (C > 0) {
            val (newPartition, _) = partition.removingLastTopBlock(targetEq)
            queue.add(Triple(newPartition, C - 1, steps + 1))
        }
    }

    return Int.MAX_VALUE
}


fun main() {
    val start = Partition(listOf(14, 9, 7, 6, 1, 1))
    val target = Partition(listOf(10, 10, 6, 3, 3, 1, 1, 1))
    val solutions = findShortestTransformationsOpt(start, target)
    for (solution in solutions) {
        for ((partition, transformation) in solution) {
            println("$transformation: ${partition.sequence}")
        }
        println("\n")
    }
}
