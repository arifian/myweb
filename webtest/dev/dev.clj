(ns dev
  (:require [io.pedestal.http :as http]
            [io.pedestal.test :as test]
            [routing :as routing]))

(def service-map
  {::http/routes routing/routes
   ::http/type   :jetty
   ::http/port   8890})

;; For interactive development
(defonce server (atom nil))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                       (assoc service-map
                              ::http/join? false)))))

(defn stop-dev []
  (http/stop @server))

(defn restart []
  (stop-dev)
  (start-dev))
 
(defn test-request [verb url]
  (io.pedestal.test/response-for (::http/service-fn @server) verb url))
