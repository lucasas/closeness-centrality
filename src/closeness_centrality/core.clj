(ns closeness-centrality.core
  (:require [closeness-centrality.edges_loader :refer :all]))

(defn update-distances [graph distances unvisited current]
  (let [current-distance (get distances current)]
    (reduce-kv
      (fn [current-distances neighbor neighbor-distance]
        (if (unvisited neighbor)
          (update-in current-distances [neighbor] min (+ current-distance neighbor-distance))
          current-distances))
      distances
      (get graph current))))

(defn shortest-path [graph source]
  (loop [distances (assoc (zipmap (keys graph) (repeat Double/POSITIVE_INFINITY)) source 0)
         current source
         unvisited (disj (apply hash-set (keys graph)) source)]

    (if (empty? unvisited)
      distances
      (let [next-distances (update-distances graph distances unvisited current)
            next-vertice (apply min-key next-distances unvisited)]
        (recur next-distances next-vertice (disj unvisited next-vertice))))))

(defn shortest-paths [vertices]
  (let [graph (edges vertices)]
    (reduce-kv
      (fn [shortest-paths vertice value]
        (assoc shortest-paths vertice (shortest-path graph vertice)))
      {}
      graph)))

(def current-vertices (ref (load! "resources/edges")))

(defn update-current-vertices [edge]
  (dosync
    (commute current-vertices conj edge)))
