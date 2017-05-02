(ns app.database.abstraction)

(defprotocol BlogDatabase
  #_(-createdb [name])
  (-initschema [dt])
  (-getallpost [db])
  (-addpost [db title content])
  (-addsample [db])
  (-editpost [db postkey postid title content])
  (-removepost [db postkey])
  (-startdb [db])
  (-stopdb [db]))
