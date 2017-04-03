(ns datomtest.core
  (:require [datomic.api :as d]
            [clojure.tools.namespace.repl :refer [refresh]]))

(def db-uri-base "datomic:mem://")

(defn scratch-conn
  "Create a connection to an anonymous, in-memory database."
  []
  (let [uri (str db-uri-base (d/squuid))]
    (d/delete-database uri)
    (d/create-database uri)
    (d/connect uri)))

(def conn (scratch-conn))

(def blog-schema [{:db/ident :post/id
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

(def first-posts [{:post/id (d/squuid)
                   :post/title "Best post in the world"
                   :post/content "Ipsum Ingsun Dilema"
                   :db/id (d/tempid :db.part/user)}
                  {:post/id (d/squuid)
                   :post/title "Second best post in the world"
                   :post/content "Ipsum Ingsun Dilema Labibade"
                   :db/id (d/tempid :db.part/user)}
                  ])

@(d/transact conn blog-schema)

@(d/transact conn first-posts)

(def db (d/db conn))

(def q-result (d/q '[:find ?e
                     :where
                     [?e :post/title _]]
                   db))

;q-result

(defn testfirsttitle [] (:post/title (d/entity db (ffirst q-result))))

(defn testfirstcontent [] (:post/title (d/entity db (ffirst q-result))))

;@(d/transact conn [[:db/add
;                    (d/tempid :db.part/user)
;                    :db/doc
;                    "Hello world"]])
