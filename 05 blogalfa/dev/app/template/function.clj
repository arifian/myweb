(ns app.template.function)

(defn post-list [post]
  (for [key (keys post)]
    [:div {:class "col-sm-4"}
     [:p (str "Post #" (:number (key post)))]
     [:a {:href (str "/post/" (:number (key post)))} [:h3 (str (:title (key post)))]]
     [:p (str (:content (key post)))[:br]]]))

(defn bootstrap []
  (for [cnt (range 4)]
    ([[:meta {:charset "utf-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
      [:link {:rel "stylesheet" :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"}]
      [:script {:src "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"}]
      [:script {:src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"}]] cnt)))

