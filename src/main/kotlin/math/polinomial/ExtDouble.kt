package math.polinomial

import kotlin.math.abs
import kotlin.math.max

/** == */
infix fun Double.eq(other: Double) = abs(this - other) < max(Math.ulp(this), Math.ulp(other) * 10)
/** != */
infix fun Double.neq(other: Double) = abs(this - other) >= max(Math.ulp(this), Math.ulp(other) * 10)
/** <= */
infix fun Double.leq(other: Double) = this < other || abs(this - other) < max(Math.ulp(this), Math.ulp(other) * 10)
/** >= */
infix fun Double.geq(other: Double) = this > other || abs(this - other) < max(Math.ulp(this), Math.ulp(other) * 10)
/** < */
infix fun Double.lt(other: Double) = this < other && abs(this - other) >= max(Math.ulp(this), Math.ulp(other) * 10)
// > */
infix fun Double.gt(other: Double) = this > other && abs(this - other) >= max(Math.ulp(this), Math.ulp(other) * 10)

// Функция сравнения ulp - последнее число мантисы?
//fun eq(t: Double, other: Double) =
//    abs(t - other) < max(Math.ulp(t), Math.ulp(other)) * 10

// Функция расширения для класса дабл(добавлять новые можно, но переопределять нельзя)
// при инфикс можно писать так: "nums eq 1.0"
//infix fun Double.eq(other: Double) =
//    abs(this - other) < max(Math.ulp(this), Math.ulp(other)) * 10