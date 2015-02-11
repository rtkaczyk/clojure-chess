(in-ns 'eu.rtkaczyk.chess)
(load "moves")


(defrecord Board [pieces-on-board pieces-left safe-squares start-square])


(defn put-next-piece [board threatens?]
  (let [[piece & rem-pieces] (:pieces-left board)
        switch               (not= piece (first rem-pieces))
        possible-squares     (filter #(>= % (:start-square board)) (:safe-squares board))]

    (for [sq possible-squares
          :when (not-any? #(threatens? [piece sq (first %)]) (:pieces-on-board board))]

      (let [pieces-on-board (conj (:pieces-on-board board) [sq piece])
            rem-squares     (remove #(threatens? [piece sq %]) (:safe-squares board))
            next-square     (if switch 0 (inc sq))]
        (->Board pieces-on-board rem-pieces rem-squares next-square)))))


(defn cached-attacks [files ranks pieces]
  (->>
    (for [f  (range files)
          r  (range ranks)
          f' (range files)
          r' (range ranks)
          p  pieces]
      (let [coord-2-idx #(+ %1 (* %2 files))
            i  (coord-2-idx f r)
            i' (coord-2-idx f' r')]
        [[p i i'] (attacks? p [f r] [f' r'])]))
    (filter second)
    (map first)
    (into #{})))
