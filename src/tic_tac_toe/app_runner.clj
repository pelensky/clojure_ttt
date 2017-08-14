(ns tic-tac-toe.app-runner
  (:require [tic-tac-toe.board :as ttt-board]
            [tic-tac-toe.player-type :as player-type]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.random-computer :as random-computer]
            [tic-tac-toe.input :as input]
            [tic-tac-toe.output :as output]
            [clj-http.client :as client]
            [cemerick.bandalore :as sqs]))

(def client (sqs/create-client (get (System/getenv) "AWS_ID") (get (System/getenv) "AWS_SECRET_KEY")))
(def uuid (str(java.util.UUID/randomUUID)))
(def play-again-selection 1)
(def player-x 0)
(def player-o 1)
(def max-players 2)

(declare play)

(defn play-again [selection]
  (if (= selection play-again-selection)
    (play)
    (output/print-message (output/exiting))))

(defn end-of-game [board-state]
  (output/clear-screen)
  (output/print-message (output/game-over board-state))
  (output/print-message (output/format-board board-state))
  (output/print-message (output/play-again))
  (play-again (input/get-number)))

(defn current-player [board-state players]
  (let [board (ttt-board/get-board board-state)]
    (if (even? (count board))
      (get players player-x)
      (get players player-o))))

(defn- api-call [board-state]
  (let [result (client/post "https://xast1bug7h.execute-api.us-east-1.amazonaws.com/ttt" {:form-params {"boardState" (str board-state)} :content-type :json} )]
    (read-string (get result :body))))

(defn- player-move [board-state player]
  (case player
    :human (human/choose-space)
    :random-computer (random-computer/choose-space board-state)
    :unbeatable-computer (api-call board-state)))

(defn single-turn [board-state players]
  (let [player (current-player board-state players)]
      (ttt-board/place-marker (player-move board-state player) board-state)))

(defn game-runner [board-state players]
  (output/clear-screen)
  (output/print-message (output/take-turn board-state))
  (output/print-message (output/format-board board-state))
  (let [updated-board (single-turn board-state players)]
    (if (ttt-board/game-over? updated-board)
      (end-of-game updated-board)
      (recur updated-board players))))

(defn select-players [players]
  (output/print-message (output/player-type (if (empty? players) "X" "O")))
  (let [updated-players (player-type/select-players players (player-type/select-player (input/get-number)))]
    (if (= max-players (count updated-players ))
      (game-runner {:size 3 :board []} updated-players)
      (recur updated-players))))

(defn play []
  (output/clear-screen)
  (output/print-message (output/welcome))
  (select-players []))
