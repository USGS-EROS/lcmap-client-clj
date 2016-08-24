(ns lcmap.client.compatibility.wmts
  (:require [lcmap.client.http :as http]
            [lcmap.client.compatibility :as compatibility]))

(def context (str compatibility/context "/wmts"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
