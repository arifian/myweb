(ns app.api.system
  (:require [io.pedestal.http :as http]
            ;[io.pedestal.test :as test]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]
            [com.stuartsierra.component :as component]
            [ring.middleware.session.cookie :as cookie]
            [io.pedestal.http.secure-headers :as http.sh]
            [clojure.edn :as edn]
            [app.database.atom :as atm]
            [app.database.datomic :as dtm]
            [app.database.db :as db]
            [app.api.routes :as api.route]))

(defn make-routes
  [database]
  (route/expand-routes (api.route/baseroutes database)))

;dev utility

(defn print-routes
  "Print our application's routes"
  []
  (route/print-routes (table-routes (api.route/baseroutes))))

(defn named-route
  "Finds a route by name"
  [route-name]
  (->> (api.route/baseroutes)
       table-routes
       (filter #(= route-name (:route-name %)))
       first))

(defn getconfig
  "return development configuration map"
  [] 
  (-> "config/config.edn"
      slurp
      edn/read-string))

(defn make-service-map
  "declaring initial service map"
  [database]
  {::http/routes (make-routes database)
   ::http/type   :jetty
   ::http/port   (:port (getconfig))})

(defn start-server [service-map]
  (-> (assoc service-map ::http/join? false)
      http/create-server
      http/start))

(defn stop-server [server]
  (http/stop server))

(defn createdb "default to dtm"
  []
  (cond
    (= (:dbtype (getconfig)) :dtm) (dtm/createdb (:dbname (getconfig)))
    (= (:dbtype (getconfig)) :atm) (atm/createdb (:dbname (getconfig)))
    :else (dtm/createdb (:dbname (getconfig)))))

(defn startsystem [system]
  (let [database (db/startdb (createdb))]
    {:database database
     :server (start-server (make-service-map database))}))

(defn stopsystem [system]
  (let [_  (stop-server (:server system))
        _  (db/stopdb (:database system))]
    system))

(defn initsystem [_]
  {:server nil
   :database nil})
