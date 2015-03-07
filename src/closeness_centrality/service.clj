(ns closeness-centrality.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [cheshire.core :refer :all]
            [closeness-centrality.core :refer :all]
            [closeness-centrality.utils :refer :all]
            [closeness-centrality.customer :refer :all]
            [halresource.resource :as hal]
            [ring.util.response :as ring-resp]))

(defn to-resource [url] 
  (hal/new-resource url))

(defn render-resource [resource]
  (hal/resource->representation resource :json))

(defn home [request]
  (let [resource (to-resource (route/url-for ::create-edge :absolute? true))]
    (ring-resp/response (render-resource resource))))

(defn create-edge [request]
  (let [origin (get (:params request) "origin")
        destiny (get (:params request) "destiny")
        vertices (update-current-vertices [(keyword origin) (keyword destiny)])]
    (ring-resp/response (generate-string @customers))))

(defroutes routes
  [[["/" {:get home}
     ^:interceptors [body-params/body-params]
     ["/edges" {:post create-edge}]]]])

(def service {:env :prod
              ::bootstrap/routes routes
              ::bootstrap/resource-path "/public"
              ::bootstrap/type :jetty
              ::bootstrap/port 8080})
