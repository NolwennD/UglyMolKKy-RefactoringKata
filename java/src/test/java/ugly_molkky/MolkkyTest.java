package ugly_molkky;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MolkkyTest {

  @Test
  void shouldHaveZeroWhenStarting() {
    Molkky game = new Molkky();

    assertThat(game.score()).isZero();
  }

  @Test
  void shouldHaveThreePointsWhenShootAThreePointsPin() {
    Molkky game = new Molkky();
    game.shoot(3);

    assertThat(game.score()).isEqualTo(3);
  }

  @Test
  void shouldHaveTwoPointsWhenShootTwoPins() {
    Molkky game = new Molkky();
    game.shoot(3, 5);

    assertThat(game.score()).isEqualTo(2);
    assertThat(game.state()).isEqualTo("RUNNING");
  }

  @Test
  void shouldKeepScoreWhenMissPin() {
    Molkky game = new Molkky();
    game.shoot(3, 5);
    game.shoot();

    assertThat(game.score()).isEqualTo(2);
  }

  @Test
  void shouldShootPinsSeveralTimes() {
    Molkky game = new Molkky();
    game.shoot(3, 5);
    game.shoot(12);

    assertThat(game.score()).isEqualTo(14);
  }

  @Test
  void shouldLooseWithThreeEmptyShootInARow() {
    Molkky game = new Molkky();
    game.shoot();
    game.shoot();
    game.shoot();

    assertThat(game.state()).isEqualTo("LOST");
  }

  @Test
  void shouldLooseWithThreeEmptyShootInARow2() {
    Molkky game = new Molkky();
    game.shoot(2);
    game.shoot();
    game.shoot();
    game.shoot();

    assertThat(game.state()).isEqualTo("LOST");
  }

  @Test
  void shouldNotLooseWithThreeEmptyShootAreNotInRow() {
    Molkky game = new Molkky();
    game.shoot();
    game.shoot(3);
    game.shoot();
    game.shoot();

    assertThat(game.state()).isEqualTo("RUNNING");
  }

  @Test
  void shouldNotLooseWithThreeEmptyShootAreNotInRow2() {
    Molkky game = new Molkky();
    game.shoot();
    game.shoot(3, 5, 12);
    game.shoot();
    game.shoot();

    assertThat(game.state()).isEqualTo("RUNNING");
    assertThat(game.score()).isEqualTo(3);
  }

  @Test
  void shouldOverFlowFiftyToTwentyFive1() {
    Molkky game = new Molkky();
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12, 12);

    assertThat(game.score()).isEqualTo(25);
  }

  @Test
  void shouldOverFlowFiftyToTwentyFive2() {
    Molkky game = new Molkky();
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);

    assertThat(game.score()).isEqualTo(25);
  }

  @Test
  void shouldOverFlowFiftyToTwentyFive3() {
    Molkky game = new Molkky();
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12, 1, 2, 2);

    assertThat(game.score()).isEqualTo(25);
    assertThat(game.state()).isEqualTo("RUNNING");
  }

  @Test
  void shouldWonAGame() {
    Molkky game = new Molkky();
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(2, 4);

    assertThat(game.score()).isEqualTo(50);
    assertThat(game.state()).isEqualTo("WON");
  }

  @Test
  void shouldNotChangeWonGame() {
    Molkky game = new Molkky();
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(12);
    game.shoot(4, 5);
    game.shoot(5);

    assertThat(game.state()).isEqualTo("GAME ALREADY WON");
    assertThat(game.score()).isEqualTo(50);
  }

  @Test
  void shouldNotChangeLooseGame() {
    Molkky game = new Molkky();
    game.shoot();
    game.shoot();
    game.shoot();
    game.shoot(4);

    assertThat(game.score()).isEqualTo(0);
    assertThat(game.state()).isEqualTo("GAME ALREADY LOST");
  }

  @Test
  void shouldNotAddPointWithZeroOrLessPinPoints() {
    Molkky game = new Molkky();
    game.shoot(4);
    game.shoot(-3);
    game.shoot(0);
    game.shoot(34);

    assertThat(game.score()).isEqualTo(4);
    assertThat(game.state()).isEqualTo("RUNNING");
  }

  @Test
  void shouldNotAddPointsWithMoreThanTwelvePointsPin() {
    Molkky game = new Molkky();
    game.shoot(4);
    game.shoot(13, 5);

    assertThat(game.score()).isEqualTo(9);
    assertThat(game.state()).isEqualTo("RUNNING");
  }

  @Test
  void shouldNotAddPointsWithOfSamePin() {
    Molkky game = new Molkky();
    game.shoot(4);
    game.shoot(1, 5, 5, 6, 6);

    assertThat(game.score()).isEqualTo(7);
    assertThat(game.state()).isEqualTo("RUNNING");
  }

  @Test
  void shouldNotAddPointsWithOfSamePin2() {
    Molkky game = new Molkky();
    game.shoot(4);
    game.shoot(6, 6);

    assertThat(game.score()).isEqualTo(10);
    assertThat(game.state()).isEqualTo("RUNNING");
  }
}
