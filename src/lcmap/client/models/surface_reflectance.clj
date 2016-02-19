(ns lcmap.client.models.surface-reflectance
  (:require [clojure.tools.logging :as log]
            [lcmap.client.http :as http]
            [lcmap.client.models :as models]
            [lcmap.client.util :as util]))

(def context (str models/context "/surface-reflectance"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))

