(ns closeness-centrality.edges_loader
  (:require [clojure.java.io :as io]
            [clojure.data.csv :as csv]))

(defn load! [filename]
    (into []
      (let [edges (with-open [in-file (io/reader filename)]
        (doall
          (csv/read-csv in-file :separator \space)))]
        (for [[o d] edges] [(keyword o) (keyword d)]))))
