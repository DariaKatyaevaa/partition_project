class Partition(val sequence: List<Int> = listOf()) {
    val sum: Int
        get() = this.sequence.sum()

    // this >= p
    fun shifting(p: Partition): Pair<Partition, String> {
        // поиск последней допустимой горки
        var lastAcceptablePitIndex = 0
        for (i in 1 until sequence.size) {
            val diff = sequence[i] - p.sequence[i]
            if (diff < 0 && sequence[i - 1] > sequence[i]) {
                lastAcceptablePitIndex = i
            }
        }

        // поиск ближайшей слева горки
        var leftNearestSlideIndex = 0
        for (i in lastAcceptablePitIndex - 1 downTo 0) {
            if (sequence[i] > sequence[lastAcceptablePitIndex] && sequence[i] > 1) {
                leftNearestSlideIndex = i
                break
            }
        }

        // сдвиг блока
        val newSequence = List(sequence.size) { i ->
            when (i) {
                lastAcceptablePitIndex -> sequence[i] + 1
                leftNearestSlideIndex -> sequence[i] - 1
                else -> sequence[i]
            }
        }

        val transformation = "Shift block from index $leftNearestSlideIndex to $lastAcceptablePitIndex"
        return Pair(Partition(newSequence), transformation)
    }

    // содержит ли текущее разбиение ямку относительно разбиения p
    fun containsPit(p: Partition): Boolean {
        for (i in sequence.indices) {
            if (sequence[i] - p.sequence[i] < 0) {
                return true
            }
        }
        return false
    }

    // this >= p
    fun removingLastTopBlock(p: Partition): Pair<Partition, String> {
        // поиск последней горки
        var lastSlideIndex = 0
        for (i in sequence.indices) {
            val diff = sequence[i] - p.sequence[i]
            if (diff > 0) {
                lastSlideIndex = i
            }
        }

        // удаление верхнего блока последней горки
        val newSequence = sequence.mapIndexed { i, value ->
            if (i == lastSlideIndex) (value - 1) else value
        }

        val transformation = "Remove top block from index $lastSlideIndex"
        return Pair(Partition(newSequence), transformation)
    }

    companion object Action {
        // уравнивание длины разбиений добавлением нулей в конец разбиения меньшей длины
        fun equalizeSequenceLengths(a: Partition, b: Partition): Pair<Partition, Partition> {
            if (a.sequence.size == b.sequence.size) {
                return Pair(a, b)
            } else if (a.sequence.size > b.sequence.size) {
                return Pair(a, equalizeSequence(a, b))
            }
            return Pair(equalizeSequence(b, a), b)
        }

        private fun equalizeSequence(a: Partition, b: Partition): Partition {
            val sequence = List(a.sequence.size) { i ->
                if (i < b.sequence.size) b.sequence[i] else 0
            }
            return Partition(sequence)
        }
    }
}