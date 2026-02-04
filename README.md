# Ugly Molkky refactoring kata
## Rules

* Pins have points from 1 to 12.
* When you shoot one pin, you add pin’s point to you current score.
* When you shoot several pins, you add the number of fell pins to your current score.
* If score exceed 50 points, it decrease to 25.
* If you miss pin three times in a row, game is over.
* You win when your score is 50
* Keep playing ended game does not have any effect.

## Python

Ce projet utilise [uv](https://docs.astral.sh/uv/) pour la gestion du projet Python.

### Prérequis

Assurez-vous d'avoir `uv` installé sur votre système.

### Installation et exécution des tests

Pour installer les dépendances et lancer les tests, exécutez la commande suivante depuis la racine du projet :

```bash
cd python
uv run pytest
```

`uv` s'occupera automatiquement de créer l'environnement virtuel et d'installer les dépendances listées dans le fichier `pyproject.toml`.
