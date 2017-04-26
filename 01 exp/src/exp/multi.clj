(ns exp.multi
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

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
