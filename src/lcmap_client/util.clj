(ns lcmap-client.util
  (:require [clojure.string :as string])

(defn parse-point [param-str]
  (transduce
    (map #(Integer/parseInt %))
    conj
    (string/split param-str #",")))

(defn parse-extent [param-str]
  (map #(parse-point %)
       (string/split param-str #";")))
