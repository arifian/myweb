(ns app.api
  (:require [io.pedestal.interceptor :refer [interceptor]]
            [app.template :as mold]))

(defonce database (atom nil))
(defonce post-numbering (atom 1))

(def samplepost {:1 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
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
            response {:status 200 :body (mold/postlist-html @database)}]
        (assoc context :response response)))}))

(defn createpost-su-15 []
  (interceptor
   {:name :create-sukhoi
    :enter
    (fn [context]
      (let [title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))
            post-num (keyword (str @post-numbering))]
        (swap! database assoc post-num {:number @post-numbering :title title :content content})
        (swap! post-numbering inc)
        (assoc context :response {:status 200 :body (mold/postlist-html @database)})))}))

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
        #_(swap! database merge samplepost)
        (swap! database assoc (keyword (str @post-numbering)) (:1 samplepost))
        (swap! post-numbering inc)
        (swap! database assoc (keyword (str @post-numbering)) (:2 samplepost))
        (swap! post-numbering inc)
        (swap! database assoc (keyword (str @post-numbering)) (:3 samplepost))
        (swap! post-numbering inc)
        (assoc context :response {:status 200 :body (mold/postlist-html @database)})))}))

(defn getpost-su-15 []
  (interceptor
   {:name :getpost-post
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/getpost-html @database postkey postid)}]
        (if (= (postkey @database) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn editpage-su-15 []
  (interceptor
   {:name :editpage-post
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/editpage-html @database postkey postid)}]
        (if (= (postkey @database) nil)
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
        (swap! database assoc postkey {:number postid :title title :content content})
        (assoc context :response {:status 200 :body (mold/postlist-html @database)})))}))

(defn delete-su-15 []
  (interceptor
   {:name :delete-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)]
        (swap! database dissoc postkey)
        (assoc context :response {:status 200 :body (mold/postlist-html @database)})))}))

#_(conj {} (find {:a "a" :b "b"} :a))
