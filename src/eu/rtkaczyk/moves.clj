(in-ns 'eu.rtkaczyk.chess)

(defn dist [x y] (Math/abs (- x y)))

(defmulti attacks? (fn [piece from target] piece))

(defmethod attacks? :rook [_ [f r] [f' r']]
  (or (= f f')
      (= r r')))

(defmethod attacks? :bishop [_ [f r] [f' r']]
  (= (dist f f')
     (dist r r')))

(defmethod attacks? :queen [_ from target]
  (or (attacks? :rook from target)
      (attacks? :bishop from target)))

(defmethod attacks? :king [_ [f r] [f' r']]
  (and (<= (dist f f') 1)
       (<= (dist r r') 1)))

(defmethod attacks? :knight [_ [f r] [f' r']]
  (let [df (dist f f')
        dr (dist r r')
        d (+ df dr)]
    (or (= d 0)
        (and (= d 3)
             (> df 0)
             (> dr 0)))))