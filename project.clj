(defproject tic_tac_toe "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main tic-tac-toe.core
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.6.1"]
                 [cheshire "5.7.1"]
                 [clj-time "0.14.0"]
                 [com.cemerick/bandalore "0.0.6" :exclusions [joda-time]]]
  :profiles {:dev {:dependencies [[speclj "3.3.2"]]}}
  :plugins [[speclj "3.3.2"] [lein-cloverage "1.0.9"]]
  :test-paths ["spec"])

