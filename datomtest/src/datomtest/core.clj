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
                   :db/id (d/tempid :db.part/user)}])

(defn add-post [title content] [{:post/id (d/squuid)
                                 :post/title title
                                 :post/content content
                                 :db/id (d/tempid :db.part/user)}])

@(d/transact conn blog-schema)

@(d/transact conn first-posts)

;@(d/transact conn (add-post "title" "content"))

(def db (d/db conn))

(defn getallpost []
  (d/q '[:find ?id ?title ?content
         :where
         [?e :post/id ?id]
         [?e :post/title ?title]
         [?e :post/content ?content]]
       (d/db conn)))

(getallpost)

(defn postnth [dt id]
  (d/q '[:find ?title ?content
         :in $ ?id
         :where
         [?e :post/id ?id]
         [?e :post/title ?title]
         [?e :post/content ?content]]
       (d/db (:conn dt))
       id))

(postnth {:conn conn} #uuid "58e74990-21ff-4a6e-a09e-0453c964761d")

(getallpost {:conn conn})
;; => #{[#uuid "58e74990-21ff-4a6e-a09e-0453c964761d" "Second best post in the world" "Ipsum Ingsun Dilema Labibade"] [#uuid "58e74990-a937-46a6-8f39-c0f8273b2de8" "Best post in the world" "Ipsum Ingsun Dilema"]}

#_(qnth )

#_(:post/content (d/entity db (ffirst q-result)))


