(ns app.datomic.user
  (:require [datomic.api :as d]
            [app.database :as adb]
            [com.stuartsierra.component :as component]))

(defn- default-uuid-reader [form]
  {:pre [(string? form)]}
  (java.util.UUID/fromString form))

(defrecord DatomicUser [conn])

(def user-schema 
  [{:db/ident :user/id
    :db/valueType :db.type/uuid
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one
    :db/doc "User's ID"
    :db/id (d/tempid :db.part/db)
    :db.install/_attribute :db.part/db}
   
   {:db/ident :user/username
    :db/valueType :db.type/string
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one
    :db/doc "User Username"
    :db/id (d/tempid :db.part/db)
    :db.install/_attribute :db.part/db}
   
   {:db/ident :user/password
    :db/valueType :db.type/string
    :db/cardinality :db.cardinality/one
    :db/doc "Password"
    :db/id (d/tempid :db.part/db)
    :db.install/_attribute :db.part/db}])

(defn initschema [dt] @(d/transact (:conn dt) user-schema))

(def user-sample
  [{:user/id (d/squuid)
    :user/username "admin"
    :user/password "admin"
    :db/id (d/tempid :db.part/user)}
   {:user/id (d/squuid)
    :user/username "admin2"
    :user/password "admin2"
    :db/id (d/tempid :db.part/user)}
   {:user/id (d/squuid)
    :user/username "SirJohn"
    :user/password "thebrave"
    :db/id (d/tempid :db.part/user)}
   ])

(defn addsample [dt] @(d/transact (:conn dt) user-sample))

(defn q-alluser [dt]
  (d/q '[:find ?id ?username ?password
         :where
         [?e :user/id ?id]
         [?e :user/username ?username]
         [?e :user/password ?password]]
       (d/db (:conn dt))))

(defn transformit [user]
  (assoc {} (keyword (str (user 0))) {:id (user 0) :username (user 1) :password (user 2)}))

(defn getalluser [dt] (into (sorted-map) (map transformit (q-alluser dt))))

(defn scratchadduser [username password]
  [{:user/id (d/squuid)
    :user/username username
    :user/password password
    :db/id (d/tempid :db.part/user)}])

(defn adduser [dt username password]
  @(d/transact (:conn dt) (scratchadduser username password)))

(defn q-user-single
  [dt squuid]
  "return entity id"
  (((apply vector
         (d/q '[:find ?e
                :in $ ?squuid
                :where
                [?e :user/id ?squuid]]
              (d/db (:conn dt))
              squuid))0)0))

(defn scratchedituser [squuid username password]
  [{:user/id squuid
    :user/username username
    :user/password password
    :db/id (d/tempid :db.part/user)}])

(defn edituser [dt userkey userid username password]
  @(d/transact (:conn dt) (scratchedituser (default-uuid-reader userid) username password)))


(defn scratchremoveuser [dt squuid]
  [[:db.fn/retractEntity (q-user-single dt squuid)]])

(defn removeuser [dt userid]
  @(d/transact (:conn dt) (scratchremoveuser dt (default-uuid-reader userid))))

(extend-type DatomicUser
  adb/UserDatabase
  (-initschema [dt]
    (initschema dt))
  (-getalluser [db]
    (getalluser db))
  (-adduser [db title content]
    (adduser db title content))
  (-addsample [db]
    (addsample db))
  (-edituser [db userkey userid title content]
    (edituser db userkey userid title content))
  (-removeuser [db userkey]
    (removeuser db userkey))
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
                      (d/create-database uri)
                      (d/connect uri)))]
      (initschema dt)
      dt))
  (stop [db]
    (let [conn (:conn db)]
      (d/release conn)
      db)))

(defn createdb [name]
  (DatomicUser. nil))

(defn scratch-conn
  "Create a connection to an anonymous, in-memory database."
  []
  (let [uri (str (createdb))]
    (d/create-database uri)
    (d/connect uri)))

