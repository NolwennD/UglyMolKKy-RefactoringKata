package ugly_molkky;

import scala.collection.mutable.ArrayBuffer

class Molkky {

  private var score: Int = 0
  private var fails: Int = 0
  private var overFlow: Boolean = false
  private var state: String = null
  private var running: Boolean = true
  private var duplicate: Boolean = false

  def getScore(): Int = score

  def shoot(pinValue: Int*): Unit = {

    if (state == "WON") {
      state = "GAME ALREADY WON"
    }

    if (!running && fails == 3) {
      state = "GAME ALREADY LOST"
    } else {

      if (pinValue.nonEmpty && running) {

        val tmpPins = ArrayBuffer[Int]()
        duplicate = false

        for (pin <- pinValue) {
          if (!tmpPins.contains(pin) && pin < 13 && pin >= 1) {
            tmpPins += pin
          }
        }

        if (tmpPins.size != pinValue.length) {
          duplicate = true
        }

        if ((score + tmpPins.size) > 50) {
          overFlow = true
          score = 25
        } else {
          overFlow = false
          score += (if (duplicate) tmpPins.size else pinValue.length)
        }

        if (overFlow) {
          state = "SCORE OVERFLOW"
        }

        fails -= 1

        if (pinValue.length == 1) {
          if (((score + pinValue.head) - pinValue.length) < 51) {
            score +=
              (if (pinValue.head > 0 && pinValue.head < 13) pinValue.head - 1
              else 0)
            overFlow = false
          } else {
            score = 25
            overFlow = true
          }
        }

        if (tmpPins.size == 1 && duplicate) {
          val pin = tmpPins.head
          if (50 > ((score + pin) - (tmpPins.size + 1))) {
            score +=
              (if (pin < 1 || pin > 12) -1
              else pin - 1)
            overFlow = false
          } else {
            score = 25
            overFlow = true
          }

          if (overFlow) {
            state = "SCORE OVERFLOW"
          }
        }

        if (score == 50) {
          state = "WON"
          running = false
        }

      } else {

        if (fails < 1) {
          fails = 0
        }

        if (fails > 1) {
          state = "LOST"
          running = false
        }

        fails += 1
      }
    }
  }

  def getState(): String = {
    if (state == null || state == "SCORE OVERFLOW") {
      state = "RUNNING"
    }
    state
  }
}
