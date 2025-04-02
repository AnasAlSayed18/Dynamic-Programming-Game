
# Optimal Coin Game Strategy with Dynamic Programming 🎮💰  
*Also known as "Optimal Strategy for a Game Using Dynamic Programming"*

![Coin Duel](https://github.com/AnasAlSayed18/img/blob/6c55f031f54b827b7df310d76e34a6ae808c9819/logo.ico)  

## Overview  
**DPCoinsGame** is a dynamic programming-based game where players strategically choose coins (either the first or last coin) during their turns to maximize their score. This game demonstrates the core concepts of dynamic programming, features a GUI built with JavaFX, and implements game logic using Java. 🎮✨

The **Singleton** design pattern is employed in the `NavigationManager` class to seamlessly navigate between different game panes. Additionally, the **Single Responsibility Principle** is followed to ensure that the code remains clean, modular, and easy to maintain. 🧑‍💻📦

## Dynamic Programming Logic  
**DP Relation Used**:  
![DP Relation](https://github.com/user-attachments/assets/be685956-1371-401f-9bf7-24599136ca87)  
Where ***i*** represents a pointer to the start of the coin array and ***j*** represents a pointer to the end.

**Initial Value**:
- If there's only one coin, take it.
- If there are two coins, take the one with the higher value.

## Features 🌟  
- **Dynamic Programming**: Implements a dynamic programming approach to optimize coin selection.  
- **Real-Time Updates**: UI updates as players make their moves, showing scores and coin selections. 📈
- **Animation**: Smooth animations of UI components during gameplay. ✨
- **Multiple Input Options**: Choose from manual input, random generation, or reading from a file. 🔢
- **Scoreboard**: Displays the current scores for both players. 🏆  
- **Game End**: Declares the winner once all coins have been selected. 🏅  
- Two game modes are available:  
  - **Two-Player Mode**: A classic turn-based mode where two players compete. 🤼‍♂️  
  - **DP-Game Mode**: The game plays out automatically, showcasing the dynamic programming logic (Computer vs. Computer). 🤖🤖  

## Sound & Buttons 🎵🔲  
This game features sound effects for actions like selecting coins and transitions between game screens. Sounds enhance the player's experience, making the gameplay more engaging. The buttons have been designed to be intuitive, with smooth interactions that make it easy to navigate through various game modes and options.

## Screenshots of Main Screens 📸  
### Start Screen  
![Main Screen](https://github.com/AnasAlSayed18/img/blob/1d081838e86c1f736e7a47fcdc37582aef2b7bc9/Screenshot%202025-04-02%20044706.png)  

### Data Input Options  
![Data Input Options](https://github.com/AnasAlSayed18/img/blob/1d081838e86c1f736e7a47fcdc37582aef2b7bc9/Screenshot%202025-04-02%20044720.png)  

### Random Data Input  
![Random Data Input ](https://github.com/AnasAlSayed18/img/blob/99e4ead5bd1a7abcb339116547d87f421ec98f17/Screenshot%202025-04-02%20044732.png)  

### Manual Data Input  
![Manual Data Input](https://github.com/AnasAlSayed18/img/blob/99e4ead5bd1a7abcb339116547d87f421ec98f17/Screenshot%202025-04-02%20050559.png)  

### Upload File Data Input  
![Upload File Data Input](https://github.com/AnasAlSayed18/img/blob/e283fe7bb1ea030ea15a964a6dff228b4a568326/image_2025-04-02_051038582.png)  

### DP Game Screen  
![DP Game Screen](https://github.com/AnasAlSayed18/img/blob/e283fe7bb1ea030ea15a964a6dff228b4a568326/Screenshot%202025-04-02%20044831.png)  

### DP Table  
![DP Table](https://github.com/AnasAlSayed18/img/blob/6c55f031f54b827b7df310d76e34a6ae808c9819/image_2025-04-02_051256047.png)  

## Demo Video  
[Click here to watch the demo :3](soon)
