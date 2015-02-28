(ns closeness-centrality.core)

(defn edges [vertices]
  (reduce 
    (fn [m [k v]] 
      (assoc-in m [k v] 1))
    {} 
    vertices))

(defn update-distances [graph distances unvisited current]
  (let [current-distance (get distances current)]
    (reduce-kv
      (fn [c neighbor neighbor-distance]
        (if (unvisited neighbor)
          (update-in c [neighbor] min (+ current-distance neighbor-distance))
          c))
      distances
      (get graph current))))

(defn shortest-path [graph src]
  (loop [distances (assoc (zipmap (keys graph) (repeat Double/POSITIVE_INFINITY)) src 0)
         current src
         unvisited (disj (apply hash-set (keys graph)) src)]

    (if (or (empty? unvisited) (= Double/POSITIVE_INFINITY (get distances current)))
      distances
      (let [next-distances (update-distances graph distances unvisited current)
            next-node (apply min-key next-distances unvisited)]
        (recur next-distances next-node (disj unvisited next-node))))))
