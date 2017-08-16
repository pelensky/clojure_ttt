(ns tic-tac-toe.queue
  (require [amazonica.aws.sqs :as aws]))

(def games-queue (aws/find-queue "InProgressGames"))

(defn create-uuid []
  (str (java.util.UUID/randomUUID)))

(defn send-uuid-to-queue [uuid]
  (aws/send-message games-queue uuid))

(defn get-messages [queue]
  (get
    (aws/receive-message :queue-url queue
                         :wait-time-seconds 6
                         :max-number-of-messages 10
                         :delete false
                         :attribute-names ["All"]) :messages))

(defn get-game-ids [messages]
    (set (map #(get % :body) messages)) )
