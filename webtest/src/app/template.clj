(ns app.template
  (:require [app.function :as foo]
            [hiccup.core :as hc]))

(defn home-html [post]
  (hc/html [:html
            [:head
             [:title "Home"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "BlogWeb"]]
               [:ul {:class "nav navbar-nav"}
                [:li {:class "active"} [:a {:href "/"} "Home"]]
                [:li [:a {:href "/"} "About"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "#"} [:span {:class "glyphicon glyphicon-user"}] " Sign Up"]]
                [:li [:a {:href "#"} [:span {:class "glyphicon glyphicon-log-in"}] " Login"]]]]]
             [:div {:class "jumbotron text-center"}
              [:h1 "Home Page"]
              [:p "Welcome to blog test page"]]
             [:div {:class "container"}
              (if (empty? post)
                [:div {:class "text-center"} "No Post Yet!" [:br][:br]]
                [:div {:class "row"}
                 (foo/post-list (into (sorted-map) post))])
              [:div {:class "text-center"}
               [:a {:href "/new"}
               [:button {:class "btn btn-primary" :type "button"} "Create New Post"]]]]]]))
