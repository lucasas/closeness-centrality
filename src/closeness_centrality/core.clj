(ns closeness-centrality.core
  (:require [closeness-centrality.edges_loader :refer :all]))

(defn update-distances [graph distances unvisited current]
  (let [current-distance (get distances current)]
    (reduce-kv
      (fn [c neighbor neighbor-distance]
        (if (unvisited neighbor)
          (update-in c [neighbor] min (+ current-distance neighbor-distance))
          c))
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

(defn shortest-paths [graph]
  (reduce
    (fn [m [k v]]
      (assoc m k (shortest-path graph k)))
    {}
    graph))

(defn closeness-centrality [vertices]
  (let [graph (edges vertices)
        shortest-paths (shortest-paths graph)]
    (into {}
      (map (fn [[k v]] [k (/ (- (count v) 1) (reduce + (vals v)))]) shortest-paths))))

(def current-vertices (ref (load! "resources/edges")))

(defn update-current-vertices [edge]
  (dosync
    (commute current-vertices conj edge)))
