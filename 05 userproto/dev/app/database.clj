(ns app.database)

(defprotocol UserDatabase
  (-initschema [dt])
  (-getalluser [db])
  (-adduser [db username password])
  (-addsample [db])
  (-edituser [db userkey userid username password] )
  (-removeuser [db userkey])
  (-startdb [db])
  (-stopdb [db]))

(defn initschema [dt]
  (-initschema dt))

(defn getalluser [db]
  (-getalluser db))

(defn adduser [db username password]
 (-adduser db username password))

(defn addsample [db]
  (-addsample db))

(defn edituser [db userkey userid username password]
  (-edituser db userkey userid username password))

(defn removeuser [db userkey]
  (-removeuser db userkey))

(defn startdb [db]
  (-startdb db))

(defn stopdb [db]
  (-stopdb db))
