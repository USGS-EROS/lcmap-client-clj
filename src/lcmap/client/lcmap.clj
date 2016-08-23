(ns lcmap.client.lcmap
  (:require [lcmap.client.http :as http]))

(def context "/api")

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
