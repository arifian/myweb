(ns app.api.system
  (:require [io.pedestal.http :as http]
            ;[io.pedestal.test :as test]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]
            [com.stuartsierra.component :as component]
            [ring.middleware.session.cookie :as cookie]
            [io.pedestal.http.secure-headers :as http.sh]
            [app.database.atom :as atm]
            [app.database.datomic :as dtm]
            [app.database.db :as db]
            [app.api.routes :as api.route]))

(defn make-routes
  [database]
  (route/expand-routes (api.route/baseroutes database)))

;dev utility

(defn print-routes
  "Print our application's routes"
  []
  (route/print-routes (table-routes (api.route/baseroutes))))

(defn named-route
  "Finds a route by name"
  [route-name]
  (->> (api.route/baseroutes)
       table-routes
       (filter #(= route-name (:route-name %)))
       first))

(defn make-service-map
  "declaring initial service map"
  [database]
  {::http/routes (make-routes database)
   ::http/type   :jetty
   ::http/port   8890})

(defn start-server [service-map]
  (-> (assoc service-map ::http/join? false)
              http/create-server
              http/start))

(defn stop-server [server]
  (http/stop server))

(defn startsystem [system]
  (let [database (db/startdb (dtm/createdb "kambing"))]
    {:database database
     :server (start-server (make-service-map database))}))

(defn stopsystem [system]
  (let [_  (stop-server (:server system))
        _  (db/stopdb (:database system))]
    system))

(defn initsystem [_]
  {:server nil
   :database nil})

;; (defn pedestal-config
;;   [config]
;;   {;::http/host "0.0.0.0"
;;    ::http/port 5050
;;    ::http/type :jetty
;;    ::http/join? false
;;    ::http/resource-path "/public/"
;;    ::http/enable-session {:store (cookie/cookie-store
;;                                    {:key (-> config :cookie-key)})
;;                           :cookie-name "blogtest-cookies"}
;;    ::http/routes (make-routes (:database config))
;;    ::http/secure-headers {:content-security-policy-settings
;;                           (http.sh/content-security-policy-header "object-src 'none';")}})

;; (defn pedestal-config-fn
;;   ""
;;   [config]
;;   (http/default-interceptors (pedestal-config config)))

;; (defn init-production-system
;;   ""
;;   [config]
;;   (component/system-map
;;     :config config
;;     :mycomponent {:my :component}
;;     :datomic (dtm/make (-> config :database :datomic))
;;     :pedestal (component/using
;;                 (pedestal/pedestal pedestal-config-fn config)
;;                 [:mycomponent :datomic])))

;; (defn init-dev-system
;;   ""
;;   [config]
;;   (merge
;;     (init-production-system config)
;;     (component/system-map
;;       :config config
;;       :mycomponent {:my :component}
;;       :datomic (component/using
;;                  (datomic/make (-> config :database :datomic))
;;                  [:content-provider])
;;       :content-provider (component/using
;;                           (content/make)
;;                           [:directories :library])
;;       :directories (directories/make config)
;;       :library (library/make)
;;       :pedestal (component/using
;;                   (pedestal/dev-pedestal pedestal-config-fn config)
;;                   [:mycomponent :datomic]))))
