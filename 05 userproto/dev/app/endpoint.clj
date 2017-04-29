(ns app.endpoint
  (:require [app.template.page :as mold]
            [app.database :as db]
            [io.pedestal.http.body-params :as bd]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :refer [interceptor]]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]))

;;interceptor

(defn landing-su-15 [service]
  (interceptor
   {:name :about-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/landing-html)}]
        (assoc context :response response)))}))

(defn about-su-15 [service]
  (interceptor
   {:name :about-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/about-html)}]
        (assoc context :response response)))}))

(defn userlist-su-15 [service]
  (interceptor
   {:name :home-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            request (:request context)
            response {:status 200 :body (mold/userlist-html (db/getalluser database))}]
        (assoc context :response response)))}))

(defn createuser-su-15 [service]
  (interceptor
   {:name :create-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))]
        (db/adduser database title content) 
        (assoc context :response {:status 200 :body (mold/userlist-html (db/getalluser database))})))}))

(defn newuser-su-15 [service]
  (interceptor
   {:name :newuser-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/newuser-html)}]
        (assoc context :response response)))}))

(defn addsample-su-15 [service]
  (interceptor
   {:name :addsample-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            request (:request context)]
        (db/addsample database)
        (assoc context :response {:status 200 :body (mold/userlist-html (db/getalluser database))})))}))

(defn getuser-su-15 [service]
  (interceptor
   {:name :getuser-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            userid (get-in context [:request :path-params :userid])
            userkey (keyword userid)
            response {:status 200 :body (mold/getuser-html (db/getalluser database) userkey userid)}]
        (if (= (userkey (db/getalluser database)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn editpage-su-15 [service]
  (interceptor
   {:name :editpage-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            userid (get-in context [:request :path-params :userid])
            userkey (keyword userid)
            response {:status 200 :body (mold/editpage-html (db/getalluser database) userkey userid)}]
        (if (= (userkey (db/getalluser database)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn submitedit-su-15 [service]
  (interceptor
   {:name :submitedit-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            userid (get-in context [:request :path-params :userid])
            title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))
            userkey (keyword userid)]
        (db/edituser database userkey userid title content)
        (assoc context :response {:status 200 :body (mold/userlist-html (db/getalluser database))})))}))

(defn delete-su-15 [service]
  (interceptor
   {:name :delete-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            userid (get-in context [:request :path-params :userid])]
        (db/removeuser database userid)
        (assoc context :response {:status 200 :body (mold/userlist-html (db/getalluser database))})))}))

(def common-interceptors [(bd/body-params) http/html-body])

(defn baseroutes
  [service]
  #{["/" :get (conj common-interceptors (landing-su-15 service)) :route-name :landing-r]
    ["/userlist" :get (conj common-interceptors (userlist-su-15 service)) :route-name :userlist-r]
    ["/about" :get (conj common-interceptors (about-su-15 service)) :route-name :about-r]
    ["/newuser" :get (conj common-interceptors (newuser-su-15 service)) :route-name :newuser-r]
    ["/createuser" :post (conj common-interceptors (createuser-su-15 service)) :route-name :createuser-r]
    ["/addsample" :post (conj common-interceptors (addsample-su-15 service)) :route-name :addsample-r]
    ["/user/:userid" :get (conj common-interceptors (getuser-su-15 service)) :route-name :getuser-r]
    ["/edit/:userid" :get (conj common-interceptors (editpage-su-15 service)) :route-name :edituser-r]
    ["/submitedit/:userid" :post (conj common-interceptors (submitedit-su-15 service)) :route-name :submitedit-r]
    ["/delete/:userid" :get (conj common-interceptors (delete-su-15 service)) :route-name :delete-r]})

(defn make-routes
  "Define the routes for the service"
  [service]
  (route/expand-routes (baseroutes service)))

(defn make-service-map
  "declaring initial service map"
  [config]
  {:database nil
   ::http/routes nil
   ::http/type   :jetty
   ::http/port   (:port config)})

;; (defn print-routes
;;   "Print our application's routes"
;;   []
;;   (route/print-routes (table-routes (baseroutes))))

;; (defn named-route
;;   "Finds a route by name"
;;   [route-name]
;;   (->> (baseroutes)
;;        table-routes
;;        (filter #(= route-name (:route-name %)))
;;        first))
