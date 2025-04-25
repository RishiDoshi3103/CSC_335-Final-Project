# 🃏 Cribbage Game

A digital implementation of the classic **Cribbage** card game using **Java**, with a **Swing GUI** and the **Observer design pattern** to connect game logic and visuals in a clean, modular way.

---

## 🚀 Features

- **🎮 Game Modes:**
  - Human vs Human
  - Human vs Computer (Easy / Hard)
  - Computer vs Computer

- **🧑 Custom Names:**
  - Human players can input their own names.

- **🖥️ Swing GUI:**
  - Visual hand and card selection
  - Logs for game events and scores
  - "Play again" prompt at game end

- **🧠 Cribbage Rules Implemented:**
  - Full scoring: 15s, runs, pairs, flushes, and nobs

- **🧩 Design Patterns:**
  - Uses the **Observer** pattern to decouple game logic from the interface.

---

## 📁 Project Structure

Cribbage/ ├── main/ │ └── Main.java # Entry point ├── controller/ │ └── GameController.java # Game loop, observer notifications ├── model/ │ ├── Game.java # Game state and scoring rules │ ├── Card.java, Deck.java # Card models ├── ui/ │ ├── GuiViewer.java # GUI using Swing │ └── GameStarter.java # CLI prompts for mode and names ├── observer/ │ ├── Observer.java # Observer interface │ └── Subject.java # Subject interface

---

## 🛠️ Compile and Run (Using JAR)

## Step 1: Compile
```javac -d out $(find . -name "*.java")`

## Step 2: Run the Game

```java -jar CribbageGame.jar`
=======
## Step 1: "*Download the Reposiortie*" or "*clone the Reposiortie from this link 'git clone https://github.com/RishiDoshi3103/CSC_335-Final-Project.git'*" 

## Step 2: Run the Game
"*java -jar CribbageGame.jar*"

## 🎮 Gameplay Flow

### Choose Game Mode:
- `1`: Human vs Human  
- `2`: Human vs Computer  
- `3`: Computer vs Computer

### Enter Player Names:
- Prompted only for human players

### Gameplay:
- Discard cards to the crib
- Play cards turn-by-turn
- Scoring is automatic (handled by game logic)

### Game End:
- First player to reach **61 points** wins
- Option to **replay** after the game ends

---

## 📦 Dependencies

- Java SE (JDK 8 or later)
- No external libraries required

## 🙋 Authors

- **Rishi Doshi**
- **Kyle Becker**
- **Kyle Velasco**
- **Liam Norris**
