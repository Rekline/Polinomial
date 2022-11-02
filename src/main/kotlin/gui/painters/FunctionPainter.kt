package gui.painters

import graphics.Converter
import graphics.Painter
import java.awt.Color
import java.awt.Graphics
import kotlin.math.max

class FunctionPainter(func: (x: Double) -> (Double), converter: Converter): Painter {

    var func: (x: Double) -> (Double)

    var color: Color = Color.BLACK

    var isVisible: Boolean = true
        get() = field
        set(value) {field = value}

    override var height: Int = 1
        get() = field
        set(value) {field = max(1,value) }

    override var width: Int = 1
        get() = field
        set(value) {field = max(1,value) }

    var converter: Converter = Converter(0.0,0.0,0.0,0.0,0,0)
        get() = field
        set(value) {
            field = value
            this.height = value.height
            this.width = value.width
        }

    init {
        this.converter = converter
        this.func = func
    }

    private fun paintPolinomial(g: Graphics)
    {
        if (isVisible == true)
        {
            g.color = color
            for (i in 0 .. converter.width)
            {
                val y1 = converter.yCrtToScr(func(converter.xScrToCrt(i)))
                val y2 = converter.yCrtToScr(func(converter.xScrToCrt(i+1)))
                g.drawLine(i,y1,i+1,y2)
            }
        }
    }

    override fun paint(g: Graphics?)
    {
        if (g != null){
            paintPolinomial(g)
        }
    }
}
