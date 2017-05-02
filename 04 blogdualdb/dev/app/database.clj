(ns app.database
  (:require [app.database.abstraction :as abstr]))

;database function calls

#_(defn createdb [name]
    (abstr/-createdb name))

(defn initschema [dt]
  (abstr/-initschema dt))

(defn getallpost [db]
  (abstr/-getallpost db))

(defn addpost [db title content]
 (abstr/-addpost db title content))

(defn addsample [db]
  (abstr/-addsample db))

(defn editpost [db postkey postid title content]
  (abstr/-editpost db postkey postid title content))

(defn removepost [db postkey]
  (abstr/-removepost db postkey))

(defn startdb [db]
  (abstr/-startdb db))

(defn stopdb [db]
  (abstr/-stopdb db))
