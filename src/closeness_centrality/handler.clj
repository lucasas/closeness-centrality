(ns closeness-centrality.handler
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [ring.middleware.session :refer [wrap-session]]
            [ring.middleware.anti-forgery :refer :all]
            [cheshire.core :refer :all]))

(defroutes app-routes
  (GET "/" [] (generate-string {:csrf-token *anti-forgery-token*}))
  (POST "/edge" [origin, destiny]
    (str "<h1>Hello user " [(keyword origin) (keyword destiny)] "</h1>")))

(def app
  (->
   (site app-routes)
   (wrap-anti-forgery)
   (wrap-session)))
