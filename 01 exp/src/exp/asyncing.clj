(ns exp.asyncing
  (:require [clojure.tools.namespace.repl :refer [refresh]]))

(defn back
  []
  "switch to test.clj ns"
  (println "\nloading test.core mode... \n")
  (require 'exp.core)
  (in-ns 'exp.core))

(def yak-butter-international
  {:store "Yak Butter International"
    :price 90
    :smoothness 90})
(def butter-than-nothing
  {:store "Butter Than Nothing"
   :price 150
   :smoothness 83})
;; This is the butter that meets our requirements
(def baby-got-yak
  {:store "Baby Got Yak"
   :price 94
   :smoothness 99})

(defn mock-api-call
  [result]
  (Thread/sleep 500)
  result)

(defn satisfactory?
  "If the butter meets our criteria, return the butter, else return false"
  [butter]
  (and (<= (:price butter) 100)
       (>= (:smoothness butter) 97)
       butter))

(time
 (some
  (comp satisfactory? mock-api-call)
  [yak-butter-international butter-than-nothing baby-got-yak]))

(let [butter-promise (promise)]
   (doseq [butter
           [yak-butter-international
            butter-than-nothing
            baby-got-yak]]
     (future
       (if-let
           [satisfactory-butter
            (satisfactory? (mock-api-call butter))]
         (deliver butter-promise satisfactory-butter))))
   (println "And the winner is:" (:store @butter-promise)))
