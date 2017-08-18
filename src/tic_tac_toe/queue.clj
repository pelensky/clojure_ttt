(ns tic-tac-toe.queue
  (require [amazonica.aws.sqs :as sqs]
           [clojure.data.json :as json]))

(def watching-queue (sqs/find-queue "WatchingGame"))

(defn create-uuid []
  (str (java.util.UUID/randomUUID)))

(defn create-subscriber-queue [id]
  (sqs/create-queue :queue-name id
                :attributes
                {:VisibilityTimeout 30 ; sec
                 :MaximumMessageSize 65536 ; bytes
                 :MessageRetentionPeriod 1209600 ; sec
                 :ReceiveMessageWaitTimeSeconds 10}) ; sec)
)

  (defn get-messages [queue]
    (get
      (sqs/receive-message :queue-url queue
                           :wait-time-seconds 6
                           :max-number-of-messages 10
                           :delete true
                           :attribute-names ["All"])  :messages))

  (defn get-game-states [messages]
    (let [bodies (vec (set (map #(get % :body)  messages)))
          json-bodies (map #(json/read-str %) bodies)]
      (vec (map #(get % "Message") json-bodies))))
