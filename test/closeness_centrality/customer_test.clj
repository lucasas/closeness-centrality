(ns closeness-centrality.customer-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.customer :refer :all]))

(deftest test-customer
  (let [customer (Customer :1 {:1 0, :2 1, :3 2, :4 3, :5 2})]
    (is (= customer {:id :1 :shortest-paths {:1 0, :2 1, :3 2, :4 3, :5 2} :score 4/8, :fraudulent false}))))

(deftest test-all
  (let [vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]]
        customers (all vertices)]
    (is (= (count customers) 5))
    (is (= customers '({:id :1, :shortest-paths {:1 0, :2 1, :3 2, :4 3, :5 2}, :score 4/8, :fraudulent false} {:id :2, :shortest-paths {:1 1, :2 0, :3 1, :4 2, :5 1}, :score 4/5, :fraudulent false} {:id :3, :shortest-paths {:1 2, :2 1, :3 0, :4 1, :5 2}, :score 4/6, :fraudulent false} {:id :4, :shortest-paths {:1 3, :2 2, :3 1, :4 0, :5 1}, :score 4/7, :fraudulent false} {:id :5, :shortest-paths {:1 2, :2 1, :3 2, :4 1, :5 0}, :score 4/6, :fraudulent false})))))

(deftest test-update-customers
  (let [vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5] [:2 :4]]]
    (update-customers vertices)
    (is (= (count @customers) 5))
    (is (= (first @customers) {:id :1, :shortest-paths {:1 0, :2 1, :3 2, :4 2, :5 2}, :score 4/7, :fraudulent false}))
    (is (= (nth @customers 1) {:id :2, :shortest-paths {:1 1, :2 0, :3 1, :4 1, :5 1}, :score 1, :fraudulent false}))
    (is (= (nth @customers 2) {:id :3, :shortest-paths {:1 2, :2 1, :3 0, :4 1, :5 2}, :score 4/6, :fraudulent false}))
    (is (= (nth @customers 3) {:id :4, :shortest-paths {:1 2, :2 1, :3 1, :4 0, :5 1}, :score 4/5, :fraudulent false}))
    (is (= (nth @customers 4) {:id :5, :shortest-paths {:1 2, :2 1, :3 2, :4 1, :5 0}, :score 4/6, :fraudulent false}))))

(deftest test-set-as-fraudulent
  (let [vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]]]
    (with-redefs [customers (ref (all vertices))] customers
      (set-as-fraudulent :1)
      (is (= ((first @customers) :score) 0))
      (is (= ((first @customers) :fraudulent) true))
      (is (= ((nth @customers 1) :score) 0.4))
      (is (= ((nth @customers 2) :score) 1/2))
      (is (= ((nth @customers 3) :score) 1/2))
      (is (= ((nth @customers 4) :score) 1/2)))))
