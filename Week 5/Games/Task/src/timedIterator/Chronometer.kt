package timedIterator

import java.awt.event.ActionEvent
import java.text.DecimalFormat
import javax.swing.Timer

class Chronometer : TimedIterator {
    private var seconds = 0
    private var minutes = 0
    private var hours = 0
    override val timer = Timer(1000, this)

    override fun start() {
        timer.start()
    }

    override fun isRunning() = timer.isRunning

    override fun stop() {
        timer.stop()
    }

    override fun actionPerformed(e: ActionEvent?) {
        seconds++
        if (seconds == 60) {
            minutes++
            seconds = 0
            if (minutes == 60) {
                hours++
                minutes = 0
                if (hours == 60) hours = 0
            }
        }
    }

    override fun toString(): String {
        val f = DecimalFormat("00")
        return "Time: ${f.format(hours)}:${f.format(minutes)}:${f.format(seconds)}"
    }

}