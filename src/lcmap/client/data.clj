(ns lcmap.client.data
  (:require [clojure.tools.logging :as log]
            [lcmap.client.http :as http]
            [lcmap.client.lcmap :as lcmap]
            [lcmap.client.util :as util]))

(def context (str lcmap/context "/data"))

(defn get-resources [& {keys [] :as args}]
  (http/get (str context "/")
            :lcmap-opts (or args {})))

(defn get-tiles [& {:keys [point extent time band] :as args}]
  (let [[point extent] (util/update-point-extent point extent)
        args (apply dissoc args [:point :extent :time :band])]
    (http/get (str context "/tiles")
              :lcmap-opts args
              :request {:query-params {:point point
                                       :extent extent
                                       :time time
                                       :band band}})))
