(ns dev
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [app.system :as sys]
            [clojure.edn :as edn]))

(defn exit
  "switch to user ns"
  []
  (println "\nloading user ns... \n")
  (require 'user)
  (in-ns 'user))

(defn dev-config
  "return development configuration map"
  [] 
  (-> "config/config.edn"
      slurp
      edn/read-string))

;; System control

(defonce system (atom nil))

(defn init-dev
  "initialize dev"
  []
  (reset! system (sys/initsystem (dev-config))))

(defn start-dev
  "starts the server"
  []
  (println "\n ------------------------starting-system-------------------------- \n")
  (swap! system sys/startsystem)
  (println "\n -------------------------systemstarted-------------------------- \n"))

(defn stop-dev
  "stopping server"
  []
  (println "\n -----------------------stoping system--------------------------- \n")
  (swap! system sys/stopsystem)
  (reset! system nil)
  (println "\n -----------------------system stoped--------------------------- \n"))

; instant transmission

(defn go-dev
  "(init-dev)
  (start-dev)"
  []
  (init-dev)
  (start-dev))

(defn restart-dev
  "(stop-dev)
  (refresh)
  (init-dev)
  (start-dev)"
  []
  (stop-dev)
  (refresh)
  (init-dev)
  (start-dev))

;; (defn test-request "route testing repl function" [verb url]
;;   (io.pedestal.test/response-for (::http/service-fn @server) verb url))

;; (defn check-routes
;;   "Print our application's routes"
;;   []
;;   (routing/print-routes))

;; (defn named-route
;;   "Finds a route by name"
;;   [route-name]
;;   (routing/named-route route-name))
