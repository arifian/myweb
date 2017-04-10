(ns dbdev
  (:require [app.datomic :as db]
            [clojure.tools.namespace.repl :refer [refresh]]))

(defn exitdbdev
  []
  "switch to user ns"
  (println "\nloading user ns... \n")
  (require 'user)
  (in-ns 'user))
