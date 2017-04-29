(ns app.system
  (:require [io.pedestal.http :as http]
            ;[io.pedestal.test :as test]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]
            [com.stuartsierra.component :as component]
            [ring.middleware.session.cookie :as cookie]
            [io.pedestal.http.secure-headers :as http.sh]
            ;[app.database.atom :as atm]
            [app.datomic.user :as dtm]
            [app.database :as db]
            [app.endpoint :as ep]
            [app.server :as server]))

;dev utility

(defn createdb
  "Create datomic database"
  [config]
  (dtm/createdb (:dbname config)))

;; (defn createdb
;;   "database switch, default to dtm. Use this if switching database in the future is needed"
;;   [config]
;;   (cond
;;     (= (:dbtype config) :dtm) (dtm/createdb (:dbname config))
;;     (= (:dbtype config) :atm) (atm/createdb (:dbname config))
;;     :else (dtm/createdb (:dbname config))))

(defn startsystem
  "Starts the system"
  [system]
  (component/start system))

;; (defn stopsystem [system]
;;   (let [_  (server/stop-server (:server system))
;;         _  (db/stopdb (:database system))]
;;     system))

(defn stopsystem
  "stops the system"
  [system]
  (component/stop system))

(defn initsystem
  "Initialize system map with a config map"
  [config]
  (component/system-map
   :database (createdb config)
   :service (component/using
             (ep/make-service-map config)
             {:database :database})
   :server (component/using
            (server/createserver config)
            {:service :service})
   :config config))
