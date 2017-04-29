(ns app.template.function)

(defn user-list [user]
  (for [key (keys user)]
    [:div {:class "col-sm-4"}
     [:p (str "User #" (:number (key user)))]
     [:a {:href (str "/user/" (:number (key user)))} [:h3 (str (:username (key user)))]]
     [:p (str (:password (key user)))[:br]]]))

(defn bootstrap []
  (for [cnt (range 4)]
    ([[:meta {:charset "utf-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
      [:link {:rel "stylesheet" :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"}]
      [:script {:src "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"}]
      [:script {:src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"}]] cnt)))

