(ns exp.multi
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn back
  []
  "switch to test.clj ns"
  (println "\nloading test.core mode... \n")
  (require 'exp.core)
  (in-ns 'exp.core))

(def db (atom nil))

(defmulti initdb (fn [type name] type))

(defmethod initdb :atm
  [type name]
  (str db " " name))

(defmethod initdb :dtm
  [type name]
  (str db " " name))

(defmethod initdb :default
  [type name]
  (str "very wow, much method, very multi"))

(initdb :atm "very wow, much method, very multi")
