(ns mission-board.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [selmer.parser :refer [render-file cache-off! set-resource-path!]]
            [grape.core :refer [read-resource]]
            [grape.rest.route :refer [handler-builder]]
            [grape.hooks.core :refer [hooks]]
            [grape.store :refer [map->MongoDataSource]]
            [monger.core :as mg]
            [monger.collection :as mc]
            [mission-board.models.account :refer [AccountResource]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.resource :refer [wrap-resource]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [mission-board.models.mission :refer [MissionResource]])
  (:gen-class))

(cache-off!)
(set-resource-path! (clojure.java.io/resource "templates"))

(def db (mg/get-db (mg/connect {:host "mongodb"}) "missionboard"))

(def deps
  {:config {}
   :hooks hooks
   :store (map->MongoDataSource {:db db})
   :resources-registry {:accounts AccountResource
                        :missions MissionResource}})

(defn missions-handler []
  (let [{missions :_documents count :_count} (read-resource deps MissionResource {} {:relations {:account {}} :opts {:count? true}})]
    (render-file "missions.html" {:missions missions :count count})))

(defroutes handler
  (GET "/" [] (render-file "home.html" {}))
  (GET "/missions" [] (missions-handler))
  (route/not-found "<h1>Oops, page not found</h1>"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run-jetty (-> handler
                 (wrap-resource "public")
                 wrap-json-response
                 (wrap-json-body {:keywords? true})
                 wrap-params
                 wrap-cookies
                 wrap-reload)
             {:port 3000}))
