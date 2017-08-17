(ns tic-tac-toe.notifications
  (:use [amazonica.aws.sns]))

(def region (get (System/getenv) "AWS_REGION"))
(def account-number (get (System/getenv) "AWS_ACCOUNT_ID"))
(def topic "tic-tac-toe")

(defn create-game [id]
  (create-topic :name id))

(defn send-move [board-state]
  (let [arn (str "arn:aws:sns:" region ":" account-number ":" topic)]
    (publish :topic-arn arn
             :subject topic
             :message (str board-state)
             :message-attributes {"attr" "value"})))

(defn subscribe-to-game [id]
  (let [arn (str "arn:aws:sns:" region ":" account-number ":")]
    (subscribe :protocol "sqs"
               :topic-arn (str arn id)
               :endpoint (str "arn:aws:sqs:" region ":" account-number ":WatchingGame"))))
