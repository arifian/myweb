(ns app.db)

(def samplepost {:posts {:1 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
                         :2 {:number 2 :title "Lorem Ipsum #2" :content (slurp "resources/postsampletext/sampletwo.txt")}
                         :3 {:number 3 :title "Lorem Ipsum #3" :content (slurp "resources/postsampletext/samplethree.txt")}},
                 :post-numbering 4})

(defonce database (atom {:posts nil :post-numbering 1}))

(defn create-dt [_] (atom {:posts nil :post-numbering 1}))

(defn initschema [_] _)

(defn initcontent [dt] (reset! dt samplepost))

(defn getallpost
"#{{:id #uuid \"58e74990-21ff-4a6e-a09e-0453c964761d\"
       :title \"Second best post in the world\"
       :content \"Ipsum Ingsun Dilema Labibade\"}
  {:id #uuid \"58e74990-a937-46a6-8f39-c0f8273b2de8\"
     :title \"Best post in the world\"
     :content \"Ipsum Ingsun Dilema\"}}"
  [db] (:posts @db))

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
