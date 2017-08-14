(ns tic-tac-toe.queue
  (:require [cemerick.bandalore :as sqs]))

(def client (sqs/create-client (get (System/getenv) "AWS_ID") (get (System/getenv) "AWS_SECRET_KEY")))
(def queue (sqs/create-queue client "InProgressGames"))
(def uuid (str(java.util.UUID/randomUUID)))

(defn send-uuid-to-queue []
  (sqs/send client queue uuid))

