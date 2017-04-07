(ns app.datomic
  (:require [datomic.api :as d]
            [clojure.tools.namespace.repl :refer [refresh]]))

(def db-uri-base "datomic:mem://mbe")

(defn scratch-conn
  "Create a connection to an anonymous, in-memory database."
  []
  (let [uri (str db-uri-base (d/squuid))]
    (d/delete-database uri)
    (d/create-database uri)
    (d/connect uri)))

(def conn (scratch-conn))

(def dt {:conn conn})

#_(def db (d/db conn))

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

#_(def schematic @(d/transact conn blog-schema))

(def first-posts
  [{:post/id (d/squuid)
    :post/title "Best post in the world"
    :post/content "Ipsum Ingsun Dilema"
    :db/id (d/tempid :db.part/user)}
   {:post/id (d/squuid)
    :post/title "Second best post in the world"
    :post/content "Ipsum Ingsun Dilema Labibade"
    :db/id (d/tempid :db.part/user)}])

(defn initcontent [dt] @(d/transact (:conn dt) first-posts))

(defn getallpost [dt]
  (d/q '[:find ?id ?title ?content
         :where
         [?e :post/id ?id]
         [?e :post/title ?title]
         [?e :post/content ?content]]
       (d/db (:conn dt))))

(defn scratchaddpost [title content]
  [{:post/id (d/squuid)
    :post/title title
    :post/content content
    :db/id (d/tempid :db.part/user)}])

(defn addpost [dt title content] @(d/transact (:conn dt) (scratchaddpost title content)))

(defn getallpost [db] nil)

(defn getcurrentpostnum [db] nil)

(defn inc-post-numbering [db] nil)

(defn assocpost [db title content] nil)

(defn assocedit [db postkey postid title content] nil)

(defn dissocpost [db postkey] nil)
