(ns closeness-centrality.customer
  (:require [closeness-centrality.core :refer :all]))

(def Customer
  (fn [id closeness-centrality]
    {:id id
     :closeness-centrality closeness-centrality}))

(defn load! [& data]
  (let [customer-data (or (first data) (closeness-centrality current-vertices))]
    (map #(Customer (first %) (second %)) customer-data)))
