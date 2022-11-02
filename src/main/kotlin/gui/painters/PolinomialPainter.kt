package gui.painters

import graphics.Converter
import graphics.Painter
import math.polinomial.Newton
import java.awt.Color
import java.awt.Graphics
import kotlin.math.max

class PolinomialPainter(polinomial: Newton, converter: Converter): Painter {

    private val polynomial: Newton

    var colorPolinomial: Color = Color.BLACK

    var colorPoints: Color = Color.BLUE

    var colorDerivative: Color = Color.GREEN
    var isPolinomialVisible: Boolean = true
        get() = field
        set(value) {field = value}

    var isPointsVisible: Boolean = true
        get() = field
        set(value) {field = value}

    var isDerivativeVisible: Boolean = true
        get() = field
        set(value) {field = value}
    override var height: Int = 1
        get() = field
        set(value) {field = max(1,value) }

    override var width: Int = 1
        get() = field
        set(value) {field = max(1,value) }

    public var converter: Converter = Converter(0.0,0.0,0.0,0.0,0,0)
        get() = field
        set(value) {
            field = value
            this.height = value.height
            this.width = value.width
        }

    init {
        this.converter = converter
        this.polynomial = polinomial
    }

    private fun paintPolinomial(g: Graphics)
    {
        if (isPolinomialVisible == true)
        {
            g.color = colorPolinomial
            for (i in 0 .. converter.width)
            {
                val y1 = converter.yCrtToScr(polynomial(converter.xScrToCrt(i)))
                val y2 = converter.yCrtToScr(polynomial(converter.xScrToCrt(i+1)))
                g.drawLine(i,y1,i+1,y2)
            }
        }
    }
    private fun paintPoint(g: Graphics)
    {
        if (isPointsVisible == true)
        {
            g.color = colorPoints
            for(i in 0 until polynomial.xList.size){
                g.fillOval(converter.xCrtToScr(polynomial.xList[i]) - 2,
                    converter.yCrtToScr(polynomial.yList[i]) - 2,
                    5,
                    5)
            }
        }
    }

    private fun paintDerivative(g: Graphics)
    {
        if (isDerivativeVisible == true)
        {
            g.color = colorDerivative
            val dPolynomial = polynomial.getDerivative()
            for (i in 0 .. converter.width)
            {
                val y1 = converter.yCrtToScr(dPolynomial(converter.xScrToCrt(i)))
                val y2 = converter.yCrtToScr(dPolynomial(converter.xScrToCrt(i+1)))
                g.drawLine(i,y1,i+1,y2)
            }
        }
    }

    override fun paint(g: Graphics?)
    {
        if (g != null){
            paintPoint(g)
            if (polynomial.order > 0){
                paintPolinomial(g)
                paintDerivative(g)
            }
        }
    }
}
