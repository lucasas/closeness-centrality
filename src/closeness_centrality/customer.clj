(ns closeness-centrality.customer
  (:require [closeness-centrality.core :as core]))

(def Customer
  (fn [id closeness-centrality]
    {:id id
     :closeness-centrality closeness-centrality}))

(defn all [data]
  (map #(Customer (first %) (second %)) data))

(def customers (ref (all (core/closeness-centrality @core/current-vertices))))
