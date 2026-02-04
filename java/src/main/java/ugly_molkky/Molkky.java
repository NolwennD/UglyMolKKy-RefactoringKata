package ugly_molkky;

import java.util.ArrayList;

public class Molkky {

  private int score;
  private int fails;
  private boolean overFlow = false;
  private String state;
  private boolean running = true;
  private boolean duplicate;

  public int score() {
    return score;
  }

  public void shoot(int ...pinValue) {
    if(state == "WON") {
      state = "GAME ALREADY WON";
    }
    if(!running && (fails == 3)) {
      state = "GAME ALREADY LOST";
    } else {
      if((pinValue.length >= 1)
        && running) {
        var tmpPins = new ArrayList<Integer>();
        for(var pin : pinValue) {
          if(!tmpPins.contains(pin) && (pin < 13) && (pin >= 1)) {
            tmpPins.add(pin);
          }
        }
        if(tmpPins.size() != pinValue.length) {
          duplicate = true;
        }
        if((score + tmpPins.size()) > 50) {
          overFlow = true;
          score = 25;
        } else {
          overFlow = false;
          score += duplicate ? tmpPins.size() : pinValue.length;
        }
        if(overFlow){
          state = "SCORE OVERFLOW";
        }
        fails -= 1;
        if ((pinValue.length == 1)) {
          if((((score + pinValue[0]) - pinValue.length) < 51)) {
            score += (pinValue[0] > 0) && (13 > pinValue[0]) ? pinValue[0] - 1 : 0;
            overFlow = false;
          } else {
            score = 25;
            overFlow = true;
          }
        }
        if ((tmpPins.size() == 1) && duplicate) {
          if(50 > (((score + tmpPins.get(0)) - (tmpPins.size() + 1)))) {
            score += (tmpPins.get(0) < 1) || (tmpPins.get(0) > 12) ? -1 : tmpPins.get(0) - 1;
            overFlow = false;
          } else {
            score = 25;
            overFlow = true;
          }
          if(overFlow) {
            state = "SCORE OVERFLOW";
          }
        }
        if(score == 50) {
          state = "WON";
          running = false;
        }
      } else {
        if(fails < 1) {
          fails = 0;
        }
        if(fails > 1) {
          state = "LOST";
          running = false;
        }
        fails += 1;
      }
    }
  }

  public String state() {
    if(state == null || state == "SCORE OVERFLOW") {
      state = "RUNNING";
    }

    return state;
  }
}
