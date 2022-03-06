package pt.carvalho.apples.classifier.utilities

internal interface TimeManager {
    fun hasTimePassedSince(time: Long, amount: Long): Boolean

    fun now(): Long
}

internal class TimeManagerImpl : TimeManager {
    override fun hasTimePassedSince(time: Long, amount: Long): Boolean = now() - amount > time

    override fun now(): Long = System.currentTimeMillis()
}
