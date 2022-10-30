package gui

import graphics.CartesianPainter
import graphics.Converter
import graphics.GraphicsPanel
import math.polinomial.Newton
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class MainWindow : JFrame() {
    val jsXMin: JSpinner
    val jsXMax: JSpinner
    val jsYMin: JSpinner
    val jsYMax: JSpinner
    val nmXMin: SpinnerNumberModel
    val nmXMax: SpinnerNumberModel
    val nmYMin: SpinnerNumberModel
    val nmYMax: SpinnerNumberModel
    val lblXMin: JLabel
    val lblXMax: JLabel
    val lblYMin: JLabel
    val lblYMax: JLabel
    val pnlColor1: JPanel
    val mainPanel: GraphicsPanel
    val minSz = Dimension(600, 450)
    val converter: Converter
    val newtonPol: Newton

    init{
        defaultCloseOperation = EXIT_ON_CLOSE
        minimumSize = minSz
        nmXMin = SpinnerNumberModel(-5.0, -100.0, 4.8, 0.1)
        nmXMax = SpinnerNumberModel( 5.0, -4.8, 100.0, 0.1)
        nmYMin = SpinnerNumberModel(-5.0, -100.0, 4.8, 0.1)
        nmYMax = SpinnerNumberModel( 5.0, -4.8, 100.0, 0.1)
        jsXMin = JSpinner(nmXMin)
        jsXMax = JSpinner(nmXMax)
        jsYMin = JSpinner(nmYMin)
        jsYMax = JSpinner(nmYMax)
        jsXMax.addChangeListener{ _ -> nmXMin.maximum = nmXMax.value as Double - 2.0 * nmXMax.stepSize as Double}
        jsXMin.addChangeListener{ _ -> nmXMax.minimum = nmXMin.value as Double + 2.0 * nmXMin.stepSize as Double}
        jsYMax.addChangeListener{ _ -> nmYMin.maximum = nmYMax.value as Double - 2.0 * nmYMax.stepSize as Double}
        jsYMin.addChangeListener{ _ -> nmYMax.minimum = nmYMin.value as Double + 2.0 * nmYMin.stepSize as Double}
        lblXMin = JLabel()
        lblXMin.text = "Xmin: "
        lblYMin = JLabel()
        lblYMin.text = "Ymin: "
        lblXMax = JLabel()
        lblXMax.text = "Xmax: "
        lblYMax = JLabel()
        lblYMax.text = "Ymax: "
        mainPanel = GraphicsPanel()
        mainPanel.background = Color.WHITE

        pnlColor1 = JPanel()
        pnlColor1.background = Color.BLUE
        pnlColor1.addMouseListener(object : MouseAdapter(){
            override fun mouseClicked(e: MouseEvent?) {
                JColorChooser.showDialog(
                    this@MainWindow,
                    "Выбор цвета полинома",
                    pnlColor1.background
                )?.let{
                    pnlColor1.background = it
                }
            }
        })

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(createSequentialGroup()
                .addGap(8)
                .addGroup(createParallelGroup()
                    .addComponent(mainPanel, GROW, GROW, GROW)
                    .addGroup(createSequentialGroup()
                        .addComponent(lblXMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsXMin, 70, SHRINK, SHRINK)
                        .addGap(16)
                        .addComponent(lblXMax, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsXMax, 70, SHRINK, SHRINK)
                    )
                    .addGroup(createSequentialGroup()
                        .addComponent(lblYMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsYMin, 70, SHRINK, SHRINK)
                        .addGap(16)
                        .addComponent(lblYMax, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsYMax, 70, SHRINK, SHRINK)
                    )
                    .addComponent(pnlColor1, 20, 20, 20)
                )
                .addGap(8)
            )

            setVerticalGroup(createSequentialGroup()
                .addGap(8)
                .addComponent(mainPanel, GROW, GROW, GROW)
                .addGap(8)
                .addGroup(
                    createParallelGroup()
                        .addComponent(lblXMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsXMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(lblXMax, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsXMax, SHRINK, SHRINK, SHRINK)
                )
                .addGroup(
                    createParallelGroup()
                        .addComponent(lblYMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsYMin, SHRINK, SHRINK, SHRINK)
                        .addComponent(lblYMax, SHRINK, SHRINK, SHRINK)
                        .addComponent(jsYMax, SHRINK, SHRINK, SHRINK)
                )
                .addComponent(pnlColor1, 20, 20, 20)
                .addGap(8)
            )
        }
        converter = Converter(jsXMin.value as Double,
            jsXMax.value as Double,
            jsYMin.value as Double,
            jsYMax.value as Double,
            mainPanel.width, mainPanel.height)
        val cartPainter = CartesianPainter(jsXMin.value as Double,
            jsXMax.value as Double,
            jsYMin.value as Double,
            jsYMax.value as Double,
            mainPanel.width, mainPanel.height, converter)

        mainPanel.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                converter.width = mainPanel.width
                converter.height = mainPanel.height
                cartPainter.converter = converter
                mainPanel.paint(mainPanel.graphics)
            }
        })

        nmXMax.addChangeListener { _ ->
            nmXMin.maximum = nmXMax.value as Double - nmXMin.stepSize.toDouble()*2.0
            converter.xEdges = Pair(nmXMin.value as Double, nmXMax.value as Double)
            cartPainter.converter = converter
            mainPanel.paint(mainPanel.graphics)
        }
        nmXMin.addChangeListener { _ ->
            nmXMax.minimum = nmXMin.value as Double + nmXMax.stepSize.toDouble()*2.0
            converter.xEdges = Pair(nmXMin.value as Double, nmXMax.value as Double)
            cartPainter.converter = converter
            mainPanel.paint(mainPanel.graphics)
        }

        nmYMax.addChangeListener { _ ->
            nmYMin.maximum = nmYMax.value as Double - nmYMin.stepSize.toDouble()*2.0
            converter.yEdges = Pair(nmYMin.value as Double, nmYMax.value as Double)
            cartPainter.converter = converter
            mainPanel.paint(mainPanel.graphics)
        }
        nmYMin.addChangeListener { _ ->
            nmYMax.minimum = nmYMin.value as Double + nmYMax.stepSize.toDouble()*2.0
            converter.yEdges = Pair(nmYMin.value as Double, nmYMax.value as Double)
            cartPainter.converter = converter
            mainPanel.paint(mainPanel.graphics)
        }

        newtonPol = Newton()

        mainPanel.addMouseListener(
            object : MouseAdapter(){
                override fun mousePressed(e: MouseEvent) {
                    newtonPol.addNode(converter.xScrToCrt(e.x), converter.yScrToCrt(e.y))
                    mainPanel.repaint()
                }
            }
        )

        mainPanel.painters.add(cartPainter)
    }



    companion object{
        val SHRINK = GroupLayout.PREFERRED_SIZE
        val GROW = GroupLayout.DEFAULT_SIZE
    }
}

// TODO: 1) Доделать класс конвертер
//       2) Сделать ещё 2 класса к конвертору: пенитер, картезианПеинтер(линия с разметкой)
//
//    val minSize = Dimension(600,450)
//
//    val btn = JButton()
//    val tf = JTextField()
//    val lblResult = JLabel()
//
//
//    init{
//        size = minSize
//        defaultCloseOperation = EXIT_ON_CLOSE
//
//        btn.apply {
//            text = "Нажми меня"
//            size = Dimension(70, 25)
//            location = Point(10,10)
//        }
//        lblResult.text = "Здесь будет текст пользователя"
//
//        val gl = GroupLayout(contentPane)
//        layout = gl
//
//        gl.setVerticalGroup(gl.createSequentialGroup()
//            .addGap(8)
//            .addComponent(tf, 25, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
//            .addGap(8, 8, Int.MAX_VALUE)
//            .addComponent(btn, 25, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
//            .addGap(8, 8, Int.MAX_VALUE)
//            .addComponent(lblResult, 25, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
//            .addGap(8)
//        )
//
//        // Default - максимум, Preferred(сплющивает) - вмещаюющий содержимое
//        gl.setHorizontalGroup(gl.createSequentialGroup()
//            .addGap(8)
//            .addGroup(gl.createParallelGroup()
//                .addComponent(tf)
//                .addGroup(gl.createSequentialGroup()
//                    .addGap(8,8,Int.MAX_VALUE)
//                    .addComponent(btn,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
//                    .addGap(8,8,Int.MAX_VALUE)
//                )
//                .addComponent(lblResult,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE)
//            )
//            .addGap(8)
//        )
//    }
//
//}