(ns app.system
  (:require [app.atom :as atm]
            [app.datomic :as dtm]
            [app.db :as db]
            [app.api :as api]))

(defn startsystem [system]
  (let [database (db/startdb (dtm/createdb "kambing"))]
    {:database database
     :server (api/start-server (api/make-service-map database))}))

(defn stopsystem [system]
  (let [_  (api/stop-server (:server system))
        _  (db/stopdb (:database system))]
    system))

(defn initsystem [_]
  {:server nil
   :database nil})
