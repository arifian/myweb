(ns app.routing
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]
            [io.pedestal.http.body-params :as bd]
            [hiccup.core :as hc]
            [app.api :as api]))

(def common-interceptors [(bd/body-params) http/html-body])

(def baseroutes #{["/" :get (conj common-interceptors api/landing-su-15) :route-name :landing-page]
                  ["/home" :get (conj common-interceptors api/home-su-15) :route-name :home-page]
                  ["/about" :get (conj [http/html-body] api/about-su-15) :route-name :about-page]
                  #_["/new" :get [common-interceptors new-post]]
                  #_["/ok" :post [common-interceptors create-post]]
                  #_["/okedit/:postid" :post [common-interceptors edit-post-ok]]
                  #_["/post/:postid" :get [common-interceptors view-post]]
                  #_["/edit/:postid" :get [common-interceptors edit-post]]
                  #_["/delete/:postid" :get [common-interceptors delete-post]]
                  #_["/deleteok/:postid" :post [common-interceptors delete-post-ok]]})

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
