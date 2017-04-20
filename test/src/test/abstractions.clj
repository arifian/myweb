(ns test.abstractions
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [test.were_records :refer [WereWolf]])
  (:import [were_records WereWolf]))

(defn exit
  []
  "switch to test.clj ns"
  (println "\nloading test.core mode... \n")
  (require 'test.core)
  (in-ns 'test.core))

;;;records

(WereWolf. "David" "London Tourist")

(def jacob (->WereWolf "Jacob" "Lead Shirt Discarder"))
(.name jacob) 
; => "Jacob"

(:name jacob) 
; => "Jacob"

(get jacob :name) 
; => "Jacob"


;;;;;; protocol

(defprotocol Psychodynamics
  "Plumb the inner depths of your data types"
  (thoughts [x] "The data type's innermost thoughts")
  (feelings-about [x] [x y] "Feelings about self or other"))

(extend-protocol Psychodynamics
  java.lang.String
  (thoughts [x] (str x " thinks, " "'Truly, the character defines the data type'"))
  (feelings-about
    ([x] (str x " is longing for a simpler way of life"))
    ([x y] (str "envious of " y "'s simpler way of life")))
  
  java.lang.Object
  (thoughts [x] "Maybe the Internet is just a vector for toxoplasmosis")
  (feelings-about
    ([x] "meh")
    ([x y] (str x " meh about " y))))

(thoughts "blorb")
; => "blorb thinks, 'Truly, the character defines the data type'"

(feelings-about "schmorb")
; => "schmorb is longing for a simpler way of life"

(feelings-about "schmorb" 2)
; => "schmorb is envious of 2's simpler way of life"

(thoughts 3)
;; => "Maybe the Internet is just a vector for toxoplasmosis"

(feelings-about 3)
;; => "meh"

(feelings-about 3 3)
;; => "3 meh about 3"

;;;; multi method

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
