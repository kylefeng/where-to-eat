(ns where-to-eat.models.db
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
     [:name "text"]
     [:gmt_create "timestamp default_timestamp"])
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
      (doall res))))

(defn todays-selection []
  (let [tommorrow (-> (t/today-at-midnight)
                      (t/plus (t/days 1))
                      .toDate)]
    (sql/with-connection
      db
      (sql/with-query-results res
        ["select * from selection_log where gmt_create < ? order by gmt_create desc" tommorrow]
        (first res)))))

(defn save-selection [name]
  (sql/with-connection
    db
    (sql/insert-values
     :selection_log
     [:name :gmt_create]
     [name (java.util.Date.)])))
