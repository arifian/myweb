(ns app.api
  (:require [io.pedestal.interceptor :refer [interceptor]]
            [app.template :as mold]))

(def database (atom {}))

(def home-main
  (interceptor
   {:name :home-main
    :enter
    (fn [context]
      (let [request (:request context)
            response {:status 200 :body (mold/home-html @database)}]
        (assoc context :response response)))}))
