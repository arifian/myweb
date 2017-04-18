(ns test.core
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

;; This is an old trick from Pedestal. When system.clj doesn't compile,
;; it can prevent the REPL from starting, which makes debugging very
;; difficult. This extra step ensures the REPL starts, no matter what.

(defn datom
  []
  "switch to datom.clj ns"
  (println "\nloading test.datom mode... \n")
  (require 'test.datom)
  (in-ns 'datom))

(defn protocol
  []
  "switch to protocol.clj ns"
  (println "\nloading test.protocol mode... \n")
  (require 'test.protocol)
  (in-ns 'protocol))
