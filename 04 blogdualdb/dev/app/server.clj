(ns app.server
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [app.endpoint :as ep]
            [com.stuartsierra.component :as component]))

(defn start-server [server]
  (let [service1 (-> (assoc (:service server) ::http/join? false)
                     (assoc ::http/routes (ep/make-routes (:service server))))
        runnable-server (http/create-server service1)]
    (http/start runnable-server)
    (assoc server :service service1 :runnable-server runnable-server))
  ;; (-> (assoc (:service server) ::http/join? false)
  ;;     (assoc ::http/routes (ep/make-routes (:service server)))
  ;;     http/create-server
  ;;     http/start)
  )

(defn stop-server [server]
  (http/stop (:runnable-server server))
  server)

(defrecord Server [service runnable-server])

(defn createserver [config]
  (Server. nil nil))

(extend-type Server
  component/Lifecycle
  (start [server]
    (start-server server))
  (stop [server]
    (stop-server server)))
