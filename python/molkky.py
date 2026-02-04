class Molkky:
    def __init__(self):
        self.current_score = 0
        self.fails = 0
        self.over_flow = False
        self.current_state = None
        self.running = True
        self.duplicate = False

    def score(self):
        return self.current_score

    def shoot(self, pin_value):
        if self.current_state == "WON":
            self.current_state = "GAME ALREADY WON"

        if not self.running and self.fails == 3:
            self.current_state = "GAME ALREADY LOST"
        else:
            if len(pin_value) >= 1 and self.running:
                tmp_pins = []
                for pin in pin_value:
                    if pin not in tmp_pins and 1 <= pin < 13:
                        tmp_pins.append(pin)

                if len(tmp_pins) != len(pin_value):
                    self.duplicate = True

                if self.current_score + len(tmp_pins) > 50:
                    self.over_flow = True
                    self.current_score = 25
                else:
                    self.over_flow = False
                    self.current_score += len(tmp_pins) if self.duplicate else len(pin_value)

                self.fails -= 1

                if len(pin_value) == 1:
                    if self.current_score + pin_value[0] - len(pin_value) < 51:
                        self.current_score += pin_value[0] - 1 if 0 < pin_value[0] < 13 else 0
                        self.over_flow = False
                    else:
                        self.current_score = 25
                        self.over_flow = True

                    if self.over_flow:
                        self.current_state = "SCORE OVERFLOW"

                if len(tmp_pins) == 1 and self.duplicate:
                    if 50 > self.current_score + tmp_pins[0] - (len(tmp_pins) + 1):
                        self.current_score += -1 if tmp_pins[0] < 1 or tmp_pins[0] > 12 else tmp_pins[0] - 1
                        local_over_flow = False
                    else:
                        self.current_score = 25
                        local_over_flow = True

                    if local_over_flow:
                        self.current_state = "SCORE OVERFLOW"

                if self.current_score == 50:
                    self.current_state = "WON"
                    self.running = False
            else:
                if self.fails < 1:
                    self.fails = 0
                if self.fails > 1:
                    self.current_state = "LOST"
                    self.running = False
                self.fails += 1

    def state(self):
        if self.current_state is None or self.current_state == "SCORE OVERFLOW":
            self.current_state = "RUNNING"

        return self.current_state
