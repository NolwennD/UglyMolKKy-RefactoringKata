<?php

namespace App\Molkky;

class Molkky
{
    private int $score = 0;
    private int $fails = 0;
    private bool $overFlow = false;
    private ?string $state = null;
    private bool $running = true;
    private bool $duplicate = false;

    public function score(): int
    {
        return $this->score;
    }

    public function shoot(int ...$pinValue): void
    {
        if ($this->state == "WON") {
            $this->state = "GAME ALREADY WON";
        }
        if (!$this->running && ($this->fails == 3)) {
            $this->state = "GAME ALREADY LOST";
        } else {
            if ((count($pinValue) >= 1)
                && $this->running) {
                $tmpPins = [];
                foreach ($pinValue as $pin) {
                    if (!in_array($pin, $tmpPins) && ($pin < 13) && ($pin >= 1)) {
                        $tmpPins[] = $pin;
                    }
                }
                if (count($tmpPins) != count($pinValue)) {
                    $this->duplicate = true;
                }
                if ($this->score + count($tmpPins) > 50) {
                    $this->overFlow = true;
                    $this->score = 25;
                } else {
                    $this->overFlow = false;
                    $this->score += $this->duplicate ? count($tmpPins) : count($pinValue);
                }
                if ($this->overFlow){
                    $this->state = "SCORE OVERFLOW";
                }
                $this->fails -= 1;
                if (count($pinValue) == 1) {
                    if (((($this->score + $pinValue[0]) - count($pinValue)) < 51)) {
                        $this->score += ($pinValue[0] > 0) && (13 > $pinValue[0]) ? $pinValue[0] - 1 : 0;
                        $this->overFlow = false;
                    } else {
                        $this->score = 25;
                        $this->overFlow = true;
                    }
                }
                if (count($tmpPins) == 1 && $this->duplicate) {
                    if (50 > ((($this->score + $tmpPins[0]) - (count($tmpPins) + 1)))) {
                        $this->score += ($tmpPins[0] < 1) || ($tmpPins[0] > 12) ? -1 : $tmpPins[0] - 1;
                        $this->overFlow = false;
                    } else {
                        $this->score = 25;
                        $this->overFlow = true;
                    }
                    if ($this->overFlow){
                        $this->state = "SCORE OVERFLOW";
                    }
                }
                if ($this->score == 50) {
                    $this->state = "WON";
                    $this->running = false;
                }
            } else {
                if ($this->fails < 1) {
                    $this->fails = 0;
                }
                if ($this->fails > 1) {
                    $this->state = "LOST";
                    $this->running = false;
                }
                $this->fails += 1;
            }
        }
    }

    public function state(): string
    {
        if ($this->state == null) {
            $this->state = "RUNNING";
        }

        return $this->state;
    }
}