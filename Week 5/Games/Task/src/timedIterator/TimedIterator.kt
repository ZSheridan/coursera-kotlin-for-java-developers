package timedIterator

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

interface TimedIterator : ActionListener {
    val timer: Timer

    fun start()
    fun isRunning(): Boolean
    fun stop()

    override fun actionPerformed(e: ActionEvent?)
    override fun toString(): String
}