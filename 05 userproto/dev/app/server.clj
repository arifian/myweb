(ns app.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [app.endpoint :as ep]
            [com.stuartsierra.component :as component]))

(defn start-server
  "Starts the server"
  [server]
  (-> (assoc (:service server) ::http/join? false)
      (assoc ::http/routes (ep/make-routes (:service server)))
      http/create-server
      http/start))

(defn stop-server
  "Stops the server"
  [server]
  (http/stop server))

(defrecord Server [service])

(defn createserver [config]
  (Server. nil))

(extend-type Server
  component/Lifecycle
  (start [server]
    (start-server server))
  (stop [server]
    (stop-server server)))
