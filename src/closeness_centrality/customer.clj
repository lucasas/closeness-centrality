(ns closeness-centrality.customer
  (:require [closeness-centrality.core :as core]))

(def Customer
  (fn [id shortest-paths]
    {:id id
     :shortest-paths shortest-paths
     :score (/ (- (count shortest-paths) 1) (reduce + (vals shortest-paths)))}))

(defn all [vertices]
  (let [shortest-paths (core/shortest-paths vertices)]
    (map #(Customer (first %) (second %)) shortest-paths)))

(def customers (ref (all @core/current-vertices)))

(defn update-customers [vertices]
  (dosync
    (ref-set customers (all vertices))))
