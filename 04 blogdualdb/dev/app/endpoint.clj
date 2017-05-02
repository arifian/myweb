(ns app.endpoint
  (:require [app.template.page :as mold]
            [app.database :as db]
            [io.pedestal.http.body-params :as bd]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :refer [interceptor]]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.route.definition.table :refer [table-routes]]))

;;interceptor, pedestal calls these stuff to reply with a response.

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

(defn postlist-su-15 [service]
  (interceptor
   {:name :home-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            request (:request context)
            response {:status 200 :body (mold/postlist-html (db/getallpost database))}]
        (assoc context :response response)))}))

(defn createpost-su-15 [service]
  (interceptor
   {:name :create-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))]
        (db/addpost database title content) 
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(defn newpost-su-15 [service]
  (interceptor
   {:name :newpost-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/newpost-html)}]
        (assoc context :response response)))}))

(defn addsample-su-15 [service]
  (interceptor
   {:name :addsample-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            request (:request context)]
        (db/addsample database)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(defn getpost-su-15 [service]
  (interceptor
   {:name :getpost-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/getpost-html (db/getallpost database) postkey postid)}]
        (if (= (postkey (db/getallpost database)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn editpage-su-15 [service]
  (interceptor
   {:name :editpage-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/editpage-html (db/getallpost database) postkey postid)}]
        (if (= (postkey (db/getallpost database)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn submitedit-su-15 [service]
  (interceptor
   {:name :submitedit-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            postid (get-in context [:request :path-params :postid])
            title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))
            postkey (keyword postid)]
        (db/editpost database postkey postid title content)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(defn delete-su-15 [service]
  (interceptor
   {:name :delete-sukhoi
    :enter
    (fn [context]
      (let [database (:database service)
            postid (get-in context [:request :path-params :postid])]
        (db/removepost database postid)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(def common-interceptors "wrap all the common interceptor" [(bd/body-params) http/html-body])

(defn baseroutes
  [service]
  #{["/" :get (conj common-interceptors (landing-su-15 service)) :route-name :landing-r]
    ["/postlist" :get (conj common-interceptors (postlist-su-15 service)) :route-name :postlist-r]
    ["/about" :get (conj common-interceptors (about-su-15 service)) :route-name :about-r]
    ["/newpost" :get (conj common-interceptors (newpost-su-15 service)) :route-name :newpost-r]
    ["/createpost" :post (conj common-interceptors (createpost-su-15 service)) :route-name :createpost-r]
    ["/addsample" :post (conj common-interceptors (addsample-su-15 service)) :route-name :addsample-r]
    ["/post/:postid" :get (conj common-interceptors (getpost-su-15 service)) :route-name :getpost-r]
    ["/edit/:postid" :get (conj common-interceptors (editpage-su-15 service)) :route-name :editpost-r]
    ["/submitedit/:postid" :post (conj common-interceptors (submitedit-su-15 service)) :route-name :submitedit-r]
    ["/delete/:postid" :get (conj common-interceptors (delete-su-15 service)) :route-name :delete-r]})

(defn make-routes
  "function to merge the routes"
  [service]
  (route/expand-routes (baseroutes service)))

(defn make-service-map
  "function to make initial service map"
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
