(ns tic-tac-toe.app-runner
  (:require [tic-tac-toe.board :as ttt-board]
            [tic-tac-toe.player-type :as player-type]
            [tic-tac-toe.human :as human]
            [tic-tac-toe.random-computer :as random-computer]
            [tic-tac-toe.input :as input]
            [tic-tac-toe.output :as output]
            [tic-tac-toe.spectator :as spectator]
            [tic-tac-toe.queue :as queue]
            [tic-tac-toe.notifications :as notifications]
            [clj-http.client :as client]))

(def selection-1 1)
(def player-x 0)
(def player-o 1)
(def max-players 2)
(def two [1 2])
(def three [1 2 3])

(declare start)

(defn play-again [selection]
  (if (= selection selection-1)
    (start)
    (output/print-message (output/exiting))))

(defn end-of-game [board-state]
  (notifications/send-move board-state)
  (output/clear-screen)
  (output/print-message (output/game-over board-state))
  (output/print-message (output/format-board board-state))
  (output/print-message (output/play-again))
  (play-again (input/get-number two)))

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
  (notifications/send-move board-state)
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

(defn select-players [players uuid]
  (output/print-message (output/player-type (if (empty? players) "X" "O")))
  (let [updated-players (player-type/select-players players (player-type/select-player (input/get-number three)))]
    (if (= max-players (count updated-players ))
      (game-runner {:uuid uuid :size 3 :board []} updated-players)
      (recur updated-players uuid))))

(defn- get-ongoing-game [spectator-id]
  (let [games (queue/get-games spectator-id)
        moves (queue/get-moves games)]
    (doall  (for [move moves]
              (output/print-message (output/format-board (read-string  move)))))
    (recur spectator-id)))

(defn spectate []
  (let [spectator-id (queue/create-uuid)]
    (queue/create-subscriber-queue spectator-id)
    (queue/set-queue-permission spectator-id)
    (dorun (notifications/subscribe-to-games spectator-id))
    (get-ongoing-game spectator-id)) )

(defn play []
  (let [game-uuid (queue/create-uuid)]
    (select-players [] game-uuid)))

(defn select-player-or-spectator []
  (output/print-message (output/player-or-spectator))
  (let [choice (input/get-number two)]
    (if (= selection-1 choice)
      (play)
      (spectate))))

(defn start []
  (output/clear-screen)
  (output/print-message (output/welcome))
  (select-player-or-spectator))
