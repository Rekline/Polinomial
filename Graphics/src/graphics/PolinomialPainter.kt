package graphics

import math.polinomial.Newton
import java.awt.Color
import java.awt.Graphics
import kotlin.math.max

class PolinomialPainter(polinomial: Newton, converter: Converter): Painter {

    private val polinomial: Newton

    public var colorPolinomial: Color = Color.BLACK

    public var colorPoint: Color = Color.BLUE

    public var colorDerivative: Color = Color.BLACK
    public var isPolinomialVisible: Boolean = true
        get() = field
        set(value) {field = value}

    public var isPointVisible: Boolean = true
        get() = field
        set(value) {field = value}

    public var isDerivativeVisible: Boolean = true
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
        this.polinomial = polinomial
    }

    private fun paintPolinomial(g: Graphics)
    {
        if (isPolinomialVisible == true)
        {
            for (i in 0 .. converter.width)
            {
                g.color = colorPolinomial
                val y1 = converter.yCrtToScr(polinomial(converter.xScrToCrt(i)))
                val y2 = converter.yCrtToScr(polinomial(converter.xScrToCrt(i+1)))
                g.drawLine(i,y1,i+1,y2)
            }
        }
    }
    private fun paintPoint(g: Graphics)
    {
        if (isPointVisible == true)
            g.color = colorPoint
            for(i in 0 until polinomial.nodeX.size){
                g.fillOval(converter.xCrtToScr(polinomial.nodeX[i]) - 2,
                    converter.yCrtToScr(polinomial.nodeY[i]) - 2,
                    5,
                    5)
            }
    }

    override fun paint(g: Graphics?)
    {
        if (g != null){
            if (polinomial.order > 0){
                paintPolinomial(g)
                paintPoint(g)
            }
        }
    }
}
