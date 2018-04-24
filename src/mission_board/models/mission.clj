(ns mission-board.models.mission
  (:require [schema.core :as s]
            [grape.schema :refer :all])
  (:import (org.bson.types ObjectId)
           (org.joda.time DateTime)))

(def MissionResource
  {:schema {(? :_id) (s/maybe ObjectId)
            :account (resource-embedded :accounts :account ObjectId)
            :title s/Str
            :status (s/enum "draft" "published" "closed")
            :equity_percent s/Num
            (? :keywords) (s/maybe [s/Str])
            (? :description) (s/maybe s/Str)
            (? :published_at) (s/maybe DateTime)
            (? :closed_at) (s/maybe DateTime)
            (? :statistics) (read-only
                              {:applicants s/Num})
            (? :_created) (read-only DateTime)
            (? :_updated) (read-only DateTime)}
   :datasource {:source "missions"}
   :url "missions"
   :operations #{:read}})
