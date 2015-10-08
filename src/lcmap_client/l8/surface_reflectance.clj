(ns lcmap-client.l8.surface-reflectance
  (:require [lcmap-client.http :as http]))

(defn get-resources []
  (http/get "/L1/T/Landsat/8/SurfaceReflectance"))

(defn get-tiles []
  (http/get "/L1/T/Landsat/8/SurfaceReflectance/tiles"))

(defn get-rod []
  (http/get "/L1/T/Landsat/8/SurfaceReflectance/rod"))

