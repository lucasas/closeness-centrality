(ns closeness-centrality.core)

(defn edges [vertices]
  (reduce 
    (fn [m [k v]] 
      (assoc-in m [k v] 1))
    {} 
    (into vertices (map reverse vertices))))

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
