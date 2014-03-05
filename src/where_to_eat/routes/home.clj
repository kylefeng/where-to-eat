(ns where-to-eat.routes.home
  (:use [hiccup.form]
        [hiccup.core]
        [hiccup.element]
        [ring.util.response]
        [clj-time.local]
        [clj-time.coerce])
  (:require [compojure.core :refer :all]
            [where-to-eat.views.layout :as layout]
            [where-to-eat.models.db :as db]
            [clj-time.format :refer [formatter]]
            [clj-time.core :refer [now today-at-midnight]]))

(defn home []
  (let [restaurants (db/all-restaurants)
        recent (db/recent-selections)
        s (if (> (count recent) 0) 
            (-> recent
                first
                :gmt_create
                from-long
                .toDateMidnight
                (= (today-at-midnight)))
            false)]
    (layout/common
     [:div.page-header
      [:h2 "今天是" (.print
                     (formatter "yyyy年MM月dd日") (local-now))]]
     [:h3 "吃饭地点列表："]
     [:div.row
      [:div.col-lg-3 [:ul
                      (for [e restaurants]
                        [:li e])]]
      [:div.col-lg-3 (image {:class "img-thumbnail"} "/images/logo.jpg")]]
     (form-to {:role "form"} [:post "/"]
              (if s
                [:p "决定了！今天我们就去 " [:span.label.label-success
                                             (:name (first recent))] "  吃饭！"]
                [:p "今天还没选的哦，亲！"])
              (when-let [last (second recent)]
                [:p "上次选的是："
                 [:span.label.label-default (:name last)] 
                 (-> last
                     :gmt_create
                     java.util.Date.
                     to-local-date-time
                     (.toString "yyyy-MM-dd HH:mm:ss")
                     (->> (format "（%s）")))])
              (if (not s)
                [:button.btn.btn-primary.btn-lg {:type "submit"}
                 [:span.glyphicon.glyphicon-hand-left] "走你"])))))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" []
        (let [restaurants (db/all-restaurants)]
          (db/make-selection restaurants)
          (redirect "/"))))
