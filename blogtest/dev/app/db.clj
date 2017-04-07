(ns app.db)

(def samplepost {:1 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
                 :2 {:number 2 :title "Lorem Ipsum #2" :content (slurp "resources/postsampletext/sampletwo.txt")}
                 :3 {:number 3 :title "Lorem Ipsum #3" :content (slurp "resources/postsampletext/samplethree.txt")}})

(defonce database (atom {:posts nil :post-numbering 1}))

(defn getallpost [db] (:posts @db))

(defn getcurrentpostnum [db] (get @db :post-numbering 1))

(defn inc-post-numbering [db] (swap! db update-in [:post-numbering] inc))

(defn assocpost [db title content]
  (swap! db update-in [:posts]
         assoc (keyword (str (getcurrentpostnum db))) {:number (getcurrentpostnum db) :title title :content content})
  (inc-post-numbering db))

(defn assocedit [db postkey postid title content]
  (swap! db update-in [:posts]
         assoc postkey {:number postid :title title :content content}))

(defn dissocpost [db postkey]
  (swap! db update-in [:posts] dissoc postkey))

#_(defn addsample [db] 
  (swap! db assoc (keyword (str @post-numbering)) (:1 samplepost))
  (swap! getcurrentpostnum inc)
  (swap! db assoc (keyword (str @post-numbering)) (:2 samplepost))
  (swap! post-numbering inc)
  (swap! db assoc (keyword (str @post-numbering)) (:3 samplepost))
  (swap! post-numbering inc))
