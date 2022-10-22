package math.polinomial

class Lagrange constructor(val coeffMap : MutableMap<Double, Double>) : Polynomial() {

    init{
        val xList = coeffMap.keys.toList()
        val yList = coeffMap.values.toList()
        val lagrangeP = Polynomial(0.0)
        yList.forEachIndexed { i, y -> lagrangeP += fundPoly(i, xList) * y}
        _coeff.clear()
        _coeff.addAll(lagrangeP.coeff)
    }

    private fun fundPoly(j : Int, xList : List<Double>) : Polynomial
    {
        var res = Polynomial(1.0)
        xList.forEachIndexed{ index, x ->
            if (index != j) {
                res *= Polynomial(-x / (xList[j] - x), 1.0 / (xList[j] - x))
            }
        }
        return res
    }
}