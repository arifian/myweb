(ns app.api
  (:require [io.pedestal.interceptor :refer [interceptor]]
            [app.template :as mold]))

(defonce database (atom nil))

(def samplepost {:001 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
                 :002 {:number 2 :title "Lorem Ipsum #2" :content (slurp "resources/postsampletext/sampletwo.txt")}
                 :003 {:number 3 :title "Lorem Ipsum #3" :content (slurp "resources/postsampletext/samplethree.txt")}})

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
            response {:status 200 :body (mold/postlist-html @database)}]
        (assoc context :response response)))}))

(defn createpost-su-15-get []
  (interceptor
   {:name :create-get-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/createpost-html @database)}]
        (assoc context :response response)))}))

(defn createpost-su-15-post []
  (interceptor
   {:name :create-post-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/createpost-html @database)}]
        (assoc context :response response)))}))
