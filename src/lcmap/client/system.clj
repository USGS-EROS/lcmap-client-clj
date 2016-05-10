(ns lcmap.client.system
  (:require [clojure.tools.logging :as log]
            [lcmap.client.http :as http]
            [lcmap.client.lcmap :as lcmap]))

(def context (str lcmap/context "/system"))
(def metrics (str context "/metrics"))
(def reference (str context "/reference"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
