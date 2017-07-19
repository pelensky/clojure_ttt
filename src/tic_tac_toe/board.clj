(ns tic-tac-toe.board)

(defn take-turn [space board]
  (if (not (.contains board space))
    (conj board space)
    board))

(defn check-value-of-space [space board]
  (cond
    (not(.contains board space)) nil
    (even? (.indexOf board space)) "X"
    (odd?  (.indexOf board space)) "O"))

(defn convert-board [board]
  (vec
    (for [space (range 9)]
      (check-value-of-space space board))))

(defn split-board-into-rows [full-board]
  (vec
    ( for [row (partition 3 full-board)]
      (vec row))))

(defn- split-board-into-columns [rows]
  (apply mapv vector rows))

(defn- split-left-diagonal [rows accumulator current-index]
  (if (>= current-index (count rows))
    (conj [] accumulator)
    (recur rows
      (conj accumulator (get ( get rows current-index) current-index))
      (inc current-index))))

(defn- split-right-diagonal [rows accumulator current-row-index current-column-index]
  (if (>= current-row-index (count rows))
    (conj [] accumulator)
    (recur rows
      (conj accumulator (get (get rows current-row-index) current-column-index))
      (inc current-row-index)
      (dec current-column-index))))

(defn winning-scenarios [board]
    (let [full-board (convert-board board)
          rows (split-board-into-rows full-board)
          columns (split-board-into-columns rows)
          left-diagonal (split-left-diagonal rows [] 0)
          right-diagonal (split-right-diagonal rows [] 0 (- (count rows) 1)) ]
            (into [] (concat rows columns left-diagonal right-diagonal))))

(defn line-won-by? [marker line]
  (every? (partial = marker) line))

(defn game-won-by? [marker board]
  (some?
    (some true?
     (for [line (winning-scenarios board)]
      (line-won-by? marker line)))))

(defn game-tied? [board]
  (and (= 9 (count board)) (not (game-won-by? "X" board)) (not (game-won-by? "O" board))))

(defn game-over? [board]
  (or (game-won-by? "X" board) (game-won-by? "O" board) (game-tied? board)))
