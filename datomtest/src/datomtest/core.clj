(ns datomtest.core
  (require [clojure.core.async :refer (<!!)]
           [datomic.client :as client]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

