(defproject closeness-centrality "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.2"]
                 [ring/ring-defaults "0.1.2"]
                 [org.clojure/data.csv "0.1.2"]
                 [ring-anti-forgery "0.3.0"]
                 [cheshire "5.4.0"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler closeness-centrality.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
