;
;         --- 3 ---
;        /         \
; 1 --- 2          4
;        \         /
;         --- 5 ---
;

(ns closeness-centrality.core-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.core :refer :all]))

(deftest test-edges
  (def vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]])

  (let [edges (edges vertices)]
    (is (= (count edges) 5))
    (is (= (get edges :4) {:3 1, :5 1}))
    (is (= (get edges :3) {:2 1, :4 1}))
    (is (= (get edges :2) {:1 1, :5 1, :3 1}))
    (is (= (get edges :1) {:2 1}))))

(deftest test-shortest-path
  (def graph {:1 {:2 1}, :2 {:1 1, :3 1, :5 1}, :3 {:2 1, :4 1}, :4 {:3 1, :5 1}, :5 {:2 1, :4 1} })
  (is (= (shortest-path graph :1) {:1 0, :2 1, :3 2, :4 3, :5 2}))
  (is (= (shortest-path graph :2) {:1 1, :2 0, :3 1, :4 2, :5 1}))
  (is (= (shortest-path graph :3) {:1 2, :2 1, :3 0, :4 1, :5 2}))
  (is (= (shortest-path graph :4) {:1 3, :2 2, :3 1, :4 0, :5 1}))
  (is (= (shortest-path graph :5) {:1 2, :2 1, :3 2, :4 1, :5 0})))

(deftest test-closeness-centrality
  (def vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]])
  (let [results (closeness-centrality vertices)]
    (is (= (get results :1) 8/5))
    (is (= (get results :2) 5/5))
    (is (= (get results :3) 6/5))
    (is (= (get results :4) 7/5))
    (is (= (get results :5) 6/5))))
