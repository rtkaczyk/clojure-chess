(ns eu.rtkaczyk.chess
  (:gen-class)
  (:require [clojure.string :as str]))
(load "algo")

(defn str-to-int [s]
  (Integer/parseInt s))

(defn view-board [files ranks pieces-on-board]
  (let [pieces-repr   {:king "K", :queen "Q", :rook "R", :bishop "B", :knight "N"}
        empty-board   (into {} (map #(vec [% "."]) (range (* files ranks))))
        with-pieces   (reduce (fn [acc [i p]] (assoc acc i (p pieces-repr))) empty-board pieces-on-board)
        as-rows       (->>  with-pieces vec (sort-by first) (map second) (partition files) (map (partial str/join " ")))]
    (str (str/join "\n" as-rows) "\n")))

(defn -main [& args]
  (time (let [[files ranks k q r b n] (map str-to-int (str/split (read-line) #"\s+"))
              solutions (recursive files ranks (piece-seq k q r b n))]
          (do (when-not (empty? solutions)
                (println (view-board files ranks (first solutions))))
              (println (str (count solutions) " solutions found"))))))

