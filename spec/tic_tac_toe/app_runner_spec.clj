(ns tic-tac-toe.app-runner-spec
  (:require [speclj.core :refer :all]
            [tic-tac-toe.notifications :refer :all :as notifications]
            [tic-tac-toe.app-runner :refer :all]))

(describe "App Runner"
          (with-stubs)

          (it "Prints Welcome"
              (with-redefs [notifications/send-move (stub :send-move)]
                (should-contain "Tic Tac Toe"
                                (with-out-str (with-in-str "1\n1\n1\na1\nb2\na2\na3\nc1\nb1\nb3\nc2\nc3\n2"
                                                (start))))))

          (it "Prints players turn"
              (with-redefs [notifications/send-move (stub :send-move)]
                (should-contain "X, take your turn"
                                (with-out-str (with-in-str "1\n1\n1\na1\nb2\na2\na3\nc1\nb1\nb3\nc2\nc3\n2"
                                                (start))))))

          (it "Plays game until board is full"
              (with-redefs [notifications/send-move (stub :send-move)]
                (should-contain "Game Over"
                                (with-out-str (with-in-str "1\n1\n1\na1\nb2\na2\na3\nc1\nb1\nb3\nc2\nc3\n2"
                                                (start))))))

          (it "Plays game until game is tied"
              (with-redefs [notifications/send-move (stub :send-move)]
                (should-contain "Game Tied"
                                (with-out-str (with-in-str "1\n1\n1\na1\nb2\na2\na3\nc1\nb1\nb3\nc2\nc3\n2"
                                                (start))))))

          (it "Plays game unitl X wins"
              (with-redefs [notifications/send-move (stub :send-move)]
                (should-contain "X is the winner"
                                (with-out-str (with-in-str "1\n1\n1\na1\na3\nb2\nb1\nc3\n2"
                                                (start))))))

          (it "Plays game unitl O wins"
              (with-redefs [notifications/send-move (stub :send-move)]
                (should-contain "O is the winner"
                                (with-out-str (with-in-str "1\n1\n1\na1\nc1\nb2\nc2\na3\nc3\n2"
                                                (start))))))

          (it "finds the current player type"
              (should= :human
                       (current-player {:size 3 :board []} [:human :computer])))

          (it "finds a computer player"
              (should= :computer
                       (current-player {:size 3 :board [1]} [:human :computer])))

          (it "plays until the game is over when two computers play each other"
              (with-redefs [notifications/send-move (stub :send-move)]
                (should-contain "Game Over"
                                (with-out-str (with-in-str "1\n2\n2\n2"
                                                (start)))))))
