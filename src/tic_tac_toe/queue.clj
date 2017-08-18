(ns tic-tac-toe.queue
  (require [amazonica.aws.sqs :as sqs]
           [amazonica.core :as aws]
           [clojure.data.json :as json])
  (import [com.amazonaws.auth.policy Statement Statement$Effect
           Principal
           Policy
           Resource
           Condition
           Action]
          [com.amazonaws.auth.policy.actions SQSActions]
          [com.amazonaws.auth.policy.conditions ConditionFactory]))

(def watching-queue (sqs/find-queue "WatchingGame"))
(def queue-url "https://sqs.eu-west-1.amazonaws.com/549374948510/WatchingGame" )
(def queue-arn (-> queue-url sqs/get-queue-attributes :QueueArn))
(def topic-arn "arn:aws:sns:eu-west-1:549374948510:tic-tac-toe")
(def policy (Policy.
                      (str queue-arn "/SQSDefaultPolicy")
                         [(doto (Statement. Statement$Effect/Allow)
                            (.setPrincipals [Principal/AllUsers])
                            (.setResources [(Resource. queue-arn)])
                            (.setActions [SQSActions/SendMessage]))]))

(defn set-queue-permission []
  (sqs/set-queue-attributes queue-url {"Policy" (.toJson policy)}))

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
