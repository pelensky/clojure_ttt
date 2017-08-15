(ns tic-tac-toe.input-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.input :refer :all]))

(describe "Input"

          (it "gets one or two"
              (should= 1
                       (with-in-str "1"
                         (get-number [1 2]))))

          (it "does not accept a letter"
              (should= 1
                       (with-in-str "dan\n1"
                         (get-number [1 2]))))

          (it "converts input to integer"
              (should= 0
                       (with-in-str "A1"
                         (select-space))))

          (it "converts different input to integer"
              (should= 4
                       (with-in-str "B2"
                         (select-space))))

          (it "converts lower case input to integer"
              (should= 8
                       (with-in-str "c3"
                         (select-space))))

          (it "strips white space from end"
              (should= 2
                       (with-in-str "c1 "
                         (select-space))))

          (it "strips white space from start"
              (should= 3
                       (with-in-str " A2"
                         (select-space))))

          (it "does not allow nil as a value"
              (should= 7
                       (with-in-str "dan \nb3"
                         (select-space)))))
