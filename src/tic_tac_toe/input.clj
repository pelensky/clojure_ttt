(ns tic-tac-toe.input
  (:require [clojure.string :as string]))


(def conversions
  { "A1" 0 "B1" 1 "C1" 2
    "A2" 3 "B2" 4 "C2" 5
    "A3" 6 "B3" 7 "C3" 8})

(defn get-input []
  (read-line))

(defn selection []
  (let [converted-selection (get conversions
    (string/upper-case
      (string/trim
        (read-line))))]
          (if (not (nil? converted-selection))
            converted-selection
            (recur))))
