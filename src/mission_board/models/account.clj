(ns mission-board.models.account
  (:require [schema.core :as s])
  (:import (org.bson.types ObjectId)))

(def AccountResource
  {:schema {:_id ObjectId
            :name s/Str}
   :datasource {:source "accounts"}
   :url "accounts"
   :operations #{:read}})
