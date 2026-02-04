using Xunit;
using Molkky;

namespace Molkky.Tests
{
    public class MolkkyTests
    {
        [Fact]
        public void GameStartWithZeroPoint()
        {
            var game = new Molkky();
            Assert.Equal(0, game.Score());
        }

        [Fact]
        public void ScoreThreePointsWhenShootAThreePointsPin()
        {
            var game = new Molkky();
            game.Shoot(new[] { 3 });
            Assert.Equal(3, game.Score());
        }

        [Fact]
        public void ScoreTwoPointsWhenShootATwoPins()
        {
            var game = new Molkky();
            game.Shoot(new[] { 3, 4 });
            Assert.Equal(2, game.Score());
            Assert.Equal("RUNNING", game.State());
        }

        [Fact]
        public void NotAddPointWhenMissPins()
        {
            var game = new Molkky();
            game.Shoot(new[] { 4 });
            game.Shoot(new int[] { });
            Assert.Equal(4, game.Score());
            Assert.Equal("RUNNING", game.State());
        }

        [Fact]
        public void ShootPinsSeveralTimes()
        {
            var game = new Molkky();
            game.Shoot(new[] { 4 });
            game.Shoot(new[] { 8, 12 });
            Assert.Equal(6, game.Score());
            Assert.Equal("RUNNING", game.State());
        }

        [Fact]
        public void LostWhenMissThreeTimesInARow()
        {
            var game = new Molkky();
            game.Shoot(new int[] { });
            game.Shoot(new[] { 6 });
            game.Shoot(new int[] { });
            game.Shoot(new int[] { });
            game.Shoot(new int[] { });
            Assert.Equal(6, game.Score());
            Assert.Equal("LOST", game.State());
        }

        [Fact]
        public void LostWhenMissThreeTimesInARow2()
        {
            var game = new Molkky();
            game.Shoot(new int[] { });
            game.Shoot(new[] { 6, 3 });
            game.Shoot(new int[] { });
            game.Shoot(new int[] { });
            game.Shoot(new int[] { });
            Assert.Equal(2, game.Score());
            Assert.Equal("LOST", game.State());
        }

        [Fact]
        public void KeepPlayingWithThreeMisses()
        {
            var game = new Molkky();
            game.Shoot(new int[] { });
            game.Shoot(new[] { 6, 3 });
            game.Shoot(new int[] { });
            game.Shoot(new int[] { });
            Assert.Equal(2, game.Score());
            Assert.Equal("RUNNING", game.State());
        }

        [Fact]
        public void Score25WhenOverflow50()
        {
            var game = new Molkky();
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 6, 3, 4 });
            Assert.Equal(25, game.Score());
            Assert.Equal("RUNNING", game.State());
        }

        [Fact]
        public void Score25WhenOverflow50_2()
        {
            var game = new Molkky();
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12, 12 });
            Assert.Equal(25, game.Score());
            Assert.Equal("RUNNING", game.State());
        }

        [Fact]
        public void WonGameWhenScore50()
        {
            var game = new Molkky();
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 2 });
            Assert.Equal(50, game.Score());
            Assert.Equal("WON", game.State());
        }

        [Fact]
        public void WonGameWhenScore50_2()
        {
            var game = new Molkky();
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 2, 3 });
            Assert.Equal(50, game.Score());
            Assert.Equal("WON", game.State());
        }

        [Fact]
        public void WonGameKeepUnchange()
        {
            var game = new Molkky();
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 12 });
            game.Shoot(new[] { 2, 3 });
            game.Shoot(new[] { 2, 3 });
            Assert.Equal(50, game.Score());
            Assert.Equal("GAME ALREADY WON", game.State());
        }

        [Fact]
        public void LostGameKeepUnchange()
        {
            var game = new Molkky();
            game.Shoot(new int[] { });
            game.Shoot(new int[] { });
            game.Shoot(new int[] { });
            game.Shoot(new[] { 2, 3 });
            Assert.Equal(0, game.Score());
            Assert.Equal("GAME ALREADY LOST", game.State());
        }

        [Fact]
        public void NotAddIrrelevantPins()
        {
            var game = new Molkky();
            game.Shoot(new[] { 4 });
            game.Shoot(new[] { -5 });
            game.Shoot(new[] { 34 });
            game.Shoot(new[] { 2, 3 });
            Assert.Equal(6, game.Score());
        }

        [Fact]
        public void NotAddDuplicatePins()
        {
            var game = new Molkky();
            game.Shoot(new[] { 4 });
            game.Shoot(new[] { 5, 6, 6 });
            Assert.Equal(6, game.Score());
        }

        [Fact]
        public void NotAddDuplicatePins_2()
        {
            var game = new Molkky();
            game.Shoot(new[] { 4 });
            game.Shoot(new[] { 6, 6 });
            Assert.Equal(10, game.Score());
        }
    }
}
