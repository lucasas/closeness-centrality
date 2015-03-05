(ns closeness-centrality.edges_loader-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.edges_loader :refer :all]))

(deftest test-load!
  (let [edges (load! "resources/tests/edges")]
    (is (= (count edges) 5))
    (is (= edges [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]]))))
