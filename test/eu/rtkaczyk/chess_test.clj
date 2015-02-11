(ns eu.rtkaczyk.chess-test
  (:require [clojure.test :refer :all]
            [eu.rtkaczyk.chess :refer :all]))


(deftest view-board-test
  (testing "View board"

    (is (= (view-board 2 2 [[0 :bishop] [2 :knight]])
           (str "B ." "\n"
                "N ." "\n")))

    (is (= (view-board 3 3 [[0 :king] [2 :bishop] [7 :rook]])
           (str "K . B" "\n"
                ". . ." "\n"
                ". R ." "\n")))

    (is (= (view-board 4 4 [[1 :queen] [7 :rook] [12 :bishop] [14 :king]])
           (str ". Q . ." "\n"
                ". . . R" "\n"
                ". . . ." "\n"
                "B . K ." "\n")))))


(defn count-solutions [[f r] pcs]
  (count (recursive f r (apply piece-seq pcs))))

(deftest solutions
  (testing "Number of solutions"

    (is (= (count-solutions [2 2] [1 0 0 1 0]) 0))

    (is (= (count-solutions [3 3] [2 0 1 0 0]) 4))

    (is (= (count-solutions [2 5] [0 0 2 0 0]) 20))

    (is (= (count-solutions [4 4] [0 0 2 0 4]) 8))

    (is (= (count-solutions [4 4] [0 0 0 6 0]) 16))

    (is (= (count-solutions [4 4] [0 1 0 0 2]) 40))

    (is (= (count-solutions [6 6] [0 6 0 0 0]) 4))

    (is (= (count-solutions [7 7] [0 7 0 0 0]) 40))))