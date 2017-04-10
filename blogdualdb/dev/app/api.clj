(ns app.api
  (:require [io.pedestal.interceptor :refer [interceptor]]
            [app.template :as mold]
            [app.db :as atomdb]
            [app.datomic :as datomdb]))

#_(defonce database (atom nil))
#_(defonce post-numbering (atom 1))

#_(def samplepost {:1 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
                 :2 {:number 2 :title "Lorem Ipsum #2" :content (slurp "resources/postsampletext/sampletwo.txt")}
                 :3 {:number 3 :title "Lorem Ipsum #3" :content (slurp "resources/postsampletext/samplethree.txt")}})

(defonce dbtype (atom nil))

(defn initatom []
  (do
    (atomdb/create-dt '_)
    (atomdb/initschema atomdb/database)
    (atomdb/initcontent atomdb/database)))
(defn stopatom [] (atomdb/stop))

(defn initdatomic []
  (do
    (datomdb/scratch-conn)
    (datomdb/initschema datomdb/database)
    (datomdb/initcontent datomdb/database)))
(defn stopdatomic [] (datomdb/stop))

(defn initdb
  [dbkey] 
  (cond
    (= dbkey :da) (do (reset! dbtype :atom) (initatom))
    (= dbkey :dt) (do (reset! dbtype :datomic) (initdatomic))
    :else nil))

(defn stopdb []
  (cond
    (= @dbtype :atom) (do (stopatom) (reset! dbtype nil))
    (= @dbtype :datomic) (do (stopdatomic) (reset! dbtype :nil))
    :else nil))

(def db 
  (cond
    (= @dbtype :atom) atomdb/database
    (= @dbtype :datomic) datomdb/database
    :else nil))

(defn getallpost
  [db]
  (cond
    (= @dbtype :atom) (atomdb/getallpost db)
    (= @dbtype :datomic) (datomdb/getallpost db)
    :else nil))

(defn assocpost
  [db title content]
  (cond
    (= @dbtype :atom) (atomdb/assocpost db title content)
    (= @dbtype :datomic) (datomdb/assocpost db title content)
    :else nil))

(defn assocedit
  [db postkey postid title content]
  (cond
    (= @dbtype :atom) (atomdb/assocedit db postkey postid title content)
    (= @dbtype :datomic) (datomdb/assocedit db postkey postid title content)
    :else nil))

(defn dissocpost
  [db postkey]
  (cond
    (= @dbtype :atom) (atomdb/dissocpost db postkey)
    (= @dbtype :datomic) (datomdb/dissocpost db postkey)
    :else nil))

                                        ;==> interceptor

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
            response {:status 200 :body (mold/postlist-html (getallpost db))}]
        (assoc context :response response)))}))

(defn createpost-su-15 []
  (interceptor
   {:name :create-sukhoi
    :enter
    (fn [context]
      (let [title (:title (:form-params (:request context)))
            content (:content (:form-params (:request context)))]
        (assocpost db title content) 
        (assoc context :response {:status 200 :body (mold/postlist-html (getallpost db))})))}))

(defn newpost-su-15 []
  (interceptor
   {:name :newpost-sukhoi
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/newpost-html)}]
        (assoc context :response response)))}))

(defn getpost-su-15 []
  (interceptor
   {:name :getpost-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/getpost-html (getallpost db) postkey postid)}]
        (if (= (postkey (getallpost db)) nil)
          (assoc context :response {:status 404 :body (mold/not-found-html)})
          (assoc context :response response))))}))

(defn editpage-su-15 []
  (interceptor
   {:name :editpage-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)
            response {:status 200 :body (mold/editpage-html (getallpost db) postkey postid)}]
        (if (= (postkey (getallpost db)) nil)
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
        (assocedit db postkey postid title content)
        #_(swap! db assoc postkey {:number postid :title title :content content})
        (assoc context :response {:status 200 :body (mold/postlist-html (getallpost db))})))}))

(defn delete-su-15 []
  (interceptor
   {:name :delete-sukhoi
    :enter
    (fn [context]
      (let [postid (get-in context [:request :path-params :postid])
            postkey (keyword postid)]
        (dissocpost db postkey)
        #_(swap! db dissoc postkey)
        (assoc context :response {:status 200 :body (mold/postlist-html (getallpost db))})))}))
