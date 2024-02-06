package com.metacto.core.utils.extensions

import kotlin.math.abs
import kotlin.random.Random

inline fun <T> Iterable<T>.firstOrDefault(default: () -> T, predicate: (T) -> Boolean): T {
    return firstOrNull(predicate) ?: default()
}

fun List<Float>.closest(targetValue: Float) = minByOrNull {
    abs(targetValue - it)
}

fun List<Int>.closest(targetValue: Int) = minByOrNull {
    abs(targetValue - it)
}

inline fun <T> Iterable<T>.contains(predicate: (T) -> Boolean): Boolean {
    return find(predicate) != null
}

inline fun <T> Array<T>.contains(predicate: (T) -> Boolean): Boolean {
    return find(predicate) != null
}

fun <T> List<T>.addIfNotExist(item: T?): List<T> {
    val newList = this.toMutableList()
    if (item != null && newList.contains(item).not()) {
        newList.add(item)
    }
    return newList
}

fun <T> List<T>.addList(list: List<T>): List<T> {
    val newList = this.toMutableList()
    newList.addAll(list)
    return newList
}

fun <T> List<T>.hasIndex(index: Int): Boolean {
    return index >= 0 && index < this.size
}

fun <T> List<T>.addIfNotNull(item: T?): List<T> {
    val newList = this.toMutableList()
    if (item != null) {
        newList.add(item)
    }
    return newList
}

infix fun <T> List<T>.equalsIgnoreOrder(other: List<T>) =
    this.size == other.size && this.toSet() == other.toSet()

fun <T> List<T>.toArrayList(): ArrayList<T> {
    val arrayList = ArrayList<T>(this.size)
    this.forEach { arrayList.add(it) }

    return arrayList
}

fun <T> MutableList<T>.removeIfPossible(index: Int): T? {
    return if (this.hasIndex(index)) this.removeAt(index) else null
}

inline fun <T> Iterable<T>.sumOf(selector: (T) -> Float): Float {
    var sum = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun <T> List<T>.secondOrNull(): T? = try {
    this[1]
} catch (e: Throwable) {
    null
}

fun <T> List<T>.thirdOrNull(): T? = try {
    this[2]
} catch (e: Throwable) {
    null
}

fun <T> List<T>?.isNotNullOrEmpty(): Boolean = isNullOrEmpty().not()

@Suppress("UNCHECKED_CAST")
inline fun <T, reified R> List<T>.mapToArray(transform: (T) -> R): Array<R> {
    val array = arrayOfNulls<R>(this.size)
    this.forEachIndexed { index, item ->
        array[index] = transform(item)
    }
    return array as Array<R>
}

@Suppress("UNCHECKED_CAST")
inline fun <T, reified R> Array<T>.mapToArray(transform: (T) -> R): Array<R> {
    val array = arrayOfNulls<R>(this.size)
    this.forEachIndexed { index, item ->
        array[index] = transform(item)
    }
    return array as Array<R>
}

fun <T> List<T>.createAdjacentPairs(): List<Pair<T, T?>> {
    val result = mutableListOf<Pair<T, T?>>()
    for (i in indices step 2) {
        val pair = this[i] to this.getOrNull(i + 1)
        result.add(pair)
    }
    return result
}

fun <T> T?.toListOrEmpty(): List<T> {
    return if (this == null) emptyList() else listOf(this)
}

fun <T> Array<T>.random(): T {
    val random = Random(this.size)
    val index = random.nextInt(size - 1)
    return this[index]
}

fun <T> List<T>.takeIfNotEmpty() = this.takeIf { it.isNotEmpty() }
