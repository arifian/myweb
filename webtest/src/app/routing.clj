(ns app.routing
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as bd]
            [hiccup.core :as hc]
            [app.api :as api]))

(def routes
  (route/expand-routes
   #{["/" :get [(bd/body-params) http/html-body api/home-main]]
     #_["/new" :get [(bd/body-params) http/html-body new-post]]
     #_["/ok" :post [(bd/body-params) http/html-body create-post]]
     #_["/okedit/:postid" :post [(bd/body-params) http/html-body edit-post-ok]]
     #_["/post/:postid" :get [(bd/body-params) http/html-body view-post]]
     #_["/edit/:postid" :get [(bd/body-params) http/html-body edit-post]]
     #_["/delete/:postid" :get [(bd/body-params) http/html-body delete-post]]
     #_["/deleteok/:postid" :post [(bd/body-params) http/html-body delete-post-ok]]}))
