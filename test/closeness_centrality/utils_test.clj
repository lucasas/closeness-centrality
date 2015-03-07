(ns closeness-centrality.utils-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.utils :refer :all]))

(deftest test-sort-map-by-value
  (let [sort-map (sorted-map-by-value {:a 2 :c 3 :b 1})]
    (is (= sort-map {:c 3 :a 2 :b 1}))))
