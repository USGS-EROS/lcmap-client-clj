(ns lcmap.client.models.surface-reflectance
  (:require [lcmap.client.http :as http]
            [lcmap.client.models :as models]))

(def context (str models/context "/surface-reflectance"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
