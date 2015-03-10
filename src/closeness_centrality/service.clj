(ns closeness-centrality.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [cheshire.core :refer :all]
            [closeness-centrality.core :refer :all]
            [closeness-centrality.customer :refer :all]
            [halresource.resource :as hal]
            [ring.util.response :as ring-resp]))

(defn to-resource [url] 
  (hal/new-resource url))

(defn render-resource [resource]
  (hal/resource->representation resource :json))

(defn customers-as-resource [customers]
  (vec (map (fn [customer]
         (let [resource
               (-> (to-resource (route/url-for ::fraudulent :params {:id (name (customer :id))} :absolute? true))
                   (hal/add-properties {:score (customer :score)
                                       :id (customer :id)}))]
           (parse-string (render-resource resource))))
    customers)))

(defn home [request]
  (let [resource (to-resource (route/url-for ::create-edge :absolute? true))]
    (ring-resp/response (render-resource resource))))

(defn create-edge [request]
  (let [origin (get (:params request) "origin")
        destiny (get (:params request) "destiny")
        vertices (update-current-vertices [(keyword origin) (keyword destiny)])
        customers (sort-by :score > (update-customers vertices))]
    (ring-resp/response (generate-string (customers-as-resource customers)))))

(defn fraudulent [request]
  (ring-resp/response ""))

(defroutes routes
  [[["/" {:get home}
     ^:interceptors [body-params/body-params]
     ["/customer/:id/fraudulent" {:put fraudulent}]
     ["/edges" {:post create-edge}]]]])

(def service {:env :prod
              ::bootstrap/routes routes
              ::bootstrap/resource-path "/public"
              ::bootstrap/type :jetty
              ::bootstrap/port 8080})
