(ns example.math)


(defn fac [i]
  (if (zero? i) 1 (* i (fac (dec i)))))
