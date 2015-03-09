(ns closeness-centrality.customer
  (:require [closeness-centrality.core :as core]
            [clojure.math.numeric-tower :as math]))

(def Customer
  (fn [id shortest-paths]
    {:id id
     :shortest-paths shortest-paths
     :score (/ (- (count shortest-paths) 1) (reduce + (vals shortest-paths)))
     :fraudulent false}))

(defn all [vertices]
  (let [shortest-paths (core/shortest-paths vertices)]
    (map #(Customer (first %) (second %)) shortest-paths)))

(def customers (ref (all @core/current-vertices)))

(defn update-customers [vertices]
  (dosync
    (ref-set customers (all vertices))))

(defn set-as-fraudulent [id]
  (dosync 
    (ref-set customers (map (fn [customer]
                          (let [shortest-paths (customer :shortest-paths)]
                            (cond
                              (= (customer :id) id) (assoc customer :score 0)
                              (= (shortest-paths id) 1) (update-in customer [:score] * 0.5)
                              (contains? shortest-paths id) (update-in customer [:score] * (- 1 (math/expt (/ 1 2) (shortest-paths id))))
                              :else customer)))
                        @customers))))
