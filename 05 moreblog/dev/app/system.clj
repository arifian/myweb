(ns app.system
  (:require [io.pedestal.http :as http]
            ;[io.pedestal.test :as test]
            [com.stuartsierra.component :as component]
            [app.database.atom :as atm]
            [app.database.datomic :as dtm]
            [app.database :as db]
            [app.endpoint :as ep]
            [app.server :as server]))

(defn createdb "create/switch db, default to dtm"
  [config]
  (cond
    (= (:dbtype config) :dtm) (dtm/createdb (:dbname config))
    (= (:dbtype config) :atm) (atm/createdb (:dbname config))
    :else (dtm/createdb (:dbname config))))

;; (defn startsystem [system]
;;   (let [database (db/startdb (:database system))
;;         service (assoc (:service system) :database database)
;;         server (assoc (:server system) :service service)]
;;     {:database database
;;      :service service
;;      :server (server/start-server server)}))

(defn startsystem
  "function to starts the system using system map"
  [system]
  (component/start system))

;; (defn stopsystem [system]
;;   (let [_  (server/stop-server (:server system))
;;         _  (db/stopdb (:database system))]
;;     system))

(defn stopsystem
  "function to stop the system using system map"
  [system]
  (component/stop system))

(defn initsystem
  "Function to initialize (starts all the component) system map, 
  dependency sorted as database -> service -> config. at the end saves the config in :config key"
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
