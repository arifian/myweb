(ns exp.stuff
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn back
  []
  "switch to test.clj ns"
  (println "\nloading test.core mode... \n")
  (require 'exp.core)
  (in-ns 'exp.core))


