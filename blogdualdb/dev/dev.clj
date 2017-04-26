(ns dev
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.edn :as edn]
            [app.atom :as atm]
            [app.datomic :as dtm]
            [app.db :as db]
            [app.api :as api]
            [app.system :as sys]))

(defn exitdev
  []
  "switch to user ns"
  (println "\nloading user ns... \n")
  (require 'user)
  (in-ns 'user))

;; (defonce dev-system nil)

;; (defn dev-config
;;   "return development configuration map"
;;   [] 
;;   (-> "config/config.edn"
;;       slurp
;;       edn/read-string))

;; (defn init []
;;   (alter-var-root #'dev-system
;;                   (constantly (sys/init-dev-system (dev-config)))))

;; For interactive development

(defonce system (atom (sys/initsystem nil)))

(defn start-dev
  "start the server, dev mode. Change the server value to a server start&create with assoc'd service map"
  []
  (println "\n -------------------------------------------------- \n")
  (reset! system (sys/startsystem nil))
  ;; (let [database (db/startdb (dtm/createdb "kambing"))]
  ;;   (reset! system
  ;;           {:database database
  ;;            :server (api/start-server (api/make-service-map database))}))
  (println "\n ------------------------schemainitialized-------------------------- \n"))

(defn stop-dev
  "stopping server"
  []
  (println "\n -------------------------------------------------- \n")
  (sys/stopsystem @system)
  ;; (let [_  (api/stop-server (:server @system))
  ;;       _  (db/stopdb (:database @system))]
  ;;   (reset! system nil))
  (reset! system nil)
  (println "\n -------------------------------------------------- \n"))

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
