(ns lcmap.client.util
  (:require [clojure.string :as string])
  (:refer-clojure :exclude [slurp]))

(def debug {:debug true
            :debug-body true
            :throw-entire-message? true})

(defn str->point [param-str]
  (transduce
    (map #?(:clj  #(Integer/parseInt %)
            :cljs #(js/parseInt %)))
    conj
    (string/split param-str #",")))

(defn str->extent [param-str]
  (map str->point
       (string/split param-str #";")))

(defn point->str [[x y :as point]]
  (string/join "," point))

(defn extent->str [[[x1 y1 :as p1] [x2 y2 :as p2]]]
  (string/join ";" (map point->str
                        (list p1 p2))))

(defn update-point-extent [point extent]
  (if extent
    ["" (extent->str extent)]
    [(point->str point) ""]))

(defn deep-merge [map-1 map-2]
  (merge-with #'merge (into map-1 map-2)))

(defn remove-nil [map-data]
  (into {}
        (filter
          (comp not nil? val)
          map-data)))

(defn in?
  "This function returns true if the provided seqenuce contains the given
  elment."
  [seq elm]
  (some #(= elm %) seq))

(defmacro slurp [file]
  "A slurp that can be used from cljs."
  (clojure.core/slurp file))
