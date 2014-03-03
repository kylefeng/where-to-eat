(ns where-to-eat.handler
  (:use [hiccup.bootstrap.middleware])
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [where-to-eat.routes.home :refer [home-routes]]))

(defn init []
  (println "where-to-eat is starting"))

(defn destroy []
  (println "where-to-eat is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes home-routes app-routes)
      (handler/site)
      (wrap-bootstrap-resources)
      (wrap-base-url)))


