(ns tic-tac-toe.input
  (:require [clojure.string :as string]))

(def conversions
  { "A1" 0 "B1" 1 "C1" 2
   "A2" 3 "B2" 4 "C2" 5
   "A3" 6 "B3" 7 "C3" 8})

(defn- convert-coordinate-to-space []
  (get conversions
       (string/upper-case
         (string/trim
           (read-line)))))

(defn select-space []
  (let [converted-selection (convert-coordinate-to-space)]
    (if (not (nil? converted-selection))
      converted-selection
      (recur))))

(defn validate-integer []
  (or (try
        (Integer/parseInt (read-line))
        (catch Exception e))
      (recur)))

(defn- valid-selection [selection valid-options]
  (some (partial = selection) valid-options))

(defn get-number [valid-options]
  (let [selection (validate-integer)]
    (if (valid-selection selection valid-options)
      selection
      (recur valid-options))))
