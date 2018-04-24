(ns mission-board.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [selmer.parser :refer [render-file cache-off! set-resource-path!]]
            [grape.core :refer [read-resource create-resource]]
            [grape.rest.route :refer [handler-builder]]
            [grape.hooks.core :refer [hooks]]
            [grape.store :refer [map->MongoDataSource]]
            [monger.core :as mg]
            [monger.collection :as mc]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.resource :refer [wrap-resource]]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [mission-board.models.account :refer [AccountResource]]
            [mission-board.models.mission :refer [MissionResource]]
            [mission-board.models.company :refer [CompanyResource]]
            [mission-board.models.application :refer [ApplicationResource]]
            [mission-board.models.contributor :refer [ContributorResource]])
  (:gen-class))

(cache-off!)
(set-resource-path! (clojure.java.io/resource "templates"))

(def db (mg/get-db (mg/connect {:host "mongodb"}) "missionboard"))

(def deps
  {:config {}
   :hooks hooks
   :store (map->MongoDataSource {:db db})
   :resources-registry {:accounts AccountResource
                        :companies CompanyResource
                        :contributors ContributorResource
                        :applications ApplicationResource
                        :missions MissionResource}})

(defn missions-handler []
  (let [{companies :_documents} (read-resource deps CompanyResource {} {})]
    (render-file "missions.html" {:companies companies})))

(defn fixtures-handler []
  (let [account (create-resource deps AccountResource {} {:user_id "linkedin|ElGXQNQ2ij" :email "cyprien.pannier@gmail.com" :name "Cyprien Pannier"})
        quidli (create-resource deps CompanyResource {} {:name "Quidli" :account (:_id account)})
        skylight (create-resource deps CompanyResource {} {:name "Skylight" :account (:_id account)})
        mission1 (create-resource deps MissionResource {} {:status "published" :equity_percent 2 :keywords ["software development"] :account (:_id account) :company (:_id quidli) :company_name (:name quidli) :title "Build User Friendly web interface to replace current preQuid distribution Gsheet" :description "Backend mission..."})
        mission2 (create-resource deps MissionResource {} {:status "published" :equity_percent 1 :keywords ["design"] :account (:_id account) :company (:_id quidli) :company_name (:name quidli) :title "Create Quidli visual identity" :description "Graphist mission..."})
        mission3 (create-resource deps MissionResource {} {:status "published" :equity_percent 5 :keywords ["electronic"] :account (:_id account) :company (:_id skylight) :company_name (:name skylight) :title "Make super sweet VR glasses" :description "Technician mission..."})]
    (println "fixtures have been inserted")))

(defroutes handler
  (GET "/" [] (render-file "home.html" {}))
  (GET "/missions" [] (missions-handler))
  (POST "/fixtures" [] (fixtures-handler))
  (handler-builder deps "/api/")
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
