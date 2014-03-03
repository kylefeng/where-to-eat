(ns where-to-eat.views.layout
  (:use [hiccup.bootstrap.page])
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "五道口到那吃？"]
     (include-bootstrap)]
    [:body body]))
