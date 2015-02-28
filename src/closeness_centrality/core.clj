(ns closeness-centrality.core)

(defn edges [vertices]
  (reduce 
    (fn [m [k v]] 
      (assoc-in m [k v] 1))
    {} 
    vertices))
