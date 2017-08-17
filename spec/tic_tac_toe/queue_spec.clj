(ns tic-tac-toe.queue-spec
  (require [speclj.core :refer :all]
           [tic-tac-toe.queue :refer :all]))

(describe "Queue"

          (it "creates a uuid"
              (should= 36
                       (count (create-uuid))))

          (it "gets the game ids from the message queue"
              (should=  ["test1" "test2" "test3"]
                       (get-game-ids [{:body "test1"} {:body "test2"} {:body "test3"}]))))
