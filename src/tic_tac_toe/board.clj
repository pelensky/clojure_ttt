(ns tic-tac-toe.board)

(defn get-board [board-state]
  (get board-state :board))

(defn get-size [board-state]
  (get board-state :size))

(defn place-marker [space board-state]
  (let [board (get-board board-state)
        size (get-size board-state)
        uuid (get board-state :uuid)]
    (if (not (.contains board space))
      (update-in board-state [:board] conj space )
      board-state)))

(defn check-value-of-space [space board]
  (cond
    (not(.contains board space)) nil
    (even? (.indexOf board space)) "X"
    (odd?  (.indexOf board space)) "O"))

(defn convert-board-to-full-board [board-state]
  (let [board (get-board board-state)
        size (get-size board-state)]
    (vec
      (for [space (range (* size size))]
        (check-value-of-space space board)))))

(defn rows [size]
  (mapv (fn [row-start] (range row-start (+ row-start size)))
        (mapv (fn [first-row] (* first-row size)) (range size))))

(defn columns [size]
  (mapv (fn [starting-index] (range starting-index (* size size) size))
        (range size)))

(defn- left-diagonal [size]
  (range 0 (* size size) (inc size)))

(defn- right-diagonal [size]
  (range (dec size) (dec (* size size)) (dec size)))

(defn diagonals [size]
  [(left-diagonal size) (right-diagonal size)])

(defn winning-positions [size]
  (into [] (concat (rows size) (columns size) (diagonals size) )))

(defn winning-scenarios [board-state]
  (let [size (get-size board-state)
        full-board (convert-board-to-full-board board-state)]
    (for [scenario (winning-positions size)]
      (map #(get full-board %) scenario))))

(defn line-won-by? [marker line]
  (every? (partial = marker) line))

(defn game-won-by? [marker board-state]
  (some?
    (some true?
          (for [line (winning-scenarios board-state)]
            (line-won-by? marker line)))))

(defn game-tied? [board-state]
  (let [board (get-board board-state)
        size (get-size board-state)]
    (and (= (* size size) (count board)) (not (game-won-by? "X" board-state)) (not (game-won-by? "O" board-state)))))

(defn game-over? [board-state]
  (or (game-won-by? "X" board-state) (game-won-by? "O" board-state) (game-tied? board-state)))

(defn- check-if-spaces-are-available [board-state]
  (let [board (get-board board-state)
        size (get-size board-state)]
    (for [space (range (* size size))]
      (if
        (not (some (partial = space) board))
        space))))

(defn find-available-spaces [board-state]
  (remove nil?
          (vec (check-if-spaces-are-available board-state) )))

