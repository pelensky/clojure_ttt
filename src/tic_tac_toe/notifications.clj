(ns tic-tac-toe.notifications
  (require [clojure.data.json :as json])
  (:use [amazonica.aws.sns]))

(def region (get (System/getenv) "AWS_REGION"))
(def account-number (get (System/getenv) "AWS_ACCOUNT_ID"))
(def topic "tic-tac-toe")

(defn send-move [board-state]
  (let [arn (str "arn:aws:sns:" region ":" account-number ":" topic)]
    (publish :topic-arn arn
             :subject topic
             :message (json/write-str board-state)
             :message-attributes {"attr" "value"})))

(defn subscribe-to-games [id]
  (let [arn (str "arn:aws:sns:" region ":" account-number ":" topic)]
    (subscribe :protocol "sqs"
               :topic-arn arn
               :endpoint (str "arn:aws:sqs:" region ":" account-number ":" id))))
