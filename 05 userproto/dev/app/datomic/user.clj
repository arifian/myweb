(ns app.datomic.user
  (:require [datomic.api :as d]
            [app.database :as adb]
            [com.stuartsierra.component :as component]))

(defn- default-uuid-reader [form]
  {:pre [(string? form)]}
  (java.util.UUID/fromString form))

(defrecord DatomicDatabase [conn])

(def blog-schema 
  [{:db/ident :post/id
    :db/valueType :db.type/uuid
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one
    :db/doc "Post's ID"
    :db/id (d/tempid :db.part/db)
    :db.install/_attribute :db.part/db}
   
   {:db/ident :post/title
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "Post's Title"
    :db/id (d/tempid :db.part/db)
    :db.install/_attribute :db.part/db}
   
   {:db/ident :post/content
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "Post's Content"
    :db/id (d/tempid :db.part/db)
    :db.install/_attribute :db.part/db}])

(defn initschema [dt] @(d/transact (:conn dt) blog-schema))

(def first-posts
  [{:post/id (d/squuid)
    :post/title "Ipsum One"
    :post/content (slurp "resources/postsampletext/sampleone.txt")
    :db/id (d/tempid :db.part/user)}
   {:post/id (d/squuid)
    :post/title "Ipsum Two"
    :post/content (slurp "resources/postsampletext/sampletwo.txt")
    :db/id (d/tempid :db.part/user)}
   {:post/id (d/squuid)
    :post/title "Ipsum Three"
    :post/content (slurp "resources/postsampletext/samplethree.txt")
    :db/id (d/tempid :db.part/user)}])

(defn addsample [dt] @(d/transact (:conn dt) first-posts))

(defn q-allpost [dt]
  (d/q '[:find ?id ?title ?content
         :where
         [?e :post/id ?id]
         [?e :post/title ?title]
         [?e :post/content ?content]]
       (d/db (:conn dt))))

(defn transformit [post]
  (assoc {} (keyword (str (post 0))) {:number (post 0) :title (post 1) :content (post 2)}))

(defn getallpost [dt] (into (sorted-map) (map transformit (q-allpost dt))))

(defn scratchaddpost [title content]
  [{:post/id (d/squuid)
    :post/title title
    :post/content content
    :db/id (d/tempid :db.part/user)}])

(defn addpost [dt title content]
  @(d/transact (:conn dt) (scratchaddpost title content)))

(defn q-post-single
  [dt squuid]
  "return entity id"
  (((apply vector
         (d/q '[:find ?e
                :in $ ?squuid
                :where
                [?e :post/id ?squuid]]
              (d/db (:conn dt))
              squuid))0)0))

(defn scratcheditpost [squuid title content]
  [{:post/id squuid
    :post/title title
    :post/content content
    :db/id (d/tempid :db.part/user)}])

(defn editpost [dt postkey postid title content]
  @(d/transact (:conn dt) (scratcheditpost (default-uuid-reader postid) title content)))


(defn scratchremovepost [dt squuid]
  [[:db.fn/retractEntity (q-post-single dt squuid)]])

(defn removepost [dt postid]
  @(d/transact (:conn dt) (scratchremovepost dt (default-uuid-reader postid))))

(extend-type DatomicDatabase
  adb/BlogDatabase
  (-initschema [dt]
    (initschema dt))
  (-getallpost [db]
    (getallpost db))
  (-addpost [db title content]
    (addpost db title content))
  (-addsample [db]
    (addsample db))
  (-editpost [db postkey postid title content]
    (editpost db postkey postid title content))
  (-removepost [db postkey]
    (removepost db postkey))
  (-startdb [db]
    (let [dt (assoc db :conn
                    (let [uri (str "datomic:mem://" name)]
                      #_(d/delete-database uri)
                      (d/create-database uri)
                      (d/connect uri)))]
      (initschema dt)
      dt))
  (-stopdb [db]
    (let [conn (:conn db)]
      (d/release conn)
      db))
  component/Lifecycle
  (start [db]
    (let [dt (assoc db :conn
                    (let [uri (str "datomic:mem://" name)]
                      #_(d/delete-database uri)
                      (d/create-database uri)
                      (d/connect uri)))]
      (initschema dt)
      dt))
  (stop [db]
    (let [conn (:conn db)]
      (d/release conn)
      db)))

(defn createdb [name]
  (DatomicDatabase. nil))

(defn scratch-conn
  "Create a connection to an anonymous, in-memory database."
  []
  (let [uri (str (createdb))]
    #_(d/delete-database uri)
    (d/create-database uri)
    (d/connect uri)))

