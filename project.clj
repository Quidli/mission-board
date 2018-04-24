(defproject mission-board "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-devel "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [selmer "1.11.7"]
                 [grape "0.1.12-SNAPSHOT"]
                 [com.auth0/java-jwt "3.3.0"]
                 [compojure "1.6.1"]]
  :main ^:skip-aot mission-board.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
