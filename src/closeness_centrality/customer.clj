(ns closeness-centrality.customer
  (:require [closeness-centrality.core :as core]))

(def Customer
  (fn [id shortest-paths]
    {:id id
     :shortest-paths shortest-paths
     :score (/ (- (count shortest-paths) 1) (reduce + (vals shortest-paths)))}))

(defn all [shortest-paths]
  (map #(Customer (first %) (second %)) shortest-paths))

(def customers (ref (all (core/shortest-paths @core/current-vertices))))
