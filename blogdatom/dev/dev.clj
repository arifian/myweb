(ns dev
  (:require [io.pedestal.http :as http]
            [io.pedestal.test :as test]
            [clojure.tools.namespace.repl :refer [refresh]]
            [app.routing :as routing]
            [app.api :as api]))

#_(refresh) ;refresh ns

(defn exitdev
  []
  "switch to user ns"
  (println "\nloading user ns... \n")
  (require 'user)
  (in-ns 'user))

(def service-map "declaring initial service map"
  {::http/routes routing/routes
   ::http/type   :jetty
   ::http/port   8890})

;; For interactive development
(defonce server (atom nil))

(defn start-dev
  "start the server, dev mode. Change the server value to a server start&create with assoc'd service map"
  []
  (println "\n -------------------------------------------------- \n")
  (reset! server
          (-> (assoc service-map ::http/join? false)
              http/create-server
              http/start))
  (println "\n -------------------------------------------------- \n")
  (api/initdb)
  (println "\n ------------------------schemainitialized-------------------------- \n"))

(defn stop-dev
  "stopping server"
  []
  (println "\n -------------------------------------------------- \n")
  (http/stop @server)
  (println "\n -------------------------------------------------- \n"))

(defn restart []
  (stop-dev)
  (start-dev))

(defn test-request "route testing repl function" [verb url]
  (io.pedestal.test/response-for (::http/service-fn @server) verb url))

(defn check-routes
  "Print our application's routes"
  []
  (routing/print-routes))

(defn named-route
  "Finds a route by name"
  [route-name]
  (routing/named-route route-name))
