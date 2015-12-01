(ns lcmap-client.data.sample-os-process
  (:require [clojure.tools.logging :as log]
            [lcmap-client.http :as http]
            [lcmap-client.data :as data]
            [lcmap-client.lcmap :as lcmap]))

(def context (str data/context "/sample/os-process"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))
