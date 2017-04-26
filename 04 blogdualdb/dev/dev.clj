(ns dev
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.edn :as edn]
            [app.api.system :as sys]))

(defn exitdev
  []
  "switch to user ns"
  (println "\nloading user ns... \n")
  (require 'user)
  (in-ns 'user))

;; For interactive development

(defonce system (atom (sys/initsystem nil)))

(defn start-dev
  "start the server, dev mode. Change the server value to a server start&create with assoc'd service map"
  []
  (println "\n ------------------------starting-system-------------------------- \n")
  (reset! system (sys/startsystem nil))
  (println "\n -------------------------systemstarted-------------------------- \n"))

(defn stop-dev
  "stopping server"
  []
  (println "\n -----------------------stoping system--------------------------- \n")
  (sys/stopsystem @system)
  (reset! system nil)
  (println "\n -----------------------system stoped--------------------------- \n"))

(defn restart []
  (stop-dev)
  (refresh)
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
