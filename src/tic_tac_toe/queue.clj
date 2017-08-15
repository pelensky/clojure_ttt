(ns tic-tac-toe.queue
  (require [amazonica.aws.sqs :as aws]))

(defn- get-environment-variable [name]
  (str (get (System/getenv) name)))

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

(defn get-game-ids []
  (map #(get % :body) (get (tic-tac-toe.queue/get-messages) :messages)))
