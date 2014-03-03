(ns where-to-eat.routes.home
  (:require [compojure.core :refer :all]
            [where-to-eat.views.layout :as layout]
            [where-to-eat.models.db :as db]))


(def restaurants
  (db/all-restaurants))

(defn home [] 
  (layout/common
   [:h2 "吃饭地点列表："]
   [:ul
    (for [e restaurants]
      [:li e])]
   [:p "今天我们去 "
    [:b (nth restaurants (rand-int (count restaurants)))]
    " 吃饭"]))

(defroutes home-routes
  (GET "/" [] (home)))
