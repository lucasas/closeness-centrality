(ns closeness-centrality.customer
  (:require [closeness-centrality.core :as core]))

(def Customer
  (fn [id score]
    {:id id
     :score score}))

(defn all [data]
  (map #(Customer (first %) (second %)) data))

(def customers (ref (all (core/closeness-centrality @core/current-vertices))))
