(ns aoc-2021.core
  (:require [clojure.java.io :as io]))

(defn input-reader [path]
  (io/reader (io/resource path)))
