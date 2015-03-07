(ns closeness-centrality.utils)

(defn sorted-map-by-value [m]
  (into (sorted-map-by (fn [k1 k2]
                          (compare [(get m k2) k2]
                                   [(get m k1) k1])))
    m))
