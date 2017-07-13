(ns tic-tac-toe.input-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.input :refer :all]))

(describe "Input"

  (it "gets input"
    (should= "1"
      (with-in-str "1"
        (get-input))))

  (it "convers input to integer"
    (should= 1
      (with-in-str "1"
        (selection)))))
