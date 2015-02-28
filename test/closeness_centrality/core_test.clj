(ns closeness-centrality.core-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.core :refer :all]))

(deftest test-edges
  (def vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]])

  (let [edges (edges vertices)]
    (is (= (count edges) 4))
    (is (= (get edges :4) {:5 1}))
    (is (= (get edges :3) {:4 1}))
    (is (= (get edges :2) {:5 1, :3 1}))
    (is (= (get edges :1) {:2 1}))))
