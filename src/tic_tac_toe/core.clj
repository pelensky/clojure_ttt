(ns tic-tac-toe.core
  (:require [tic-tac-toe.app-runner :as ttt-app-runner]))

(defn -main [& args]
  (ttt-app-runner/start))
