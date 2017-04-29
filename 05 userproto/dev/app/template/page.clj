(ns app.template.page
  (:require [app.template.function :as foo]
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
                [:li [:a {:href "/userlist"} [:span {:class "glyphicon glyphicon-list"}] " Users"]]
                [:li [:a {:href "/newuser"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
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
                [:li [:a {:href "/userlist"} [:span {:class "glyphicon glyphicon-list"}] " Users"]]
                [:li [:a {:href "/newuser"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
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
                [:li [:a {:href "/userlist"} [:span {:class "glyphicon glyphicon-list"}] " Users"]]
                [:li [:a {:href "/newuser"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "jumbotron text-center"}
              [:h1 "404"]
              [:p "Move along, these aren't the page we are looking for."]]]]))

(defn userlist-html [userlist]
  (hc/html [:html
            [:head
             [:title "User List"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li {:class "active"} [:a {:href "/userlist"} [:span {:class "glyphicon glyphicon-list"}] " Users"]]
                [:li [:a {:href "/newuser"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container"}
              (if (empty? userlist)
                [:div {:class "text-center"} "No User Yet!" [:br][:br]
                 [:form {:action "/addsample" :method "post" :id "input-form"}
                  [:button {:type "submit" :class "btn btn-primary"} "Add Samples"]]]
                [:div {:class "row"}
                 (foo/user-list (into (sorted-map) userlist))])]]]))

(defn newuser-html []
  (hc/html [:html
            [:head
             [:title "Create User"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/userlist"} [:span {:class "glyphicon glyphicon-list"}] " Users List"]]
                [:li {:class "active"} [:a {:href "/newuser"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container"}
              [:form {:action "/createuser" :method "post" :id "input-form"}
               [:div {:class "form-group"}
                [:label {:for "username"} "Username:"]
                [:input {:type "text" :class "form-control" :id "username" :name "username" :required ""}]]
               [:div {:class "form-group"}
                [:label {:for "password"} "Password:"]
                [:textarea {:class "form-control" :rows "5" :id "password" :name "password" :required ""}]]
               [:button {:type "submit" :class "btn btn-primary"} "Submit"]]]]]))


(defn getuser-html [user userid userkey]
  (hc/html [:html
            [:head
             [:title "Get User"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/userlist"} [:span {:class "glyphicon glyphicon-list"}] " Users List"]]
                [:li {:class "active"} [:a {:href "/newuser"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container":align "center"}
              [:h1 (str (:username (userid user)))]
              (str (:password (userid user)))[:br][:br]
              [:a {:href (str "/edit/" userkey)} [:button {:class "btn btn-info" :type "button"} "Edit"]] "    "
              [:a {:href (str "/delete/" userkey)} [:button {:class "btn btn-danger" :type "button"} "Delete"]]]]]))

(defn editpage-html [user userid userkey]
  (hc/html [:html
            [:head
             [:title "Edit User"]
             (foo/bootstrap)]
            [:body
             [:nav {:class "navbar navbar-inverse"}
              [:div {:class "container-fluid"}
               [:div {:class "navbar-header"}
                [:a {:class "navbar-brand" :href "/"} "Blog"]]
               [:ul {:class "nav navbar-nav"}
                [:li [:a {:href "/userlist"} [:span {:class "glyphicon glyphicon-list"}] " Users List"]]
                [:li {:class "active"} [:a {:href "/newuser"} [:span {:class "glyphicon glyphicon-pencil"}] " Create"]]]
               [:ul {:class "nav navbar-nav navbar-right"}
                [:li [:a {:href "/about"} "About"]]]]]

             [:div {:class "container"}
              [:form {:action (str "/submitedit/" userkey) :method "post" :id "input-form"}
               [:div {:class "form-group"}
                [:label {:for "username"} "Username:"]
                [:textarea {:class "form-control" :rows "1" :id "username" :name "username" :required ""} (str (:username (userid user)))]]
               [:div {:class "form-group"}
                [:label {:for "password"} "Password:"]
                [:textarea {:class "form-control" :rows "5" :id "password" :name "password" :required ""} (str (:password (userid user)))]]
               [:button {:type "submit" :class "btn btn-primary"} "Confirm Edit"]]]]]))


