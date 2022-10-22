package graphics

import java.awt.Graphics

interface Painter {
    var width: Int
    var height: Int
    fun paint(g: Graphics?)
}