(ns lcmap.client.data.ccdc
  (:require [clojure.tools.logging :as log]
            [lcmap.client.http :as http]
            [lcmap.client.data :as data]
            [lcmap.client.lcmap :as lcmap]))

(def context (str data/context "/ccdc"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
