package gui.painters

import graphics.Converter
import graphics.Painter
import java.awt.Color
import java.awt.Graphics
import kotlin.math.max

class PointPainter(
    converter: Converter): Painter {

    var colorPoints: Color = Color.BLUE

    var xList: MutableList<Double> = mutableListOf()
    var yList: MutableList<Double> = mutableListOf()

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

    var isVisible: Boolean = true
        get() = field
        set(value) {field = value}

    init {
        this.converter = converter
    }

    private fun paintPoint(g: Graphics)
    {
        if (isVisible == true)
        {
            g.color = colorPoints
            for(i in 0 until xList.size){
                g.fillOval(converter.xCrtToScr(xList[i]) - 2,
                    converter.yCrtToScr(yList[i]) - 2,
                    5,
                    5)
            }
        }
    }

    override fun paint(g: Graphics?)
    {
        if (g != null){
            paintPoint(g)
        }
    }
}