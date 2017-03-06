(ns routing
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.http.body-params :as bd]
            [io.pedestal.interceptor :refer [interceptor]]
            [hiccup.core :as hc]))
;foo

(def database (atom {}))

(defn post-list [post]
  (for [key (keys post)]
    [:div {:class "col-sm-4"}
     [:p (str "Post #" (:number (key post)))]
     [:a {:href (str "/post/" (:number (key post)))} [:h3 (str (:title (key post)))]]
     [:p (str (:content (key post)))[:hr][:br][:br]]]))

(defn bootstrap []
  (for [cnt (range 4)]
    ([[:meta {:charset "utf-8"}]
      [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
      [:link {:rel "stylesheet" :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"}]
      [:script {:src "https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"}]
      [:script {:src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"}]] cnt)))

;;html

(defn home-html [post]
  (hc/html [:html
            [:head
             [:title "Home"]
             (bootstrap)]
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
                 (post-list (into (sorted-map) post))])
              [:div {:class "text-center"}
               [:a {:href "/new"}
               [:button {:class "btn btn-primary" :type "button"} "Create New Post"]]]]]]))

;interceptor

(def home-main
  (interceptor
   {:name :home-main
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (home-html @database)}]
        (assoc context :response response)))}))

(def routes
  (route/expand-routes
   #{["/" :get [(bd/body-params) http/html-body home-main]]
     #_["/new" :get [(bd/body-params) http/html-body new-post]]
     #_["/ok" :post [(bd/body-params) http/html-body create-post]]
     #_["/okedit/:postid" :post [(bd/body-params) http/html-body edit-post-ok]]
     #_["/post/:postid" :get [(bd/body-params) http/html-body view-post]]
     #_["/edit/:postid" :get [(bd/body-params) http/html-body edit-post]]
     #_["/delete/:postid" :get [(bd/body-params) http/html-body delete-post]]
     #_["/deleteok/:postid" :post [(bd/body-params) http/html-body delete-post-ok]]}))
