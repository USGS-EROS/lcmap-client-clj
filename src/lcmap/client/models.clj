(ns lcmap.client.models
  (:require [lcmap.client.http :as http]
            [lcmap.client.lcmap :as lcmap]))

(def context (str lcmap/context "/models"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
