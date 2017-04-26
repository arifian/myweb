(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

;; This is an old trick from Pedestal. When system.clj doesn't compile,
;; it can prevent the REPL from starting, which makes debugging very
;; difficult. This extra step ensures the REPL starts, no matter what.

(defn dev
  []
  "switch to dev ns"
  (println "\nloading dev mode... \n")
  (require 'dev)
  (in-ns 'dev))
