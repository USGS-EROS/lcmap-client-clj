(ns lcmap-client.l8.surface-reflectance
  (:require [lcmap-client.http :as http]
            [lcmap-client.util :as util]
            [lcmap-client.l8.core :as l8]))

(def context (str l8/context "/SurfaceReflectance"))

(defn get-resources []
  (http/get (str context "/")))

(defn get-tiles [& {:keys [point extent time band]}]
  (let [[point extent] (util/update-point-extent point extent)]
    (http/get (str context "/tiles")
              {:query-params {:point point
                              :extent extent
                              :time time
                              :band band}})))

(defn get-rod [& {:keys [point time band]}]
  (http/get (str context "/rod")
            {:query-params {:point (util/point->str point)
                            :time time
                            :band band}}))
