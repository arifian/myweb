(ns app.endpoint.routes
  (:require [app.template.page :as mold]
            [app.database.db :as db]
            [io.pedestal.http.body-params :as bd]
            [io.pedestal.http :as http]
            [io.pedestal.interceptor :refer [interceptor]]))

;;interceptor

(defn landing-su-15 [database]
  (interceptor
   {:name :about-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/landing-html)}]
        (assoc context :response response)))}))

(defn about-su-15 [database]
  (interceptor
   {:name :about-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/about-html)}]
        (assoc context :response response)))}))

(defn postlist-su-15 [database]
  (interceptor
   {:name :home-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/postlist-html (db/getallpost database))}]
        (assoc context :response response)))}))

(defn createpost-su-15 [database]
  (interceptor
   {:name :create-sukhoi
    :enter
    (fn [context]
      (let [title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))]
        (db/addpost database title content) 
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(defn newpost-su-15 [database]
  (interceptor
   {:name :newpost-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/newpost-html)}]
        (assoc context :response response)))}))

(defn addsample-su-15 [database]
  (interceptor
   {:name :addsample-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)]
        (db/addsample database)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(defn getpost-su-15 [database]
  (interceptor
   {:name :getpost-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/getpost-html (db/getallpost database) postkey postid)}]
        (if (= (postkey (db/getallpost database)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn editpage-su-15 [database]
  (interceptor
   {:name :editpage-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/editpage-html (db/getallpost database) postkey postid)}]
        (if (= (postkey (db/getallpost database)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn submitedit-su-15 [database]
  (interceptor
   {:name :submitedit-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))
            postkey (keyword postid)]
        (db/editpost database postkey postid title content)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(defn delete-su-15 [database]
  (interceptor
   {:name :delete-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])]
        (db/removepost database postid)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getallpost database))})))}))

(def common-interceptors [(bd/body-params) http/html-body])

(defn baseroutes
  [database]
  #{["/" :get (conj common-interceptors (landing-su-15 database)) :route-name :landing-r]
    ["/postlist" :get (conj common-interceptors (postlist-su-15 database)) :route-name :postlist-r]
    ["/about" :get (conj common-interceptors (about-su-15 database)) :route-name :about-r]
    ["/newpost" :get (conj common-interceptors (newpost-su-15 database)) :route-name :newpost-r]
    ["/createpost" :post (conj common-interceptors (createpost-su-15 database)) :route-name :createpost-r]
    ["/addsample" :post (conj common-interceptors (addsample-su-15 database)) :route-name :addsample-r]
    ["/post/:postid" :get (conj common-interceptors (getpost-su-15 database)) :route-name :getpost-r]
    ["/edit/:postid" :get (conj common-interceptors (editpage-su-15 database)) :route-name :editpost-r]
    ["/submitedit/:postid" :post (conj common-interceptors (submitedit-su-15 database)) :route-name :submitedit-r]
    ["/delete/:postid" :get (conj common-interceptors (delete-su-15 database)) :route-name :delete-r]})
