from molkky import Molkky

def test_game_start_with_zero_point():
    game = Molkky()
    assert game.score() == 0

def test_score_three_points_when_shoot_a_three_points_pin():
    game = Molkky()
    game.shoot([3])
    assert game.score() == 3

def test_score_two_points_when_shoot_a_two_pins():
    game = Molkky()
    game.shoot([3, 4])
    assert game.score() == 2
    assert game.state() == "RUNNING"

def test_not_add_point_when_miss_pins():
    game = Molkky()
    game.shoot([4])
    game.shoot([])
    assert game.score() == 4
    assert game.state() == "RUNNING"

def test_shoot_pins_several_times():
    game = Molkky()
    game.shoot([4])
    game.shoot([8, 12])
    assert game.score() == 6
    assert game.state() == "RUNNING"

def test_lost_when_miss_three_times_in_a_row():
    game = Molkky()
    game.shoot([])
    game.shoot([6])
    game.shoot([])
    game.shoot([])
    game.shoot([])
    assert game.score() == 6
    assert game.state() == "LOST"

def test_lost_when_miss_three_times_in_a_row_2():
    game = Molkky()
    game.shoot([])
    game.shoot([6, 3])
    game.shoot([])
    game.shoot([])
    game.shoot([])
    assert game.score() == 2
    assert game.state() == "LOST"

def test_keep_playing_with_three_misses():
    game = Molkky()
    game.shoot([])
    game.shoot([6, 3])
    game.shoot([])
    game.shoot([])
    assert game.score() == 2
    assert game.state() == "RUNNING"

def test_score_25_when_overflow_50():
    game = Molkky()
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([6, 3, 4])
    assert game.score() == 25
    assert game.state() == "RUNNING"

def test_score_25_when_overflow_50_2():
    game = Molkky()
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([12, 12])
    assert game.score() == 25
    assert game.state() == "RUNNING"

def test_won_game_when_score_50():
    game = Molkky()
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([2])
    assert game.score() == 50
    assert game.state() == "WON"

def test_won_game_when_score_50_2():
    game = Molkky()
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([2, 3])
    assert game.score() == 50
    assert game.state() == "WON"

def test_won_game_keep_unchange():
    game = Molkky()
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([12])
    game.shoot([2, 3])
    game.shoot([2, 3])
    assert game.score() == 50
    assert game.state() == "GAME ALREADY WON"

def test_lost_game_keep_unchange():
    game = Molkky()
    game.shoot([])
    game.shoot([])
    game.shoot([])
    game.shoot([2, 3])
    assert game.score() == 0
    assert game.state() == "GAME ALREADY LOST"

def test_not_add_irrelevant_pins():
    game = Molkky()
    game.shoot([4])
    game.shoot([-5])
    game.shoot([34])
    game.shoot([2, 3])
    assert game.score() == 6

def test_not_add_duplicate_pins():
    game = Molkky()
    game.shoot([4])
    game.shoot([5, 6, 6])
    assert game.score() == 6

def test_not_add_duplicate_pins_2():
    game = Molkky()
    game.shoot([4])
    game.shoot([6, 6])
    assert game.score() == 10
