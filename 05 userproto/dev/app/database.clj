(ns app.database)

(defprotocol BlogDatabase
  #_(-createdb [name])
  (-initschema [dt])
  (-getallpost [db])
  (-addpost [db title content])
  (-addsample [db])
  (-editpost [db postkey postid title content] )
  (-removepost [db postkey])
  (-startdb [db])
  (-stopdb [db]))


#_(defn createdb [name] )

(defn initschema [dt]
  (-initschema dt))

(defn getallpost [db]
  (-getallpost db))

(defn addpost [db title content]
 (-addpost db title content))

(defn addsample [db]
  (-addsample db))

(defn editpost [db postkey postid title content]
  (-editpost db postkey postid title content))

(defn removepost [db postkey]
  (-removepost db postkey))

(defn startdb [db]
  (-startdb db))

(defn stopdb [db]
  (-stopdb db))
