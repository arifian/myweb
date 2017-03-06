(ns app.routing
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as bd]
            [hiccup.core :as hc]
            [app.api :as api]))

(def common-interceptors [(body-params/body-params) http/html-body])

(def routes
  (route/expand-routes
   #{["/" :get [common-interceptors api/home-main]]
     #_["/new" :get [common-interceptors new-post]]
     #_["/ok" :post [common-interceptors create-post]]
     #_["/okedit/:postid" :post [common-interceptors edit-post-ok]]
     #_["/post/:postid" :get [common-interceptors view-post]]
     #_["/edit/:postid" :get [common-interceptors edit-post]]
     #_["/delete/:postid" :get [common-interceptors delete-post]]
     #_["/deleteok/:postid" :post [common-interceptors delete-post-ok]]}))
