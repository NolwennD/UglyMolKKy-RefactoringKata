package ugly_molkky;

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class MolkkyTest extends AnyFunSuite with Matchers {
  test("Should have zero when starting") {
    val game = new Molkky();
    game.getScore() shouldEqual 0;
  }

  test("should have three points when shoot a three points pin") {
    val game = new Molkky();
    game.shoot(3);

    game.getScore() shouldEqual 3;
  }

  test("Should have two points when shoot two pins") {
    val game = new Molkky()
    game.shoot(3, 5)

    game.getScore() shouldEqual 2
    game.getState() shouldEqual "RUNNING"
  }

  test("Should keep getScore() when miss pin") {
    val game = new Molkky()
    game.shoot(3, 5)
    game.shoot()

    game.getScore() shouldEqual 2
  }

  test("Should shoot pins several times") {
    val game = new Molkky()
    game.shoot(3, 5)
    game.shoot(12)

    game.getScore() shouldEqual 14
  }

  test("Should lose with three empty shoot in a row") {
    val game = new Molkky()
    game.shoot()
    game.shoot()
    game.shoot()

    game.getState() shouldEqual "LOST"
  }

  test("Should lose with three empty shoot in a row 2") {
    val game = new Molkky()
    game.shoot(2)
    game.shoot()
    game.shoot()
    game.shoot()

    game.getState() shouldEqual "LOST"
  }

  test("Should not lose when three empty shoot are not in row") {
    val game = new Molkky()
    game.shoot()
    game.shoot(3)
    game.shoot()
    game.shoot()

    game.getState() shouldEqual "RUNNING"
  }

  test("Should not lose when three empty shoot are not in row 2") {
    val game = new Molkky()
    game.shoot()
    game.shoot(3, 5, 12)
    game.shoot()
    game.shoot()

    game.getState() shouldEqual "RUNNING"
    game.getScore() shouldEqual 3
  }

  test("Should overflow fifty to twenty five 1") {
    val game = new Molkky()
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12, 12)

    game.getScore() shouldEqual 25
  }

  test("Should overflow fifty to twenty five 2") {
    val game = new Molkky()
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)

    game.getScore() shouldEqual 25
  }

  test("Should overflow fifty to twenty five 3") {
    val game = new Molkky()
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12, 1, 2, 2)

    game.getScore() shouldEqual 25
    game.getState() shouldEqual "RUNNING"
  }

  test("Should win a game") {
    val game = new Molkky()
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(2, 4)

    game.getScore() shouldEqual 50
    game.getState() shouldEqual "WON"
  }

  test("Should not change won game") {
    val game = new Molkky()
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(12)
    game.shoot(4, 5)
    game.shoot(5)

    game.getState() shouldEqual "GAME ALREADY WON"
    game.getScore() shouldEqual 50
  }

  test("Should not change lost game") {
    val game = new Molkky()
    game.shoot()
    game.shoot()
    game.shoot()
    game.shoot(4)

    game.getScore() shouldEqual 0
    game.getState() shouldEqual "GAME ALREADY LOST"
  }

  test("Should not add point with zero or less pin points") {
    val game = new Molkky()
    game.shoot(4)
    game.shoot(-3)
    game.shoot(0)
    game.shoot(34)

    game.getScore() shouldEqual 4
    game.getState() shouldEqual "LOST"
  }

  test("Should not add points with more than twelve points pin") {
    val game = new Molkky()
    game.shoot(4)
    game.shoot(13, 5)

    game.getScore() shouldEqual 9
    game.getState() shouldEqual "RUNNING"
  }

  test("Should not add points with same pin") {
    val game = new Molkky()
    game.shoot(4)
    game.shoot(1, 5, 5, 6, 6)

    game.getScore() shouldEqual 7
    game.getState() shouldEqual "RUNNING"
  }

  test("Should not add points with same pin 2") {
    val game = new Molkky()
    game.shoot(4)
    game.shoot(6, 6)

    game.getScore() shouldEqual 10
    game.getState() shouldEqual "RUNNING"
  }
}
