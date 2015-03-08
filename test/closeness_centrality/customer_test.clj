(ns closeness-centrality.customer-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.customer :refer :all]))

(deftest test-customer
  (let [customer (Customer :1 {:1 0, :2 1, :3 2, :4 3, :5 2})]
    (is (= customer {:id :1 :shortest-paths {:1 0, :2 1, :3 2, :4 3, :5 2} :score 4/8}))))

(deftest test-all
  (let [customers (all {:1 {:1 0, :2 1, :3 2, :4 3, :5 2}, :2 {:1 1, :2 0, :3 1, :4 2, :5 1}, :3 {:1 2, :2 1, :3 0, :4 1, :5 2}, :4 {:1 3, :2 2, :3 1, :4 0, :5 1}, :5 {:1 2, :2 1, :3 2, :4 1, :5 0}})]
    (is (= (count customers) 5))
    (is (= customers '({:id :4, :shortest-paths {:4 0, :1 3, :2 2, :5 1, :3 1}, :score 4/7} {:id :1, :shortest-paths {:4 3, :1 0, :2 1, :5 2, :3 2}, :score 4/8} {:id :2, :shortest-paths {:4 2, :1 1, :2 0, :5 1, :3 1}, :score 4/5} {:id :5, :shortest-paths {:4 1, :1 2, :2 1, :5 0, :3 2}, :score 4/6} {:id :3, :shortest-paths {:4 1, :1 2, :2 1, :5 2, :3 0}, :score 4/6})))))
