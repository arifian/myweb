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
                [:li [:a {:href "/newpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
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
                [:li [:a {:href "/newpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li {:class "active"} [:a {:href "/about"} "About"]]]]]

             [:div {:class "jumbotron text-center"}
              [:h1 "Lorem Ipsum"]
              [:p "Lorem ipsum dolor sit amet, consectetur adipiscing elit!"]]
             [:div {:class "container"}
              [:p (:satu aboutstring)]
              [:p (:dua aboutstring)]
              [:p (:tiga aboutstring)]]]]))

(defn not-found-html []
  (hc/html [:html
            [:head
             [:title "404"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/postlist"} [:span {:class "glyphicon glyphicon-list"}] " Posts"]]
                [:li [:a {:href "/newpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "jumbotron text-center"}
              [:h1 "404"]
              [:p "Move along, these aren't the page we are looking for."]]]]))

(defn postlist-html [postlist]
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
                [:li [:a {:href "/newpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container"}
              (if (empty? postlist)
                [:div {:class "text-center"} "No Post Yet!" [:br][:br]]
                [:div {:class "row"}
                 (foo/post-list (into (sorted-map) postlist))])]]]))

(defn newpost-html []
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
                [:li {:class "active"} [:a {:href "/newpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container"}
              [:form {:action "/createpost" :method "post" :id "input-form"}
               [:div {:class "form-group"}
                [:label {:for "title"} "Title:"]
                [:input {:type "text" :class "form-control" :id "title" :name "title" :required ""}]]
               [:div {:class "form-group"}
                [:label {:for "content"} "Content:"]
                [:textarea {:class "form-control" :rows "5" :id "content" :name "content" :required ""}]]
               [:button {:type "submit" :class "btn btn-primary"} "Submit"]]
              [:div
               [:form {:action "/addsample" :method "post" :id "input-form"}
                [:button {:type "submit" :class "btn btn-primary"} "Add Samples"]]]]]]))


(defn getpost-html [post postid postkey]
  (hc/html [:html
            [:head
             [:title "Get Post"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/postlist"} [:span {:class "glyphicon glyphicon-list"}] " Posts List"]]
                [:li {:class "active"} [:a {:href "/newpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container":align "center"}
              [:h1 (str (:title (postid post)))]
              (str (:content (postid post)))[:br][:br]
              [:a {:href (str "/edit/" postkey)} [:button {:class "btn btn-info" :type "button"} "Edit"]] "    "
              [:a {:href (str "/delete/" postkey)} [:button {:class "btn btn-danger" :type "button"} "Delete"]]]]]))

(defn editpage-html [post postid postkey]
  (hc/html [:html
            [:head
             [:title "Edit Post"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/postlist"} [:span {:class "glyphicon glyphicon-list"}] " Posts List"]]
                [:li {:class "active"} [:a {:href "/newpost"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container"}
              [:form {:action (str "/submitedit/" postkey) :method "post" :id "input-form"}
               [:div {:class "form-group"}
                [:label {:for "title"} "Title:"]
                [:textarea {:class "form-control" :rows "1" :id "title" :name "title" :required ""} (str (:title (postid post)))]]
               [:div {:class "form-group"}
                [:label {:for "content"} "Content:"]
                [:textarea {:class "form-control" :rows "5" :id "content" :name "content" :required ""} (str (:content (postid post)))]]
               [:button {:type "submit" :class "btn btn-primary"} "Confirm Edit"]]]]]))


