using System;
using System.Collections.Generic;

namespace Molkky
{
    public class Molkky
    {
        private int currentScore = 0;
        private int fails = 0;
        private bool overFlow = false;
        private string? currentState = null;
        private bool running = true;
        private bool duplicate = false;

        public int Score()
        {
            return currentScore;
        }

        public void Shoot(int[] pinValue)
        {
            if (currentState == "WON")
            {
                currentState = "GAME ALREADY WON";
            }

            if (!running && fails == 3)
            {
                currentState = "GAME ALREADY LOST";
            }
            else
            {
                if (pinValue.Length >= 1 && running)
                {
                    var tmpPins = new List<int>();
                    foreach (var pin in pinValue)
                    {
                        if (!tmpPins.Contains(pin) && pin < 13 && pin >= 1)
                        {
                            tmpPins.Add(pin);
                        }
                    }

                    if (tmpPins.Count != pinValue.Length)
                    {
                        duplicate = true;
                    }

                    if (currentScore + tmpPins.Count > 50)
                    {
                        overFlow = true;
                        currentScore = 25;
                    }
                    else
                    {
                        overFlow = false;
                        currentScore += duplicate ? tmpPins.Count : pinValue.Length;
                    }

                    fails -= 1;

                    if (pinValue.Length == 1)
                    {
                        if (currentScore + pinValue[0] - pinValue.Length < 51)
                        {
                            currentScore += pinValue[0] > 0 && 13 > pinValue[0] ? pinValue[0] - 1 : 0;
                            overFlow = false;
                        }
                        else
                        {
                            currentScore = 25;
                            overFlow = true;
                        }

                        if (overFlow)
                        {
                            currentState = "SCORE OVERFLOW";
                        }
                    }

                    if (tmpPins.Count == 1 && duplicate)
                    {
                        bool localOverFlow;
                        if (50 > currentScore + tmpPins[0] - (tmpPins.Count + 1))
                        {
                            currentScore += tmpPins[0] < 1 || tmpPins[0] > 12 ? -1 : tmpPins[0] - 1;
                            localOverFlow = false;
                        }
                        else
                        {
                            currentScore = 25;
                            localOverFlow = true;
                        }

                        if (localOverFlow)
                        {
                            currentState = "SCORE OVERFLOW";
                        }
                    }

                    if (currentScore == 50)
                    {
                        currentState = "WON";
                        running = false;
                    }
                }
                else
                {
                    if (fails < 1)
                    {
                        fails = 0;
                    }
                    if (fails > 1)
                    {
                        currentState = "LOST";
                        running = false;
                    }
                    fails += 1;
                }
            }
        }

        public string State()
        {
            if (currentState == null || currentState == "SCORE OVERFLOW")
            {
                currentState = "RUNNING";
            }

            return currentState;
        }
    }
}
