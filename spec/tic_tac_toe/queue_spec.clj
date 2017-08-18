(ns tic-tac-toe.queue-spec
  (require [speclj.core :refer :all]
           [tic-tac-toe.queue :refer :all]))

(describe "Queue"

          (it "creates a uuid"
              (should= 36
                       (count (create-uuid))))
          )
