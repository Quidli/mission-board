(ns mission-board.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [selmer.parser :refer [render-file cache-off! set-resource-path!]]
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
            [clojure.string :as s])
  (:gen-class))

(cache-off!)
(set-resource-path! (clojure.java.io/resource "templates"))

(def db (mg/get-db (mg/connect {:host "mongodb"}) "missionboard"))

(def deps
  {:config {}
   :hooks hooks
   :store (map->MongoDataSource {:db db})
   :resources-registry {:accounts AccountResource}})

(defn handler [req]
  {:status 200
   :body (render-file "home.html" {})})

(def config
  {:domain "quidli.eu.auth0.com"
   :issuer "https://quidli.eu.auth0.com/"
   :client-id "4tpn3VEn16Lph6XiBl98W2LaWbHVWTJ9"
   :signing-algorithm :hs256
   :client-secret "wlX5ZUxnYP1DgdAhq4KYH-gdFsmFM2JE8MOTayfNG3wLOLectCOBGVzjWplxI_1m"
   :scope "openid user_id name nickname email picture"
   :callback-path "/auth/callback"
   :error-redirect "/login"
   :success-redirect "/"
   :logout-handler "/logout"
   :logout-redirect "/"})

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
