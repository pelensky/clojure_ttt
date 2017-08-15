(ns tic-tac-toe.notifications
  (:use [amazonica.aws.sns]))

(defn create-game [id]
  (create-topic :name id))

(defn send-move [board-state]
  (let [region (get (System/getenv) "AWS_REGION")
        account-number (get (System/getenv) "AWS_ACCOUNT_ID")
        id (get board-state :uuid)]
    (println region)
    (println account-number)
    (println id)
    (publish :topic-arn (str "arn:aws:sns:" region ":" account-number ":" id)
                 :subject "Move"
                 :message (str board-state)
                 :message-attributes {"attr" "value"})))
