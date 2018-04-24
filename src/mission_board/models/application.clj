(ns mission-board.models.application
  (:require [schema.core :as s]
            [grape.schema :refer :all])
  (:import (org.bson.types ObjectId)))

(def ApplicationResource
  {:schema {(? :_id) (s/maybe ObjectId)
            :name s/Str
            :roles [s/Str]}
   :datasource {:source "applications"}
   :url "accounts"
   :operations #{:read}})
