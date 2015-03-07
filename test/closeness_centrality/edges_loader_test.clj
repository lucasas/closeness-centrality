(ns closeness-centrality.edges_loader-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.edges_loader :refer :all]))

(deftest test-load!
  (let [edges (load! "resources/tests/edges")]
    (is (= (count edges) 5))
    (is (= edges [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]]))))

(deftest test-edges
  (def vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]])

  (let [edges (edges vertices)]
    (is (= (count edges) 5))
    (is (= (get edges :4) {:3 1, :5 1}))
    (is (= (get edges :3) {:2 1, :4 1}))
    (is (= (get edges :2) {:1 1, :5 1, :3 1}))
    (is (= (get edges :1) {:2 1}))))
