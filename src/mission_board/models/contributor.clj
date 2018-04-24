(ns mission-board.models.contributor
  (:require [schema.core :as s]
            [grape.schema :refer :all])
  (:import (org.bson.types ObjectId)
           (org.joda.time DateTime)))

(def ContributorResource
  {:schema {(? :_id) (s/maybe ObjectId)
            :headline s/Str
            (? :description) (s/maybe s/Str)
            (? :keywords) (s/maybe [s/Str])
            (? :hours_per_week_availability) (s/maybe s/Int)}
   :datasource {:source "contributors"}
   :url "contributors"
   :operations #{:read}})
