(ns app.atom)

(def samplepost {:posts {:1 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
                         :2 {:number 2 :title "Lorem Ipsum #2" :content (slurp "resources/postsampletext/sampletwo.txt")}
                         :3 {:number 3 :title "Lorem Ipsum #3" :content (slurp "resources/postsampletext/samplethree.txt")}},
                 :post-numbering 4})

(defonce database (atom {:posts nil :post-numbering 1}))

(defn getallpost [db] (:posts @db))

(defn getcurrentpostnum [db] (get @db :post-numbering 1))

(defn inc-post-numbering [db] (swap! db update-in [:post-numbering] inc))

(defn dec-post-numbering [db] (swap! db update-in [:post-numbering] dec))

(defn addpost [db title content]
  (swap! db update-in [:posts]
         assoc (keyword (str (getcurrentpostnum db))) {:number (getcurrentpostnum db) :title title :content content})
  (inc-post-numbering db))

(defn addsample [db]
  (addpost db "Lorem Ipsum #1" (slurp "resources/postsampletext/sampleone.txt"))
  (addpost db "Lorem Ipsum #2" (slurp "resources/postsampletext/sampletwo.txt"))
  (addpost db "Lorem Ipsum #3" (slurp "resources/postsampletext/samplethree.txt")))

(defn editpost [db postkey postid title content]
  (swap! db update-in [:posts]
         assoc postkey {:number postid :title title :content content}))

(defn removepost [db postkey]
  (swap! db update-in [:posts] dissoc postkey)
  (dec-post-numbering db))

#_(defn addsample [db] 
  (swap! db assoc (keyword (str @post-numbering)) (:1 samplepost))
  (swap! getcurrentpostnum inc)
  (swap! db assoc (keyword (str @post-numbering)) (:2 samplepost))
  (swap! post-numbering inc)
  (swap! db assoc (keyword (str @post-numbering)) (:3 samplepost))
  (swap! post-numbering inc))
