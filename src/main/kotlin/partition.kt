class Partition(val sequence: List<Int> = listOf()) {
    val sum: Int
        get() = this.sequence.sum()

    //this >= p
    fun shifting(p: Partition): Partition {
        //поиск последней горки
        var lastAcceptablePitIndex = 0
        for (i in 1 until sequence.size) {
            val diff = sequence[i] - p.sequence[i]
            if (diff < 0 && sequence[i - 1] > sequence[i]) {
                lastAcceptablePitIndex = i
            }
        }

        //сдвиг блока
        val sequence = List(sequence.size) { i ->
            when (i) {
                lastAcceptablePitIndex -> sequence[i] + 1
                lastAcceptablePitIndex - 1 -> sequence[i] - 1
                else -> sequence[i]
            }
        }

        return Partition(sequence)
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

    //this >= p
    fun removingLastTopBlock(p: Partition): Partition {
        //поиск последней горки
        var lastSlideIndex = 0
        for (i in sequence.indices) {
            val diff = sequence[i] - p.sequence[i]
            if (diff > 0) {
                lastSlideIndex = i
            }
        }

        //удаление верхнего блока последней горки
        val sequence = sequence.mapIndexed { i, value ->
            if (i == lastSlideIndex) (value - 1) else value
        }

        return Partition(sequence)
    }

    companion object Action {
        //уравнивание длины разбиений добавлением нулей в конец разбиения меньшей длины
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