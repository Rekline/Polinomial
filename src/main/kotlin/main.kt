import gui.MainWindow
import math.polinomial.Lagrange
import math.polinomial.Newton
import kotlin.random.Random

fun main() {
    MainWindow().isVisible = true

}
// Генерация массивов случайных чисел
//    val sz = 1000
//    val xArrD = DoubleArray(sz){Random.nextDouble()*Random.nextInt(-10,10)}
//    val yArrD = DoubleArray(sz){Random.nextDouble()*Random.nextInt(-10,10)}
//    val map = mutableMapOf<Double,Double>()
//    xArrD.forEachIndexed{ i, v -> map.put(xArrD[i], yArrD[i]) }
//
//    // Подсчет времени создания полиномов
//    var timeL = System.currentTimeMillis()
//    val lagP = Lagrange(map)
//    timeL = System.currentTimeMillis() - timeL
//
//    var timeP = System.currentTimeMillis()
//    val newtP1 = Newton(map)
//    timeP = System.currentTimeMillis() - timeP
//
//    println("Лагранж 1: " + timeL)
//    println("Ньютон 1: " + timeP)
//
//    println(lagP)
//    println(newtP1)
//
//    val x = Random.nextDouble()*Random.nextInt(-10,10) + 0.1//(r.nextDouble()*r.nextInt(-10,10)+0.1)*if(r.nextBoolean())-1.0 else 1.0
//    val y = Random.nextDouble()*Random.nextInt(-10,10) + 0.1//(r.nextDouble()*r.nextInt(10)+0.1)*if(r.nextBoolean())-1.0 else 1.0
//    map.put(x,y)
//
//    timeL = System.currentTimeMillis()
//    val lagP2 = Lagrange(map)
//    timeL = System.currentTimeMillis() - timeL
//
//    timeP = System.currentTimeMillis()
//    newtP1.addNode(x,y)
//    timeP = System.currentTimeMillis() - timeP
//
//    println("Лагранж 2: " + timeL)
//    println("Ньютон 2: " + timeP)
//    println(lagP2)
//    println(newtP1)

