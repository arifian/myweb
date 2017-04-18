(ns test.protocol
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn exit
  []
  "switch to test.clj ns"
  (println "\nloading test.core mode... \n")
  (require 'test.core)
  (in-ns 'test.core))

(defmulti full-moon-behavior (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :wolf
  [were-creature]
  (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behavior :simmons
  [were-creature]
  (str (:name were-creature) " will encourage people and sweat to the oldies"))

(defmethod full-moon-behavior :kambing
  [were-creature]
  (str (:name were-creature) " will eat grass and what not"))

(full-moon-behavior {:were-type :wolf
                      :name "Rachel from next door"})
; => "Rachel from next door will howl and murder"

(full-moon-behavior {:name "Andy the baker"
                      :were-type :simmons})
; => "Andy the baker will encourage people and sweat to the oldies"

(full-moon-behavior {:name "Mbe"
                      :were-type :kambing})
;; => "Mbe will eat grass and what not"
