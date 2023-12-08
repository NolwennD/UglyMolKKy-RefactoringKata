#[derive(Clone)]
struct Molkky {
    score: u8,
    fails: i8,
    overflow: bool,
    state: Option<String>,
    running: bool,
    duplicate: bool,
}

impl Molkky {
    fn new() -> Self {
        Molkky {
            score: 0,
            fails: 0,
            overflow: false,
            state: None,
            running: true,
            duplicate: false,
        }
    }
    fn shoot(&mut self, pinValue: Vec<u8>) {
        if self.state == Some("WON".into()) {
            self.state = Some("GAME ALREADY WON".into());
        }
        if !self.running && (self.fails == 3) {
            self.state = Some("GAME ALREADY LOST".into());
        } else {
            if (pinValue.len() >= 1) && self.running {
                let mut tmpPins: Vec<u8> = Vec::new();
                for pin in &pinValue {
                    if !tmpPins.contains(&pin) && (pin < &13) && (pin >= &1) {
                        tmpPins.push(*pin);
                    }
                }
                if tmpPins.len() != pinValue.len() {
                    self.duplicate = true;
                }
                if (self.score + tmpPins.len() as u8) > 50 {
                    self.overflow = true;
                    self.score = 25;
                } else {
                    self.overflow = false;
                    if self.duplicate {
                        self.score += tmpPins.len() as u8;
                    } else {
                        self.score += pinValue.len() as u8;
                    }
                }
                self.fails -= 1;
                if pinValue.len() == 1 {
                    if ((self.score + pinValue[0]) - pinValue.len() as u8) < 51 {
                        self.score += if (pinValue[0] > 0) && (13 > pinValue[0]) {
                            pinValue[0] - 1
                        } else {
                            0
                        };
                        self.overflow = false;
                    } else {
                        self.score = 25;
                        self.overflow = true;
                    }
                }
                if (tmpPins.len() == 1) && self.duplicate {
                    if 50 > ((self.score + tmpPins[0]) - (tmpPins.len() as u8 + 1)) {
                        self.score -= 1;
                        self.score += if (tmpPins[0] < 1) || (tmpPins[0] > 12) {
                            0
                        } else {
                            tmpPins[0]
                        };
                        self.overflow = false;
                    } else {
                        self.score = 25;
                        self.state = Some("SCORE OVERFLOW".into());
                        self.overflow = true;
                    }
                }
                if self.score == 50 {
                    self.state = Some("WON".into());
                    self.running = false;
                }
            } else {
                if self.fails < 1 {
                    self.fails = 0;
                }
                if self.fails > 1 {
                    self.state = Some("LOST".into());
                    self.running = false;
                }
                self.fails += 1;
            }
        }
    }
    fn score(&self) -> u8 {
        self.score
    }

    fn state(&self) -> String {
        let res = self.state.clone();
        res.unwrap_or("RUNNING".into())
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use fluent_asserter::prelude::*;

    #[test]
    fn should_have_zero_when_starting() {
        let mut game = Molkky::new();

        assert_that!(game.score()).is_equal_to(0);
    }

    #[test]
    fn should_have_three_points_when_shoot_a_three_points_pin() {
        let mut game = Molkky::new();
        game.shoot(vec![3]);

        assert_that!(game.score()).is_equal_to(3);
    }

    #[test]
    fn should_have_two_points_when_shoot_two_pins() {
        let mut game = Molkky::new();
        game.shoot(vec![3, 5]);

        assert_that!(game.score()).is_equal_to(2);
        assert_that!(game.state()).is_equal_to("RUNNING".into());
    }

    #[test]
    fn should_keep_score_when_miss_pin() {
        let mut game = Molkky::new();
        game.shoot(vec![3, 5]);
        game.shoot(vec![]);

        assert_that!(game.score()).is_equal_to(2);
    }

    #[test]
    fn should_shoot_pins_several_times() {
        let mut game = Molkky::new();
        game.shoot(vec![3, 5]);
        game.shoot(vec![12]);

        assert_that!(game.score()).is_equal_to(14);
    }

    #[test]
    fn should_loose_with_three_empty_shoot_in_arow() {
        let mut game = Molkky::new();
        game.shoot(vec![]);
        game.shoot(vec![]);
        game.shoot(vec![]);

        assert_that!(game.state()).is_equal_to("LOST".into());
    }

    #[test]
    fn should_loose_with_three_empty_shoot_in_a_row2() {
        let mut game = Molkky::new();
        game.shoot(vec![2]);
        game.shoot(vec![]);
        game.shoot(vec![]);
        game.shoot(vec![]);

        assert_that!(game.state()).is_equal_to("LOST".into());
    }

    #[test]
    fn should_not_loose_with_three_empty_shoot_are_not_in_row() {
        let mut game = Molkky::new();
        game.shoot(vec![]);
        game.shoot(vec![3]);
        game.shoot(vec![]);
        game.shoot(vec![]);

        assert_that!(game.state()).is_equal_to("RUNNING".into());
    }

    #[test]
    fn should_not_loose_with_three_empty_shoot_are_not_in_row2() {
        let mut game = Molkky::new();
        game.shoot(vec![]);
        game.shoot(vec![3, 5, 12]);
        game.shoot(vec![]);
        game.shoot(vec![]);

        assert_that!(game.state()).is_equal_to("RUNNING".into());
        assert_that!(game.score()).is_equal_to(3);
    }

    #[test]
    fn should_over_flow_fifty_to_twenty_five1() {
        let mut game = Molkky::new();
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12, 12]);

        assert_that!(game.score()).is_equal_to(25);
    }

    #[test]
    fn should_over_flow_fifty_to_twenty_five2() {
        let mut game = Molkky::new();
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);

        assert_that!(game.score()).is_equal_to(25);
    }

    #[test]
    fn should_over_flow_fifty_to_twenty_five3() {
        let mut game = Molkky::new();
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12, 1, 2, 2]);

        assert_that!(game.score()).is_equal_to(25);
    }

    #[test]
    fn should_won_agame() {
        let mut game = Molkky::new();
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![2, 4]);

        assert_that!(game.score()).is_equal_to(50);
        assert_that!(game.state()).is_equal_to("WON".into());
    }

    #[test]
    fn should_not_change_won_game() {
        let mut game = Molkky::new();
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![12]);
        game.shoot(vec![4, 5]);
        game.shoot(vec![5]);

        assert_that!(game.state()).is_equal_to("GAME ALREADY WON".into());
        assert_that!(game.score()).is_equal_to(50);
    }

    #[test]
    fn should_not_change_loose_game() {
        let mut game = Molkky::new();
        game.shoot(vec![]);
        game.shoot(vec![]);
        game.shoot(vec![]);
        game.shoot(vec![4]);

        assert_that!(game.score()).is_equal_to(0);
        assert_that!(game.state()).is_equal_to("GAME ALREADY LOST".into());
    }

    #[test]
    fn should_not_add_point_with_zero_or_less_pin_points() {
        let mut game = Molkky::new();
        game.shoot(vec![4]);
        game.shoot(vec![0]);
        game.shoot(vec![34]);

        assert_that!(game.score()).is_equal_to(4);
        assert_that!(game.state()).is_equal_to("RUNNING".into());
    }

    #[test]
    fn should_not_add_points_with_more_than_twelve_points_pin() {
        let mut game = Molkky::new();
        game.shoot(vec![4]);
        game.shoot(vec![13, 5]);

        assert_that!(game.score()).is_equal_to(9);
        assert_that!(game.state()).is_equal_to("RUNNING".into());
    }

    #[test]
    fn should_not_add_points_with_of_same_pin() {
        let mut game = Molkky::new();
        game.shoot(vec![4]);
        game.shoot(vec![1, 5, 5, 6, 6]);

        assert_that!(game.score()).is_equal_to(7);
        assert_that!(game.state()).is_equal_to("RUNNING".into());
    }

    #[test]
    fn should_not_add_points_with_of_same_pin2() {
        let mut game = Molkky::new();
        game.shoot(vec![4]);
        game.shoot(vec![6, 6]);

        assert_that!(game.score()).is_equal_to(10);
        assert_that!(game.state()).is_equal_to("RUNNING".into());
    }
}
