export class Molkky {
  private currentScore: number = 0;
  private fails: number = 0;
  private overFlow = false;
  private currentState: string | null = null;
  private running = true;
  private duplicate = false;

  public score() {
    return this.currentScore;
  }

  public shoot(pinValue: number[]) {
    if (this.currentState == "WON") {
      this.currentState = "GAME ALREADY WON";
    }
    if (!this.running && this.fails == 3) {
      this.currentState = "GAME ALREADY LOST";
    } else {
      if (pinValue.length >= 1 && this.running) {
        let tmpPins: number[] = [];
        for (const pin of pinValue) {
          if (!tmpPins.find((v) => v == pin) && pin < 13 && pin >= 1) {
            tmpPins.push(pin);
          }
        }
        if (tmpPins.length != pinValue.length) {
          this.duplicate = true;
        }
        if (this.currentScore + tmpPins.length > 50) {
          this.overFlow = true;
          this.currentScore = 25;
        } else {
          this.overFlow = false;
          this.currentScore += this.duplicate
            ? tmpPins.length
            : pinValue.length;
        }
        this.fails -= 1;
        if (pinValue.length == 1) {
          if (this.currentScore + pinValue[0] - pinValue.length < 51) {
            this.currentScore +=
              pinValue[0] > 0 && 13 > pinValue[0] ? pinValue[0] - 1 : 0;
            this.overFlow = false;
          } else {
            this.currentScore = 25;
            this.overFlow = true;
          }
          if (this.overFlow) {
            this.currentState = "SCORE OVERFLOW";
          }
        }
        if (tmpPins.length == 1 && this.duplicate) {
          if (50 > this.currentScore + tmpPins[0] - (tmpPins.length + 1)) {
            this.currentScore +=
              tmpPins[0] < 1 || tmpPins[0] > 12 ? -1 : tmpPins[0] - 1;
            var overFlow = false;
          } else {
            this.currentScore = 25;
            overFlow = true;
          }
          if (overFlow) {
            this.currentState = "SCORE OVERFLOW";
          }
        }
        if (this.currentScore == 50) {
          this.currentState = "WON";
          this.running = false;
        }
      } else {
        if (this.fails < 1) {
          this.fails = 0;
        }
        if (this.fails > 1) {
          this.currentState = "LOST";
          this.running = false;
        }
        this.fails += 1;
      }
    }
  }

  public state(): string {
    if (this.currentState == null) {
      this.currentState = "RUNNING";
    }

    return this.currentState;
  }
}
