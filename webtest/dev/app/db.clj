(ns app.db)

(def samplepost {:1 {:number 1 :title "Lorem Ipsum #1" :content (slurp "resources/postsampletext/sampleone.txt")}
                 :2 {:number 2 :title "Lorem Ipsum #2" :content (slurp "resources/postsampletext/sampletwo.txt")}
                 :3 {:number 3 :title "Lorem Ipsum #3" :content (slurp "resources/postsampletext/samplethree.txt")}})

(defonce database (atom nil))
(defonce post-numbering (atom 1))

(defn getdb [] @database)
(defn getcurrentpostnum [] @post-numbering)

(defn assocpost [title content]
  (swap! database assoc
   (keyword (str (getcurrentpostnum)))
   {:number (getcurrentpostnum) :title title :content content}))

(defn inc-post-numbering [] (swap! post-numbering inc))

(defn addsample [] 
  (swap! database assoc (keyword (str @post-numbering)) (:1 samplepost))
  (swap! post-numbering inc)
  (swap! database assoc (keyword (str @post-numbering)) (:2 samplepost))
  (swap! post-numbering inc)
  (swap! database assoc (keyword (str @post-numbering)) (:3 samplepost))
  (swap! post-numbering inc))

(defn assocedit [postkey postid title content]
  (swap! database assoc postkey {:number postid :title title :content content}))

(defn dissocpost [postkey]
  (swap! database dissoc postkey))
