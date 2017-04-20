(ns exp.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

;; This is an old trick from Pedestal. When system.clj doesn't compile,
;; it can prevent the REPL from starting, which makes debugging very
;; difficult. This extra step ensures the REPL starts, no matter what.

(defn datom
  []
  "switch to datom.clj ns"
  (println "\nloading datom mode... \n")
  (require 'exp.datom)
  (in-ns 'exp.datom))

(defn abstractions
  []
  "switch to abstractions.clj ns"
  (println "\nloading abstractions mode... \n")
  (require 'exp.abstractions)
  (in-ns 'exp.abstractions))
