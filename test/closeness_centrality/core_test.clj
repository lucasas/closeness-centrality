;
;         --- 3 ---
;        /         \
; 1 --- 2          4
;        \         /
;         --- 5 ---
;

(ns closeness-centrality.core-test
  (:require [clojure.test :refer :all]
            [closeness-centrality.core :refer :all]
            [closeness-centrality.edges_loader :refer :all]))

(deftest test-shortest-path
  (def graph {:1 {:2 1}, :2 {:1 1, :3 1, :5 1}, :3 {:2 1, :4 1}, :4 {:3 1, :5 1}, :5 {:2 1, :4 1} })
  (is (= (shortest-path graph :1) {:1 0, :2 1, :3 2, :4 3, :5 2}))
  (is (= (shortest-path graph :2) {:1 1, :2 0, :3 1, :4 2, :5 1}))
  (is (= (shortest-path graph :3) {:1 2, :2 1, :3 0, :4 1, :5 2}))
  (is (= (shortest-path graph :4) {:1 3, :2 2, :3 1, :4 0, :5 1}))
  (is (= (shortest-path graph :5) {:1 2, :2 1, :3 2, :4 1, :5 0})))

(deftest test-shortest-paths
  (def vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5]])
  (is (= (shortest-paths vertices) {:1 {:1 0, :2 1, :3 2, :4 3, :5 2}, :2 {:1 1, :2 0, :3 1, :4 2, :5 1}, :3 {:1 2, :2 1, :3 0, :4 1, :5 2}, :4 {:1 3, :2 2, :3 1, :4 0, :5 1}, :5 {:1 2, :2 1, :3 2, :4 1, :5 0}})))

(deftest test-update-current-vertices
  (with-redefs [current-vertices (ref (load! "resources/tests/edges"))] current-vertices
    (let [updated-current-vertices (update-current-vertices [:9 :12])]
      (is (= updated-current-vertices [[:1 :2] [:2 :3] [:3 :4] [:4 :5] [:2 :5] [:9 :12]])))))
