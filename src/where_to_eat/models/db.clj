(ns where-to-eat.models.db
  (:use [clj-time.local])
  (:require [clojure.java.jdbc :as sql]
            [clj-time.core :as t])
  (:import java.sql.DriverManager))

(def db {:classname "org.sqlite.JDBC"
         :subprotocol "sqlite"
         :subname "db.sq3"})

(defn create-where-to-eat-tables []
  (sql/with-connection 
    db
    (sql/create-table 
     :restaurants
     [:id "integer primary key autoincrement"]
     [:name "text"])
    (sql/create-table
     :selection_log
     [:id "integer primary key autoincrement"]
     [:name "text"]
     [:gmt_create "timestamp default_timestamp"]
     )
    (sql/do-commands "create index timestamp_idx on selection_log (gmt_create)")))


(defn all-restaurants []
  (sql/with-connection
    db
    (sql/with-query-results res
      ["select * from restaurants"]
      (for [record (doall res)]
        (:name record)))))

(defn save-selection [name]
  (sql/with-connection
    db
    (sql/insert-values
     :selection_log
     [:name :gmt_create]
     [name (.toDate (local-now))])))

(defn make-selection
  [restaurants]
  (let [c (count restaurants)
        r (rand-int c)
        new-selection (nth restaurants r)]
    (save-selection new-selection)
    new-selection))

(defn recent-selections []
  (let [tommorrow (-> (t/today-at-midnight)
                      (t/plus (t/days 1))
                      .toDate)]
    (sql/with-connection
      db
      (sql/with-query-results res
        ["select * from selection_log where gmt_create < ? order by gmt_create desc" tommorrow]
        (take 2 (doall res))))))


