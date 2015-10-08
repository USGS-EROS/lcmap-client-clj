(ns lcmap-client.util
  (:require [clojure.string :as string])

(defn parse-point [param-str]
  (map #(Integer/parseInt %)
       (string/split param-str #",")))

(defn parse-extent [param-str]
  (map #(parse-point %)
       (string/split param-str #";")))
