<?php

use App\Molkky\Molkky;

describe('The molkky $game ', function() {
    it("game start with zero point", function() {
        $game = new Molkky();

        expect($game->score())->toBe(0);
    });
    it("Score three points when shoot a three points pin", function() {
        $game = new Molkky();
        $game->shoot(3);

        expect($game->score())->toBe(3);
    });
    it("Score two points when shoot a two pins", function() {
        $game = new Molkky();
        $game->shoot(3, 4);

        expect($game->score())->toBe(2);
        expect($game->state())->toBe("RUNNING");
    });
    it("Not add point when miss pins", function() {
        $game = new Molkky();
        $game->shoot(4);
        $game->shoot();

        expect($game->score())->toBe(4);
        expect($game->state())->toBe("RUNNING");
    });
    it("Shoot pins several times", function() {
        $game = new Molkky();
        $game->shoot(4);
        $game->shoot(8, 12);

        expect($game->score())->toBe(6);
        expect($game->state())->toBe("RUNNING");
    });
    it("Lost when miss three times in a row", function() {
        $game = new Molkky();
        $game->shoot();
        $game->shoot(6);
        $game->shoot();
        $game->shoot();
        $game->shoot();

        expect($game->score())->toBe(6);
        expect($game->state())->toBe("LOST");
    });
    it("Lost when miss three times in a row 2", function() {
        $game = new Molkky();
        $game->shoot();
        $game->shoot(6, 3);
        $game->shoot();
        $game->shoot();
        $game->shoot();

        expect($game->score())->toBe(2);
        expect($game->state())->toBe("LOST");
    });
    it("Keep playing with three misses", function() {
        $game = new Molkky();
        $game->shoot();
        $game->shoot(6, 3);
        $game->shoot();
        $game->shoot();

        expect($game->score())->toBe(2);
        expect($game->state())->toBe("RUNNING");
    });
    it("Score 25 when overflow 50", function() {
        $game = new Molkky();
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(6, 3, 4);

        expect($game->score())->toBe(25);
    });
    it("Score 25 when overflow 50 2", function() {
        $game = new Molkky();
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12, 12);

        expect($game->score())->toBe(25);
    });
    it("Score 25 when overflow 50 3", function() {
        $game = new Molkky();
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);

        expect($game->score())->toBe(25);
    });
    it("Won game when score 50", function() {
        $game = new Molkky();
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(2);

        expect($game->score())->toBe(50);
        expect($game->state())->toBe("WON");
    });
    it("Won game when score 50 2", function() {
        $game = new Molkky();
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(2, 3);

        expect($game->score())->toBe(50);
        expect($game->state())->toBe("WON");
    });
    it("Won game keep unchanged", function() {
        $game = new Molkky();
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(12);
        $game->shoot(2, 3);
        $game->shoot(2, 3);

        expect($game->score())->toBe(50);
        expect($game->state())->toBe("GAME ALREADY WON");
    });
    it("Lost game keep unchanged", function() {
        $game = new Molkky();
        $game->shoot();
        $game->shoot();
        $game->shoot();
        $game->shoot(2, 3);

        expect($game->score())->toBe(0);
        expect($game->state())->toBe("GAME ALREADY LOST");
    });
    it("Not add irrelevant pins", function() {
        $game = new Molkky();
        $game->shoot(4);
        $game->shoot(-5);
        $game->shoot(34);
        $game->shoot(2, 3);

        expect($game->score())->toBe(6);
    });
    it("Not add duplicate pins", function() {
        $game = new Molkky();
        $game->shoot(4);
        $game->shoot(5, 6, 6);

        expect($game->score())->toBe(6);
    });
    it("Not add duplicate pins 2", function() {
        $game = new Molkky();
        $game->shoot(4);
        $game->shoot(6, 6);

        expect($game->score())->toBe(10);
    });
});
