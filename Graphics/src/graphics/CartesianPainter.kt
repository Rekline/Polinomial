package graphics

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
        val x0 = converter.xCrtToScr(0.0)
        val y0 = converter.yCrtToScr(0.0)
        g.color = Color.BLACK
        g.drawLine(0, y0, width, y0)
        g.drawLine(x0,0,x0, height)
    }

    private fun paintMarkup(g: Graphics){
        val x0 = converter.xCrtToScr(0.0)
        val y0 = converter.yCrtToScr(0.0)
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

        val X_0= converter.xCrtToScr(0.0)
        val Y_0 = converter.yCrtToScr(0.0)
        //g.d
    }

    private fun paintLabel(g:Graphics){
        val x0 = converter.xCrtToScr(0.0)
        val y0 = converter.yCrtToScr(0.0)
        g.color= Color.BLUE
        for (i in  (xMin*10).toInt()..(xMax*10).toInt()) {
            if (i % 5 != 0 || i == 0) continue
            val x = converter.xCrtToScr(i / 10.0)
            val d :Int
            if (i>0)
                d = 20
            else
                d=-10
            g.drawString((i/10.0).toString(),x-10,y0+d)
        }
        for (i in  (yMin*10).toInt()..(yMax*10).toInt()) {
            if (i % 5!=0) continue;
            if (i==0) continue;
            val y = converter.yCrtToScr(i / 10.0)
            val d:Int
            if (i>0)
                d=5
            else
                d=-30
            g.drawString((i/10.0).toString(),x0+d,y+5)
        }
    }

    override fun paint(g: Graphics?) {
        if (g != null) {
            paintAxes(g)
            paintMarkup(g)
            //paintLabel(g)
        }
    }
}