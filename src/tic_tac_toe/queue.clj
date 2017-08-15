(ns tic-tac-toe.queue
  (require [amazonica.aws.sqs :as aws]))

(def queue (aws/find-queue "InProgressGames"))

(defn create-uuid []
  (str (java.util.UUID/randomUUID)))

(defn send-uuid-to-queue [uuid]
  (aws/send-message queue uuid))

(defn get-messages []
  (aws/receive-message :queue-url queue
                       :wait-time-seconds 6
                       :max-number-of-messages 10
                       :delete false
                       :attribute-names ["All"]))

(defn get-game-ids [all-messages]
  (let [messages (get all-messages :messages)]
  (set (map #(get % :body) messages))))
