(ns tic-tac-toe.queue
  (require [amazonica.aws.sqs :as aws]
           [clojure.data.json :as json]))

(def games-queue (aws/find-queue "InProgressGames"))
(def watching-queue (aws/find-queue "WatchingGame"))

(defn create-uuid []
  (str (java.util.UUID/randomUUID)))

(defn create-subscriber-queue [id]
  (aws/create-queue :queue-name id
                :attributes
                {:VisibilityTimeout 30 ; sec
                 :MaximumMessageSize 65536 ; bytes
                 :MessageRetentionPeriod 1209600 ; sec
                 :ReceiveMessageWaitTimeSeconds 10}) ; sec)
)

  (defn send-uuid-to-queue [uuid]
    (println (aws/list-queues))
    (aws/send-message games-queue uuid))

  (defn get-messages [queue delete?]
    (get
      (aws/receive-message :queue-url queue
                           :wait-time-seconds 6
                           :max-number-of-messages 10
                           :delete delete?
                           :attribute-names ["All"])  :messages))

  (defn get-game-states [messages]
    (let [bodies (vec (set (map #(get % :body)  messages)))
          json-bodies (map #(json/read-str %) bodies)]
      (vec (map #(get % "Message") json-bodies))))

  (defn get-game-ids [messages]
    (vec (set (map #(get % :body) messages))))
