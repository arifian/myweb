(ns app.system
  (:require [io.pedestal.http :as http]
            ;[io.pedestal.test :as test]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]
            [com.stuartsierra.component :as component]
            [ring.middleware.session.cookie :as cookie]
            [io.pedestal.http.secure-headers :as http.sh]
            [app.database.atom :as atm]
            [app.database.datomic :as dtm]
            [app.database.db :as db]
            [app.endpoint.routes :as ep.route]))

(defn make-routes
  [database]
  (route/expand-routes (ep.route/baseroutes database)))

;dev utility

(defn print-routes
  "Print our application's routes"
  []
  (route/print-routes (table-routes (ep.route/baseroutes))))

(defn named-route
  "Finds a route by name"
  [route-name]
  (->> (ep.route/baseroutes)
       table-routes
       (filter #(= route-name (:route-name %)))
       first))

(defn make-service-map
  "declaring initial service map"
  [database config]
  {::http/routes (make-routes database)
   ::http/type   :jetty
   ::http/port   (:port config)})

(defn start-server [service-map]
  (-> (assoc service-map ::http/join? false)
      http/create-server
      http/start))

(defn stop-server [server]
  (http/stop server))

(defn createdb "default to dtm"
  [config]
  (cond
    (= (:dbtype config) :dtm) (dtm/createdb (:dbname config))
    (= (:dbtype config) :atm) (atm/createdb (:dbname config))
    :else (dtm/createdb (:dbname config))))

(defn startsystem [system]
  (let [database (db/startdb (createdb (:config system)))]
    {:database database
     :server (start-server (make-service-map database (:config system)))}))

(defn stopsystem [system]
  (let [_  (stop-server (:server system))
        _  (db/stopdb (:database system))]
    system))

(defn initsystem [config]
  {:server nil
   :database nil
   :config config})
