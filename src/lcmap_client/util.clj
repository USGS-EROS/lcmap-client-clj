(ns lcmap-client.util
  (:require [clojure.string :as string]))

(def debug {:debug true
            :debug-body true
            :throw-entire-message? true})

(defn str->point [param-str]
  (transduce
    (map #(Integer/parseInt %))
    conj
    (string/split param-str #",")))

(defn str->extent [param-str]
  (map #(str->point %)
       (string/split param-str #";")))

(defn point->str [[x y :as point]]
  (string/join "," point))

(defn extent->str [[[x1 y1 :as p1] [x2 y2 :as p2]]]
  (string/join ";" (map #(point->str %)
                        (list p1 p2))))

(defn update-point-extent [point extent]
  (if extent
    ["" (extent->str extent)]
    [(point->str point) ""]))
