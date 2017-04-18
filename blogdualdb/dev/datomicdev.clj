(ns datomicdev
  (:require [app.datomic :refer :all]
            [clojure.tools.namespace.repl :refer [refresh]]))

(defn exitdatomicdev
  []
  "switch to user ns"
  (println "\nloading user ns... \n")
  (require 'user)
  (in-ns 'user))
