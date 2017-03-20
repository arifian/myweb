(ns app.template
  (:require [app.function :as foo]
            [hiccup.core :as hc]))

(def aboutstring {:satu (slurp "resources/aboutsampletext/sampleone.txt")
                  :dua (slurp "resources/aboutsampletext/sampletwo.txt")
                  :tiga (slurp "resources/aboutsampletext/samplethree.txt")})

(defn landing-html []
  (hc/html [:html
            [:head
             [:title "Welcome!"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/postlist"} [:span {:class "glyphicon glyphicon-list"}] " Posts"]]
                [:li [:a {:href "/createpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]
             [:div {:class "jumbotron text-center"}
              [:h1 "Landing"]
              [:p "Welcome!"]]]]))

(defn about-html []
  (hc/html [:html
            [:head
             [:title "About"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/postlist"} [:span {:class "glyphicon glyphicon-list"}] " Posts"]]
                [:li [:a {:href "/createpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li {:class "active"} [:a {:href "/about"} "About"]]]]]
             [:div {:class "jumbotron text-center"}
              [:h1 "Lorem Ipsum"]
              [:p "Lorem ipsum dolor sit amet, consectetur adipiscing elit!"]]
             [:div {:class "container"}
              [:p (:satu aboutstring)]
              [:p (:dua aboutstring)]
              [:p (:tiga aboutstring)]]]]))

(defn postlist-html [post]
  (hc/html [:html
            [:head
             [:title "Post List"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li {:class "active"} [:a {:href "/postlist"} [:span {:class "glyphicon glyphicon-list"}] " Posts"]]
                [:li [:a {:href "/createpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]
             [:div {:class "container"}
              (if (empty? post)
                [:div {:class "text-center"} "No Post Yet!" [:br][:br]]
                [:div {:class "row"}
                 (foo/post-list (into (sorted-map) post))])]]]))

(defn createpost-html [post]
  (hc/html [:html
            [:head
             [:title "Create Post"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/postlist"} [:span {:class "glyphicon glyphicon-list"}] " Posts List"]]
                [:li {:class "active"} [:a {:href "/createpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]
             [:div {:class "container"}
              [:form
               [:div {:class "form-group"}
                [:label {:for "title"} "Title:"]
                [:input {:type "text" :class "form-control" :id "form"}]]
               [:div {:class "form-group"}
                [:label {:for "content"} "Content:"]
                [:textarea {:class "form-control" :rows "5" :id "content"}]]
               [:button {:type "submit" :class "btn btn-primary"} "Submit"]]]]]))

