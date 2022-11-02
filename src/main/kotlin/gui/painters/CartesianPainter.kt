package gui.painters

import graphics.Converter
import graphics.Painter
import java.awt.Color
import java.awt.Graphics
import kotlin.math.max

class CartesianPainter(xMin: Double,
                       xMax: Double,
                       yMin: Double,
                       yMax: Double,
                       width: Int,
                       height: Int,
                        converter: Converter
) : Painter {

    override var height: Int = 1
        get() = field
        set(value) {field = max(1,value)}

    override var width: Int = 1
        get() = field
        set(value) {field = max(1,value)}

    private var xMin = 0.0
    private var xMax = 0.0
    private var yMin = 0.0
    private var yMax = 0.0

    public var converter: Converter = Converter(0.0,0.0,0.0,0.0,0,0)
        get() = field
        set(value) {
            field = value
            this.height = value.height
            this.width = value.width
            this.xMin = value.xEdges.first
            this.xMax = value.xEdges.second
            this.yMin = value.yEdges.first
            this.yMax = value.yEdges.second
        }

    init {
        this.converter = converter
    }

    private fun paintAxes(g:Graphics){
        var x0 = converter.xCrtToScr(0.0)
        var y0 = converter.yCrtToScr(0.0)
        if (yMax <= 0)
            y0 = 0
        else if (yMin >= 0)
            y0 = height-1
        if(xMax <= 0)
            x0 = width-1
        else if(xMin >= 0)
            x0 = 0
        g.color = Color.BLACK
        g.drawLine(0, y0, width, y0)
        g.drawLine(x0,0,x0, height)
    }

    private fun paintMarkup(g: Graphics){

        if (yMax <= 0)
            paintXMarkups(0, g)
        else if (yMin >= 0)
            paintXMarkups(height,g)
        else
            paintXMarkups(converter.yCrtToScr(0.0),g)

        if(xMax <= 0)
            paintYMarkups(width-1, g)
        else if (xMin >= 0)
            paintYMarkups(0, g)
        else
            paintYMarkups(converter.xCrtToScr(0.0), g)
    }

    private fun paintXMarkups(y0: Int, g: Graphics)
    {
        for( i in (xMin*10).toInt()..(xMax*10).toInt())
        {
            g.color = Color.BLUE
            var div = 2;
            if ((i % 5)!=0) {
                div += 1;
                g.color=Color.RED
            }
            if ((i % 10)!=0) {
                div += 1;
                g.color=Color.BLACK
            }
            val x=converter.xCrtToScr(i/10.0)
            g.drawLine(x, y0 - div, x, y0 + div);
        }
    }
    private fun paintYMarkups(x0: Int, g: Graphics)
    {
        for (i in (yMin*10).toInt()..(yMax*10).toInt()){
            g.color= Color.BLUE
            var div = 2;
            if ((i % 5)!=0) {
                div += 1;
                g.color=Color.RED
            }
            if ((i % 10)!=0) {
                div += 1;
                g.color=Color.BLACK
            }
            val y = converter.yCrtToScr(i / 10.0)
            g.drawLine( x0-div, y, x0+div, y);
        }
    }
    private fun paintLabel(g:Graphics){
        var x0 = converter.xCrtToScr(0.0)
        var y0 = converter.yCrtToScr(0.0)
        if (yMax <= 0)
            y0 = 0
        else if (yMin >= 0)
            y0 = height-1
        if(xMax <= 0)
            x0 = width-1
        else if(xMin >= 0)
            x0 = 0

        g.color= Color.BLUE
        for (i in  (xMin*10).toInt()..(xMax*10).toInt()) {
            if (i % 5 != 0 || i == 0) continue
            val x = converter.xCrtToScr(i / 10.0)
            var d :Int
            if (i > 0) d = 20
            else d=-10
            if (yMax < 0) d = 20
            else if (yMin > 0) d = -10
            g.drawString((i/10.0).toString(),x-10,y0+d)
        }
        for (i in  (yMin*10).toInt()..(yMax*10).toInt()) {
            if (i % 5!=0) continue;
            if (i==0) continue;
            val y = converter.yCrtToScr(i / 10.0)
            var d:Int
            if (i>0) d=10
            else d=-30
            if (xMax < 0) d = -30
            else if (xMin > 0) d = 10
            g.drawString((i/10.0).toString(),x0+d,y+5)
        }
    }

    override fun paint(g: Graphics?) {
        if (g != null) {
            paintAxes(g)
            paintMarkup(g)
            paintLabel(g)
        }
    }
}