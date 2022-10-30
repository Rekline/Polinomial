package math.polinomial

import java.lang.StringBuilder
import kotlin.math.abs
import kotlin.math.pow

open class Polynomial(vararg coeffs: Double) {
    protected val _coeff: MutableList<Double>

    val coeff: List<Double>
        get() = _coeff.toList()

    val order: Int
        get() = _coeff.size - 1

    init{
        _coeff = coeffs.toMutableList()
        for(i in _coeff.size - 1 downTo  0)
        {
            if (abs(_coeff[i]) eq 0.0 && _coeff.size > 1)
                _coeff.removeAt(i)
            else
                break
        }
    }
    constructor(): this(0.0)

    override fun toString(): String {
        val sb = StringBuilder()
        if (this.order == 0 && coeff[0] eq 0.0 )
        {
            sb.append(0)
            return sb.toString()
        }
        _coeff.asReversed().forEachIndexed{ index, d ->
            if (index > 0)
                if (d gt 0.0)
                    sb.append("+")
                else if (d neq 0.0)
                    sb.append("-")
            if (index == 0 && d lt 0.0)
                sb.append("-")

            if (abs(d) neq 1.0 && d neq 0.0 || (_coeff.size - index - 1 == 0 && abs(d) eq 1.0))
                if (d == d.toInt().toDouble())
                    sb.append(abs(d).toInt())
                else
                    sb.append(abs(d))
            else if (this.order == 0)
                sb.append(abs(d).toInt())
            if (_coeff.size - index - 1 > 1 && d neq 0.0)
            {
                sb.append("x^")
                sb.append(_coeff.size - index - 1)
            }
            else if (_coeff.size - index - 1 == 1 && d neq 0.0)
                sb.append("x")
        }
        return sb.toString()
    }

    operator fun plus(other: Polynomial): Polynomial {
        val (min, max) =
            if (order < other.order)
                arrayOf(coeff, other.coeff)
            else
                arrayOf(other.coeff, coeff)
        val res = max.toDoubleArray()
        min.forEachIndexed { i, v -> res[i] += v}
        return Polynomial(*res)
    }

    operator fun minus(other: Polynomial): Polynomial = this + other * (-1.0)

    operator fun unaryPlus() : Polynomial = Polynomial(*this.coeff.toDoubleArray())

    operator fun unaryMinus() : Polynomial = this * (-1.0)

    operator fun times(other: Polynomial): Polynomial {
        val newCoeffArr = DoubleArray(_coeff.size + other._coeff.size + 1){ 0.0 }
        coeff.forEachIndexed { ti, tc ->
            other.coeff.forEachIndexed{ oi, oc ->
                newCoeffArr[ti + oi] += tc * oc
            }
        }
        return Polynomial(*newCoeffArr)
    }

    operator fun times(scalar: Double): Polynomial {
        val res = DoubleArray(order + 1) { coeff[it] * scalar }
        return Polynomial(*res)
    }

    operator fun div(scalar: Double): Polynomial {
        if (scalar == 0.0)
            throw Exception("Divided by zero!")
        val res = DoubleArray(coeff.size) { coeff[it] / scalar}
        return Polynomial(*res)
    }

    operator fun plusAssign(other: Polynomial){
        other.coeff.forEachIndexed{ i , d ->
            if (i < _coeff.size) _coeff[i] += d else _coeff.add(d)}
    }

    operator fun minusAssign(other: Polynomial) = this.plusAssign(other * -1.0)

    operator fun timesAssign(k: Double) : Unit =
        _coeff.forEachIndexed{i, _ -> _coeff[i]*=k}

    operator fun divAssign(k: Double) : Unit =
        _coeff.forEachIndexed{i, _ -> _coeff[i]/=k}

    override operator fun equals(other: Any?): Boolean{
        if (other !is Polynomial) return false
        _coeff.forEachIndexed{i, v -> if(v neq other.coeff[i]) return false}
        return true
    }

    override fun hashCode(): Int = _coeff.hashCode()

    /** Взятие значения в точке */
    operator fun invoke(x: Double): Double {
        var p = 1.0
        return _coeff.reduce { acc, d -> p *= x; acc + d * p; }
    }

    // Второй способ в одну строчку
    //operator fun times(k: Double) =
    //    Polinomial(*_coeff.map { it * k }.toDoubleArray())
    // можно добавить конструктор по списку и написать так:
    //    Polinomial(*coeff.map { it * k })

}
