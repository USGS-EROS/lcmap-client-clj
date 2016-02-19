(ns lcmap.client.compatibility.wmts
  (:require [clojure.tools.logging :as log]
            [lcmap.client.http :as http]
            [lcmap.client.compatibility :as compatibility]
            [lcmap.client.lcmap :as lcmap]))

(def context (str compatibility/context "/wmts"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
