(ns where-to-eat.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "五道口到那吃？"]
     (include-css "/css/screen.css")]
    [:body body]))
