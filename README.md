# Sudoku Solver

A full-stack Sudoku solver with a clean minimal frontend and a Java-powered backend. Enter any puzzle, hit **Solve**, and your Java code finds the solution instantly using recursive backtracking.

🔗 **Live Demo:** https://sudoku-solver-chi-ten.vercel.app/

---

## Features

- **Java Backtracking Solver** — the core algorithm runs on a Spring Boot backend, solving any valid puzzle using recursive backtracking
- **Interactive Grid** — click any cell and type, or use the numpad row to fill in numbers
- **Keyboard Navigation** — move between cells with arrow keys
- **Row / Column / Box Highlight** — selecting a cell highlights all related cells so you can spot conflicts visually
- **Check** — validates the current board and highlights conflicts in red
- **Hint** — reveals one random empty cell from the solution
- **Presets** — load Easy, Medium, or Hard puzzles to get started quickly
- **Clear** — reset the board in one click

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | HTML, CSS, Vanilla JS |
| Backend | Java 21, Spring Boot 3 |
| Frontend Hosting | Vercel |
| Backend Hosting | Railway |
| Build Tool | Maven |

---

## How It Works

1. The frontend collects the 9×9 board as a flat array of 81 integers (0 for empty)
2. It sends a `POST` request to the Spring Boot API:
```json
POST /api/solve
{ "board": [5,3,0,0,7,0,...] }
```
3. The Java solver fills empty cells using recursive backtracking — trying digits 1–9, checking row/column/box constraints, and backtracking on conflicts
4. The solved board is returned as JSON and rendered in green on the frontend:
```json
{ "success": true, "board": [5,3,4,6,7,8,...] }
```

---

## Running Locally

### Prerequisites
- Java 21
- Maven 3.9+

### Backend
```bash
cd sudoku
mvn spring-boot:run
# Server starts at http://localhost:8080
```

### Frontend
Open `index.html` directly in your browser — no server needed.

Make sure the API URL in `index.html` points to localhost:
```js
const API = 'http://localhost:8080/api/solve';
```

---

## Project Structure

```
sudoku/
├── src/
│   └── main/
│       └── java/com/sudoku/sudoku/
│           └── SudokuController.java   # solver + REST endpoint
├── index.html                          # frontend
├── Dockerfile                          # for Railway deployment
└── pom.xml                             # Maven config
```

---

## API Reference

### `POST /api/solve`

**Request body:**
```json
{
  "board": [5,3,0,0,7,0,0,0,0, ...]   // 81 integers, 0 = empty
}
```

**Response:**
```json
{
  "success": true,
  "board": [5,3,4,6,7,8,9,1,2, ...]   // 81 integers, fully solved
}
```

If no solution exists:
```json
{ "success": false, "board": null }
```

---

## Deployment

- **Frontend** is deployed on [Vercel](https://vercel.com) — push to `main` branch and it auto-deploys
- **Backend** is deployed on [Railway](https://railway.app)  — Railway auto-detects and builds on every push

---

## Author

**Krishna Mishra**  
GitHub: [@Krishna18-dev](https://github.com/Krishna18-dev)
