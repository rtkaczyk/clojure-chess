(in-ns 'eu.rtkaczyk.chess)
(load "board")


(defn piece-seq [kings queens rooks bishops knights]
  (flatten
    (map (partial apply repeat)
         [[queens  :queen]
          [rooks   :rook]
          [bishops :bishop]
          [kings   :king]
          [knights :knight]])))


(defn recursive [files ranks pieces]
  (let [threatens? (cached-attacks files ranks (set pieces))
        squares    (set (range (* files ranks)))
        init-board (->Board [] pieces squares 0)
        solve      (fn solve [board]
                     (cond
                       (empty? (:pieces-left board)) [(:pieces-on-board board)]
                       (empty? (:safe-squares board)) []
                       :else (mapcat solve (put-next-piece board threatens?))))]
    (solve init-board)))
