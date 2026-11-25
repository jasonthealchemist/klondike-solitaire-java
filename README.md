# Klondike Solitaire

A Java-based terminal implementation of **Klondike Solitaire** with automated gameplay, configurable shuffle algorithms, and support for Draw One and Draw Three modes.

Unlike a traditional Solitaire game where the user manually moves cards, this application uses a rule-based autoplay system to play the game automatically. It displays the board, records moves, tracks game outcomes, and calculates the overall win rate across multiple games.

## Features

- **Automated Gameplay** – Automatically plays Klondike Solitaire using legal game actions.
- **Four Shuffle Options** – Random Shuffle, Out-Faro Shuffle, In-Faro Shuffle, and No Shuffle.
- **Two Draw Modes** – Draw One and Draw Three.
- **Board Display** – Displays the current game board during autoplay.
- **Move Highlights** – Highlights cards and moves that have just occurred.
- **Move Logging** – Displays the actions performed by the game engine.
- **Multiple Game Simulation** – Runs multiple games and summarizes the results.
- **Win Rate Tracking** – Tracks individual game outcomes and cumulative session win rate.
- **Custom Deck Input** – Supports loading a predefined deck from a resource file.
- **Configurable Settings** – Allows game limits, shuffle limits, and engine settings to be configured.

## Requirements

- Java Development Kit (JDK) 25
- Apache Maven
- IntelliJ IDEA or another Java IDE (optional)

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
cd <repository-folder>
```

### 2. Build the application

Using Maven:

```bash
mvn clean package
```

The build generates the JAR file inside the `target` directory.

If the Maven configuration includes the distribution-copy execution, another copy will also be generated inside `dist`.

### 3. Run the application

Using the generated JAR:

```bash
java -jar dist/solitaire-java-1.0-SNAPSHOT.jar
```

Alternatively, run the `Main` class directly from IntelliJ IDEA:

```text
com.svi.klondike.Main
```

## Application Workflow

The application follows this general execution flow:

1. Display the application title and startup prompt.
2. Allow the user to start the application or quit.
3. Select a shuffle algorithm.
4. Select a draw mode.
5. Enter the number of games to simulate.
6. Enter the shuffle count when required by the selected shuffle algorithm.
7. Initialize the deck and deal the cards.
8. Execute the automated Solitaire game.
9. Display the board and move updates during gameplay.
10. Display the result of each game and the cumulative session statistics.
11. Allow the user to start another game session or exit.

For a single-game session, the application can perform the configured silent win-rate evaluation. For multiple games, it summarizes the results of the simulation.

Session statistics remain available throughout the application's execution and reset when the application is restarted.

## Game Rules

This project implements the standard rules of Klondike Solitaire.

### Board Setup

A standard game uses a 52-card deck with:

- **Tableau:** Seven columns containing 1 to 7 cards respectively.
- **Foundation:** Four foundation piles, one for each suit.
- **Stock:** The remaining cards that have not been dealt into the tableau.
- **Waste:** Cards drawn from the stock.

Only the top card of each tableau column is initially face-up. The remaining cards are face-down.

### Tableau Rules

- Cards must be arranged in descending rank order.
- Alternating card colors must be used when building tableau sequences.
- Only a King can be placed in an empty tableau column.
- Face-down cards are revealed when the face-up card above them is moved.

### Foundation Rules

- Each foundation pile starts with an Ace.
- Cards must be placed in ascending rank order.
- Cards must belong to the same suit.
- The game is won when all 52 cards are placed into the four foundation piles.

### Stock and Waste Rules

The player can draw cards from the stock depending on the selected draw mode.

- **Draw One:** Draws one card from the stock at a time.
- **Draw Three:** Draws up to three cards from the stock at a time.

When the stock is exhausted, the waste pile can be recycled according to the game's stock-recycling rules.

## Shuffle Algorithms

The application supports four shuffle configurations.

| Shuffle Type | Description |
|---|---|
| Random Shuffle | Randomly rearranges the deck. |
| Out-Faro Shuffle | Perfectly interleaves the two halves of the deck while keeping the original top and bottom cards in their respective positions. |
| In-Faro Shuffle | Perfectly interleaves the two halves of the deck while swapping the positions of the original top and bottom cards. |
| No Shuffle | Uses the original deck order without applying a shuffle. |

### Faro Shuffle Details

Both Faro algorithms divide the 52-card deck into two equal halves and interleave their cards perfectly.

- **Out-Faro:** Returns the deck to its original order after 8 consecutive shuffles.
- **In-Faro:** Returns the deck to its original order after 52 consecutive shuffles.

These deterministic shuffle algorithms make it possible to reproduce deck arrangements when using the same starting deck and shuffle configuration.

## Automated Gameplay

The application uses a rule-based strategy to perform Solitaire actions automatically.

The autoplay system evaluates available moves and prioritizes actions according to the game's strategy.

Its general priorities include:

1. Moving eligible cards to the foundation.
2. Revealing face-down tableau cards.
3. Making legal tableau moves.
4. Drawing cards from the stock when necessary.
5. Recycling the waste pile when the stock is exhausted and recycling is permitted.

The application uses the game's legal action mechanisms rather than directly modifying piles to bypass game rules.

### Game Outcomes

A game can produce different outcomes depending on its execution:

- **WON:** All 52 cards have been moved to the foundation piles.
- **STUCK / NOT SOLVED:** The autoplay strategy cannot find a useful legal action or reaches its configured execution limit.

A game that is not solved by the autoplay strategy is not necessarily mathematically unwinnable. The application uses a rule-based strategy rather than an exhaustive solver.

Repeated game states and execution limits are handled to prevent the autoplay process from running indefinitely.

## Board Display and Move Logging

The terminal board display provides a visual representation of the current game state.

During autoplay, the application updates the board and highlights the cards involved in recent actions.

Move logs provide information about the actions performed, including the source and destination of card movements.

Example card notation:

```text
[♦ D-10]
```

The move display distinguishes between different action types, such as moving cards, drawing from the stock, and transferring cards to the foundation.

When multiple cards are drawn together, the display can summarize the draw action rather than printing every card movement separately.

## Results and Win Rate

The application tracks game results and calculates the session win rate.

The win rate is calculated using:

```text
Win Rate = (Number of Wins / Total Completed Games) * 100
```

The results include:

- Total games completed.
- Number of games won.
- Number of games not solved.
- Overall session win rate.

Interrupted or abandoned games are not counted as losses.

The session statistics are retained while the application remains running and reset when it is restarted.

## Configuration

Application settings can be configured through:

```text
src/main/resources/klondike.properties
```

The configuration file contains settings that control simulation limits, shuffle limits, win-rate evaluation, engine execution limits, and the deck input file.

| Property | Description |
|---|---|
| `games.min` | Minimum number of games allowed. |
| `games.max` | Maximum number of games allowed. |
| `shuffles.min` | Minimum shuffle count allowed. |
| `shuffles.max` | Maximum shuffle count allowed. |
| `winrate.check.games` | Number of games used for the configured win-rate evaluation. |
| `engine.max.actions` | Maximum number of actions allowed per game. |
| `deck.file` | Location or name of the deck input file. |

Invalid configuration values are handled during application initialization.

## Custom Deck Input

The application supports loading a predefined deck from:

```text
src/main/resources/input.txt
```

The deck file must contain exactly 52 unique cards.

Each card follows this format:

```text
SUIT-RANK
```

Example:

```text
D-A
D-2
D-3
D-4
D-5
...
H-A
...
S-A
...
C-K
```

Supported suit symbols:

| Symbol | Suit |
|---|---|
| D | Diamonds |
| H | Hearts |
| S | Spades |
| C | Clubs |

Supported ranks:

```text
A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K
```

Each card must appear exactly once. Duplicate, missing, or invalid cards cause the deck validation process to fail.

The first card in the file represents the bottom of the deck, while the last card represents the top.

The deck loader supports reading from a file path or a classpath resource, allowing the application to load `input.txt` when running from an IDE or packaged JAR.

## Exit Codes

The application uses exit codes to indicate how execution ended.

| Exit Code | Description |
|---|---|
| `0` | Normal application termination. |
| `1` | Unexpected application error. |
| `2` | Invalid or missing application settings. |
| `3` | Invalid or missing deck file. |
| `4` | File-related error. |

## Project Structure

The project follows a package-based structure that separates configuration, game logic, application services, and terminal interaction.

```text
com.svi.klondike
│
├── Main.java
│
├── config
│   ├── DrawMode.java
│   ├── GameConfig.java
│   ├── Settings.java
│   └── ShuffleType.java
│
├── console
│   ├── BoardPrinter.java
│   ├── ConfigPrompter.java
│   ├── ConsoleGameHandler.java
│   ├── ConsoleInput.java
│   ├── GameRunner.java
│   ├── GameSetup.java
│   ├── MoveLogPrinter.java
│   └── ResultPrinter.java
│
├── engine
│   ├── ActionType.java
│   ├── GameAction.java
│   ├── GameEngine.java
│   ├── GameOutcome.java
│   ├── GameResult.java
│   └── PlayObserver.java
│
├── enums
│   ├── Ranks.java
│   ├── SuitColor.java
│   └── Suits.java
│
├── game
│   ├── Board.java
│   ├── DrawResult.java
│   └── MoveResult.java
│
├── io
│   └── DeckLoader.java
│
├── models
│   ├── Card.java
│   ├── Deck.java
│   └── Shuffler.java
│
├── piles
│   ├── FoundationPile.java
│   ├── Pile.java
│   ├── StockPile.java
│   ├── TableauPile.java
│   └── WastePile.java
│
├── service
│   ├── GameHandler.java
│   ├── GameService.java
│   ├── SimulationResult.java
│   └── SimulationService.java
│
├── shuffle
│   ├── FaroShuffler.java
│   ├── InFaroShuffler.java
│   ├── OutFaroShuffler.java
│   ├── RandomShuffler.java
│   └── ShufflerFactory.java
```

*Note: The tree above describes the intended package organization. Keep the class names synchronized with the actual source code if any have been renamed.*

## Design and Object-Oriented Programming

The application uses object-oriented programming principles to separate responsibilities and keep the code maintainable.

### Encapsulation

Game data is managed through classes that control access to their internal state. Card properties, deck contents, and pile operations are handled through their respective classes.

### Abstraction

The application uses abstractions such as piles, game actions, and shuffle behavior to hide implementation details and expose only the operations required by other components.

### Inheritance

Shared pile behavior is defined through a common pile abstraction, allowing specialized pile types to implement their own behavior.

### Polymorphism

Different shuffle implementations follow a common shuffling abstraction. The application can select and execute different shuffle algorithms without requiring the game logic to know their individual implementations.

### Separation of Responsibilities

The project separates the main responsibilities into packages:

- `console` – Handles terminal input, output, and presentation.
- `config` – Stores application configuration and game options.
- `engine` – Executes game actions and manages game outcomes.
- `game` – Represents the board and game-related operations.
- `models` – Contains core game objects such as cards and decks.
- `piles` – Implements the different Solitaire piles.
- `service` – Coordinates game operations and simulation workflows.
- `shuffle` – Contains the different shuffle algorithms.
- `io` – Handles deck file loading.
- `utils` – Provides shared utility functionality.

## Development Background

This project was developed as part of the Software Ventures International Corporation (SVI) trainee program.

The implementation incorporates feedback and improvement considerations from trainee project evaluations, with emphasis on:

- Correct game logic and execution.
- Readable and maintainable code.
- Modular and reusable components.
- Object-oriented design principles.
- Proper separation of responsibilities.
- Clear terminal presentation and reporting.

## Evaluation Criteria

The project considers the following evaluation areas:

| Category | Weight |
|---|---:|
| Logic and Execution | 20% |
| Coding Standards | 30% |
| Object-Oriented Programming | 40% |
| Presentation | 10% |
| **Total** | **100%** |

## Limitations

- The autoplay strategy is rule-based and does not guarantee that every solvable deal will be completed.
- A game reported as `STUCK / NOT SOLVED` is not proof that the deal is impossible to win.
- The application is designed for terminal-based gameplay rather than graphical interaction.

## License

This project is intended for educational and training purposes.