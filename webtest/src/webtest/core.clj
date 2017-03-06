(ns webtest.core) ;@squest's tests

(defn testfoo
  "I don't do a whole lot foo."
  [x]
  (println x "Well Hello, Shithead!"))

(set! *unchecked-math* true)

(defn sieve
  [^long lim]
  (let [llim (inc (int (Math/sqrt lim)))
        primes (boolean-array (inc lim) true)]
    (loop [i (int 3) res (long 2)]
      (if (> i lim)
        res
        (if (<= i llim)
          (if (aget primes i)
            (do (loop [j (int (* i i))]
                  (when (<= j lim)
                    (aset primes j false)
                    (recur (+ j i i))))
                (recur (+ i 2) (+ i res)))
            (recur (+ i 2) res))
          (if (aget primes i)
            (recur (+ i 2) (+ i res))
            (recur (+ i 2) res)))))))

(defn -main [& args]
  (dotimes [i 5]
    (println "Environment speed test : ")
    (time (sieve 2000))))
