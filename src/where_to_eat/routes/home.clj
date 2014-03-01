(ns where-to-eat.routes.home
  (:require [compojure.core :refer :all]
            [where-to-eat.views.layout :as layout]))


(def restaurants
  (sorted-map
   0 :八月
   1 :乡绅
   2 :牛肉
   3 :麻辣烫
   4 :老娘舅
   5 :酸辣粉
   6 :巧哥湖南粉
   7 :喜士多
   8 :花溪牛肉粉
   9 :杭味面馆
   10 :蒸菜
   ))

(defn home [] 
  (layout/common
   [:h2 "吃饭地点列表："]
   [:ul
    (for [[k v] restaurants]
      [:li v])]
   [:p "今天我们去 "
    [:b (restaurants (rand-int (count restaurants)))]
    " 吃饭"]))

(defroutes home-routes
  (GET "/" [] (home)))
