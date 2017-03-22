(ns app.api
  (:require [io.pedestal.interceptor :refer [interceptor]]
            [app.template :as mold]
            [app.db :as db]))

#_(defonce database (atom nil))
#_(defonce post-numbering (atom 1))

#_(def samplepost {:1 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
                 :2 {:number 2 :title "Lorem Ipsum #2" :content (slurp "resources/postsampletext/sampletwo.txt")}
                 :3 {:number 3 :title "Lorem Ipsum #3" :content (slurp "resources/postsampletext/samplethree.txt")}})

(defn landing-su-15 []
  (interceptor
   {:name :about-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/landing-html)}]
        (assoc context :response response)))}))

(defn about-su-15 []
  (interceptor
   {:name :about-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/about-html)}]
        (assoc context :response response)))}))

(defn postlist-su-15 []
  (interceptor
   {:name :home-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/postlist-html (db/getdb))}]
        (assoc context :response response)))}))

(defn createpost-su-15 []
  (interceptor
   {:name :create-sukhoi
    :enter
    (fn [context]
      (let [title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))]
        (db/assocpost title content)
        (db/inc-post-numbering)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getdb))})))}))

(defn newpost-su-15 []
  (interceptor
   {:name :newpost-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/newpost-html)}]
        (assoc context :response response)))}))

(defn addsample-su-15 []
  (interceptor
   {:name :addsample-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)]
        (db/addsample)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getdb))})))}))

(defn getpost-su-15 []
  (interceptor
   {:name :getpost-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/getpost-html (db/getdb) postkey postid)}]
        (if (= (postkey (db/getdb)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn editpage-su-15 []
  (interceptor
   {:name :editpage-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/editpage-html (db/getdb) postkey postid)}]
        (if (= (postkey (db/getdb)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn submitedit-su-15 []
  (interceptor
   {:name :submitedit-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))
            postkey (keyword postid)]
        (db/assocedit postkey postid title content)
        #_(swap! database assoc postkey {:number postid :title title :content content})
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getdb))})))}))

(defn delete-su-15 []
  (interceptor
   {:name :delete-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)]
        (db/dissocpost postkey)
        #_(swap! database dissoc postkey)
        (assoc context :response {:status 200 :body (mold/postlist-html (db/getdb))})))}))

