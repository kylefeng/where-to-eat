(defproject where-to-eat "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.4"]
                 [ring-server "0.3.0"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [clj-time "0.6.0"]
                 [hiccup-bootstrap-3 "0.2.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler where-to-eat.handler/app
         :init where-to-eat.handler/init
         :destroy where-to-eat.handler/destroy}
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.0"]]}})
