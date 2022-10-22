package graphics

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class GraphicsPanel : JPanel() {

    var painters: MutableList<Painter> = mutableListOf()

    fun addPainter(painter: Painter){
        painters.add(painter)
    }

    fun removePainter(painter: Painter){
        painters.remove(painter)
    }

    fun addPainter(painters: List<Painter>){
        this.painters.addAll(painters)
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let{ gr ->
            painters.forEach { it.paint(gr) }
        }
    }
}