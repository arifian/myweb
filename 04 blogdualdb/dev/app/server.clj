(ns app.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [app.endpoint :as ep]))

(defn start-server [server]
  (-> (assoc (:service server) ::http/join? false)
      (assoc ::http/routes (ep/make-routes (:service server)))
      http/create-server
      http/start))

(defn stop-server [server]
  (http/stop server))

(defn createserver [config]
  {:service nil})
