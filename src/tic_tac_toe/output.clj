(ns tic-tac-toe.output
  (:require [tic-tac-toe.board :as ttt-board]))

(defn welcome []
  "Tic Tac Toe")

(defn player-or-spectator []
  "Select your role\n1) Player\n2) Spectator")

(defn take-turn [board-state]
  (str (if (even? (count (ttt-board/get-board board-state))) "X" "O") ", take your turn"))

(defn- update-space [value]
  (if (nil? value)
    " "
    value))

(defn split-board-into-rows [full-board size]
  (vec
    ( for [row (partition size full-board)]
      (vec row))))

(defn- replace-nil-values-with-spaces [board-state]
  (let [full-board (ttt-board/convert-board-to-full-board board-state)]
    (for [space full-board]
      (update-space space))))

(defn- format-row [number row]
  (str number " " (clojure.string/join " | " row) "\n"))

(defn- format-rows [board-state]
  (let [board (ttt-board/get-board board-state)
        size (ttt-board/get-size board-state)
        split (map-indexed vector (split-board-into-rows (replace-nil-values-with-spaces board-state) size))]
    (for [row split]
      (format-row (inc (get row 0)) (get row 1)))))

(defn format-board [board-state]
  (let [formatted (format-rows board-state)]
    (str "  A | B | C\n"
         (clojure.string/join "------------\n" formatted))))

(defn- tied-game []
  "Game Tied")

(defn- won-game [board-state]
  (str
    (if (ttt-board/game-won-by? "X" board-state ) "X" "O") " is the winner"))

(defn play-again []
  "Play again?\n1) Yes\n2) No")

(defn game-over [board-state]
  (str "Game Over\n"
       (if (ttt-board/game-tied? board-state)
         (tied-game)
         (won-game board-state))))

(defn player-type [marker]
  (str "Select player type for " marker "\n" "1) Human\n" "2) Random Computer\n" "3) Unbeatable Computer"))

(defn exiting []
  "Exiting")

(defn print-message [message]
  (println message))

(defn clear-screen []
  (print (str (char 27) "[2J"))
  (print (str (char 27) "[;H")))
