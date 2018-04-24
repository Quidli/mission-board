(ns mission-board.models.account
  (:require [schema.core :as s]
            [grape.schema :refer :all])
  (:import (org.bson.types ObjectId)
           (org.joda.time DateTime)))

(def AccountResource
  {:schema {(? :_id) (s/maybe ObjectId)
            :user_id s/Str
            :email s/Str
            (? :nickname) (s/maybe s/Str)
            (? :name) (s/maybe s/Str)
            (? :picture) (s/maybe s/Str)
            (? :family_name) (s/maybe s/Str)
            (? :given_name) (s/maybe s/Str)
            (? :contributor_profile) (s/maybe ObjectId)}
   :datasource {:source "accounts"}
   :url "accounts"
   :operations #{:read :create :update}})
