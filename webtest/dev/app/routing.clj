(ns app.routing
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]
            [io.pedestal.http.body-params :as bd]
            [hiccup.core :as hc]
            [app.api :as api]))

(def common-interceptors [(bd/body-params) http/html-body])

(def baseroutes #{["/" :get (conj common-interceptors (api/landing-su-15)) :route-name :landing-r]
                  ["/postlist" :get (conj common-interceptors (api/postlist-su-15)) :route-name :postlist-r]
                  ["/about" :get (conj common-interceptors (api/about-su-15)) :route-name :about-r]
                  ["/newpost" :get (conj common-interceptors (api/newpost-su-15)) :route-name :newpost-r]
                  ["/createpost" :post (conj common-interceptors (api/createpost-su-15)) :route-name :createpost-r]
                  ["/addsample" :post (conj common-interceptors (api/addsample-su-15)) :route-name :addsample-r]})

(def routes
  (route/expand-routes baseroutes))

;dev utility

(defn print-routes
  "Print our application's routes"
  []
  (route/print-routes (table-routes baseroutes)))

(defn named-route
  "Finds a route by name"
  [route-name]
  (->> baseroutes
       table-routes
       (filter #(= route-name (:route-name %)))
       first))
