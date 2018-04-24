(ns mission-board.models.company
  (:require [schema.core :as s]
            [grape.schema :refer :all])
  (:import (org.bson.types ObjectId)
           (org.joda.time DateTime)))

(def CompanyResource
  {:schema {(? :_id) (s/maybe ObjectId)
            :account ObjectId
            :name s/Str
            (? :logo) s/Str
            (? :description) (s/maybe s/Str)
            (? :statistics) (read-only
                              {:missions s/Num})
            (? :_created) (read-only DateTime)
            (? :_updated) (read-only DateTime)}
   :datasource {:source "companies"}
   :url "companies"
   :operations #{:read}})
