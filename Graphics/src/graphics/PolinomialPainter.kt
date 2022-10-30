package graphics

import math.polinomial.Newton
import java.awt.Color
import java.awt.Graphics

class PolinomialPainter(var polinomial: Newton) {

    public fun paint(g: Graphics, conv: Converter)
    {
        g.color = Color.DARK_GRAY
        if (polinomial.order > 0){
            for (i in 0 .. conv.width)
            {
                val y1 = conv.yCrtToScr(polinomial(conv.xScrToCrt(i)))
                val y2 = conv.yCrtToScr(polinomial(conv.xScrToCrt(i+1)))
                g.drawLine(i,y1,i+1,y2)         //линия по двум точкам все пиксели в координаты X
            }
        }
    }
}
