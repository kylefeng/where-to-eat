(ns where-to-eat.views.layout
  (:use [hiccup.core]
        [hiccup.page])
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "五道口到那吃？"]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
     (include-css "/css/bootstrap.css")]
    [:body.container body
     (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js")
     (include-js "/js/bootstrap.js")]))
