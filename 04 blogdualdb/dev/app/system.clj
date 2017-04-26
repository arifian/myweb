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
            [app.endpoint :as ep]
            [app.server :as server]))

;dev utility

#_(make-routes database)

(defn createdb "default to dtm"
  [config]
  (cond
    (= (:dbtype config) :dtm) (dtm/createdb (:dbname config))
    (= (:dbtype config) :atm) (atm/createdb (:dbname config))
    :else (dtm/createdb (:dbname config))))

(defn startsystem [system]
  (let [database (db/startdb (:database system))
        service (assoc (:service system) :database database)
        server (assoc (:server system) :service service)]
    {:database database
     :service service
     :server (server/start-server server)}))

(defn stopsystem [system]
  (let [_  (server/stop-server (:server system))
        _  (db/stopdb (:database system))]
    system))

(defn initsystem [config]
  {:server (server/createserver config)
   :service (ep/make-service-map config)
   :database (createdb config)
   :config config})
