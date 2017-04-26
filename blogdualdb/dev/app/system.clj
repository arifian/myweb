(ns app.system
  (:require [app.atom :as atm]
            [app.datomic :as dtm]
            [app.db :as db]
            [app.api :as api]
            [com.stuartsierra.component :as component]
            [ring.middleware.session.cookie :as cookie]
            [io.pedestal.http.secure-headers :as http.sh]))

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
;;    ::http/routes (api/make-routes (:database config))
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
