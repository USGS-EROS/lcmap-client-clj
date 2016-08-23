(ns lcmap.client.data.ccdc
  (:require [lcmap.client.http :as http]
            [lcmap.client.data :as data]))

(def context (str data/context "/ccdc"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
