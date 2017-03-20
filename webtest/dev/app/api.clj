(ns app.api
  (:require [io.pedestal.interceptor :refer [interceptor]]
            [app.template :as mold]))

(defonce database (atom nil))
(defonce post-numbering (atom 1))

(defn keymaker [num]
  (cond
   (= (count (str num)) 1) (keyword (str "000" num))
   (= (count (str num)) 2) (keyword (str "00" num))
   (= (count (str num)) 3) (keyword (str "0" num))
   (= (count (str num)) 4) (keyword (str num))))

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
   {:name :create-get-sukhoi
    :enter
    (fn [context]
      (let [title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))
            post-num @post-numbering
            post-num-key (keymaker post-num)]
        (swap! database assoc post-num-key {:number post-num :title title :content content})
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
        (swap! database merge samplepost)
        (assoc context :response {:status 200 :body (mold/postlist-html @database)})))}))
