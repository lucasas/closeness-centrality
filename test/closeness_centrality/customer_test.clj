(ns closeness-centrality.customer-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.customer :refer :all]))

(deftest test-customer
  (let [customer (Customer :1 0.98)]
    (is (= customer {:id :1 :score 0.98}))))

(deftest test-all
  (let [customers (all {:1 0.98 :2 0.78})]
    (is (= (count customers) 2))
    (is (= (first customers) {:id :1 :score 0.98}))
    (is (= (last customers) {:id :2 :score 0.78}))))
