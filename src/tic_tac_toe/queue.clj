(ns tic-tac-toe.queue
  (require [amazonica.aws.sqs :as aws]))

(defn- get-environment-variable [name]
  (str (get (System/getenv) name)))

(def cred {:access-key (get-environment-variable "AWS_ACCESS_KEY_ID")
           :secret-key (get-environment-variable "AWS_SECRET_KEY")
           :endpoint (get-environment-variable "AWS_REGION")})

(def queue (aws/find-queue "InProgressGames"))

(defn create-uuid []
  (str (java.util.UUID/randomUUID)))

(defn send-uuid-to-queue [uuid]
  (aws/send-message queue uuid))

(defn get-game-uuids []
  (aws/receive-message 
                     :queue-url queue
                       :wait-time-seconds 6
                       :max-number-of-messages 10
                       :delete false
                       :attribute-names ["All"]))

