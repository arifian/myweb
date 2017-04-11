(ns atomdev
  (:require [app.atom :refer :all]
            [clojure.tools.namespace.repl :refer [refresh]]))

(defn exitatomdev
  []
  "switch to user ns"
  (println "\nloading user ns... \n")
  (require 'user)
  (in-ns 'user))
